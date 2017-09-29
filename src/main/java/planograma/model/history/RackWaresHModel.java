package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.*;
import planograma.constant.data.history.RackWaresHConst;
import planograma.data.RackWares;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 15.06.12
 * Time: 4:38
 *
 * @author Alexandr Polyakov
 */
public class RackWaresHModel {

	private static final Logger LOG = Logger.getLogger(RackWaresHModel.class);

	private static final String Q_LIST = "SELECT " +
			" rw." + RackWaresHConst.CODE_RACK + "," +
			" rw." + RackWaresHConst.CODE_WARES + "," +
			" rw." + RackWaresHConst.CODE_UNIT + "," +
			" rw." + RackWaresHConst.CODE_WARES_ON_RACK + "," +
			" rw." + RackWaresHConst.TYPE_WARES_ON_RACK + "," +
			" rw." + RackWaresHConst.ORDER_NUMBER_ON_RACK + "," +
			" rw." + RackWaresHConst.POSITION_X + "," +
			" rw." + RackWaresHConst.POSITION_Y + "," +
			" rw." + RackWaresHConst.WARES_LENGTH + "," +
			" rw." + RackWaresHConst.WARES_WIDTH + "," +
			" rw." + RackWaresHConst.WARES_HEIGHT + "," +
			" rw." + RackWaresHConst.COUNT_LENGTH_ON_SHELF + "," +
			" rw." + RackWaresHConst.USER_INSERT + "," +
			" rw." + RackWaresHConst.DATE_INSERT + "," +
			" rw." + RackWaresHConst.USER_INSERT + " " + RackWaresConst.USER_UPDATE + "," +
			" rw." + RackWaresHConst.DATE_INSERT + " " + RackWaresConst.DATE_UPDATE + "," +
			" wi." + WaresImageConst.CODE_IMAGE + "," +
			" w." + WaresConst.NAME_WARES + "," +
			" ud." + UnitDimensionConst.ABR_UNIT + "," +
			" au." + AdditionUnitConst.BAR_CODE + " " +
			"FROM " + RackWaresHConst.TABLE_NAME + " rw" +
			" JOIN " + WaresConst.TABLE_NAME + " w ON w." + WaresConst.CODE_WARES + " = rw." + RackWaresHConst.CODE_WARES + " " +
			" JOIN " + UnitDimensionConst.TABLE_NAME + " ud ON ud." + UnitDimensionConst.CODE_UNIT + " = rw." + RackWaresHConst.CODE_UNIT + " " +
			" JOIN " + AdditionUnitConst.TABLE_NAME + " au ON au." + AdditionUnitConst.CODE_WARES + " = rw." + RackWaresHConst.CODE_WARES + " AND au." + AdditionUnitConst.CODE_UNIT + " = rw." + RackWaresHConst.CODE_UNIT + " " +
			" LEFT JOIN " + WaresImageConst.TABLE_NAME + " wi ON wi." + WaresImageConst.CODE_WARES + " = rw." + RackWaresHConst.CODE_WARES + " " +
			"WHERE" +
			" rw." + RackWaresHConst.CODE_RACK + "=?" +
			" AND rw." + RackWaresHConst.TYPE_OPERATION + " <> 'D'" +
			" AND" +
			"  rw." + RackWaresHConst.DATE_INSERT + " = " +
			"  (" +
			"   SELECT MAX(ss1." + RackWaresHConst.DATE_INSERT + ") " +
			"   FROM " + RackWaresHConst.TABLE_NAME + " ss1" +
			"   WHERE " +
			"    ss1." + RackWaresHConst.CODE_RACK + "=?" +
			"    AND ss1." + RackWaresHConst.DATE_INSERT + "<=?" +
			"  )" +
			"ORDER BY rw." + RackWaresHConst.ORDER_NUMBER_ON_RACK;

	public List<RackWares> list(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final List<RackWares> list = new ArrayList<RackWares>();
		if (date != null) {
			final Connection connection = userContext.getConnection();
			final PreparedStatement ps = connection.prepareStatement(Q_LIST);
			ps.setInt(1, code_rack);
			ps.setInt(2, code_rack);
			ps.setTimestamp(3, new Timestamp(date.getTime()));
			final ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				final RackWares item = new RackWares(resultSet);
				list.add(item);
			}
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return list;
	}

	private static RackWaresHModel instance = new RackWaresHModel();

	public static RackWaresHModel getInstance() {
		return instance;
	}

	private RackWaresHModel() {
	}
}
