package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.constant.data.RackWaresConst;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 17.01.13
 * Time: 14:49
 *
 * @author Alexandr Polyakov
 */
public class SectorFindWaresModel {
	private static final Logger LOG = Logger.getLogger(SectorFindWaresModel.class);

	private static final String Q_FIND = "select" +
			" distinct r." + RackConst.RACK_BARCODE + " " +
			"from " + RackConst.TABLE_NAME + " r " +
			" join " + RackWaresConst.TABLE_NAME + " rw on rw." + RackWaresConst.CODE_RACK + "=r." + RackConst.CODE_RACK + " " +
			"where r." + RackConst.CODE_SECTOR + "=?" +
			" and rw." + RackWaresConst.CODE_WARES + "=?";

	public List<String> findRackInSectorContainsWares(final UserContext userContext, final int code_sector, final int code_wares) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_FIND);
		ps.setInt(1, code_sector);
		ps.setInt(2, code_wares);
		final ResultSet resultSet = ps.executeQuery();
		final List<String> list = new ArrayList<String>();
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ")");
		return list;
	}

	private static SectorFindWaresModel instance = new SectorFindWaresModel();

	public static SectorFindWaresModel getInstance() {
		return instance;
	}

	private SectorFindWaresModel() {
	}
}
