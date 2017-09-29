package planograma.utils.geometry;

/**
 * Date: 17.06.12
 * Time: 21:11
 *
 * @author Alexandr Polyakov
 */
public class Rectangle2D {
	final protected Point2D p1;
	final protected Point2D p2;

	public Rectangle2D(float x1, float y1, float x2, float y2) {
		p1 = new Point2D(x1, y1);
		p2 = new Point2D(x2, y2);
	}

	public float getMinX() {
		return Math.min(p1.getX(), p2.getX());
	}

	public float getMinY() {
		return Math.min(p1.getY(), p2.getY());
	}

	public float getMaxX() {
		return Math.max(p1.getX(), p2.getX());
	}

	public float getMaxY() {
		return Math.max(p1.getY(), p2.getY());
	}
}
