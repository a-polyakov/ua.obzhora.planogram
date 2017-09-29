package planograma.utils.geometry;

/**
 * прямоугольный параллелепипед
 * Date: 09.01.13
 * Time: 8:57
 *
 * @author Alexandr Polyakov
 */
public class Box3D {
	final protected Point3D center; // положение обекта
	private float dx;               // размер по X
	private float dy;               // размер по Y
	private float dz;               // размер по Z
	private float angleX;           // угол поворота по X
	private float angleY;           // угол поворота по Y
	private float angleZ;           // угол поворота по Z
	private double cosX, sinX;      // коефициенты для поворота по X
	private double cosY, sinY;      // коефициенты для поворота по Y
	private double cosZ, sinZ;      // коефициенты для поворота по Z
	private final Point3D points[] = {// точки
			new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 0),
			new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 0),
	};

	public Box3D(Point3D center,
				 float dx,
				 float dy,
				 float dz,
				 float angleX,
				 float angleY,
				 float angleZ) {
		this.center = center;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.angleX = angleX;
		this.angleY = angleY;
		this.angleZ = angleZ;
	}

	public void calcPosition() {
		cosX = Math.cos(Math.toRadians(angleX));
		sinX = Math.sin(Math.toRadians(angleX));
		cosY = Math.cos(Math.toRadians(angleY));
		sinY = Math.sin(Math.toRadians(angleY));
		cosZ = Math.cos(Math.toRadians(angleZ));
		sinZ = Math.sin(Math.toRadians(angleZ));


	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getDz() {
		return dz;
	}

	public void setDz(float dz) {
		this.dz = dz;
	}

	public float getAngleX() {
		return angleX;
	}

	public void setAngleX(float angleX) {
		this.angleX = angleX;
	}

	public float getAngleY() {
		return angleY;
	}

	public void setAngleY(float angleY) {
		this.angleY = angleY;
	}

	public float getAngleZ() {
		return angleZ;
	}

	public void setAngleZ(float angleZ) {
		this.angleZ = angleZ;
	}

//	public float getMinX() {
//		return Math.min(p1.getX(), p2.getX());
//	}
//
//	public float getMinY() {
//		return Math.min(p1.getY(), p2.getY());
//	}
//
//	public float getMinZ() {
//		return Math.min(p1.getZ(), p2.getZ());
//	}
//
//	public float getMaxX() {
//		return Math.max(p1.getX(), p2.getX());
//	}
//
//	public float getMaxY() {
//		return Math.max(p1.getY(), p2.getY());
//	}
//
//	public float getMaxZ() {
//		return Math.max(p1.getZ(), p2.getZ());
//	}
}
