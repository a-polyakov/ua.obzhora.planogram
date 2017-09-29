package planograma.utils.geometry;

import planograma.data.geometry.Rack2D;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;

/**
 * Утилитный класс определяющий пересечение обектов
 * Date: 18.06.12
 * Time: 3:15
 *
 * @author Alexandr Polyakov
 */
public class Intersection2DUtils {

	/**
	 * Пересечение двух отрезков
	 *
	 * @param a отчезок
	 * @param b отчезок
	 * @return точка пересечения
	 */
	public static Point2D intersection(final Segment2D a, final Segment2D b) {
		float x21 = a.p2.x - a.p1.x;
		float y21 = a.p2.y - a.p1.y;
		float x43 = b.p2.x - b.p1.x;
		float y43 = b.p2.y - b.p1.y;
		// отрезки не параллельны
		if (x21 * y43 != x43 * y21) {
			// точка пересечения линий
			final float y = ((a.p2.x * a.p1.y - a.p1.x * a.p2.y) * y43 + y21 * (b.p1.x * b.p2.y - b.p2.x * b.p1.y)) / (x21 * y43 - x43 * y21);
			final float x;
			if (y21 == 0) {
				x = (y * x43 + b.p1.x * b.p2.y - b.p2.x * b.p1.y) / y43;
			} else {
				x = (y * x21 + a.p1.x * a.p2.y - a.p2.x * a.p1.y) / y21;
			}
			// точка пересерения линий в пределах отрезков
			if (((a.p1.x <= x && x <= a.p2.x) || (a.p1.x >= x && x >= a.p2.x)) &&
					((b.p1.x <= x && x <= b.p2.x) || (b.p1.x >= x && x >= b.p2.x)) &&
					((a.p1.y <= y && y <= a.p2.y) || (a.p1.y >= y && y >= a.p2.y)) &&
					((b.p1.y <= y && y <= b.p2.y) || (b.p1.y >= y && y >= b.p2.y)))
				return new Point2D(x, y);
		}
		return null;
	}

	/**
	 * Пересечение двух прямоугольников (угол поворота = 0)
	 *
	 * @param a прямоугольник (угол поворота = 0)
	 * @param b прямоугольник (угол поворота = 0)
	 * @return a∩b
	 */
	public static Rectangle2D intersection(Rectangle2D a, Rectangle2D b) {
		float x1 = Math.max(a.getMinX(), b.getMinX());
		float x2 = Math.min(a.getMaxX(), b.getMaxX());
		float y1 = Math.max(a.getMinY(), b.getMinY());
		float y2 = Math.min(a.getMaxY(), b.getMaxY());
		if (x1 <= x2 && y1 <= y2)
			return new Rectangle2D(x1, y1, x2, y2);
		else
			return null;
	}

	public static boolean isIntersection(Rack2D a, Rack2D b) {
		boolean intersect = false;
		if (a.getMaxX() >= b.getMinX() && a.getMinX() <= b.getMaxX() &&
				a.getMaxY() >= b.getMinY() && a.getMinY() <= b.getMaxY()) {
			intersect = true;
			// четырехугольники возможно пересекаются необходима детализация
			boolean angleEquals = (a.getRack().getAngle() % 90) == (b.getRack().getAngle() % 90);
			double angleArray[] = new double[angleEquals ? 2 : 4];
			angleArray[0] = -a.getRack().getAngle() % 90;
			angleArray[1] = angleArray[0] + 90;
			if (!angleEquals) {
				angleArray[2] = -b.getRack().getAngle() % 90;
				angleArray[3] = angleArray[2] + 90;
			}
			for (int i = 0; intersect && i < angleArray.length; i++) {
				final float sin = (float) Math.sin(Math.toRadians(angleArray[i]));
				final float cos = (float) Math.cos(Math.toRadians(angleArray[i]));
				float ax1 = a.p1.x * cos - a.p1.y * sin;
				float ay1 = a.p1.x * sin + a.p1.y * cos;
				float ax2 = a.p2.x * cos - a.p2.y * sin;
				float ay2 = a.p2.x * sin + a.p2.y * cos;
				float ax3 = a.p3.x * cos - a.p3.y * sin;
				float ay3 = a.p3.x * sin + a.p3.y * cos;
				float ax4 = a.p4.x * cos - a.p4.y * sin;
				float ay4 = a.p4.x * sin + a.p4.y * cos;
				float aminx = Math.min(Math.min(ax1, ax2), Math.min(ax3, ax4));
				float amaxx = Math.max(Math.max(ax1, ax2), Math.max(ax3, ax4));
				float aminy = Math.min(Math.min(ay1, ay2), Math.min(ay3, ay4));
				float amaxy = Math.max(Math.max(ay1, ay2), Math.max(ay3, ay4));
				float bx1 = b.p1.x * cos - b.p1.y * sin;
				float by1 = b.p1.x * sin + b.p1.y * cos;
				float bx2 = b.p2.x * cos - b.p2.y * sin;
				float by2 = b.p2.x * sin + b.p2.y * cos;
				float bx3 = b.p3.x * cos - b.p3.y * sin;
				float by3 = b.p3.x * sin + b.p3.y * cos;
				float bx4 = b.p4.x * cos - b.p4.y * sin;
				float by4 = b.p4.x * sin + b.p4.y * cos;
				float bminx = Math.min(Math.min(bx1, bx2), Math.min(bx3, bx4));
				float bmaxx = Math.max(Math.max(bx1, bx2), Math.max(bx3, bx4));
				float bminy = Math.min(Math.min(by1, by2), Math.min(by3, by4));
				float bmaxy = Math.max(Math.max(by1, by2), Math.max(by3, by4));

				if (amaxx < bminx || aminx > bmaxx || amaxy < bminy || aminy > bmaxy)
					intersect = false;
			}
		}
		return intersect;
	}

	public static boolean isIntersection(RackShelf2D a, RackWares2D b) {
		boolean intersect = false;
		if (a.getMaxX() >= b.getMinX() && a.getMinX() <= b.getMaxX() &&
				a.getMaxY() >= b.getMinY() && a.getMinY() <= b.getMaxY()) {
			intersect = true;
			// четырехугольники возможно пересекаются необходима детализация
//			boolean angleEquals = (a.getRackShelf().getAngle() % 90) == (b.getRackWares().getAngle() % 90);
			boolean angleEquals = (a.getRackShelf().getAngle() % 90) == 0;
			double angleArray[] = new double[angleEquals ? 2 : 4];
			angleArray[0] = -a.getRackShelf().getAngle() % 90;
			angleArray[1] = angleArray[0] + 90;
			if (!angleEquals) {
//				angleArray[2] = -b.getRackWares().getAngle() % 90;
				angleArray[2] = 0;
				angleArray[3] = angleArray[2] + 90;
			}

			for (int i = 0; intersect && i < angleArray.length; i++) {
				final float sin = (float) Math.sin(Math.toRadians(angleArray[i]));
				final float cos = (float) Math.cos(Math.toRadians(angleArray[i]));
				float ax1 = a.p1.x * cos - a.p1.y * sin;
				float ay1 = a.p1.x * sin + a.p1.y * cos;
				float ax2 = a.p2.x * cos - a.p2.y * sin;
				float ay2 = a.p2.x * sin + a.p2.y * cos;
				float ax3 = a.p3.x * cos - a.p3.y * sin;
				float ay3 = a.p3.x * sin + a.p3.y * cos;
				float ax4 = a.p4.x * cos - a.p4.y * sin;
				float ay4 = a.p4.x * sin + a.p4.y * cos;
				float aminx = Math.min(Math.min(ax1, ax2), Math.min(ax3, ax4));
				float amaxx = Math.max(Math.max(ax1, ax2), Math.max(ax3, ax4));
				float aminy = Math.min(Math.min(ay1, ay2), Math.min(ay3, ay4));
				float amaxy = Math.max(Math.max(ay1, ay2), Math.max(ay3, ay4));
				float bx1 = b.p1.x * cos - b.p1.y * sin;
				float by1 = b.p1.x * sin + b.p1.y * cos;
				float bx2 = b.p2.x * cos - b.p2.y * sin;
				float by2 = b.p2.x * sin + b.p2.y * cos;
				float bx3 = b.p3.x * cos - b.p3.y * sin;
				float by3 = b.p3.x * sin + b.p3.y * cos;
				float bx4 = b.p4.x * cos - b.p4.y * sin;
				float by4 = b.p4.x * sin + b.p4.y * cos;
				float bminx = Math.min(Math.min(bx1, bx2), Math.min(bx3, bx4));
				float bmaxx = Math.max(Math.max(bx1, bx2), Math.max(bx3, bx4));
				float bminy = Math.min(Math.min(by1, by2), Math.min(by3, by4));
				float bmaxy = Math.max(Math.max(by1, by2), Math.max(by3, by4));

				if (amaxx < bminx || aminx > bmaxx || amaxy < bminy || aminy > bmaxy)
					intersect = false;
			}
		}
		return intersect;
	}

	public static boolean isIntersection(RackWares2D a, RackWares2D b) {
		boolean intersect = false;
		if (a.getMaxX() >= b.getMinX() && a.getMinX() <= b.getMaxX() &&
				a.getMaxY() >= b.getMinY() && a.getMinY() <= b.getMaxY()) {
			intersect = true;
			// четырехугольники возможно пересекаются необходима детализация
//			boolean angleEquals = (a.getRackShelf().getAngle() % 90) == (b.getRackWares().getAngle() % 90);
			boolean angleEquals = true;
			double angleArray[] = new double[angleEquals ? 2 : 4];
//			angleArray[0] = -a.getRackShelf().getAngle() % 90;
			angleArray[0] = 0;
			angleArray[1] = angleArray[0] + 90;
			if (!angleEquals) {
//				angleArray[2] = -b.getRackWares().getAngle() % 90;
				angleArray[2] = 0;
				angleArray[3] = angleArray[2] + 90;
			}

			for (int i = 0; intersect && i < angleArray.length; i++) {
				final float sin = (float) Math.sin(Math.toRadians(angleArray[i]));
				final float cos = (float) Math.cos(Math.toRadians(angleArray[i]));
				float ax1 = a.p1.x * cos - a.p1.y * sin;
				float ay1 = a.p1.x * sin + a.p1.y * cos;
				float ax2 = a.p2.x * cos - a.p2.y * sin;
				float ay2 = a.p2.x * sin + a.p2.y * cos;
				float ax3 = a.p3.x * cos - a.p3.y * sin;
				float ay3 = a.p3.x * sin + a.p3.y * cos;
				float ax4 = a.p4.x * cos - a.p4.y * sin;
				float ay4 = a.p4.x * sin + a.p4.y * cos;
				float aminx = Math.min(Math.min(ax1, ax2), Math.min(ax3, ax4));
				float amaxx = Math.max(Math.max(ax1, ax2), Math.max(ax3, ax4));
				float aminy = Math.min(Math.min(ay1, ay2), Math.min(ay3, ay4));
				float amaxy = Math.max(Math.max(ay1, ay2), Math.max(ay3, ay4));
				float bx1 = b.p1.x * cos - b.p1.y * sin;
				float by1 = b.p1.x * sin + b.p1.y * cos;
				float bx2 = b.p2.x * cos - b.p2.y * sin;
				float by2 = b.p2.x * sin + b.p2.y * cos;
				float bx3 = b.p3.x * cos - b.p3.y * sin;
				float by3 = b.p3.x * sin + b.p3.y * cos;
				float bx4 = b.p4.x * cos - b.p4.y * sin;
				float by4 = b.p4.x * sin + b.p4.y * cos;
				float bminx = Math.min(Math.min(bx1, bx2), Math.min(bx3, bx4));
				float bmaxx = Math.max(Math.max(bx1, bx2), Math.max(bx3, bx4));
				float bminy = Math.min(Math.min(by1, by2), Math.min(by3, by4));
				float bmaxy = Math.max(Math.max(by1, by2), Math.max(by3, by4));

				if (amaxx < bminx || aminx > bmaxx || amaxy < bminy || aminy > bmaxy)
					intersect = false;
			}
		}
		return intersect;
	}
}
