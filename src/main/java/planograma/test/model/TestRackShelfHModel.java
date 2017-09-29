package planograma.test.model;

import planograma.data.*;
import planograma.model.RackModel;
import planograma.model.RackShelfModel;
import planograma.model.SectorModel;
import planograma.model.history.RackShelfHModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * Date: 16.08.12
 * Time: 8:41
 *
 * @author Alexandr Polyakov
 */
public class TestRackShelfHModel {

	public static void testRackShelfHModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		final RackModel rackModel = RackModel.getInstance();
		final RackShelfModel rackShelfModel = RackShelfModel.getInstance();
		final RackShelfHModel rackShelfHModel = RackShelfHModel.getInstance();
		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final int length = 20;
		final int width = 200;
		final int height = 150;
		Rack rack = new Rack(sector.getCode_sector(), null, "rack 1", "bar code 1", length, width, height, 50, 50, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, length, width, height, 0, 0, 0, 0);
		rackModel.insert(userContext, rack);
		RackShelf rackShelf = new RackShelf(rack.getCode_rack(), null, 50, 50, 5, 100, 100, 0, TypeShelf.DZ, null, null, null, null);
		rackShelfModel.insert(userContext, rackShelf);
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		final Date inserted = rackShelf.getDate_insert();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rackShelf.setAngle(10);
		rackShelfModel.update(userContext, rackShelf);
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		final Date updated = rackShelf.getDate_update();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rackShelfModel.delete(userContext, rackShelf.getCode_shelf());
		final Statement statement = userContext.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery("select SYSDATE from dual");
		resultSet.next();
		final Date deleted = resultSet.getTimestamp(1);

		System.out.println("LIST");
		System.out.println("inserted");
		List<RackShelf> list = rackShelfHModel.list(userContext, rack.getCode_rack(), inserted);
		for (int i = 0; i < list.size(); i++) {
			rackShelf = list.get(i);
			System.out.println(rackShelf.toJsonObject());
		}
		System.out.println("updated");
		list = rackShelfHModel.list(userContext, rack.getCode_rack(), updated);
		for (int i = 0; i < list.size(); i++) {
			rackShelf = list.get(i);
			System.out.println(rackShelf.toJsonObject());
		}
		System.out.println("deleted");
		list = rackShelfHModel.list(userContext, rack.getCode_rack(), deleted);
		for (int i = 0; i < list.size(); i++) {
			rackShelf = list.get(i);
			System.out.println(rackShelf.toJsonObject());
		}

	}
}
