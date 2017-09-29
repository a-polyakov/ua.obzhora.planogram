package planograma.servlet.rack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.EStateRack;
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
 * изменение состояния стеллажа в зале на утвержден
 * Date: 15.01.13
 * Time: 9:50
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_SET_STATE_IN_SECTOR_A)
public class RackSetStateInSectorA extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_SET_STATE_IN_SECTOR_A;

	private RackStateModel rackStateModel;
	private RackStateInSectorModel rackStateInSectorModel;
	private UserModel userModel;
	private SecurityModel securityModel;

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
		final RackStateInSector rackStateInSector = rackStateInSectorModel.select(userContext, code_rack);
		boolean canSetStateInSectorA =
				// стеллаж в состоянии черновик
				rackStateInSector.getState_rack() == EStateRack.D
						&&
						// я являюсь редактором стеллажа
						(rackStateInSector.getUser_draft() == userModel.getCodeUser(userContext)
								// есть право к принудительному утверждению стеллажа
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_IN_SECTOR_SET_A)
								// есть доступ к глобальному утверждению стеллажей зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A));
		if (canSetStateInSectorA) {
			rackStateModel.changestate(userContext, code_rack, EStateRack.A, null);
			commit(userContext);
		}
		jsonObject.addProperty("canSetStateInSectorA", canSetStateInSectorA);
		return jsonObject;
	}

}
