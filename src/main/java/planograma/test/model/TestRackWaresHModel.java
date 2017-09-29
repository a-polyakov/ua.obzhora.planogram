package planograma.test.model;

import planograma.data.*;
import planograma.model.RackModel;
import planograma.model.RackWaresModel;
import planograma.model.SectorModel;
import planograma.model.history.RackWaresHModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * Date: 16.08.12
 * Time: 9:10
 *
 * @author Alexandr Polyakov
 */
public class TestRackWaresHModel {

	public static void testRackWaresHModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		final RackModel rackModel = RackModel.getInstance();
		final RackWaresModel rackWaresModel = RackWaresModel.getInstance();
		final RackWaresHModel rackWaresHModel = RackWaresHModel.getInstance();
		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final int length = 20;
		final int width = 200;
		final int height = 150;
		Rack rack = new Rack(sector.getCode_sector(), null, "rack 1", "bar code 1", length, width, height, 50, 50, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, length, width, height, 0, 0, 0, 0);
		rackModel.insert(userContext, rack);
		RackWares rackWares = new RackWares(rack.getCode_rack(), 10, 19, null, TypeRackWares.NA, 1, 10, 10, 50, 50, 50, 1, null, null, null, null, null, "waresTest", "unitTest", "barcodeTest");
		rackWaresModel.insert(userContext, rackWares);
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		final Date inserted = rackWares.getDate_insert();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rackWares.setPosition_x(20);
		rackWaresModel.update(userContext, rackWares);
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		final Date updated = rackWares.getDate_update();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rackWaresModel.delete(userContext, rackWares.getCode_wares_on_rack());
		final Statement statement = userContext.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery("select SYSDATE from dual");
		resultSet.next();
		final Date deleted = resultSet.getTimestamp(1);

		System.out.println("LIST");
		System.out.println("inserted");
		List<RackWares> list = rackWaresHModel.list(userContext, rack.getCode_rack(), inserted);
		for (int i = 0; i < list.size(); i++) {
			rackWares = list.get(i);
			System.out.println(rackWares.toJsonObject());
		}
		System.out.println("updated");
		list = rackWaresHModel.list(userContext, rack.getCode_rack(), updated);
		for (int i = 0; i < list.size(); i++) {
			rackWares = list.get(i);
			System.out.println(rackWares.toJsonObject());
		}
		System.out.println("deleted");
		list = rackWaresHModel.list(userContext, rack.getCode_rack(), deleted);
		for (int i = 0; i < list.size(); i++) {
			rackWares = list.get(i);
			System.out.println(rackWares.toJsonObject());
		}
	}
}
