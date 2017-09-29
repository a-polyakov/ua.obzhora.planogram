package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateInSectorConst;
import planograma.data.RackStateInSector;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 19.10.12
 * Time: 16:55
 *
 * @author Alexandr Polyakov
 */
public class RackStateInSectorModel {

	private static final Logger LOG = Logger.getLogger(RackStateInSectorModel.class);

	private static final String Q_SELECT_FROM = "SELECT" +
			" " + RackStateInSectorConst.CODE_RACK + "," +
			" " + RackStateInSectorConst.STATE_RACK + "," +
			" " + RackStateInSectorConst.DATE_DRAFT + "," +
			" " + RackStateInSectorConst.USER_DRAFT + "," +
			" " + RackStateInSectorConst.DATE_ACTIVE + "," +
			" " + RackStateInSectorConst.USER_ACTIVE + "," +
			" " + RackStateInSectorConst.DATE_COMPLETE + "," +
			" " + RackStateInSectorConst.USER_COMPLETE + " " +
			"FROM " + RackStateInSectorConst.TABLE_NAME + " ";

	private static final String Q_SELECT = Q_SELECT_FROM +
			"WHERE " + RackStateInSectorConst.CODE_RACK + " = ?";

	public RackStateInSector select(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		RackStateInSector rack = null;
		if (resultSet.next()) {
			rack = new RackStateInSector(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return rack;
	}

	private static RackStateInSectorModel instance = new RackStateInSectorModel();

	public static RackStateInSectorModel getInstance() {
		return instance;
	}

	private RackStateInSectorModel() {
	}
}
