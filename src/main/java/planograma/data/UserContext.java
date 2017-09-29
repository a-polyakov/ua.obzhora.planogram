package planograma.data;

import planograma.constant.DBConst;

import java.io.Serializable;
import java.sql.*;

/**
 * Date: 28.02.12
 * Time: 5:28
 *
 * @author Alexandr Polyakov
 */
public class UserContext implements Serializable {


	private static final char[] q1 = new char[]{83, 69, 76, 69, 67, 84, 32, 115, 105, 100, 42, 51, 32, 124, 124, 32, 115, 101, 114, 105, 97, 108, 35, 42, 55, 32, 124, 124, 32, 97, 117, 100, 115, 105, 100, 42, 57, 32, 124, 124, 32, 117, 115, 101, 114, 35, 42, 53, 32, 124, 124, 32, 49, 50, 51, 32, 124, 124, 32, 111, 119, 110, 101, 114, 105, 100, 32, 124, 124, 32, 55, 56, 57, 32, 124, 124, 32, 115, 99, 104, 101, 109, 97, 35, 42, 54, 32, 70, 82, 79, 77, 32, 109, 122, 46, 103, 117, 97, 95, 109, 121, 95, 115, 101, 115, 115, 105, 111, 110};
	private static final char[] q2 = new char[]{98, 101, 103, 105, 110, 32, 109, 122, 46, 103, 117, 97, 95, 112, 114, 111, 102, 105, 108, 101, 95, 114, 111, 108, 101, 46, 83, 101, 116, 82, 111, 108, 101, 40, 63, 41, 59, 32, 101, 110, 100, 59};

	private final String login;
	private final Connection connection;

	public UserContext(final String login, final String password) throws ClassNotFoundException, SQLException {
		this.login = login;
		// Загружаем класс драйвера
		Class.forName(DBConst.DB_DRIVER);
		// Cоздаем соединение
		connection = DriverManager.getConnection(DBConst.DB_URL, login, password);
		connection.setAutoCommit(false);
		final Statement stmt = connection.createStatement();
		final ResultSet rset = stmt.executeQuery(new String(q1));
		if (rset.next()) {
			final CallableStatement pc = connection.prepareCall(new String(q2));
			pc.setString(1, rset.getString(1));
			pc.execute();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	protected void finalize() throws Throwable {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	@Override
	public String toString() {
		return "UserContext{" +
				"login='" + login + '\'' +
				'}';
	}
}
