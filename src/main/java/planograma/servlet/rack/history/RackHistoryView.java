package planograma.servlet.rack.history;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.data.RackWares;
import planograma.exception.UnauthorizedException;
import planograma.model.history.RackHModel;
import planograma.model.history.RackShelfHModel;
import planograma.model.history.RackWaresHModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Date: 06.05.12
 * Time: 2:13
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_HISTORY_VIEW)
public class RackHistoryView extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_HISTORY_VIEW;

	private RackHModel rackHModel;
	private RackShelfHModel rackShelfHModel;
	private RackWaresHModel rackWaresHModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackHModel = RackHModel.getInstance();
		rackShelfHModel = RackShelfHModel.getInstance();
		rackWaresHModel = RackWaresHModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();
		final Date date = new Date(requestData.getAsJsonObject().get("date").getAsLong());

		final JsonObject jsonObject = new JsonObject();

		final Rack rack = rackHModel.select(getUserContext(session), code_rack, date);
		jsonObject.add("rack", rack.toJsonObject());

		final JsonArray rackShelfListJson = new JsonArray();
		final List<RackShelf> rackShelfList = rackShelfHModel.list(getUserContext(session), code_rack, date);
		for (final RackShelf item : rackShelfList) {
			rackShelfListJson.add(item.toJsonObject());
		}
		jsonObject.add("rackShelfList", rackShelfListJson);

		final JsonArray rackWaresListJson = new JsonArray();
		final List<RackWares> rackWaresList = rackWaresHModel.list(getUserContext(session), code_rack, date);
		for (final RackWares item : rackWaresList) {
			rackWaresListJson.add(item.toJsonObject());
		}
		jsonObject.add("rackWaresList", rackWaresListJson);
		return jsonObject;
	}
}
