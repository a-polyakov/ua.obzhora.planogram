package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.AdditionUnitConst;
import planograma.constant.data.UnitDimensionConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresImageConst;
import planograma.data.UserContext;
import planograma.data.wrapper.WaresWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 27.04.12
 * Time: 5:20
 *
 * @author Alexandr Polyakov
 */
public class WaresModel {

	private static final Logger LOG = Logger.getLogger(WaresModel.class);

	private static final String Q_SELECT_FROM_WHERE =
			"select" +
					" w." + WaresConst.CODE_GROUP + "," +
					" w." + WaresConst.CODE_WARES + "," +
					" u." + AdditionUnitConst.CODE_UNIT + "," +
					" wi." + WaresImageConst.CODE_IMAGE + "," +
					" w." + WaresConst.NAME_WARES + "," +
					" ud." + UnitDimensionConst.ABR_UNIT + "," +
					" 10*u." + AdditionUnitConst.LENGTH + " " + AdditionUnitConst.LENGTH + "," +
					" 10*u." + AdditionUnitConst.WIDTH + " " + AdditionUnitConst.WIDTH + "," +
					" 10*u." + AdditionUnitConst.HEIGHT + " " + AdditionUnitConst.HEIGHT + "," +
					" u." + AdditionUnitConst.BAR_CODE + " " +
					"from" +
					" " + AdditionUnitConst.TABLE_NAME + " u" +
					" join " + WaresConst.TABLE_NAME + " w on w." + WaresConst.CODE_WARES + " = u." + AdditionUnitConst.CODE_WARES +
					" join " + UnitDimensionConst.TABLE_NAME + " ud on ud." + UnitDimensionConst.CODE_UNIT + "  = u." + AdditionUnitConst.CODE_UNIT +
					" left join " + WaresImageConst.TABLE_NAME + " wi on wi." + WaresImageConst.CODE_WARES + " = w." + WaresConst.CODE_WARES + " " +
					"where" +
					" w." + WaresConst.OLD_WARES + "='No'" +
					" and u." + AdditionUnitConst.LENGTH + ">0" +
					" and u." + AdditionUnitConst.WIDTH + ">0" +
					" and u." + AdditionUnitConst.HEIGHT + ">0";

	private static final String Q_ORDER_BY = "order by w." + WaresConst.NAME_WARES;

	private static final String Q_LIST = Q_SELECT_FROM_WHERE +
			" and w." + WaresConst.CODE_GROUP + " = ? " +
			Q_ORDER_BY;

	public List<WaresWrapper> list(final UserContext userContext, final int code_group) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_group);
		final ResultSet resultSet = ps.executeQuery();
		final List<WaresWrapper> list = new ArrayList<WaresWrapper>();
		while (resultSet.next()) {
			final WaresWrapper item = new WaresWrapper(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_group:" + code_group + ")");
		return list;
	}

	private static final String Q_LIST_FOR_GROUP_AND_SHOP = Q_SELECT_FROM_WHERE +
			" and w." + WaresConst.CODE_GROUP + " = ? " +
			" and eugene_saz.sev_po.GetMAXQtyShopAM (?, w." + WaresConst.CODE_WARES + ")>0 " +
			Q_ORDER_BY;

	public List<WaresWrapper> listForGroupAndShop(final UserContext userContext, final int code_shop, final int code_group) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST_FOR_GROUP_AND_SHOP);
		ps.setInt(1, code_group);
		ps.setInt(2, code_shop);
		final ResultSet resultSet = ps.executeQuery();
		final List<WaresWrapper> list = new ArrayList<WaresWrapper>();
		while (resultSet.next()) {
			final WaresWrapper item = new WaresWrapper(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_shop:" + code_shop + ", code_group:" + code_group + ")");
		return list;
	}

	public List<WaresWrapper> search(final UserContext userContext, final String text, final String field,
									 final int code_shop, final int code_group) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();

		String query = Q_SELECT_FROM_WHERE;

		if (WaresConst.CODE_WARES.equals(field)) {
			query += " and w." + WaresConst.CODE_WARES + " = ? ";
		} else if (WaresConst.NAME_WARES.equals(field)) {
			query += " and w." + WaresConst.NAME_WARES + " like ? ";
		} else if (AdditionUnitConst.BAR_CODE.equals(field)) {
			query += " and u." + AdditionUnitConst.BAR_CODE + " like ? ";
		}
		if (code_shop != 0) {
			query += " and eugene_saz.sev_po.GetMAXQtyShopAM (?, w." + WaresConst.CODE_WARES + ")>0";
		}
		if (code_group != 0) {
			query += " and w." + WaresConst.CODE_GROUP + " = ? ";
		}
		query += Q_ORDER_BY;

		final PreparedStatement ps = connection.prepareStatement(query);

		if (WaresConst.CODE_WARES.equals(field)) {
			ps.setInt(1, Integer.valueOf(text));
		} else if (WaresConst.NAME_WARES.equals(field)) {
			// TODO %
			ps.setString(1, "%" + text + "%");
		} else if (AdditionUnitConst.BAR_CODE.equals(field)) {
			// TODO %
			ps.setString(1, "%" + text + "%");
		}

		if (code_shop != 0) {
			ps.setInt(2, code_shop);
		}
		if (code_group != 0) {
			ps.setInt((code_shop != 0) ? 3 : 2, code_group);
		}

		final ResultSet resultSet = ps.executeQuery();
		final List<WaresWrapper> list = new ArrayList<WaresWrapper>();
		while (resultSet.next()) {
			final WaresWrapper item = new WaresWrapper(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (text:" + text + "; field:" + field + "; code_group:" + code_group + ")");
		return list;
	}

	private static WaresModel instance = new WaresModel();

	public static WaresModel getInstance() {
		return instance;
	}

	private WaresModel() {
	}
}
