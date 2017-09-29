package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.ETypeRack;
import planograma.data.Rack;
import planograma.data.geometry.Rack2D;
import planograma.exception.EntityFieldException;
import planograma.utils.geometry.Intersection2DUtils;

import java.util.List;

/**
 * стеллажи не могут пересекаться
 * Date: 14.01.13
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class RackIntersectValidation {
	/**
	 * стеллажи не могут пересекаться
	 *
	 * @param fieldExceptionList
	 * @param rack2DList
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final List<Rack2D> rack2DList) {
		for (int i = 0; i < rack2DList.size(); i++) {
			final Rack2D a = rack2DList.get(i);
			if (a.getRack().getType_rack() != ETypeRack.DZ && a.getRack().getAllow_intersect() == 0) {
				for (int j = i + 1; j < rack2DList.size(); j++) {
					final Rack2D b = rack2DList.get(j);
					if (b.getRack().getType_rack() != ETypeRack.DZ && b.getRack().getAllow_intersect() == 0) {
						// пересечение мертвых зон разрешается
						if (Intersection2DUtils.isIntersection(a, b)) {
							//if (a.getRack().getType_rack()==ETypeRack.R)
							fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_INTERSECT(), Rack.class, i, a.getRack().getCode_rack(), "rack_intersect"));
						}
					}
				}
			}
		}
	}
}
