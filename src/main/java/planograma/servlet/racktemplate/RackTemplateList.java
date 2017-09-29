package planograma.servlet.racktemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.data.RackTemplate;
import planograma.exception.UnauthorizedException;
import planograma.model.RackTemplateModel;
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
@WebServlet("/" + UrlConst.URL_RACK_TEMPLATE_LIST)
public class RackTemplateList extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_TEMPLATE_LIST;

	private RackTemplateModel rackTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackTemplateModel = RackTemplateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final List<RackTemplate> list = rackTemplateModel.list(getUserContext(session));
		for (final RackTemplate rackTemplate : list) {
			jsonArray.add(rackTemplate.toJsonObject());
		}
		jsonObject.add("rackTemplateList", jsonArray);
		return jsonObject;
	}
}
