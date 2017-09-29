package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.SectorConst;
import planograma.data.Sector;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 28.02.12
 * Time: 6:01
 *
 * @author Alexandr Polyakov
 */

public class SectorModel {

	private static final Logger LOG = Logger.getLogger(SectorModel.class);

	private static final String Q_SELECT_FROM = "select" +
			" " + SectorConst.CODE_SHOP + "," +
			" " + SectorConst.CODE_SECTOR + "," +
			" " + SectorConst.NAME_SECTOR + "," +
			" " + SectorConst.LENGTH + "," +
			" " + SectorConst.WIDTH + "," +
			" " + SectorConst.HEIGHT + "," +
			" " + SectorConst.USER_INSERT + "," +
			" " + SectorConst.DATE_INSERT + "," +
			" " + SectorConst.USER_UPDATE + "," +
			" " + SectorConst.DATE_UPDATE + " " +
			"from " + SectorConst.TABLE_NAME;

	private static final String Q_LIST = Q_SELECT_FROM +
			" where " + SectorConst.CODE_SHOP + "=? " +
			"order by " + SectorConst.NAME_SECTOR;

	public List<Sector> list(final UserContext userContext, final int code_shop) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_shop);
		final ResultSet resultSet = ps.executeQuery();
		final List<Sector> list = new ArrayList<Sector>();
		while (resultSet.next()) {
			final Sector item = new Sector(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shop:" + code_shop + ")");
		return list;
	}

	private static final String Q_SELECT = Q_SELECT_FROM +
			" where " + SectorConst.CODE_SECTOR + " = ?";

	public Sector select(final UserContext userContext, final int code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		Sector sector = null;
		if (resultSet.next()) {
			sector = new Sector(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ")");
		return sector;
	}

	private static final String Q_INSERT_UPDATE = "{call :new_code_sector := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IUSECTOR(" +
			":mode, " +
			":" + SectorConst.CODE_SECTOR + ", " +
			":" + SectorConst.CODE_SHOP + ", " +
			":" + SectorConst.NAME_SECTOR + ", " +
			":" + SectorConst.LENGTH + ", " +
			":" + SectorConst.WIDTH + ", " +
			":" + SectorConst.HEIGHT + ")}";

	public int insert(final UserContext userContext, final Sector sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_sector", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setObject(SectorConst.CODE_SECTOR, sector.getCode_sector());
		callableStatement.setInt(SectorConst.CODE_SHOP, sector.getCode_shop());
		callableStatement.setString(SectorConst.NAME_SECTOR, sector.getName_sector());
		callableStatement.setInt(SectorConst.LENGTH, sector.getLength());
		callableStatement.setInt(SectorConst.WIDTH, sector.getWidth());
		callableStatement.setInt(SectorConst.HEIGHT, sector.getHeight());
		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_sector");
		sector.setCode_sector(id);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return sector.getCode_sector();
	}

	public void update(final UserContext userContext, final Sector sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_sector", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setObject(SectorConst.CODE_SECTOR, sector.getCode_sector());
		callableStatement.setInt(SectorConst.CODE_SHOP, sector.getCode_shop());
		callableStatement.setString(SectorConst.NAME_SECTOR, sector.getName_sector());
		callableStatement.setInt(SectorConst.LENGTH, sector.getLength());
		callableStatement.setInt(SectorConst.WIDTH, sector.getWidth());
		callableStatement.setInt(SectorConst.HEIGHT, sector.getHeight());
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DSector(:" + SectorConst.CODE_SECTOR + ")}";

	public void delete(final UserContext userContext, final int code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(SectorConst.CODE_SECTOR, code_sector);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ")");
	}

	private static SectorModel instance = new SectorModel();

	public static SectorModel getInstance() {
		return instance;
	}

	private SectorModel() {
	}
}
