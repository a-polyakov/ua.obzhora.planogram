package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.data.RackWares;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
import planograma.model.RackShelfModel;
import planograma.model.RackWaresModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Date: 06.05.12
 * Time: 2:13
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_WARES_PLACEMENT_EDIT)
public class WaresEdit extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_WARES_PLACEMENT_EDIT;

	private static final Logger LOG = Logger.getLogger(WaresEdit.class);

	private RackModel rackModel;
	private RackShelfModel rackShelfModel;
	private RackWaresModel rackWaresModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
		rackWaresModel = RackWaresModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();

		final JsonObject jsonObject = new JsonObject();

		final Rack rack = rackModel.select(getUserContext(session), code_rack);
		jsonObject.add("rack", rack.toJsonObject());

		final JsonArray rackShelfListJson = new JsonArray();
		final List<RackShelf> rackShelfList = rackShelfModel.list(getUserContext(session), code_rack);
		for (final RackShelf item : rackShelfList) {
			rackShelfListJson.add(item.toJsonObject());
		}
		jsonObject.add("rackShelfList", rackShelfListJson);

		final JsonArray rackWaresListJson = new JsonArray();
		final List<RackWares> rackWaresList = rackWaresModel.list(getUserContext(session), code_rack);
		for (final RackWares item : rackWaresList) {
			rackWaresListJson.add(item.toJsonObject());
		}
		jsonObject.add("rackWaresList", rackWaresListJson);
		// инициализация корзины
		JsonObject basket = (JsonObject) session.getAttribute(SessionConst.SESSION_BASKET);
		if (basket == null)
			basket = new JsonObject();
		jsonObject.add("basket", basket);
		return jsonObject;
	}
}
