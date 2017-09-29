package planograma.servlet.wares.buffer;

import com.google.gson.JsonArray;
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
@WebServlet("/" + UrlConst.URL_BUFFER_SET)
public class BufferSet extends AbstractAction {

	public static final String URL = UrlConst.URL_BUFFER_SET;

	public static final String NAME_COPY_OBJECT_LIST = "copyObjectList";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final JsonArray copyObjectList = requestData.getAsJsonObject().getAsJsonArray(NAME_COPY_OBJECT_LIST);
		session.setAttribute(SessionConst.SESSION_COPY_WARES_LIST, copyObjectList);
		return null;
	}
}
