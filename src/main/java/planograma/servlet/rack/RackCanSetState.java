package planograma.servlet.rack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.EStateRack;
import planograma.data.RackState;
import planograma.data.RackStateInSector;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
import planograma.model.RackStateInSectorModel;
import planograma.model.RackStateModel;
import planograma.model.SecurityModel;
import planograma.model.UserModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * проверка доступности состояний пользователю для указаного стеллажа
 * Date: 15.01.13
 * Time: 9:50
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_CAN_SET_STATE)
public class RackCanSetState extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_CAN_SET_STATE;

	private UserModel userModel;
	private SecurityModel securityModel;
	private RackStateModel rackStateModel;
	private RackStateInSectorModel rackStateInSectorModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userModel = UserModel.getInstance();
		securityModel = SecurityModel.getInstance();
		rackStateModel = RackStateModel.getInstance();
		rackStateInSectorModel = RackStateInSectorModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();
		final UserContext userContext = getUserContext(session);
		final RackState rackState = rackStateModel.select(userContext, code_rack);
		final RackStateInSector rackStateInSector = rackStateInSectorModel.select(userContext, code_rack);
		boolean canSetStateA =
				// стеллаж в состоянии черновик
				rackState.getState_rack() == EStateRack.D
						&&
						// и я являюсь редактором стеллажа
						(rackState.getUser_draft() == userModel.getCodeUser(userContext)
								// или есть право на пренудительное утверждение зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_A)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A));
		boolean canSetStateInSectorA =
				// стеллаж в состоянии черновик
				rackStateInSector.getState_rack() == EStateRack.D
						&&
						// и я являюсь редактором стеллажа
						(rackStateInSector.getUser_draft() == userModel.getCodeUser(userContext)
								// или есть право на пренудительное утверждение зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_IN_SECTOR_SET_A)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A));
		boolean canSetStatePC =
				// стеллаж в состоянии утвержден
				rackState.getState_rack() == EStateRack.A
						&&
						// и есть право на подтверждение выполнения стеллажа
						(securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_PC)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC));
		boolean canSetStateInSectorPC =
				// стеллаж в состоянии утвержден
				rackStateInSector.getState_rack() == EStateRack.A
						&&
						// и есть право на подтверждение выполнения стеллажа
						(securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_IN_SECTOR_SET_PC)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC));
		jsonObject.addProperty("rack_state", rackState.getState_rack().name());
		jsonObject.addProperty("rack_state_in_sector", rackStateInSector.getState_rack().name());
		jsonObject.addProperty("canSetStateA", canSetStateA);
		jsonObject.addProperty("canSetStateInSectorA", canSetStateInSectorA);
		jsonObject.addProperty("canSetStatePC", canSetStatePC);
		jsonObject.addProperty("canSetStateInSectorPC", canSetStateInSectorPC);
		return jsonObject;
	}

}
