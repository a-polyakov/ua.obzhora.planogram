package planograma.data.geometry;

import planograma.data.RackWares;
import planograma.utils.geometry.ConvexQuadrilateral2D;
import planograma.utils.geometry.Point2D;

/**
 * Date: 08.06.12
 * Time: 4:35
 *
 * @author Alexandr Polyakov
 */
public class RackWares2D extends ConvexQuadrilateral2D {
	private final RackWares rackWares;

	public RackWares2D(RackWares rackWares) {
		this.rackWares = rackWares;
		// поворот объекта
//		final float cos = (float) Math.cos(Math.toRadians(rack.getAngle()));
//		final float sin = (float) Math.sin(Math.toRadians(rack.getAngle()));
		// правый верхний угол
		float x = 0.5F * rackWares.getWares_width();
		float y = 0.5F * rackWares.getWares_height();
		// относительно сцены
//		p1=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p1 = new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
		// правый нижний угол
		y = -y;
		// относительно сцены
//		p2=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p2 = new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
		// левый нижний угол
		x = -x;
		// относительно сцены
//		p3=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p3 = new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
		// левый верхний угол
		y = -y;
		// относительно сцены
//		p4=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p4 = new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
	}

	public RackWares getRackWares() {
		return rackWares;
	}
}
