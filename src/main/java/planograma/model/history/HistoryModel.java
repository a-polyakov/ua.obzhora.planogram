package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.constant.data.history.*;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 08.08.12
 * Time: 11:19
 *
 * @author Alexandr Polyakov
 */
public class HistoryModel {

	private static final Logger LOG = Logger.getLogger(HistoryModel.class);

	private final String Q_HISTORYMARK_FOR_SECTOR =
			" (SELECT " + SectorHConst.DATE_INSERT + " FROM " + SectorHConst.TABLE_NAME + " WHERE " + SectorHConst.CODE_SECTOR + "=?) " +
					"UNION" +
					" (SELECT " + RackHConst.DATE_INSERT + " FROM " + RackHConst.TABLE_NAME + " WHERE " + RackHConst.CODE_SECTOR + "=?) " +
					"UNION" +
					" (SELECT " + RackStateHConst.DATE_INSERT + " FROM " + RackStateHConst.TABLE_NAME + " WHERE " + RackStateHConst.CODE_RACK + " IN (SELECT ss1." + RackConst.CODE_RACK + " FROM " + RackConst.TABLE_NAME + " ss1 WHERE ss1." + RackConst.CODE_SECTOR + "=?)) " +
					"UNION" +
					" (SELECT " + RackStateInSectorHConst.DATE_INSERT + " FROM " + RackStateInSectorHConst.TABLE_NAME + " WHERE " + RackStateInSectorHConst.CODE_RACK + " IN (SELECT ss1." + RackConst.CODE_RACK + " FROM " + RackConst.TABLE_NAME + " ss1 WHERE ss1." + RackConst.CODE_SECTOR + "=?)) " +
					"UNION" +
					" (SELECT " + RackShelfHConst.DATE_INSERT + " FROM " + RackShelfHConst.TABLE_NAME + " WHERE " + RackShelfHConst.CODE_RACK + " IN (SELECT ss1." + RackConst.CODE_RACK + " FROM " + RackConst.TABLE_NAME + " ss1 WHERE ss1." + RackConst.CODE_SECTOR + "=?)) " +
					"ORDER BY 1";


	public List<Date> getHistoryMarkForSector(final UserContext userContext, final int code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_HISTORYMARK_FOR_SECTOR);
		ps.setInt(1, code_sector);
		ps.setInt(2, code_sector);
		ps.setInt(3, code_sector);
		ps.setInt(4, code_sector);
		ps.setInt(5, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		final List<Date> list = new ArrayList<Date>();
		while (resultSet.next()) {
			final Date date = resultSet.getTimestamp(1);
			list.add(date);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private final String Q_HISTORYMARK_FOR_RACK =
			" (SELECT " + RackHConst.DATE_INSERT + " FROM " + RackHConst.TABLE_NAME + " WHERE " + RackHConst.CODE_RACK + "=?) " +
					"UNION" +
					" (SELECT " + RackShelfHConst.DATE_INSERT + " FROM " + RackShelfHConst.TABLE_NAME + " WHERE " + RackShelfHConst.CODE_RACK + " =?) " +
					"UNION" +
					" (SELECT " + RackWaresHConst.DATE_INSERT + " FROM " + RackWaresHConst.TABLE_NAME + " WHERE " + RackWaresHConst.CODE_RACK + " =?) " +
					"ORDER BY 1";

	public List<Date> getHistoryMarkForRack(final UserContext userContext, final int code_race) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_HISTORYMARK_FOR_RACK);
		ps.setInt(1, code_race);
		ps.setInt(2, code_race);
		ps.setInt(3, code_race);
		final ResultSet resultSet = ps.executeQuery();
		final List<Date> list = new ArrayList<Date>();
		while (resultSet.next()) {
			final Date date = resultSet.getTimestamp(1);
			list.add(date);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private static HistoryModel instance = new HistoryModel();

	public static HistoryModel getInstance() {
		return instance;
	}

	private HistoryModel() {
	}
}
