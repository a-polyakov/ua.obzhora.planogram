package planograma.utils.geometry;

/**
 * четырехугольник
 * Date: 18.06.12
 * Time: 4:11
 *
 * @author Alexandr Polyakov
 */
public class Quadrilateral2D {
	protected Point2D p1;
	protected Point2D p2;
	protected Point2D p3;
	protected Point2D p4;

	public Point2D getP1() {
		return p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public Point2D getP3() {
		return p3;
	}

	public Point2D getP4() {
		return p4;
	}

	public float getMinX() {
		return Math.min(Math.min(p1.getX(), p2.getX()), Math.min(p3.getX(), p4.getX()));
	}

	public float getMinY() {
		return Math.min(Math.min(p1.getY(), p2.getY()), Math.min(p3.getY(), p4.getY()));
	}

	public float getMaxX() {
		return Math.max(Math.max(p1.getX(), p2.getX()), Math.max(p3.getX(), p4.getX()));
	}

	public float getMaxY() {
		return Math.max(Math.max(p1.getY(), p2.getY()), Math.max(p3.getY(), p4.getY()));
	}

	public Rectangle2D getDescribedRectangle2D() {
		return new Rectangle2D(getMinX(), getMinY(), getMaxX(), getMaxY());
	}
}
