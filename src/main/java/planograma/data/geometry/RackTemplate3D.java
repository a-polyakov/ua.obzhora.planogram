package planograma.data.geometry;

import planograma.data.LoadSide;
import planograma.data.RackTemplate;
import planograma.utils.geometry.Box3D;
import planograma.utils.geometry.Point3D;

/**
 * Date: 27.09.12
 * Time: 8:56
 *
 * @author Alexandr Polyakov
 */
public class RackTemplate3D extends Box3D {
	private final RackTemplate rackTemplate;

	private RackTemplate3D(final RackTemplate rackTemplate, final Point3D p1, final Point3D p2) {
		// TODO
		super(p1, 0, 0, 0, 0, 0, 0);
		this.rackTemplate = rackTemplate;
	}

	public static RackTemplate3D init(final RackTemplate rackTemplate) {
		float w, h, l;
		if (rackTemplate.getLoad_side() == LoadSide.F) {
			w = rackTemplate.getReal_length();
			h = rackTemplate.getReal_height();
			l = rackTemplate.getReal_width();
		} else {
			w = rackTemplate.getReal_length();
			h = rackTemplate.getReal_width();
			l = rackTemplate.getReal_height();
		}
		RackTemplate3D result = new RackTemplate3D(rackTemplate, new Point3D(0, 0, 0), new Point3D(w, h, l));
		return result;
	}

	public RackTemplate getRackTemplate() {
		return rackTemplate;
	}
}
