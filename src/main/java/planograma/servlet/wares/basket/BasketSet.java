package planograma.servlet.wares.basket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Date: 21.03.12
 * Time: 23:58
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_WARES_PLACEMENT_SET_BASKET)
public class BasketSet extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_WARES_PLACEMENT_SET_BASKET;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final JsonObject basket = requestData.getAsJsonObject();
		session.setAttribute(SessionConst.SESSION_BASKET, basket);
		return null;
	}
}
