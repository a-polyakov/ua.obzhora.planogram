package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.constant.data.WaresConst;
import planograma.exception.UnauthorizedException;
import planograma.model.SectorFindWaresModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Date: 17.01.13
 * Time: 14:40
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_SECTOR_FIND_WARES)
public class SectorFindRackContainsWares extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_FIND_WARES;

	private SectorFindWaresModel sectorFindWaresModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorFindWaresModel = SectorFindWaresModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final int code_wares = requestData.getAsJsonObject().get(WaresConst.CODE_WARES).getAsInt();

		final List<String> rackList = sectorFindWaresModel.findRackInSectorContainsWares(getUserContext(session), code_sector, code_wares);
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		for (String barcode : rackList) {
			jsonArray.add(new JsonPrimitive(barcode));
		}
		jsonObject.add("findBarcodeRackList", jsonArray);

		return jsonObject;
	}
}
