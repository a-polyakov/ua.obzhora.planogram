package planograma.servlet.rack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
import planograma.model.RackShelfModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Date: 21.03.12
 * Time: 20:38
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_EDIT)
public class RackEdit extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_EDIT;

	private RackModel rackModel;
	private RackShelfModel rackShelfModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();
		final Rack rack = rackModel.select(getUserContext(session), code_rack);
		final List<RackShelf> list = rackShelfModel.list(getUserContext(session), code_rack);
		for (final RackShelf item : list) {
			jsonArray.add(item.toJsonObject());
		}
		jsonObject.add("rack", rack.toJsonObject());
		jsonObject.add("rackShelfList", jsonArray);
		return jsonObject;
	}
}
