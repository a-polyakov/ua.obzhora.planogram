package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 05.06.12
 * Time: 1:29
 *
 * @author Alexandr Polyakov
 */
public class SecurityModel {

	private static final Logger LOG = Logger.getLogger(SecurityModel.class);

	private static final String Q_CAN_ACCESS = "select ADM.CAN_I_GET_THIS_OBJECT(?) intRES from dual";

	public boolean canAccess(final UserContext userContext, final SecurityConst code_object) {
		long time = System.currentTimeMillis();
		boolean result = false;
		try {
			final Connection connection = userContext.getConnection();
			final PreparedStatement ps = connection.prepareStatement(Q_CAN_ACCESS);
			ps.setInt(1, code_object.getValue());
			final ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				final int uid = resultSet.getInt(1);
				result = uid > 0;
			}
		} catch (SQLException e) {
			LOG.error("Error canAccess for code_object:" + code_object, e);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_object:" + code_object + ")");
		return result;
	}

	private static SecurityModel instance = new SecurityModel();

	public static SecurityModel getInstance() {
		return instance;
	}

	private SecurityModel() {
	}
}
