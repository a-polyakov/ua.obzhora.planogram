package planograma.servlet.racktemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.OptionsNameConst;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackTemplateConst;
import planograma.data.RackShelfTemplate;
import planograma.data.RackTemplate;
import planograma.data.UserContext;
import planograma.data.geometry.RackShelf2D;
import planograma.exception.EntityFieldException;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.OptionsModel;
import planograma.model.RackShelfTemplateModel;
import planograma.model.RackTemplateModel;
import planograma.servlet.AbstractAction;
import planograma.servlet.validate.RackMinDimensionsValidation;
import planograma.servlet.validate.RackShelfMinDimensionsValidation;
import planograma.servlet.validate.RackShelfOutsideRackValidation;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 21.03.12
 * Time: 23:58
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_TEMPLATE_SAVE)
public class RackTemplateSave extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_TEMPLATE_SAVE;

	private RackTemplateModel rackTemplateModel;
	private RackShelfTemplateModel rackShelfTemplateModel;
	private OptionsModel optionsModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackTemplateModel = RackTemplateModel.getInstance();
		rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
		optionsModel = OptionsModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_TEMPLATE_EDIT);
		final JsonObject rackTemplateJson = requestData.getAsJsonObject().getAsJsonObject("rackTemplate");
		final RackTemplate rackTemplate = new RackTemplate(rackTemplateJson);
		final JsonArray shelfListJson = requestData.getAsJsonObject().getAsJsonArray("rackShelfTemplateList");
		final List<RackShelf2D<RackShelfTemplate>> itemList = new ArrayList<RackShelf2D<RackShelfTemplate>>(shelfListJson.size());
		for (int i = 0; i < shelfListJson.size(); i++) {
			final JsonObject rackJson = shelfListJson.get(i).getAsJsonObject();
			final RackShelfTemplate shelf = new RackShelfTemplate(rackJson);
			final RackShelf2D<RackShelfTemplate> shelf2D = new RackShelf2D(shelf);
			itemList.add(shelf2D);
		}

		//	ПРОВЕРКИ
		List<EntityFieldException> fieldExceptionList = new ArrayList<EntityFieldException>();
		//	Проверка параметров шаблонного стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_TEMPLATE_SAVE_DIMENSION)) {
			RackMinDimensionsValidation.validate(fieldExceptionList, rackTemplate, 0);
		}

		for (int i = 0; i < itemList.size(); i++) {
			final RackShelf2D<RackShelfTemplate> shelf2D = itemList.get(i);
			final RackShelfTemplate newItem = shelf2D.getRackShelf();
			// Проверка параметров полки шаблонного стеллажа: высота, ширина глубина должны быть больше 5мм
			if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_TEMPLATE_SAVE_SHELF_DIMENSION)) {
				RackShelfMinDimensionsValidation.validate(fieldExceptionList, newItem, i);
			}
		}
		// Проверка: полка не может выходить за пределы стеллажа
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_TEMPLATE_SAVE_SHELF_OUT_OF_RACK)) {
			RackShelfOutsideRackValidation.validate(fieldExceptionList, rackTemplate, itemList);
		}

		final JsonObject jsonObject = new JsonObject();
		if (fieldExceptionList.isEmpty()) {
			if (rackTemplate.getCode_rack_template() == null) {
				// insert
				rackTemplateModel.insert(userContext, rackTemplate);
			} else {
				// update
				rackTemplateModel.update(userContext, rackTemplate);
				List<RackShelfTemplate> oldItemList = rackShelfTemplateModel.list(userContext, rackTemplate.getCode_rack_template());
				for (final RackShelfTemplate oldItem : oldItemList) {
					RackShelfTemplate findItem = null;
//				поиск среди сохраненых рание
					for (int i = 0; findItem == null && i < itemList.size(); i++) {
						final RackShelfTemplate currentItem = itemList.get(i).getRackShelf();
						if (oldItem.getCode_shelf_template().equals(currentItem.getCode_shelf_template())) {
							findItem = currentItem;
//						запись была обновлена
							rackShelfTemplateModel.update(userContext, findItem);
							itemList.remove(i);
							i--;
						}
					}
					if (findItem == null) {
//					запись была удалена
						rackShelfTemplateModel.delete(userContext, oldItem.getCode_shelf_template());
					}
				}
			}
			for (final RackShelf2D<RackShelfTemplate> newItem : itemList) {
//			запись была добавлена
				final RackShelfTemplate shelf = newItem.getRackShelf();
				shelf.setCode_rack_template(rackTemplate.getCode_rack_template());
				rackShelfTemplateModel.insert(userContext, shelf);
			}
			commit(userContext);
			jsonObject.addProperty(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
		} else {
			JsonArray jsonArray = new JsonArray();
			for (final EntityFieldException entityFieldException : fieldExceptionList) {
				jsonArray.add(entityFieldException.toJSON());
			}
			jsonObject.add("errorField", jsonArray);
		}
		return jsonObject;
	}
}
