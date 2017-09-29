package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.ShopConst;
import planograma.data.Shop;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 01.03.12
 * Time: 2:29
 *
 * @author Alexandr Polyakov
 */
public class ShopModel {

	private static final Logger LOG = Logger.getLogger(ShopModel.class);

	private static final String Q_LIST = "select" +
			" " + ShopConst.CODE_SHOP + "," +
			" " + ShopConst.NAME_SHOP + " " +
			"from " + ShopConst.TABLE_NAME + " " +
			"order by " + ShopConst.NAME_SHOP;

	public List<Shop> list(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		final ResultSet resultSet = ps.executeQuery();
		final List<Shop> list = new ArrayList<Shop>();
		while (resultSet.next()) {
			final Shop item = new Shop(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private static final String Q_SELECT = "select" +
			" " + ShopConst.CODE_SHOP + "," +
			" " + ShopConst.NAME_SHOP + " " +
			"from " + ShopConst.TABLE_NAME + " " +
			"where " + ShopConst.CODE_SHOP + "=?";

	public Shop select(final UserContext userContext, final int code_shop) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_shop);
		final ResultSet resultSet = ps.executeQuery();
		Shop shop = null;
		if (resultSet.next()) {
			shop = new Shop(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shop:" + code_shop + ")");
		return shop;
	}

	private static ShopModel instance = new ShopModel();

	public static ShopModel getInstance() {
		return instance;
	}

	private ShopModel() {
	}
}
