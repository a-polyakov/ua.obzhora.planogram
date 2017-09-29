package planograma.data.geometry;

import planograma.data.Rack;
import planograma.utils.geometry.ConvexQuadrilateral2D;
import planograma.utils.geometry.Point2D;

/**
 * Date: 07.06.12
 * Time: 5:06
 *
 * @author Alexandr Polyakov
 */
public class Rack2D extends ConvexQuadrilateral2D {
	private final Rack rack;

	public Rack2D(Rack rack) {
		this.rack = rack;
		// поворот объекта
		final float cos = (float) Math.cos(Math.toRadians(rack.getAngle()));
		final float sin = (float) Math.sin(Math.toRadians(rack.getAngle()));
		// правый верхний угол
		float x = 0.5F * rack.getLength();
		float y = 0.5F * rack.getWidth();
		// относительно сцены
		p1 = new Point2D(rack.getX_coord() + x * cos - y * sin, rack.getY_coord() + x * sin + y * cos);
		// правый нижний угол
		y = -y;
		// относительно сцены
		p2 = new Point2D(rack.getX_coord() + x * cos - y * sin, rack.getY_coord() + x * sin + y * cos);
		// левый нижний угол
		x = -x;
		// относительно сцены
		p3 = new Point2D(rack.getX_coord() + x * cos - y * sin, rack.getY_coord() + x * sin + y * cos);
		// левый верхний угол
		y = -y;
		// относительно сцены
		p4 = new Point2D(rack.getX_coord() + x * cos - y * sin, rack.getY_coord() + x * sin + y * cos);
	}

	public Rack getRack() {
		return rack;
	}


}
