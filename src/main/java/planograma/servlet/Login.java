package planograma.servlet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.data.UserContext;
import planograma.exception.InvalidLoginOrPassword;
import planograma.model.StateAllModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 * Date: 17.02.12
 * Time: 6:52
 *
 * @author Alexandr Polyakov
 */
@WebServlet('/' + UrlConst.URL_LOGIN)
public class Login extends AbstractAction {

	public static final String URL = UrlConst.URL_LOGIN;

	private StateAllModel stateAllModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		stateAllModel = StateAllModel.getInstance();
	}

	@Override
	protected JsonObject execute(final HttpSession session, final JsonElement requestData) throws Exception {
		final String login = requestData.getAsJsonObject().get("login").getAsString();
		final String password = requestData.getAsJsonObject().get("password").getAsString();
		if (login == null || password == null || login.isEmpty() || password.isEmpty())
			throw InvalidLoginOrPassword.getInstance();
		session.removeAttribute(SessionConst.SESSION_USER);
		final UserContext userContext = new UserContext(login, password);
		checkAccess(userContext, SecurityConst.ACCESS_MODULE);
		session.setAttribute(SessionConst.SESSION_USER, userContext);
		stateAllModel.initEnum(userContext);
		return null;
	}
}
