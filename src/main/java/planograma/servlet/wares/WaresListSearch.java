package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.constant.data.ShopConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresGroupConst;
import planograma.data.wrapper.WaresWrapper;
import planograma.exception.UnauthorizedException;
import planograma.model.WaresModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Date: 01.05.12
 * Time: 19:44
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_WARES_LIST_SEARCH)
public class WaresListSearch extends AbstractAction {

	public static final String URL = UrlConst.URL_WARES_LIST_SEARCH;

	private WaresModel waresModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		waresModel = WaresModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject requestObject = requestData.getAsJsonObject();
		final String searchText = requestObject.get("searchText").getAsString();
		final String searchBy = requestObject.get("searchBy").getAsString();
		if (WaresConst.CODE_WARES.equals(searchBy)) {
			try {
				Integer.parseInt(searchText);
			} catch (Exception e) {
				throw new SQLException("Недопустимое значение кода: \"" + searchText + "\"");
			}
		}
		final int code_group;
		if (requestObject.has(WaresGroupConst.CODE_GROUP_WARES))
			code_group = requestObject.get(WaresGroupConst.CODE_GROUP_WARES).getAsInt();
		else
			code_group = 0;
		final int code_shop;
		if (requestObject.has(ShopConst.CODE_SHOP)) {
			code_shop = requestObject.get(ShopConst.CODE_SHOP).getAsInt();
		} else {
			code_shop = 0;
		}

		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final List<WaresWrapper> list = waresModel.search(getUserContext(session), searchText, searchBy, code_shop, code_group);
		for (final WaresWrapper item : list) {
			jsonArray.add(item.toJsonObject());
		}
		jsonObject.add("waresList", jsonArray);
		return jsonObject;
	}
}
