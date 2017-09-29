package planograma.test.model;

import planograma.data.LoadSide;
import planograma.data.RackTemplate;
import planograma.data.UserContext;
import planograma.model.RackTemplateModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 19.10.12
 * Time: 11:04
 *
 * @author Alexandr Polyakov
 */
public class TestRackTemplateModel {
	public static void testRackTemplateModel(final UserContext userContext) throws SQLException {
		final RackTemplateModel rackTemplateModel = RackTemplateModel.getInstance();
		final int length = 20;
		final int width = 200;
		final int height = 150;
		RackTemplate rackTemplate = new RackTemplate(null, null, "rack template 1", length, width, height, LoadSide.F, null, null, null, null, null, null, length, width, height, 0, 0, 0);
		System.out.println(rackTemplate.toJsonObject());
		rackTemplateModel.insert(userContext, rackTemplate);
		System.out.println("insert");
		List<RackTemplate> list = rackTemplateModel.list(userContext);
		System.out.println("list");
		for (final RackTemplate o : list) {
			System.out.println(o.toJsonObject());
		}
		rackTemplate = rackTemplateModel.select(userContext, rackTemplate.getCode_rack_template());
		System.out.println("select");
		System.out.println(rackTemplate.toJsonObject());
		rackTemplate.setWidth(250);
		rackTemplate.setReal_width(240);
		rackTemplateModel.update(userContext, rackTemplate);
		System.out.println("update");
		rackTemplate = rackTemplateModel.select(userContext, rackTemplate.getCode_rack_template());
		System.out.println(rackTemplate.toJsonObject());
		//TODO changestate
		rackTemplateModel.delete(userContext, rackTemplate.getCode_rack_template());
		System.out.println("delete");
		rackTemplate = rackTemplateModel.select(userContext, rackTemplate.getCode_rack_template());
		if (rackTemplate != null)
			System.out.println("Ошибка удаления");
	}
}
