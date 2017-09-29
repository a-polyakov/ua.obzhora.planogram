package planograma.utils.geometry;

/**
 * Date: 20.05.12
 * Time: 15:04
 *
 * @author Alexandr Polyakov
 */
public class Point2D implements Cloneable {
	protected float x;
	protected float y;

	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public void scale(float m) {
		y = y / m;
		x = x / m;
	}

	@Override
	public Point2D clone() {
		return new Point2D(x, y);
	}
}
