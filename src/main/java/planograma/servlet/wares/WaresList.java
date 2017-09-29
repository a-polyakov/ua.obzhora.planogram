package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.constant.data.ShopConst;
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
@WebServlet("/" + UrlConst.URL_WARES_LIST)
public class WaresList extends AbstractAction {

	public static final String URL = UrlConst.URL_WARES_LIST;

	private WaresModel waresModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		waresModel = WaresModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject requestJsonObject = requestData.getAsJsonObject();
		final int code_group = requestJsonObject.get(WaresGroupConst.CODE_GROUP_WARES).getAsInt();
		final List<WaresWrapper> list;
		if (requestJsonObject.has(ShopConst.CODE_SHOP)) {
			final int code_shop = requestJsonObject.get(ShopConst.CODE_SHOP).getAsInt();
			list = waresModel.listForGroupAndShop(getUserContext(session), code_shop, code_group);
		} else {
			list = waresModel.list(getUserContext(session), code_group);
		}

		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		if (list != null) {
			for (final WaresWrapper item : list) {
				jsonArray.add(item.toJsonObject());
			}
		}
		jsonObject.add("waresList", jsonArray);
		return jsonObject;
	}
}
