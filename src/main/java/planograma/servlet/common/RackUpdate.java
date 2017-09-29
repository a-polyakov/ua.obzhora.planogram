package planograma.servlet.common;

import planograma.data.EStateRack;
import planograma.data.Rack;
import planograma.data.UserContext;
import planograma.model.RackModel;
import planograma.model.RackStateModel;

import java.sql.SQLException;

/**
 * Date: 16.01.13
 * Time: 9:45
 *
 * @author Alexandr Polyakov
 */
public class RackUpdate {
	public static void update(final UserContext userContext, final RackModel rackModel, final RackStateModel rackStateModel,
							  final Rack oldRack, final Rack newRack) throws SQLException {
		// стеллаж изменил свое положение в зале
		boolean changeRackInSector =
				!oldRack.getAngle().equals(newRack.getAngle())
						|| !oldRack.getX_coord().equals(newRack.getX_coord())
						|| !oldRack.getY_coord().equals(newRack.getY_coord());
		// стеллаж изменил габариты
		boolean changeRack =
				oldRack.getLoad_side() != newRack.getLoad_side()
						|| oldRack.getType_rack() != newRack.getType_rack()
						|| !oldRack.getWidth().equals(newRack.getWidth())
						|| !oldRack.getReal_width().equals(newRack.getReal_width())
						|| !oldRack.getHeight().equals(newRack.getHeight())
						|| !oldRack.getReal_height().equals(newRack.getReal_height())
						|| !oldRack.getLength().equals(newRack.getLength())
						|| !oldRack.getReal_length().equals(newRack.getReal_length())
						|| !oldRack.getX_offset().equals(newRack.getX_offset())
						|| !oldRack.getY_offset().equals(newRack.getY_offset())
						|| !oldRack.getZ_offset().equals(newRack.getZ_offset());
		// TODO измененние полок или товаров
		if (changeRackInSector || changeRack
				|| oldRack.isLock_move() != newRack.isLock_move()
				|| oldRack.isLock_size() != newRack.isLock_size()
				|| !oldRack.getName_rack().equals(newRack.getName_rack())) {
			rackModel.update(userContext, newRack);
			rackStateModel.changestate(userContext, newRack.getCode_rack(), (changeRackInSector) ? EStateRack.D : null, (changeRack) ? EStateRack.D : null);
		}
	}
}
