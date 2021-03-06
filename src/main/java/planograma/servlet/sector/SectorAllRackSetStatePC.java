package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.*;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
import planograma.model.RackStateInSectorModel;
import planograma.model.RackStateModel;
import planograma.model.SecurityModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * изменение состояния всех стеллажей зала на утвержден
 * Date: 15.01.13
 * Time: 9:50
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_SECTOR_ALL_RACK_SET_STATE_PC)
public class SectorAllRackSetStatePC extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_ALL_RACK_SET_STATE_PC;

	private RackModel rackModel;
	private SecurityModel securityModel;
	private RackStateModel rackStateModel;
	private RackStateInSectorModel rackStateInSectorModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackModel = RackModel.getInstance();
		securityModel = SecurityModel.getInstance();
		rackStateModel = RackStateModel.getInstance();
		rackStateInSectorModel = RackStateInSectorModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final List<String> notAccessRackList = new ArrayList<String>();
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final UserContext userContext = getUserContext(session);
		final boolean accessAllRackInSectorSetStatePC = securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC);
		final boolean accessRackStateSetPC = securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_PC);
		final boolean accessRackStateInSectorSetPC = securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_IN_SECTOR_SET_PC);
		final List<Rack> rackList = rackModel.list(userContext, code_sector);
		for (final Rack rack : rackList) {
			final RackState rackState = rackStateModel.select(userContext, rack.getCode_rack());
			final RackStateInSector rackStateInSector = rackStateInSectorModel.select(userContext, rack.getCode_rack());
			boolean changeRackState = false;
			if (rackState.getState_rack() == EStateRack.A) {
				if (accessAllRackInSectorSetStatePC || accessRackStateSetPC) {
					changeRackState = true;
				} else {
					notAccessRackList.add(rack.getRack_barcode());
				}
			} else if (rackState.getState_rack() == EStateRack.D) {
				if (accessAllRackInSectorSetStatePC) {
					rackStateModel.changestate(userContext, rack.getCode_rack(), null, EStateRack.A);
					changeRackState = true;
				} else {
					notAccessRackList.add(rack.getRack_barcode());
				}
			}
			boolean changeRackStateInSector = false;
			if (rackStateInSector.getState_rack() == EStateRack.A) {
				if (accessAllRackInSectorSetStatePC || accessRackStateInSectorSetPC) {
					changeRackStateInSector = true;
				} else {
					if (!notAccessRackList.contains(rack.getCode_rack())) {
						notAccessRackList.add(rack.getRack_barcode());
					}
				}
			} else if (rackState.getState_rack() == EStateRack.D) {
				if (accessAllRackInSectorSetStatePC) {
					rackStateModel.changestate(userContext, rack.getCode_rack(), EStateRack.A, null);
					changeRackStateInSector = true;
				} else {
					if (!notAccessRackList.contains(rack.getCode_rack())) {
						notAccessRackList.add(rack.getRack_barcode());
					}
				}
			}
			if (changeRackState || changeRackStateInSector) {
				rackStateModel.changestate(userContext, rack.getCode_rack(), (changeRackStateInSector) ? EStateRack.A : null, (changeRackState) ? EStateRack.A : null);
			}
		}
		if (!notAccessRackList.isEmpty()) {
			JsonArray jsonArray = new JsonArray();
			for (String barcode : notAccessRackList) {
				jsonArray.add(new JsonPrimitive(barcode));
			}
			jsonObject.add("notAccessRackList", jsonArray);
		}
		commit(userContext);
		return jsonObject;
	}

}
