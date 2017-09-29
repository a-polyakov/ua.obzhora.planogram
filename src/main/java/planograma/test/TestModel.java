package planograma.test;

import planograma.constant.data.*;
import planograma.constant.data.history.RackHConst;
import planograma.constant.data.history.SectorHConst;
import planograma.data.Shop;
import planograma.data.StateAll;
import planograma.data.UserContext;
import planograma.model.ShopModel;
import planograma.model.StateAllModel;
import planograma.model.history.HistoryModel;
import planograma.test.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * Date: 28.03.12
 * Time: 20:10
 *
 * @author Alexandr Polyakov
 */
public class TestModel {
	public static void main(String args[]) throws SQLException {

		UserContext userContext = null;
		try {
			userContext = new UserContext("", "");
			System.out.println("-------------------------StateAllModel-------------------------");
			testStateAllModel(userContext);
			System.out.println("-------------------------ShopModel-------------------------");
			testShopModel(userContext);
			System.out.println("-------------------------SectorModel-------------------------");
			TestSectorModel.testSectorModel(userContext);
			System.out.println("-------------------------RackModel-------------------------");
			// TODO RackStateModel, RackStateInSectorModel
			TestRackModel.testRackModel(userContext);
			System.out.println("-------------------------RackShelfModel-------------------------");
			TestRackShelfModel.testRackShelfModel(userContext);
			System.out.println("-------------------------RackTemplateModel-------------------------");
			TestRackTemplateModel.testRackTemplateModel(userContext);
			System.out.println("-------------------------RackShelfTemplateModel-------------------------");
			TestRackShelfTemplateModel.testRackShelfTemplateModel(userContext);
			System.out.println("-------------------------RackWaresModel-------------------------");
			TestRackWaresModel.testRackWaresModel(userContext);
			System.out.println("-------------------------HistoryModel-------------------------");
			testHistoryModel(userContext);
			System.out.println("-------------------------SectorHModel-------------------------");
			TestSectorHModel.testSectorHModel(userContext);
			System.out.println("-------------------------RackHModel-------------------------");
			TestRackHModel.testRackHModel(userContext);
			System.out.println("-------------------------RackShelfHModel-------------------------");
			TestRackShelfHModel.testRackShelfHModel(userContext);
			System.out.println("-------------------------RackWaresHModel-------------------------");
			TestRackWaresHModel.testRackWaresHModel(userContext);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (userContext != null && userContext.getConnection() != null) {
				userContext.getConnection().rollback();
				userContext.getConnection().close();
			}
		}
	}

	public static void testStateAllModel(final UserContext userContext) throws SQLException {
		testStateAllModel(userContext, RackConst.TABLE_NAME, RackStateConst.STATE_RACK, RackStateConst.STATE_ALL_PART_STATE_RACK_STATE);
		testStateAllModel(userContext, RackConst.TABLE_NAME + '|' + RackTemplateConst.TABLE_NAME, AbstractRackConst.LOAD_SIDE, AbstractRackConst.STATE_ALL_PART_STATE_LOAD_SIDE);
		testStateAllModel(userContext, RackTemplateConst.TABLE_NAME, RackTemplateConst.STATE_RACK_TEMPLATE, RackTemplateConst.STATE_ALL_PART_STATE_STATE_RACK_TEMPLATE);
		testStateAllModel(userContext, RackShelfConst.TABLE_NAME + '|' + RackShelfTemplateConst.TABLE_NAME, AbstractRackShelfConst.TYPE_SHELF, AbstractRackShelfConst.STATE_ALL_PART_STATE_TYPE_SHELF);
	}

	private static void testStateAllModel(final UserContext userContext, final String tableName, final String columnName, final int starteAllPartState) throws SQLException {
		final StateAllModel stateAllModel = StateAllModel.getInstance();
		System.out.println(tableName + '.' + columnName);
		final List<StateAll> list = stateAllModel.list(userContext, starteAllPartState);
		System.out.println("list");
		for (final StateAll o : list) {
			System.out.println(o.toJsonObject());
		}
	}

	public static void testShopModel(final UserContext userContext) throws SQLException {
		final ShopModel shopModel = ShopModel.getInstance();
		final List<Shop> list = shopModel.list(userContext);
		System.out.println("list");
		for (final Shop o : list) {
			System.out.println(o.toJsonObject());
		}
	}

	public static void testHistoryModel(final UserContext userContext) throws SQLException {
		final HistoryModel historyModel = HistoryModel.getInstance();

		final Connection connection = userContext.getConnection();
		final Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select " + SectorHConst.CODE_SECTOR + " from " + SectorHConst.TABLE_NAME);
		if (resultSet.next()) {
			final int code_sector = resultSet.getInt(1);
			List<Date> list = historyModel.getHistoryMarkForSector(userContext, code_sector);
			System.out.print("getHistoryMarkForSector ");
			if (list != null && !list.isEmpty()) {
				System.out.print(list.get(0) + " - " + list.get(list.size() - 1));
			}
			System.out.println();
		}

		resultSet = statement.executeQuery("select " + RackHConst.CODE_RACK + " from " + RackHConst.TABLE_NAME);
		if (resultSet.next()) {
			final int code_rack = resultSet.getInt(1);
			List<Date> list = historyModel.getHistoryMarkForRack(userContext, code_rack);
			System.out.print("getHistoryMarkForRack ");
			if (list != null && !list.isEmpty()) {
				System.out.print(list.get(0) + " - " + list.get(list.size() - 1));
			}
			System.out.println();
		}
	}
}
