package planograma.test.model;

import planograma.data.*;
import planograma.model.RackModel;
import planograma.model.SectorModel;
import planograma.model.history.RackHModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * Date: 11.08.12
 * Time: 17:59
 *
 * @author Alexandr Polyakov
 */
public class TestRackHModel {
	public static void testRackHModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		final RackModel rackModel = RackModel.getInstance();
		final RackHModel rackHModel = RackHModel.getInstance();

		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final int length = 20;
		final int width = 200;
		final int height = 150;
		Rack rack = new Rack(sector.getCode_sector(), null, "rack 1", "bar code 1", length, width, height, 50, 50, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, length, width, height, 0, 0, 0, 0);
		rackModel.insert(userContext, rack);
		rack = rackModel.select(userContext, rack.getCode_rack());
		final Date inserted = rack.getDate_insert();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rack.setWidth(250);
		rackModel.update(userContext, rack);
		rack = rackModel.select(userContext, rack.getCode_rack());
		final Date updated = rack.getDate_update();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rackModel.delete(userContext, rack.getCode_rack());
		final Statement statement = userContext.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery("select SYSDATE from dual");
		resultSet.next();
		final Date deleted = resultSet.getTimestamp(1);


		System.out.println("SELECT");
		rack = rackHModel.select(userContext, rack.getCode_rack(), inserted);
		System.out.println(rack.toJsonObject());
		rack = rackHModel.select(userContext, rack.getCode_rack(), updated);
		System.out.println(rack.toJsonObject());
		rack = rackHModel.select(userContext, rack.getCode_rack(), deleted);
		if (rack == null) {
			System.out.println("Rack delete");
		} else {
			System.out.println(rack.toJsonObject());
		}

		System.out.println("LIST");
		System.out.println("inserted");
		List<Rack> list = rackHModel.list(userContext, sector.getCode_sector(), inserted);
		for (int i = 0; i < list.size(); i++) {
			rack = list.get(i);
			System.out.println(rack.toJsonObject());
		}
		System.out.println("updated");
		list = rackHModel.list(userContext, sector.getCode_sector(), updated);
		for (int i = 0; i < list.size(); i++) {
			rack = list.get(i);
			System.out.println(rack.toJsonObject());
		}
		System.out.println("deleted");
		list = rackHModel.list(userContext, sector.getCode_sector(), deleted);
		for (int i = 0; i < list.size(); i++) {
			rack = list.get(i);
			System.out.println(rack.toJsonObject());
		}
	}
}
