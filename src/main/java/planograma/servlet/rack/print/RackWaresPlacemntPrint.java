package planograma.servlet.rack.print;

import planograma.constant.UrlConst;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.data.RackWares;
import planograma.data.UserContext;
import planograma.model.RackModel;
import planograma.model.RackShelfModel;
import planograma.model.RackWaresModel;

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
@WebServlet("/" + UrlConst.URL_RACK_PRINT + "*")
public class RackWaresPlacemntPrint extends AbstractRackPrint {
	public static final String URL = UrlConst.URL_RACK_PRINT;

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
	public Date getDate(UserContext userContext, int code_rack) throws SQLException {
		Date date = null;
		Rack rack = rackModel.select(userContext, code_rack);
		if (rack != null)
			date = rack.getDate_update();
		return date;
	}

	@Override
	public Rack getRack(UserContext userContext, int code_rack, final Date date) throws SQLException {
		return rackModel.select(userContext, code_rack);
	}

	@Override
	public List<RackShelf> getRackShelfList(UserContext userContext, int code_rack, final Date date) throws SQLException {
		return rackShelfModel.list(userContext, code_rack);
	}

	@Override
	public List<RackWares> getRackWaresList(UserContext userContext, int code_rack, final Date date) throws SQLException {
		return rackWaresModel.list(userContext, code_rack);
	}
}
