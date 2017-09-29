package planograma.servlet.shop;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.data.Shop;
import planograma.exception.UnauthorizedException;
import planograma.model.ShopModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Date: 26.02.12
 * Time: 2:18
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_SHOP_LIST)
public class ShopList extends AbstractAction {

	public static final String URL = UrlConst.URL_SHOP_LIST;

	private ShopModel shopModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		shopModel = ShopModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final List<Shop> list = shopModel.list(getUserContext(session));
		for (final Shop shop : list) {
			jsonArray.add(shop.toJsonObject());
		}
		jsonObject.add("shopList", jsonArray);
		return jsonObject;
	}
}
