package planograma.servlet.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.ImageModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Date: 27.04.12
 * Time: 3:17
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_IMAGE_CLEAN_CACHE)
public class ImageCleanCache extends AbstractAction {
	public static final String URL = UrlConst.URL_IMAGE_CLEAN_CACHE;

	private ImageModel imageModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imageModel = ImageModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_IMAGE_CLEAN_CACHE);
		imageModel.clearCache();
		return null;
	}
}
