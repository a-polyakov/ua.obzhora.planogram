package planograma.test.model;

import planograma.data.*;
import planograma.model.RackModel;
import planograma.model.RackWaresModel;
import planograma.model.SectorModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 16.08.12
 * Time: 9:10
 *
 * @author Alexandr Polyakov
 */
public class TestRackWaresModel {

	public static void testRackWaresModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final RackModel rackModel = RackModel.getInstance();
		final int length = 20;
		final int width = 200;
		final int height = 150;
		final Rack rack = new Rack(sector.getCode_sector(), null, "rack 1", "1", length, width, height, 1000, 1000, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, length, width, height, 0, 0, 0, 0);
		rackModel.insert(userContext, rack);
		final RackWaresModel rackWaresModel = RackWaresModel.getInstance();
		RackWares rackWares = new RackWares(rack.getCode_rack(), 10, 19, null, TypeRackWares.NA, 1, 10, 10, 50, 50, 50, 1, null, null, null, null, null, "waresTest", "unitTest", "barcodeTest");
		System.out.println(rackWares.toJsonObject());
		rackWaresModel.insert(userContext, rackWares);
		System.out.println("insert");
		List<RackWares> list = rackWaresModel.list(userContext, rack.getCode_rack());
		System.out.println("list");
		for (final RackWares o : list) {
			System.out.println(o.toJsonObject());
		}
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		System.out.println("select");
		System.out.println(rackWares.toJsonObject());
		rackWares.setWares_width(250);
		rackWaresModel.update(userContext, rackWares);
		System.out.println("update");
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		System.out.println(rackWares.toJsonObject());
		rackWaresModel.delete(userContext, rackWares.getCode_wares_on_rack());
		System.out.println("delete");
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		if (rackWares != null)
			System.out.println("Ошибка удаления");
	}
}
