package planograma.servlet.wares.rackWaresPlacementSaveHelp;

import planograma.data.geometry.RackWares2D;
import planograma.utils.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 17.06.12
 * Time: 20:21
 *
 * @author Alexandr Polyakov
 */
public class GroupRackWares extends Rectangle2D {
	final int code_wares;
	final List<RackWares2D> waresInGroup;

	public GroupRackWares(final RackWares2D rackWares2D) {
		super(rackWares2D.getMinX(), rackWares2D.getMaxY(), rackWares2D.getMaxX(), rackWares2D.getMinY());
		code_wares = rackWares2D.getRackWares().getCode_wares();
		waresInGroup = new ArrayList<RackWares2D>();
		waresInGroup.add(rackWares2D);
	}

	public int getCode_wares() {
		return code_wares;
	}

	public List<RackWares2D> getWaresInGroup() {
		return waresInGroup;
	}

	public void add(final RackWares2D rackWares2D) {
		if (code_wares == rackWares2D.getRackWares().getCode_wares()) {
			p1.setX(Math.min(p1.getX(), rackWares2D.getMinX()));
			p1.setY(Math.max(p1.getY(), rackWares2D.getMaxY()));
			p2.setX(Math.max(p2.getX(), rackWares2D.getMaxX()));
			p2.setY(Math.min(p2.getY(), rackWares2D.getMinY()));
			waresInGroup.add(rackWares2D);
		}
	}

	public void calc() {
		float x1 = Integer.MAX_VALUE;
		float y1 = Integer.MAX_VALUE;
		float x2 = Integer.MIN_VALUE;
		float y2 = Integer.MIN_VALUE;
		for (final RackWares2D rackWares2D : waresInGroup) {
			x1 = Math.min(x1, rackWares2D.getMinX());
			y1 = Math.min(y1, rackWares2D.getMinY());
			x2 = Math.max(x2, rackWares2D.getMaxX());
			y2 = Math.max(y2, rackWares2D.getMaxY());
		}
		p1.setX(x1);
		p1.setY(y1);
		p2.setX(x2);
		p2.setY(y2);
	}

}
