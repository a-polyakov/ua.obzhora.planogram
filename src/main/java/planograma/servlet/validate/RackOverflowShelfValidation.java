package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.LoadSide;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.data.geometry.RackShelf2D;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * стеллаж не может стать меньше чем расположеные на нем полки
 * Date: 14.01.13
 * Time: 9:28
 *
 * @author Alexandr Polyakov
 */
public class RackOverflowShelfValidation {
	/**
	 * стеллаж не может стать меньше чем расположеные на нем полки
	 *
	 * @param fieldExceptionList
	 * @param rack
	 * @param rackIndex
	 * @param rackShelfList
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Rack rack, final int rackIndex, final List<RackShelf> rackShelfList) {
		float dx;
		float dy;
		float dz;
		// относительно стороны загрузки
		if (rack.getLoad_side() == LoadSide.F) {
			dx = rack.getLength();
			dy = rack.getHeight();
			dz = rack.getWidth();
		} else {
			dx = rack.getLength();
			dy = rack.getWidth();
			dz = rack.getHeight();
		}
		//	стеллаж не может стать меньше чем расположеные на нем полки (не сохраняется, выделяется один из стеллажей)
		if (rackShelfList != null) {
			for (int j = 0; j < rackShelfList.size(); j++) {
				final RackShelf rackShelf = rackShelfList.get(j);
				final RackShelf2D shelf2D = new RackShelf2D(rackShelf);
				if (shelf2D.getMinX() < 0 ||
						shelf2D.getMaxX() > dx ||
						shelf2D.getMinY() < 0 ||
						shelf2D.getMaxY() > dy ||
						rackShelf.getShelf_length() < 0 ||
						rackShelf.getShelf_length() > dz) {
					fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_OVERFLOW_SHELF(), Rack.class, rackIndex, rack.getCode_rack(), "rack_overflow_shelf"));
				}
			}
		}
	}
}
