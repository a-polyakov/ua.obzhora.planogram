package planograma.servlet.rack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.OptionsNameConst;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.*;
import planograma.data.geometry.Rack2D;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.exception.EntityFieldException;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.*;
import planograma.servlet.AbstractAction;
import planograma.servlet.validate.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сохранение стеллажа
 * Date: 21.03.12
 * Time: 23:58
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_SAVE)
public class RackSave extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_SAVE;

	private SectorModel sectorModel;
	private RackModel rackModel;
	private RackShelfModel rackShelfModel;
	private RackWaresModel rackWaresModel;
	private RackStateModel rackStateModel;
	private OptionsModel optionsModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
		rackWaresModel = RackWaresModel.getInstance();
		rackStateModel = RackStateModel.getInstance();
		optionsModel = OptionsModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_EDIT);
		final JsonObject rackJson = requestData.getAsJsonObject().getAsJsonObject("rack");
		final Rack editRack = new Rack(rackJson);
		final Rack2D editRack2D = new Rack2D(editRack);
		int editRackIndex = 0;
		final JsonArray shelfListJson = requestData.getAsJsonObject().getAsJsonArray("rackShelfList");
		final List<RackShelf2D<RackShelf>> itemList = new ArrayList<RackShelf2D<RackShelf>>(shelfListJson.size());
		for (int i = 0; i < shelfListJson.size(); i++) {
			final JsonObject rackShelfJson = shelfListJson.get(i).getAsJsonObject();
			final RackShelf rackShelf = new RackShelf(rackShelfJson);
			final RackShelf2D rackShelf2D = new RackShelf2D(rackShelf);
			itemList.add(rackShelf2D);
		}

		//	ПРОВЕРКИ
		final List<EntityFieldException> fieldExceptionList = new ArrayList<EntityFieldException>();
		// Проверка параметров стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_DIMENSION)) {
			RackMinDimensionsValidation.validate(fieldExceptionList, editRack, 0);
		}
		for (int i = 0; i < itemList.size(); i++) {
			final RackShelf2D rackShelf2D = itemList.get(i);
			// Проверка параметров полки стеллажа: высота, ширина, глубина должны быть больше 5мм
			if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_SHELF_DIMENSION)) {
				RackShelfMinDimensionsValidation.validate(fieldExceptionList, rackShelf2D.getRackShelf(), i);
			}
		}
		// Проверка: полка не может выходить за пределы стеллажа
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_SHELF_OUT_OF_RACK)) {
			RackShelfOutsideRackValidation.validate(fieldExceptionList, editRack, itemList);
		}

		// зал
		Sector sector = sectorModel.select(userContext, editRack.getCode_sector());
		// все стеллажи зала
		final List<Rack> rackList = rackModel.list(userContext, editRack.getCode_sector());
		final List<Rack2D> rack2DList = new ArrayList<Rack2D>(rackList.size());
		for (int i = 0; i < rackList.size(); i++) {
			final Rack rack = rackList.get(i);
			if (rack.getCode_rack().equals(editRack.getCode_rack())) {
				// заменить старый стеллаж новым
				rackList.set(i, editRack);
				editRackIndex = i;
				rack2DList.add(editRack2D);
			} else {
				// геометрическое представление стеллажей
				final Rack2D rack2D = new Rack2D(rack);
				rack2DList.add(rack2D);
			}
		}

		// стеллаж не может выходить за пределы зала(не сохраняется)
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_RACK_OUT_SECTOR)) {
			RackOutsideSectorValidation.validate(fieldExceptionList, sector, editRack2D, editRackIndex);
		}

		// стеллажи не могут пересекаться(не сохраняется)
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_RACK_INTERSECTION)) {
			RackIntersectValidation.validate(fieldExceptionList, rack2DList);
		}

		// товары стеллажа
		final List<RackWares> rackWaresList = rackWaresModel.list(userContext, editRack.getCode_rack());
		final List<RackWares2D> rackWares2DList = new ArrayList<RackWares2D>(rackWaresList.size());
		for (int i = 0; i < rackWaresList.size(); i++) {
			final RackWares rackWares = rackWaresList.get(i);
			final RackWares2D rackWares2D = new RackWares2D(rackWares);
			rackWares2DList.add(rackWares2D);
		}
		// полезная зона стеллажа не может стать меньше чем расположеные на нем товары
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_RACK_REAL_DIMENSION_LESS_THEN_WARES)) {
			RackOverflowWaresValidation.validate2D(fieldExceptionList, editRack, editRackIndex, rackWares2DList);
		}

		// полка не может пересекать товары(не сохраняется, выделяется одна из полок)
		if (optionsModel.getBoolean(userContext, OptionsNameConst.RACK_SAVE_SHELF_INTERSECT_WARES)) {
			RackShelfIntersectWaresValidation.validate(fieldExceptionList, itemList, rackWares2DList);
		}

		final JsonObject jsonObject = new JsonObject();
		if (fieldExceptionList.isEmpty()) {
			// update
			rackModel.update(userContext, editRack);
			List<RackShelf> oldItemList = rackShelfModel.list(userContext, editRack.getCode_rack());
			for (final RackShelf oldItem : oldItemList) {
				RackShelf findItem = null;
				// поиск среди сохраненых рание
				for (int i = 0; findItem == null && i < itemList.size(); i++) {
					final RackShelf currentItem = itemList.get(i).getRackShelf();
					if (oldItem.getCode_shelf().equals(currentItem.getCode_shelf())) {
						findItem = currentItem;
						// запись была обновлена
						rackShelfModel.update(userContext, findItem);
						itemList.remove(i);
						i--;
					}
				}
				if (findItem == null) {
					// запись была удалена
					rackShelfModel.delete(userContext, oldItem.getCode_shelf());
				}
			}
			for (final RackShelf2D<RackShelf> newItem : itemList) {
				// запись была добавлена
				newItem.getRackShelf().setCode_rack(editRack.getCode_rack());
				rackShelfModel.insert(userContext, newItem.getRackShelf());
			}

			// TODO только при изменениях
			rackStateModel.changestate(userContext, editRack.getCode_rack(), null, EStateRack.D);

			commit(userContext);
			jsonObject.addProperty(RackConst.CODE_RACK, editRack.getCode_rack());
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
