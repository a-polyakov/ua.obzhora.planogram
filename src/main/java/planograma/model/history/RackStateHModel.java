package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateConst;
import planograma.constant.data.history.RackStateHConst;
import planograma.data.RackState;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.Date;

/**
 * Date: 21.03.12
 * Time: 1:18
 *
 * @author Alexandr Polyakov
 */
public class RackStateHModel {

	private static final Logger LOG = Logger.getLogger(RackStateHModel.class);

	private static final String Q_SELECT_FROM = "SELECT" +
			" o." + RackStateHConst.CODE_RACK + "," +
			" o." + RackStateHConst.STATE_RACK + "," +
			" o." + RackStateHConst.USER_INSERT + " " + RackStateConst.USER_DRAFT + "," +
			" o." + RackStateHConst.DATE_INSERT + " " + RackStateConst.DATE_DRAFT + "," +
			" o." + RackStateHConst.USER_INSERT + " " + RackStateConst.USER_ACTIVE + "," +
			" o." + RackStateHConst.DATE_INSERT + " " + RackStateConst.DATE_ACTIVE + "," +
			" o." + RackStateHConst.USER_INSERT + " " + RackStateConst.USER_COMPLETE + "," +
			" o." + RackStateHConst.DATE_INSERT + " " + RackStateConst.DATE_COMPLETE + " " +
			"FROM " + RackStateHConst.TABLE_NAME + " o ";

	private static final String Q_SELECT = Q_SELECT_FROM +
			" WHERE o." + RackStateHConst.CODE_RACK + " = ?" +
			" AND o." + RackStateHConst.DATE_INSERT + " <= ?" +
			"ORDER BY o." + RackStateHConst.DATE_INSERT + " DESC";

	public RackState select(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		RackState rackState = null;
		if (resultSet.next()) {
			rackState = new RackState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return rackState;
	}

	private static final String Q_SELECT_A = Q_SELECT_FROM +
			" WHERE o." + RackStateHConst.CODE_RACK + " = ?" +
			" AND o." + RackStateHConst.DATE_INSERT + " in " +
			"  (SELECT MAX(ss1." + RackStateHConst.DATE_INSERT + ")" +
			"   FROM " + RackStateHConst.TABLE_NAME + " ss1" +
			"   WHERE " +
			"    ss1." + RackStateHConst.CODE_RACK + "= o." + RackStateHConst.CODE_RACK +
			"    AND ss1." + RackStateHConst.STATE_RACK + " in ('A')" +
			"  )";

	public RackState selectA(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT_A);
		ps.setInt(1, code_rack);
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		RackState rackState = null;
		if (resultSet.next()) {
			rackState = new RackState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return rackState;
	}

	private static final String Q_SELECT_PC = Q_SELECT_FROM +
			" WHERE o." + RackStateHConst.CODE_RACK + " = ?" +
			" AND o." + RackStateHConst.DATE_INSERT + " in " +
			"  (SELECT MAX(ss1." + RackStateHConst.DATE_INSERT + ")" +
			"   FROM " + RackStateHConst.TABLE_NAME + " ss1" +
			"   WHERE " +
			"    ss1." + RackStateHConst.CODE_RACK + "= o." + RackStateHConst.CODE_RACK +
			"    AND ss1." + RackStateHConst.STATE_RACK + " in ('PC')" +
			"  )";

	public RackState selectPC(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT_PC);
		ps.setInt(1, code_rack);
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		RackState rackState = null;
		if (resultSet.next()) {
			rackState = new RackState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return rackState;
	}


	private static RackStateHModel instance = new RackStateHModel();

	public static RackStateHModel getInstance() {
		return instance;
	}

	private RackStateHModel() {
	}
}
