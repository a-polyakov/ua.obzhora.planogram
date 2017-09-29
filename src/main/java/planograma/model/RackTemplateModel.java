package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackTemplateConst;
import planograma.data.RackTemplate;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 21.03.12
 * Time: 1:18
 *
 * @author Alexandr Polyakov
 */
public class RackTemplateModel {

	private static final Logger LOG = Logger.getLogger(RackTemplateModel.class);

	private static final String Q_SELECT_FROM = "select" +
			" " + RackTemplateConst.CODE_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.STATE_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.NAME_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.LENGTH + "," +
			" " + RackTemplateConst.WIDTH + "," +
			" " + RackTemplateConst.HEIGHT + "," +
			" " + RackTemplateConst.LOAD_SIDE + "," +
			" " + RackTemplateConst.USER_INSERT + "," +
			" " + RackTemplateConst.DATE_INSERT + "," +
			" " + RackTemplateConst.USER_UPDATE + "," +
			" " + RackTemplateConst.DATE_UPDATE + "," +
			" " + RackTemplateConst.USER_DRAFT + "," +
			" " + RackTemplateConst.DATE_DRAFT + "," +
			" " + RackTemplateConst.REAL_LENGTH + "," +
			" " + RackTemplateConst.REAL_WIDTH + "," +
			" " + RackTemplateConst.REAL_HEIGHT + "," +
			" " + RackTemplateConst.X_OFFSET + "," +
			" " + RackTemplateConst.Y_OFFSET + "," +
			" " + RackTemplateConst.Z_OFFSET + " " +
			"from " + RackTemplateConst.TABLE_NAME + " ";

	private static final String Q_LIST = Q_SELECT_FROM +
			"order by " + RackTemplateConst.NAME_RACK_TEMPLATE;

	public List<RackTemplate> list(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackTemplate> list = new ArrayList<RackTemplate>();
		while (resultSet.next()) {
			final RackTemplate item = new RackTemplate(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private static final String Q_SELECT = Q_SELECT_FROM +
			"where " + RackTemplateConst.CODE_RACK_TEMPLATE + " = ?";

	public RackTemplate select(final UserContext userContext, final int code_rack_template) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack_template);
		final ResultSet resultSet = ps.executeQuery();
		RackTemplate rackTemplate = null;
		if (resultSet.next()) {
			rackTemplate = new RackTemplate(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack_template:" + code_rack_template + ")");
		return rackTemplate;
	}

	private static final String Q_INSERT_UPDATE = "{call :new_code_rack_template := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IURackTemplate(" +
			":mode, " +
			":" + RackTemplateConst.CODE_RACK_TEMPLATE + ", " +
			":" + RackTemplateConst.NAME_RACK_TEMPLATE + ", " +
			":" + RackTemplateConst.LENGTH + ", " +
			":" + RackTemplateConst.WIDTH + ", " +
			":" + RackTemplateConst.HEIGHT + ", " +
			":" + RackTemplateConst.LOAD_SIDE + ", " +
			":" + RackTemplateConst.REAL_LENGTH + ", " +
			":" + RackTemplateConst.REAL_WIDTH + ", " +
			":" + RackTemplateConst.REAL_HEIGHT + ", " +
			":" + RackTemplateConst.X_OFFSET + ", " +
			":" + RackTemplateConst.Y_OFFSET + ", " +
			":" + RackTemplateConst.Z_OFFSET +
			")}";

	public int insert(final UserContext userContext, final RackTemplate rackTemplate) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_rack_template", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setObject(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
		callableStatement.setString(RackTemplateConst.NAME_RACK_TEMPLATE, rackTemplate.getName_rack_template());
		callableStatement.setInt(RackTemplateConst.LENGTH, rackTemplate.getLength());
		callableStatement.setInt(RackTemplateConst.WIDTH, rackTemplate.getWidth());
		callableStatement.setInt(RackTemplateConst.HEIGHT, rackTemplate.getHeight());
		callableStatement.setString(RackTemplateConst.LOAD_SIDE, rackTemplate.getLoad_sideAtStr());
		callableStatement.setInt(RackTemplateConst.REAL_LENGTH, rackTemplate.getReal_length());
		callableStatement.setInt(RackTemplateConst.REAL_WIDTH, rackTemplate.getReal_width());
		callableStatement.setInt(RackTemplateConst.REAL_HEIGHT, rackTemplate.getReal_height());
		callableStatement.setInt(RackTemplateConst.X_OFFSET, rackTemplate.getX_offset());
		callableStatement.setInt(RackTemplateConst.Y_OFFSET, rackTemplate.getY_offset());
		callableStatement.setInt(RackTemplateConst.Z_OFFSET, rackTemplate.getZ_offset());

		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_rack_template");
		rackTemplate.setCode_rack_template(id);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return rackTemplate.getCode_rack_template();
	}

	public void update(final UserContext userContext, final RackTemplate rackTemplate) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_rack_template", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setObject(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
		callableStatement.setString(RackTemplateConst.NAME_RACK_TEMPLATE, rackTemplate.getName_rack_template());
		callableStatement.setInt(RackTemplateConst.LENGTH, rackTemplate.getLength());
		callableStatement.setInt(RackTemplateConst.WIDTH, rackTemplate.getWidth());
		callableStatement.setInt(RackTemplateConst.HEIGHT, rackTemplate.getHeight());
		callableStatement.setString(RackTemplateConst.LOAD_SIDE, rackTemplate.getLoad_sideAtStr());
		callableStatement.setInt(RackTemplateConst.REAL_LENGTH, rackTemplate.getReal_length());
		callableStatement.setInt(RackTemplateConst.REAL_WIDTH, rackTemplate.getReal_width());
		callableStatement.setInt(RackTemplateConst.REAL_HEIGHT, rackTemplate.getReal_height());
		callableStatement.setInt(RackTemplateConst.X_OFFSET, rackTemplate.getX_offset());
		callableStatement.setInt(RackTemplateConst.Y_OFFSET, rackTemplate.getY_offset());
		callableStatement.setInt(RackTemplateConst.Z_OFFSET, rackTemplate.getZ_offset());

		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATERACK(" +
			":" + RackTemplateConst.CODE_RACK_TEMPLATE + ", " +
			":" + RackTemplateConst.STATE_RACK_TEMPLATE + ")}";

	public void changestate(final UserContext userContext, final int code_rack_template, final String state_rack_template) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(RackTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		callableStatement.setString(RackTemplateConst.STATE_RACK_TEMPLATE, state_rack_template);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DRACKTEMPLATE(:" + RackTemplateConst.CODE_RACK_TEMPLATE + ")}";

	public void delete(final UserContext userContext, final int code_rack_template) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(RackTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack_template:" + code_rack_template + ")");
	}

	private static RackTemplateModel instance = new RackTemplateModel();

	public static RackTemplateModel getInstance() {
		return instance;
	}

	private RackTemplateModel() {
	}
}
