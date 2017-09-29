package planograma.model;

import org.apache.log4j.Logger;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 24.10.12
 * Time: 15:10
 *
 * @author Alexandr Polyakov
 */
public class UserModel {

	private static final Logger LOG = Logger.getLogger(UserModel.class);

	private static final String Q_SELECT = "select ADM.ADM_GET_NAME_USER (?) from dual";

	// TODO возможно нужно сделать кеш для ускорения работы

	public String getFullName(final UserContext userContext, final int code_user) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_user);
		final ResultSet resultSet = ps.executeQuery();
		String fullName = null;
		if (resultSet.next()) {
			fullName = resultSet.getString(1);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_user:" + code_user + ")");
		return fullName;
	}

	private static final String Q_GET_CODE_USER = "select MZ.GETCODEUSER() from dual";

	// TODO возможно нужно сделать кеш для ускорения работы

	public int getCodeUser(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_GET_CODE_USER);
		final ResultSet resultSet = ps.executeQuery();
		int code_user = 0;
		if (resultSet.next()) {
			code_user = resultSet.getInt(1);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (return:" + code_user + ")");
		return code_user;
	}

	private static UserModel instance = new UserModel();

	public static UserModel getInstance() {
		return instance;
	}

	private UserModel() {
	}
}
