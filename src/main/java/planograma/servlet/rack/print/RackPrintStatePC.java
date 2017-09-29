package planograma.servlet.rack.print;

import planograma.constant.UrlConst;
import planograma.data.*;
import planograma.model.history.RackHModel;
import planograma.model.history.RackShelfHModel;
import planograma.model.history.RackStateHModel;
import planograma.model.history.RackWaresHModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Date: 08.06.12
 * Time: 4:25
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_RACK_PRINT_PC + "*")
public class RackPrintStatePC extends AbstractRackPrint {
	public static final String URL = UrlConst.URL_RACK_PRINT_PC;

	private RackHModel rackHModel;
	private RackShelfHModel rackShelfHModel;
	private RackWaresHModel rackWaresHModel;
	private RackStateHModel rackStateHModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackHModel = RackHModel.getInstance();
		rackShelfHModel = RackShelfHModel.getInstance();
		rackWaresHModel = RackWaresHModel.getInstance();
		rackStateHModel = RackStateHModel.getInstance();
	}

	@Override
	public Date getDate(UserContext userContext, int code_rack) throws SQLException {
		RackState rackState = rackStateHModel.selectPC(userContext, code_rack);
		Date date = null;
		if (rackState != null)
			date = rackState.getDate_complete();
		return date;
	}

	@Override
	public Rack getRack(UserContext userContext, int code_rack, final Date date) throws SQLException {
		return rackHModel.select(userContext, code_rack, date);
	}

	@Override
	public List<RackShelf> getRackShelfList(UserContext userContext, int code_rack, final Date date) throws SQLException {
		return rackShelfHModel.list(userContext, code_rack, date);
	}

	@Override
	public List<RackWares> getRackWaresList(UserContext userContext, int code_rack, final Date date) throws SQLException {
		return rackWaresHModel.list(userContext, code_rack, date);
	}
}
