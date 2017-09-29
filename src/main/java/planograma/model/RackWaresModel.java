package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.*;
import planograma.data.RackWares;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 06.05.12
 * Time: 17:05
 *
 * @author Alexandr Polyakov
 */
public class RackWaresModel {

	private static final Logger LOG = Logger.getLogger(RackWaresModel.class);

	private static final String Q_SELECT_FROM = "select " +
			" rw." + RackWaresConst.CODE_RACK + "," +
			" rw." + RackWaresConst.CODE_WARES + "," +
			" rw." + RackWaresConst.CODE_UNIT + "," +
			" rw." + RackWaresConst.CODE_WARES_ON_RACK + "," +
			" rw." + RackWaresConst.TYPE_WARES_ON_RACK + "," +
			" rw." + RackWaresConst.ORDER_NUMBER_ON_RACK + "," +
			" rw." + RackWaresConst.POSITION_X + "," +
			" rw." + RackWaresConst.POSITION_Y + "," +
			" rw." + RackWaresConst.WARES_LENGTH + "," +
			" rw." + RackWaresConst.WARES_WIDTH + "," +
			" rw." + RackWaresConst.WARES_HEIGHT + "," +
			" rw." + RackWaresConst.COUNT_LENGTH_ON_SHELF + "," +
			" rw." + RackWaresConst.USER_INSERT + "," +
			" rw." + RackWaresConst.DATE_INSERT + "," +
			" rw." + RackWaresConst.USER_UPDATE + "," +
			" rw." + RackWaresConst.DATE_UPDATE + "," +
			" wi." + WaresImageConst.CODE_IMAGE + "," +
			" w." + WaresConst.NAME_WARES + "," +
			" ud." + UnitDimensionConst.ABR_UNIT + "," +
			" au." + AdditionUnitConst.BAR_CODE + " " +
			"from " + RackWaresConst.TABLE_NAME + " rw" +
			" join " + WaresConst.TABLE_NAME + " w on w." + WaresConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " " +
			" join " + UnitDimensionConst.TABLE_NAME + " ud on ud." + UnitDimensionConst.CODE_UNIT + " = rw." + RackWaresConst.CODE_UNIT + " " +
			" join " + AdditionUnitConst.TABLE_NAME + " au on au." + AdditionUnitConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " and au." + AdditionUnitConst.CODE_UNIT + " = rw." + RackWaresConst.CODE_UNIT + " " +
			" left join " + WaresImageConst.TABLE_NAME + " wi on wi." + WaresImageConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " ";

	private static final String Q_LIST = Q_SELECT_FROM +
			"where " + RackWaresConst.CODE_RACK + " = ? " +
			"order by " + RackWaresConst.ORDER_NUMBER_ON_RACK;

	public List<RackWares> list(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackWares> list = new ArrayList<RackWares>();
		while (resultSet.next()) {
			final RackWares item = new RackWares(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return list;
	}

	private static final String Q_SELECT = Q_SELECT_FROM +
			"where " + RackWaresConst.CODE_WARES_ON_RACK + " = ?";

	public RackWares select(final UserContext userContext, final int code_rack_wares) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack_wares);
		final ResultSet resultSet = ps.executeQuery();
		RackWares item = null;
		if (resultSet.next()) {
			item = new RackWares(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack_wares:" + code_rack_wares + ")");
		return item;
	}

	private static final String Q_INSERT_UPDATE = "{call :new_code_wares_on_rack := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IUWaresRack(" +
			":mode, " +
			":" + RackWaresConst.CODE_RACK + ", " +
			":" + RackWaresConst.CODE_WARES + ", " +
			":" + RackWaresConst.CODE_UNIT + ", " +
			":" + RackWaresConst.CODE_WARES_ON_RACK + ", " +
			":" + RackWaresConst.TYPE_WARES_ON_RACK + ", " +
			":" + RackWaresConst.ORDER_NUMBER_ON_RACK + ", " +
			":" + RackWaresConst.POSITION_X + ", " +
			":" + RackWaresConst.POSITION_Y + ", " +
			":" + RackWaresConst.WARES_LENGTH + ", " +
			":" + RackWaresConst.WARES_WIDTH + ", " +
			":" + RackWaresConst.WARES_HEIGHT + ", " +
			":" + RackWaresConst.COUNT_LENGTH_ON_SHELF + ")}";

	public int insert(final UserContext userContext, final RackWares rackWares) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_wares_on_rack", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setInt(RackWaresConst.CODE_RACK, rackWares.getCode_rack());
		callableStatement.setInt(RackWaresConst.CODE_WARES, rackWares.getCode_wares());
		callableStatement.setInt(RackWaresConst.CODE_UNIT, rackWares.getCode_unit());
		callableStatement.setObject(RackWaresConst.CODE_WARES_ON_RACK, rackWares.getCode_wares_on_rack());
		callableStatement.setString(RackWaresConst.TYPE_WARES_ON_RACK, rackWares.getType_wares_on_rackAtStr());
		callableStatement.setInt(RackWaresConst.ORDER_NUMBER_ON_RACK, rackWares.getOrder_number_on_rack());
		callableStatement.setInt(RackWaresConst.POSITION_X, rackWares.getPosition_x());
		callableStatement.setInt(RackWaresConst.POSITION_Y, rackWares.getPosition_y());
		callableStatement.setInt(RackWaresConst.WARES_LENGTH, rackWares.getWares_length());
		callableStatement.setInt(RackWaresConst.WARES_WIDTH, rackWares.getWares_width());
		callableStatement.setInt(RackWaresConst.WARES_HEIGHT, rackWares.getWares_height());
		callableStatement.setInt(RackWaresConst.COUNT_LENGTH_ON_SHELF, rackWares.getCount_length_on_shelf());
		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_wares_on_rack");
		rackWares.setCode_wares_on_rack(id);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return rackWares.getCode_wares_on_rack();
	}

	public void update(final UserContext userContext, final RackWares rackWares) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_wares_on_rack", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setInt(RackWaresConst.CODE_RACK, rackWares.getCode_rack());
		callableStatement.setInt(RackWaresConst.CODE_WARES, rackWares.getCode_wares());
		callableStatement.setInt(RackWaresConst.CODE_UNIT, rackWares.getCode_unit());
		callableStatement.setObject(RackWaresConst.CODE_WARES_ON_RACK, rackWares.getCode_wares_on_rack());
		callableStatement.setString(RackWaresConst.TYPE_WARES_ON_RACK, rackWares.getType_wares_on_rackAtStr());
		callableStatement.setInt(RackWaresConst.ORDER_NUMBER_ON_RACK, rackWares.getOrder_number_on_rack());
		callableStatement.setInt(RackWaresConst.POSITION_X, rackWares.getPosition_x());
		callableStatement.setInt(RackWaresConst.POSITION_Y, rackWares.getPosition_y());
		callableStatement.setInt(RackWaresConst.WARES_LENGTH, rackWares.getWares_length());
		callableStatement.setInt(RackWaresConst.WARES_WIDTH, rackWares.getWares_width());
		callableStatement.setInt(RackWaresConst.WARES_HEIGHT, rackWares.getWares_height());
		callableStatement.setInt(RackWaresConst.COUNT_LENGTH_ON_SHELF, rackWares.getCount_length_on_shelf());
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DWaresRack(:" + RackWaresConst.CODE_WARES_ON_RACK + ")}";

	public void delete(final UserContext userContext, final int code_rack_wares) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(RackWaresConst.CODE_WARES_ON_RACK, code_rack_wares);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack_wares:" + code_rack_wares + ")");
	}

	private static RackWaresModel instance = new RackWaresModel();

	public static RackWaresModel getInstance() {
		return instance;
	}

	private RackWaresModel() {
	}
}
