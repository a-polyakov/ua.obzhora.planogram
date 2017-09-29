package planograma.data.geometry;

import planograma.data.RackTemplate;
import planograma.utils.geometry.Rectangle2D;

/**
 * Date: 27.09.12
 * Time: 8:56
 *
 * @author Alexandr Polyakov
 */
public class RackTemplate2D extends Rectangle2D {
	private final RackTemplate rackTemplate;

	public RackTemplate2D(final RackTemplate rackTemplate) {
		super(0, 0, rackTemplate.getReal_width(), rackTemplate.getHeight());
		this.rackTemplate = rackTemplate;
	}

	public RackTemplate getRackTemplate() {
		return rackTemplate;
	}
}
