package planograma.servlet.rack.history;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.exception.UnauthorizedException;
import planograma.model.history.HistoryModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Date: 18.01.13
 * Time: 8:59
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_HISTORY_GET_MARK)
public class RackHistoryGetMark extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_HISTORY_GET_MARK;

	private HistoryModel historyModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		historyModel = HistoryModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();
		final List<Date> list = historyModel.getHistoryMarkForRack(getUserContext(session), code_rack);
		for (Date date : list) {
			jsonArray.add(new JsonPrimitive(date.getTime()));
		}
		jsonObject.add("rackHistoryMark", jsonArray);
		return jsonObject;
	}
}
