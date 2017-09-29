package planograma.test.model;

import planograma.data.Sector;
import planograma.data.UserContext;
import planograma.model.SectorModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 19.10.12
 * Time: 11:03
 *
 * @author Alexandr Polyakov
 */
public class TestSectorModel {
	public static void testSectorModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		System.out.println(sector.toJsonObject());
		sectorModel.insert(userContext, sector);
		System.out.println("insert");
		List<Sector> list = sectorModel.list(userContext, 8);
		System.out.println("list");
		for (final Sector o : list) {
			System.out.println(o.toJsonObject());
		}
		sector = sectorModel.select(userContext, sector.getCode_sector());
		System.out.println("select");
		System.out.println(sector.toJsonObject());
		sector.setWidth(2500);
		sectorModel.update(userContext, sector);
		System.out.println("update");
		sector = sectorModel.select(userContext, sector.getCode_sector());
		System.out.println(sector.toJsonObject());
		//TODO changestate
		sectorModel.delete(userContext, sector.getCode_sector());
		System.out.println("delete");
		sector = sectorModel.select(userContext, sector.getCode_sector());
		if (sector != null)
			System.out.println("Ошибка удаления");
	}
}
