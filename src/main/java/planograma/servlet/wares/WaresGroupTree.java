package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.data.WaresGroup;
import planograma.exception.UnauthorizedException;
import planograma.model.WaresGroupModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Date: 27.04.12
 * Time: 4:41
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_WARES_GROUP_TREE)
public class WaresGroupTree extends AbstractAction {

	public static final String URL = UrlConst.URL_WARES_GROUP_TREE;

	private WaresGroupModel waresGroupModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		waresGroupModel = WaresGroupModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final List<WaresGroup> list = waresGroupModel.tree(getUserContext(session));
		for (final WaresGroup item : list) {
			jsonArray.add(item.toJsonObject());
		}
		jsonObject.add("waresGroupTree", jsonArray);
		return jsonObject;
	}
}
