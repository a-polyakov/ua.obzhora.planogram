package planograma.servlet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.exception.UnauthorizedException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 * Date: 17.02.12
 * Time: 6:52
 *
 * @author Alexandr Polyakov
 */
@WebServlet('/' + UrlConst.URL_LOGOUT)
public class Logout extends AbstractAction {

	public static final String URL = UrlConst.URL_LOGOUT;

	@Override
	protected JsonObject execute(final HttpSession session, final JsonElement requestData) throws UnauthorizedException {
		session.removeAttribute(SessionConst.SESSION_USER);
		session.invalidate();
		System.gc();
		return null;
	}
}
