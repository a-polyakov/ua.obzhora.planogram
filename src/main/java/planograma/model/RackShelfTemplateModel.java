package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackShelfTemplateConst;
import planograma.data.RackShelfTemplate;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 28.03.12
 * Time: 20:15
 *
 * @author Alexandr Polyakov
 */
public class RackShelfTemplateModel {

	private static final Logger LOG = Logger.getLogger(RackShelfTemplateModel.class);

	private static final String Q_SELECT_FROM = "select " +
			" " + RackShelfTemplateConst.CODE_RACK_TEMPLATE + "," +
			" " + RackShelfTemplateConst.CODE_SHELF_TEMPLATE + "," +
			" " + RackShelfTemplateConst.X_COORD + "," +
			" " + RackShelfTemplateConst.Y_COORD + "," +
			" " + RackShelfTemplateConst.SHELF_HEIGHT + "," +
			" " + RackShelfTemplateConst.SHELF_WIDTH + "," +
			" " + RackShelfTemplateConst.SHELF_LENGTH + "," +
			" " + RackShelfTemplateConst.ANGLE + "," +
			" " + RackShelfTemplateConst.TYPE_SHELF + "," +
			" " + RackShelfTemplateConst.USER_INSERT + "," +
			" " + RackShelfTemplateConst.DATE_INSERT + "," +
			" " + RackShelfTemplateConst.USER_UPDATE + "," +
			" " + RackShelfTemplateConst.DATE_UPDATE + " " +
			"from ";

	private static final String Q_LIST = Q_SELECT_FROM + RackShelfTemplateConst.TABLE_NAME + " " +
			"where " + RackShelfTemplateConst.CODE_RACK_TEMPLATE + "=? ";

	public List<RackShelfTemplate> list(final UserContext userContext, final int code_rack_template) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack_template);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackShelfTemplate> list = new ArrayList<RackShelfTemplate>();
		while (resultSet.next()) {
			final RackShelfTemplate item = new RackShelfTemplate(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack_template:" + code_rack_template + ")");
		return list;
	}

	private static final String Q_SELECT = Q_SELECT_FROM + RackShelfTemplateConst.TABLE_NAME + " " +
			"where " + RackShelfTemplateConst.CODE_SHELF_TEMPLATE + " = ?";

	public RackShelfTemplate select(final UserContext userContext, final int code_shelf_template) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_shelf_template);
		final ResultSet resultSet = ps.executeQuery();
		RackShelfTemplate rackShelfTemplate = null;
		if (resultSet.next()) {
			rackShelfTemplate = new RackShelfTemplate(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shelf_template:" + code_shelf_template + ")");
		return rackShelfTemplate;
	}

	private static final String Q_INSERT_UPDATE = "{call :new_code_shelf_template := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IUShelfTemplate(" +
			":mode, " +
			":" + RackShelfTemplateConst.CODE_RACK_TEMPLATE + ", " +
			":" + RackShelfTemplateConst.CODE_SHELF_TEMPLATE + ", " +
			":" + RackShelfTemplateConst.SHELF_HEIGHT + ", " +
			":" + RackShelfTemplateConst.SHELF_WIDTH + ", " +
			":" + RackShelfTemplateConst.SHELF_LENGTH + ", " +
			":" + RackShelfTemplateConst.ANGLE + ", " +
			":" + RackShelfTemplateConst.X_COORD + ", " +
			":" + RackShelfTemplateConst.Y_COORD + ", " +
			":" + RackShelfTemplateConst.TYPE_SHELF + ")}";

	public int insert(final UserContext userContext, final RackShelfTemplate rackShelfTemplate) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_shelf_template", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setInt(RackShelfTemplateConst.CODE_RACK_TEMPLATE, rackShelfTemplate.getCode_rack_template());
		callableStatement.setObject(RackShelfTemplateConst.CODE_SHELF_TEMPLATE, rackShelfTemplate.getCode_shelf_template());
		callableStatement.setInt(RackShelfTemplateConst.SHELF_HEIGHT, rackShelfTemplate.getShelf_height());
		callableStatement.setInt(RackShelfTemplateConst.SHELF_WIDTH, rackShelfTemplate.getShelf_width());
		callableStatement.setInt(RackShelfTemplateConst.SHELF_LENGTH, rackShelfTemplate.getShelf_length());
		callableStatement.setInt(RackShelfTemplateConst.ANGLE, rackShelfTemplate.getAngle());
		callableStatement.setInt(RackShelfTemplateConst.X_COORD, rackShelfTemplate.getX_coord());
		callableStatement.setInt(RackShelfTemplateConst.Y_COORD, rackShelfTemplate.getY_coord());
		callableStatement.setString(RackShelfTemplateConst.TYPE_SHELF, rackShelfTemplate.getType_shelfAtStr());
		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_shelf_template");
		rackShelfTemplate.setCode_shelf_template(id);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return rackShelfTemplate.getCode_shelf_template();
	}

	public void update(final UserContext userContext, final RackShelfTemplate rackShelfTemplate) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_shelf_template", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setInt(RackShelfTemplateConst.CODE_RACK_TEMPLATE, rackShelfTemplate.getCode_rack_template());
		callableStatement.setObject(RackShelfTemplateConst.CODE_SHELF_TEMPLATE, rackShelfTemplate.getCode_shelf_template());
		callableStatement.setInt(RackShelfTemplateConst.SHELF_HEIGHT, rackShelfTemplate.getShelf_height());
		callableStatement.setInt(RackShelfTemplateConst.SHELF_WIDTH, rackShelfTemplate.getShelf_width());
		callableStatement.setInt(RackShelfTemplateConst.SHELF_LENGTH, rackShelfTemplate.getShelf_length());
		callableStatement.setInt(RackShelfTemplateConst.ANGLE, rackShelfTemplate.getAngle());
		callableStatement.setInt(RackShelfTemplateConst.X_COORD, rackShelfTemplate.getX_coord());
		callableStatement.setInt(RackShelfTemplateConst.Y_COORD, rackShelfTemplate.getY_coord());
		callableStatement.setString(RackShelfTemplateConst.TYPE_SHELF, rackShelfTemplate.getType_shelfAtStr());
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DShelfTemplate(:" + RackShelfTemplateConst.CODE_SHELF_TEMPLATE + ")}";

	public void delete(final UserContext userContext, final int code_shelf_template) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(RackShelfTemplateConst.CODE_SHELF_TEMPLATE, code_shelf_template);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shelf_template:" + code_shelf_template + ")");
	}

	private static RackShelfTemplateModel instance = new RackShelfTemplateModel();

	public static RackShelfTemplateModel getInstance() {
		return instance;
	}

	private RackShelfTemplateModel() {
	}
}
