package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.*;
import planograma.data.geometry.RackShelf2D;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * Проверка: полка не может выходить за пределы стеллажа
 * Date: 09.01.13
 * Time: 14:45
 *
 * @author Alexandr Polyakov
 */
public class RackShelfOutsideRackValidation {
	/**
	 * Проверка: полка не может выходить за пределы стеллажа
	 *
	 * @param fieldExceptionList список ошибок
	 * @param rack               стеллаж
	 */
	public static <T extends AbstractRackShelf> void validate(final List<EntityFieldException> fieldExceptionList, final AbstractRack rack, final List<RackShelf2D<T>> shelf2DList) {
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
		for (int i = 0; i < shelf2DList.size(); i++) {
			final RackShelf2D<T> shelf2D = shelf2DList.get(i);
			final T rackShelf = shelf2D.getRackShelf();
			if (shelf2D.getMinX() < 0 ||
					shelf2D.getMaxX() > dx ||
					shelf2D.getMinY() < 0 ||
					shelf2D.getMaxY() > dy ||
					rackShelf.getShelf_length() < 0 ||
					rackShelf.getShelf_length() > dz) {
				Integer code = null;
				if (rackShelf instanceof RackShelf) {
					code = ((RackShelf) rackShelf).getCode_shelf();
				} else if (rackShelf instanceof RackShelfTemplate) {
					code = ((RackShelfTemplate) rackShelf).getCode_shelf_template();
				}
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_OUTSIDE_RACK(), rackShelf.getClass(), i, code, "outside"));
			}
		}
	}
}
