package planograma.servlet.rack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.EStateRack;
import planograma.data.RackState;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
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
 * изменение состояния стеллажа на утвержден
 * Date: 15.01.13
 * Time: 9:50
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_SET_STATE_A)
public class RackSetStateA extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_SET_STATE_A;

	private RackStateModel rackStateModel;
	private UserModel userModel;
	private SecurityModel securityModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackStateModel = RackStateModel.getInstance();
		userModel = UserModel.getInstance();
		securityModel = SecurityModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final JsonObject jsonObject = new JsonObject();
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();
		final UserContext userContext = getUserContext(session);
		final RackState rackState = rackStateModel.select(userContext, code_rack);
		boolean canSetStateA =
				// стеллаж в состоянии черновик
				rackState.getState_rack() == EStateRack.D
						&&
						// я являюсь редактором стеллажа
						(rackState.getUser_draft() == userModel.getCodeUser(userContext)
								// есть право к принудительному утверждению стеллажа
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_A)
								// есть доступ к глобальному утверждению стеллажей зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A));
		if (canSetStateA) {
			rackStateModel.changestate(userContext, code_rack, null, EStateRack.A);
			commit(userContext);
		}
		jsonObject.addProperty("canSetStateA", canSetStateA);
		return jsonObject;
	}

}
