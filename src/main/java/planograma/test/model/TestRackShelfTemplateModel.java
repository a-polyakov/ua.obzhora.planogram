package planograma.test.model;

import planograma.data.*;
import planograma.model.RackShelfTemplateModel;
import planograma.model.RackTemplateModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 19.10.12
 * Time: 11:04
 *
 * @author Alexandr Polyakov
 */
public class TestRackShelfTemplateModel {
	public static void testRackShelfTemplateModel(final UserContext userContext) throws SQLException {
		final RackTemplateModel rackTemplateModel = RackTemplateModel.getInstance();
		final int length = 20;
		final int width = 200;
		final int height = 150;
		final RackTemplate rackTemplate = new RackTemplate(null, null, "rack template 1", length, width, height, LoadSide.F, null, null, null, null, null, null, length, width, height, 0, 0, 0);
		rackTemplateModel.insert(userContext, rackTemplate);
		final RackShelfTemplateModel rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
		RackShelfTemplate rackShelfTemplate = new RackShelfTemplate(rackTemplate.getCode_rack_template(), null, 10, 10, 50, 50, 50, 0, TypeShelf.DZ, null, null, null, null);
		System.out.println(rackShelfTemplate.toJsonObject());
		rackShelfTemplateModel.insert(userContext, rackShelfTemplate);
		System.out.println("insert");
		List<RackShelfTemplate> list = rackShelfTemplateModel.list(userContext, rackTemplate.getCode_rack_template());
		System.out.println("list");
		for (final RackShelfTemplate o : list) {
			System.out.println(o.toJsonObject());
		}
		rackShelfTemplate = rackShelfTemplateModel.select(userContext, rackShelfTemplate.getCode_shelf_template());
		System.out.println("select");
		System.out.println(rackShelfTemplate.toJsonObject());
		rackShelfTemplate.setShelf_width(250);
		rackShelfTemplateModel.update(userContext, rackShelfTemplate);
		System.out.println("update");
		rackShelfTemplate = rackShelfTemplateModel.select(userContext, rackShelfTemplate.getCode_shelf_template());
		System.out.println(rackShelfTemplate.toJsonObject());
		//TODO changestate
		rackShelfTemplateModel.delete(userContext, rackShelfTemplate.getCode_shelf_template());
		System.out.println("delete");
		rackShelfTemplate = rackShelfTemplateModel.select(userContext, rackShelfTemplate.getCode_shelf_template());
		if (rackShelfTemplate != null)
			System.out.println("Ошибка удаления");
	}
}
