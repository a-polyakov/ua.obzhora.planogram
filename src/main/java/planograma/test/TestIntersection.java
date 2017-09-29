package planograma.test;

import planograma.data.Rack;
import planograma.data.geometry.Rack2D;
import planograma.utils.geometry.Intersection2DUtils;

/**
 * Date: 10.01.13
 * Time: 11:01
 *
 * @author Alexandr Polyakov
 */
public class TestIntersection {
	public static void main(String args[]) throws Exception {
		Rack a = new Rack(0, 0, null, null, 10, 10, 10, 5, 5, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		Rack b = new Rack(0, 0, null, null, 10, 10, 10, 5, 5, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");
		b = new Rack(0, 0, null, null, 10, 10, 10, 5, 5, 45, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");
		b = new Rack(0, 0, null, null, 10, 10, 10, 10, 10, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");
		b = new Rack(0, 0, null, null, 10, 10, 10, 15, 10, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");
		b = new Rack(0, 0, null, null, 10, 10, 10, 15, 15, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");
		b = new Rack(0, 0, null, null, 10, 4, 20, 5, 5, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");
		a = new Rack(0, 0, null, null, 10, 10, 10, 5, 5, 45, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		b = new Rack(0, 0, null, null, 10, 10, 10, 12, 12, 45, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != true)
			throw new Exception("Error");

		a = new Rack(0, 0, null, null, 10, 10, 10, 5, 5, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		b = new Rack(0, 0, null, null, 10, 10, 10, 20, 5, 0, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != false)
			throw new Exception("Error");
		b = new Rack(0, 0, null, null, 10, 10, 10, 20, 5, 45, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != false)
			throw new Exception("Error");
		a = new Rack(0, 0, null, null, 10, 10, 10, 5, 5, 45, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		b = new Rack(0, 0, null, null, 10, 10, 10, 13, 13, 45, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null);
		if (Intersection2DUtils.isIntersection(new Rack2D(a), new Rack2D(b)) != false)
			throw new Exception("Error");
	}
}
