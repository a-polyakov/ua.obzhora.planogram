package planograma.servlet.sector.history;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
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
@WebServlet("/" + UrlConst.URL_SECTOR_HISTORY_GET_MARK)
public class SectorHistoryGetMark extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_HISTORY_GET_MARK;

	private static final Logger LOG = Logger.getLogger(SectorHistoryGetMark.class);

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
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final List<Date> list = historyModel.getHistoryMarkForSector(getUserContext(session), code_sector);
		for (Date date : list) {
			jsonArray.add(new JsonPrimitive(date.getTime()));
		}
		jsonObject.add("sectorHistoryMark", jsonArray);
		return jsonObject;
	}
}
