package planograma.servlet.racktemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackTemplateConst;
import planograma.data.RackShelfTemplate;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.RackShelfTemplateModel;
import planograma.model.RackTemplateModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Date: 26.02.12
 * Time: 2:18
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_TEMPLATE_REMOVE)
public class RackTemplateRemove extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_TEMPLATE_REMOVE;

	private RackTemplateModel rackTemplateModel;
	private RackShelfTemplateModel rackShelfTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackTemplateModel = RackTemplateModel.getInstance();
		rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_TEMPLATE_EDIT);
		final int code_rack_template = Integer.valueOf(requestData.getAsJsonObject().get(RackTemplateConst.CODE_RACK_TEMPLATE).getAsString());
		for (final RackShelfTemplate rackShelfTemplate : rackShelfTemplateModel.list(userContext, code_rack_template)) {
			rackShelfTemplateModel.delete(userContext, rackShelfTemplate.getCode_shelf_template());
		}
		rackTemplateModel.delete(userContext, code_rack_template);
		commit(userContext);
		return null;
	}
}
