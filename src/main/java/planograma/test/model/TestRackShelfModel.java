package planograma.test.model;

import planograma.data.*;
import planograma.model.RackModel;
import planograma.model.RackShelfModel;
import planograma.model.SectorModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 19.10.12
 * Time: 10:53
 *
 * @author Alexandr Polyakov
 */
public class TestRackShelfModel {
	public static void testRackShelfModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final RackModel rackModel = RackModel.getInstance();
		final int length = 50;
		final int width = 200;
		final int height = 150;
		final Rack rack = new Rack(sector.getCode_sector(), null, "rack 1", "1", length, width, height, 1000, 1000, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, length, width, height, 0, 0, 0, 0);
		rackModel.insert(userContext, rack);
		final RackShelfModel rackShelfModel = RackShelfModel.getInstance();
		RackShelf rackShelf = new RackShelf(rack.getCode_rack(), null, 10, 10, 50, 50, 50, 0, TypeShelf.DZ, null, null, null, null);
		System.out.println(rackShelf.toJsonObject());
		rackShelfModel.insert(userContext, rackShelf);
		System.out.println("insert");
		List<RackShelf> list = rackShelfModel.list(userContext, rack.getCode_rack());
		System.out.println("list");
		for (final RackShelf o : list) {
			System.out.println(o.toJsonObject());
		}
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		System.out.println("select");
		System.out.println(rackShelf.toJsonObject());
		rackShelf.setShelf_width(250);
		rackShelfModel.update(userContext, rackShelf);
		System.out.println("update");
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		System.out.println(rackShelf.toJsonObject());
		//TODO changestate
		rackShelfModel.delete(userContext, rackShelf.getCode_shelf());
		System.out.println("delete");
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		if (rackShelf != null)
			System.out.println("Ошибка удаления");
	}
}
