package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.LoadSide;
import planograma.data.Rack;
import planograma.data.RackWares;
import planograma.data.geometry.RackWares2D;
import planograma.exception.EntityFieldException;

import java.util.ArrayList;
import java.util.List;

/**
 * полезная зона стеллажа не может стать меньше чем расположеные на нем товары
 * Date: 14.01.13
 * Time: 9:13
 *
 * @author Alexandr Polyakov
 */
public class RackOverflowWaresValidation {
	/**
	 * полезная зона стеллажа не может стать меньше чем расположеные на нем товары
	 *
	 * @param fieldExceptionList
	 * @param rack
	 * @param rackIndex
	 * @param rackWaresList
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Rack rack, final int rackIndex, final List<RackWares> rackWaresList) {
		final List<RackWares2D> rackWares2DList = new ArrayList<RackWares2D>(rackWaresList.size());
		for (int i = 0; i < rackWaresList.size(); i++) {
			final RackWares rackWares = rackWaresList.get(i);
			final RackWares2D rackWares2D = new RackWares2D(rackWares);
			rackWares2DList.add(rackWares2D);
		}
		validate2D(fieldExceptionList, rack, rackIndex, rackWares2DList);
	}

	/**
	 * полезная зона стеллажа не может стать меньше чем расположеные на нем товары
	 *
	 * @param fieldExceptionList
	 * @param rack
	 * @param rackIndex
	 * @param rackWares2DList
	 */
	public static void validate2D(final List<EntityFieldException> fieldExceptionList, final Rack rack, final int rackIndex, final List<RackWares2D> rackWares2DList) {
		float oy, ox;
		float dx;
		float dy;
		float dz;
		// относительно стороны загрузки
		if (rack.getLoad_side() == LoadSide.F) {
			ox = rack.getX_offset();
			oy = rack.getZ_offset();
			dx = ox + rack.getReal_length();
			dy = oy + rack.getReal_height();
			dz = rack.getReal_width();
		} else {
			ox = rack.getX_offset();
			oy = rack.getY_offset();
			dx = ox + rack.getReal_length();
			dy = oy + rack.getReal_width();
			dz = rack.getReal_height();
		}
		//	полезная зона не может стать меньше чем расположеные на нем товары
		if (rackWares2DList != null) {
			for (int j = 0; j < rackWares2DList.size(); j++) {
				final RackWares2D rackWares2D = rackWares2DList.get(j);
				final RackWares rackWares = rackWares2D.getRackWares();
				if (rackWares2D.getMinX() < ox ||
						rackWares2D.getMaxX() > dx ||
						rackWares2D.getMinY() < oy ||
						rackWares2D.getMaxY() > dy ||
						rackWares.getWares_length() * rackWares.getCount_length_on_shelf() < 0 ||
						rackWares.getWares_length() * rackWares.getCount_length_on_shelf() > dz) {
					fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_OVERFLOW_WARES(), Rack.class, rackIndex, rack.getCode_rack(), "rack_overflow_wares"));
				}
			}
		}
	}
}
