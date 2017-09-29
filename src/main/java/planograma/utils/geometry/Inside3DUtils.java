package planograma.utils.geometry;

/**
 * Утилитный класс определяющий полное перекрытие одного обекта другим
 * Date: 27.09.12
 * Time: 8:34
 *
 * @author Alexandr Polyakov
 */
public class Inside3DUtils {
	/**
	 * Точка лежит внутри паралелограмма
	 *
	 * @param point3D точка
	 * @param box3D   прямоугольным параллелепипед (угол поворота = 0)
	 * @return Точка внутри прямоугольного параллелепипеда
	 */
//	public static boolean inside(final Point3D point3D, final Box3D box3D) {
//		return point3D.x >= box3D.getMinX() &&
//				point3D.x <= box3D.getMaxX() &&
//				point3D.y >= box3D.getMinY() &&
//				point3D.y <= box3D.getMaxY() &&
//				point3D.z >= box3D.getMinZ() &&
//				point3D.z <= box3D.getMaxZ();
//	}

	/**
	 * прямоугольный параллелепипед a лежит внутри прямоугольного параллелепипеда b
	 *
	 * @param a прямоугольным параллелепипед (угол поворота = 0)
	 * @param b прямоугольным параллелепипед (угол поворота = 0)
	 * @return прямоугольным параллелепипед a внутри прямоугольным параллелепипед b
	 */
//	public static boolean inside(final Box3D a, final Box3D b) {
//		return a.getMinX() >= b.getMinX() &&
//				a.getMaxX() <= b.getMaxX() &&
//				a.getMinY() >= b.getMinY() &&
//				a.getMaxY() <= b.getMaxY() &&
//				a.getMinZ() >= b.getMinZ() &&
//				a.getMaxZ() <= b.getMaxZ();
//	}
}
