package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.Rack;
import planograma.data.Sector;
import planograma.data.geometry.Rack2D;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * Date: 11.01.13
 * Time: 17:39
 *
 * @author Alexandr Polyakov
 */
public class RackOutsideSectorValidation {
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Sector sector, final Rack2D rack2D, int index) {
		if (rack2D.getMinX() < 0 ||
				rack2D.getMaxX() > sector.getLength() ||
				rack2D.getMinY() < 0 ||
				rack2D.getMaxY() > sector.getWidth() ||
				rack2D.getRack().getHeight() < 0 ||
				rack2D.getRack().getHeight() > sector.getHeight()) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_OUTSIDE_SECTOR(), Rack.class, index, rack2D.getRack().getCode_rack(), "outside"));
		}
	}
}
