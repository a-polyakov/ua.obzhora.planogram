package planograma.utils;

/**
 * Date: 20.05.12
 * Time: 5:06
 *
 * @author Alexandr Polyakov
 */
public class GeometryUtils {
	public static boolean intersectsLine(double ax1, double ay1, double ax2, double ay2, double bx1, double by1, double bx2, double by2) {
		double v1 = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1);
		double v2 = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1);
		double v3 = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1);
		double v4 = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1);
		return ((v1 * v2 <= 0) && (v3 * v4 <= 0));
	}

	public static boolean intersectSegment(double ax1, double ay1, double ax2, double ay2, double bx1, double by1, double bx2, double by2) {
		return false;
	}

	public static void main(String args[]) {
		System.out.println(intersectsLine(2598, 1431, 2598, 1577,
				2735, 1701, 2735, 1151));
	}
}
