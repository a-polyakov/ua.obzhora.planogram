package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.RackShelf;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.exception.EntityFieldException;
import planograma.utils.geometry.Intersection2DUtils;

import java.util.List;

/**
 * полка не может пересекать товары
 * Date: 14.01.13
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class RackShelfIntersectWaresValidation {
	/**
	 * полка не может пересекать товары
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
					fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_INTERSECT_WARES(), RackShelf.class, i, a.getRackShelf().getCode_shelf(), "shelf_intersect_wares"));
				}
			}
		}
	}
}
