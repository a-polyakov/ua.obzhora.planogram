package planograma.utils.geometry;

/**
 * Date: 20.05.12
 * Time: 15:05
 *
 * @author Alexandr Polyakov
 */
public class Segment2D {
	protected Point2D p1;
	protected Point2D p2;

	public Segment2D(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Point2D getP1() {
		return p1;
	}

	public void setP1(Point2D p1) {
		this.p1 = p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public void setP2(Point2D p2) {
		this.p2 = p2;
	}

	public double distance(Point2D p) {
		double x21 = p2.x - p1.x;
		double y21 = p2.y - p1.y;
		return (x21 * p.y - y21 * p.x + (p1.x * p2.y - p2.x * p1.y)) / Math.sqrt(x21 * x21 + y21 * y21);
	}


}
