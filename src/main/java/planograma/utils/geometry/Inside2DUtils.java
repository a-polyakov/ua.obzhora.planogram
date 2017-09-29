package planograma.utils.geometry;

/**
 * Утилитный класс определяющий полное перекрытие одного обекта другим
 * Date: 27.09.12
 * Time: 8:34
 *
 * @author Alexandr Polyakov
 */
public class Inside2DUtils {
	/**
	 * Точка лежит внутри прямоугольника
	 *
	 * @param point2D     точка
	 * @param rectangle2D прямоугольник (угол поворота = 0)
	 * @return Точка внутри прямоугольника
	 */
	public static boolean inside(final Point2D point2D, final Rectangle2D rectangle2D) {
		return point2D.x >= rectangle2D.getMinX() &&
				point2D.x <= rectangle2D.getMaxX() &&
				point2D.y >= rectangle2D.getMinY() &&
				point2D.y <= rectangle2D.getMaxY();
	}

	/**
	 * Прямоугольник a лежит внутри прямоугольника b
	 *
	 * @param a прямоугольник (угол поворота = 0)
	 * @param b прямоугольник (угол поворота = 0)
	 * @return Прямоугольник a внутри прямоугольника b
	 */
	public static boolean inside(final Rectangle2D a, final Rectangle2D b) {
		return a.getMinX() >= b.getMinX() &&
				a.getMaxX() <= b.getMaxX() &&
				a.getMinY() >= b.getMinY() &&
				a.getMaxY() <= b.getMaxY();
	}

	/**
	 * Четырехугольник a лежит внутри прямоугольника b
	 *
	 * @param a четырехугольник
	 * @param b прямоугольник (угол поворота = 0)
	 * @return Четырехугольник a внутри прямоугольника b
	 */
	public static boolean inside(final Quadrilateral2D a, final Rectangle2D b) {
		return a.getMinX() >= b.getMinX() &&
				a.getMaxX() <= b.getMaxX() &&
				a.getMinY() >= b.getMinY() &&
				a.getMaxY() <= b.getMaxY();
	}
}
