package planograma.test.model;

import planograma.data.*;
import planograma.model.RackModel;
import planograma.model.SectorModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 19.10.12
 * Time: 10:55
 *
 * @author Alexandr Polyakov
 */
public class TestRackModel {
	public static void testRackModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final RackModel rackModel = RackModel.getInstance();
		final int length = 50;
		final int width = 200;
		final int height = 150;
		Rack rack = new Rack(sector.getCode_sector(), null, "rack 1", "bar code 1", length, width, height, 50, 50, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, length, width, height, 0, 0, 0, 0);
		System.out.println(rack.toJsonObject());
		rackModel.insert(userContext, rack);
		System.out.println("insert");
		List<Rack> list = rackModel.list(userContext, sector.getCode_sector());
		System.out.println("list");
		for (final Rack o : list) {
			System.out.println(o.toJsonObject());
		}
		rack = rackModel.select(userContext, rack.getCode_rack());
		System.out.println("select");
		System.out.println(rack.toJsonObject());
		rack.setWidth(250);
		rack.setReal_width(240);
		rackModel.update(userContext, rack);
		System.out.println("update");
		rack = rackModel.select(userContext, rack.getCode_rack());
		System.out.println(rack.toJsonObject());
		//TODO changestate
		rackModel.delete(userContext, rack.getCode_rack());
		System.out.println("delete");
		rack = rackModel.select(userContext, rack.getCode_rack());
		if (rack != null)
			System.out.println("Ошибка удаления");
	}

}
