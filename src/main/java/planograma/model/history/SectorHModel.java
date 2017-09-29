package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.SectorConst;
import planograma.constant.data.history.SectorHConst;
import planograma.data.Sector;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.Date;

/**
 * Date: 15.06.12
 * Time: 5:52
 *
 * @author Alexandr Polyakov
 */
public class SectorHModel {

	private static final Logger LOG = Logger.getLogger(SectorHModel.class);

	private static final String Q_SELECT = "select" +
			" " + SectorHConst.CODE_SHOP + "," +
			" " + SectorHConst.CODE_SECTOR + "," +
			" " + SectorHConst.NAME_SECTOR + "," +
			" " + SectorHConst.LENGTH + "," +
			" " + SectorHConst.WIDTH + "," +
			" " + SectorHConst.HEIGHT + "," +
			" " + SectorHConst.USER_INSERT + "," +
			" " + SectorHConst.DATE_INSERT + "," +
			" " + SectorHConst.USER_INSERT + " " + SectorConst.USER_UPDATE + "," +
			" " + SectorHConst.DATE_INSERT + " " + SectorConst.DATE_UPDATE + "," +
			" " + SectorHConst.TYPE_OPERATION + " " +
			"from " + SectorHConst.TABLE_NAME + " " +
			"where " + SectorHConst.CODE_SECTOR + " = ?" +
			" and " + SectorHConst.DATE_INSERT + " <= ?" +
			"order by " + SectorHConst.DATE_INSERT + " desc";

	public Sector select(final UserContext userContext, final int code_sector, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_sector);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		Sector sector = null;
		if (resultSet.next()) {
			final String type_operation = resultSet.getString(SectorHConst.TYPE_OPERATION);
			if (!type_operation.equals("D")) {
				sector = new Sector(resultSet);
			}
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return sector;
	}

	private static SectorHModel instance = new SectorHModel();

	public static SectorHModel getInstance() {
		return instance;
	}

	private SectorHModel() {
	}
}
