package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackShelfConst;
import planograma.data.RackShelf;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 28.03.12
 * Time: 20:15
 *
 * @author Alexandr Polyakov
 */
public class RackShelfModel {

	private static final Logger LOG = Logger.getLogger(RackShelfModel.class);

	private static final String Q_SELECT_FROM = "select " +
			" " + RackShelfConst.CODE_RACK + "," +
			" " + RackShelfConst.CODE_SHELF + "," +
			" " + RackShelfConst.X_COORD + "," +
			" " + RackShelfConst.Y_COORD + "," +
			" " + RackShelfConst.SHELF_HEIGHT + "," +
			" " + RackShelfConst.SHELF_WIDTH + "," +
			" " + RackShelfConst.SHELF_LENGTH + "," +
			" " + RackShelfConst.ANGLE + "," +
			" " + RackShelfConst.TYPE_SHELF + "," +
			" " + RackShelfConst.USER_INSERT + "," +
			" " + RackShelfConst.DATE_INSERT + "," +
			" " + RackShelfConst.USER_UPDATE + "," +
			" " + RackShelfConst.DATE_UPDATE + " " +
			"from ";

	private static final String Q_LIST = Q_SELECT_FROM + RackShelfConst.TABLE_NAME + " " +
			"where " + RackShelfConst.CODE_RACK + "=? ";

	public List<RackShelf> list(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackShelf> list = new ArrayList<RackShelf>();
		while (resultSet.next()) {
			final RackShelf item = new RackShelf(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return list;
	}

	private static final String Q_SELECT = Q_SELECT_FROM + RackShelfConst.TABLE_NAME + " " +
			"where " + RackShelfConst.CODE_SHELF + " = ?";

	public RackShelf select(final UserContext userContext, final int code_shelf) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_shelf);
		final ResultSet resultSet = ps.executeQuery();
		RackShelf rackShelf = null;
		if (resultSet.next()) {
			rackShelf = new RackShelf(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shelf:" + code_shelf + ")");
		return rackShelf;
	}

	private static final String Q_INSERT_UPDATE = "{call :new_code_shelf := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IUShelf(" +
			":mode, " +
			":" + RackShelfConst.CODE_RACK + ", " +
			":" + RackShelfConst.CODE_SHELF + ", " +
			":" + RackShelfConst.SHELF_HEIGHT + ", " +
			":" + RackShelfConst.SHELF_WIDTH + ", " +
			":" + RackShelfConst.SHELF_LENGTH + ", " +
			":" + RackShelfConst.ANGLE + ", " +
			":" + RackShelfConst.X_COORD + ", " +
			":" + RackShelfConst.Y_COORD + ", " +
			":" + RackShelfConst.TYPE_SHELF + ")}";

	public int insert(final UserContext userContext, final RackShelf rackShelf) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_shelf", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setInt(RackShelfConst.CODE_RACK, rackShelf.getCode_rack());
		callableStatement.setObject(RackShelfConst.CODE_SHELF, rackShelf.getCode_shelf());
		callableStatement.setInt(RackShelfConst.SHELF_HEIGHT, rackShelf.getShelf_height());
		callableStatement.setInt(RackShelfConst.SHELF_WIDTH, rackShelf.getShelf_width());
		callableStatement.setInt(RackShelfConst.SHELF_LENGTH, rackShelf.getShelf_length());
		callableStatement.setInt(RackShelfConst.ANGLE, rackShelf.getAngle());
		callableStatement.setInt(RackShelfConst.X_COORD, rackShelf.getX_coord());
		callableStatement.setInt(RackShelfConst.Y_COORD, rackShelf.getY_coord());
		callableStatement.setString(RackShelfConst.TYPE_SHELF, rackShelf.getType_shelfAtStr());
		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_shelf");
		rackShelf.setCode_shelf(id);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return rackShelf.getCode_shelf();
	}

	public void update(final UserContext userContext, final RackShelf rackShelf) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_shelf", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setInt(RackShelfConst.CODE_RACK, rackShelf.getCode_rack());
		callableStatement.setObject(RackShelfConst.CODE_SHELF, rackShelf.getCode_shelf());
		callableStatement.setInt(RackShelfConst.SHELF_HEIGHT, rackShelf.getShelf_height());
		callableStatement.setInt(RackShelfConst.SHELF_WIDTH, rackShelf.getShelf_width());
		callableStatement.setInt(RackShelfConst.SHELF_LENGTH, rackShelf.getShelf_length());
		callableStatement.setInt(RackShelfConst.ANGLE, rackShelf.getAngle());
		callableStatement.setInt(RackShelfConst.X_COORD, rackShelf.getX_coord());
		callableStatement.setInt(RackShelfConst.Y_COORD, rackShelf.getY_coord());
		callableStatement.setString(RackShelfConst.TYPE_SHELF, rackShelf.getType_shelfAtStr());
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DShelf(:" + RackShelfConst.CODE_SHELF + ")}";

	public void delete(final UserContext userContext, final int code_shelf) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(RackShelfConst.CODE_SHELF, code_shelf);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shelf:" + code_shelf + ")");
	}

	private static RackShelfModel instance = new RackShelfModel();

	public static RackShelfModel getInstance() {
		return instance;
	}

	private RackShelfModel() {
	}
}
