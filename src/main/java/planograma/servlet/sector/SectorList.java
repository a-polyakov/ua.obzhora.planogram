package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.Sector;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
import planograma.model.SectorModel;
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
@WebServlet("/" + UrlConst.URL_SECTOR_LIST)
public class SectorList extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_LIST;

	private SectorModel sectorModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final int code_shop = requestData.getAsJsonObject().get(SectorConst.CODE_SHOP).getAsInt();

		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final UserContext userContext = getUserContext(session);
		final List<Sector> list = sectorModel.list(userContext, code_shop);
		for (final Sector sector : list) {
			jsonArray.add(sector.toJsonObject());
		}
		jsonObject.add("sectorList", jsonArray);
		return jsonObject;
	}
}
