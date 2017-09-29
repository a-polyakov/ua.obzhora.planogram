package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.OptionsNameConst;
import planograma.constant.data.OptionsConst;
import planograma.data.Options;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexandr Polyakov
 */
public class OptionsModel {

	private static final Logger LOG = Logger.getLogger(ImageModel.class);

	private static final String Q_SELECT_FROM = "select" +
			" " + OptionsConst.NAME_OPTION + "," +
			" " + OptionsConst.VALUE + " " +
			"from " + OptionsConst.TABLE_NAME + " ";

	private static final String Q_SELECT = Q_SELECT_FROM +
			"where " + OptionsConst.NAME_OPTION + " = ?";

	public Options select(final UserContext userContext, final String name_option) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setString(1, name_option);
		final ResultSet resultSet = ps.executeQuery();
		Options result = null;
		if (resultSet.next()) {
			result = new Options(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (name_option:" + name_option + ")");
		return result;
	}

	private static final String Q_GET_BOOLEAN = Q_SELECT_FROM +
			"where " + OptionsConst.NAME_OPTION + " = ?" +
			" AND " + OptionsConst.VALUE + " = 1";

	public boolean getBoolean(final UserContext userContext, final OptionsNameConst name_option) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_GET_BOOLEAN);
		ps.setString(1, name_option.name());
		final ResultSet resultSet = ps.executeQuery();
		boolean result = resultSet.next();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (name_option:" + name_option + ")");
		return result;
	}

	private static OptionsModel instance = new OptionsModel();

	public static OptionsModel getInstance() {
		return instance;
	}

	private OptionsModel() {
	}
}
