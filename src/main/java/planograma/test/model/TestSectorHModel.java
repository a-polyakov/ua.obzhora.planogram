package planograma.test.model;

import planograma.data.Sector;
import planograma.data.UserContext;
import planograma.model.SectorModel;
import planograma.model.history.SectorHModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Date: 11.08.12
 * Time: 17:03
 *
 * @author Alexandr Polyakov
 */
public class TestSectorHModel {
	public static void testSectorHModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		final SectorHModel sectorHModel = SectorHModel.getInstance();

		Sector sector = new Sector(8, null, "sector 1", 2000, 2000, 300, null, null, null, null);
		sectorModel.insert(userContext, sector);
		sector = sectorModel.select(userContext, sector.getCode_sector());
		final Date inserted = sector.getDate_insert();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sector.setWidth(2500);
		sectorModel.update(userContext, sector);
		sector = sectorModel.select(userContext, sector.getCode_sector());
		final Date updated = sector.getDate_update();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sectorModel.delete(userContext, sector.getCode_sector());
		final Statement statement = userContext.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery("select SYSDATE from dual");
		resultSet.next();
		final Date deleted = resultSet.getTimestamp(1);

		sector = sectorHModel.select(userContext, sector.getCode_sector(), inserted);
		System.out.println(sector.toJsonObject());
		sector = sectorHModel.select(userContext, sector.getCode_sector(), updated);
		System.out.println(sector.toJsonObject());
		sector = sectorHModel.select(userContext, sector.getCode_sector(), deleted);
		if (sector == null) {
			System.out.println("Sector delete");
		} else {
			System.out.println(sector.toJsonObject());
		}
	}
}
