package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateConst;
import planograma.constant.data.history.RackStateInSectorHConst;
import planograma.data.RackStateInSector;
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
public class RackStateInSectorHModel {

	private static final Logger LOG = Logger.getLogger(RackStateInSectorHModel.class);

	private static final String Q_SELECT_FROM = "SELECT" +
			" " + RackStateInSectorHConst.CODE_RACK + "," +
			" " + RackStateInSectorHConst.STATE_RACK + "," +
			" " + RackStateInSectorHConst.USER_INSERT + " " + RackStateConst.USER_DRAFT + "," +
			" " + RackStateInSectorHConst.DATE_INSERT + " " + RackStateConst.DATE_DRAFT + "," +
			" " + RackStateInSectorHConst.USER_INSERT + " " + RackStateConst.USER_ACTIVE + "," +
			" " + RackStateInSectorHConst.DATE_INSERT + " " + RackStateConst.DATE_ACTIVE + "," +
			" " + RackStateInSectorHConst.USER_INSERT + " " + RackStateConst.USER_COMPLETE + "," +
			" " + RackStateInSectorHConst.DATE_INSERT + " " + RackStateConst.DATE_COMPLETE + " " +
			"FROM " + RackStateInSectorHConst.TABLE_NAME;

	private static final String Q_SELECT = Q_SELECT_FROM +
			" WHERE " + RackStateInSectorHConst.CODE_RACK + " = ?" +
			" AND " + RackStateInSectorHConst.DATE_INSERT + " <= ?" +
			"ORDER BY " + RackStateInSectorHConst.DATE_INSERT + " DESC";

	public RackStateInSector select(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		RackStateInSector rackStateInSector = null;
		if (resultSet.next()) {
			rackStateInSector = new RackStateInSector(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return rackStateInSector;
	}

	private static RackStateInSectorHModel instance = new RackStateInSectorHModel();

	public static RackStateInSectorHModel getInstance() {
		return instance;
	}

	private RackStateInSectorHModel() {
	}
}
