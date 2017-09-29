package planograma.data.geometry;

import planograma.data.AbstractRackShelf;
import planograma.utils.geometry.ConvexQuadrilateral2D;
import planograma.utils.geometry.Point2D;

/**
 * Date: 08.06.12
 * Time: 5:06
 *
 * @author Alexandr Polyakov
 */
public class RackShelf2D<E extends AbstractRackShelf> extends ConvexQuadrilateral2D {
	private final E rackShelf;

	public RackShelf2D(E rackShelf) {
		this.rackShelf = rackShelf;
		// поворот объекта
		final float cos = (float) Math.cos(Math.toRadians(rackShelf.getAngle()));
		final float sin = (float) Math.sin(Math.toRadians(rackShelf.getAngle()));
		// правый верхний угол
		float x = 0.5F * rackShelf.getShelf_width();
		float y = 0.5F * rackShelf.getShelf_height();
		// относительно сцены
		p1 = new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
		// правый нижний угол
		y = -y;
		// относительно сцены
		p2 = new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
		// левый нижний угол
		x = -x;
		// относительно сцены
		p3 = new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
		// левый верхний угол
		y = -y;
		// относительно сцены
		p4 = new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
	}

	public E getRackShelf() {
		return rackShelf;
	}
}
