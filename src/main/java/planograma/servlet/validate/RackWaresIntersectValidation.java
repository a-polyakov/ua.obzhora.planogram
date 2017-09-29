package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.RackShelf;
import planograma.data.RackWares;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.exception.EntityFieldException;
import planograma.utils.geometry.Intersection2DUtils;

import java.util.List;

/**
 * товар не может пересекать полку
 * Date: 14.01.13
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class RackWaresIntersectValidation {
	/**
	 * товар не может пересекать полку
	 *
	 * @param fieldExceptionList
	 * @param rackShelf2DList
	 * @param rackWares2DList
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final List<RackShelf2D<RackShelf>> rackShelf2DList, final List<RackWares2D> rackWares2DList) {
		for (int i = 0; i < rackShelf2DList.size(); i++) {
			final RackShelf2D<RackShelf> a = rackShelf2DList.get(i);
			for (int j = 0; j < rackWares2DList.size(); j++) {
				final RackWares2D b = rackWares2DList.get(j);
				if (Intersection2DUtils.isIntersection(a, b)) {
					fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WARES_INTERSECT_SHELF(), RackWares.class, j, b.getRackWares().getCode_wares_on_rack(), "wares_intersect_shelf"));
				}
			}
		}
	}

	/**
	 * товар не может пересекать с соседними товарами
	 *
	 * @param fieldExceptionList
	 * @param rackWares2DList
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final List<RackWares2D> rackWares2DList) {
		for (int i = 0; i < rackWares2DList.size(); i++) {
			final RackWares2D a = rackWares2DList.get(i);
			for (int j = i + 1; j < rackWares2DList.size(); j++) {
				final RackWares2D b = rackWares2DList.get(j);
				if (Intersection2DUtils.isIntersection(a, b)) {
					fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WARES_INTERSECT_WARES(), RackWares.class, j, b.getRackWares().getCode_wares_on_rack(), "wares_intersect_shelf"));
				}
			}
		}
	}
}
