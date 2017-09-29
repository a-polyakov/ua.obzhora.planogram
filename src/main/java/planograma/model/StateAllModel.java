package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.*;
import planograma.data.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 20.03.12
 * Time: 23:39
 *
 * @author Alexandr Polyakov
 */
public class StateAllModel {

	private static final Logger LOG = Logger.getLogger(StateAllModel.class);

	private static final String Q_LIST = "select * " +
			"from " + StateAllConst.TABLE_NAME + " " +
			"where " + StateAllConst.PART_STATE + " = ?";

	public List<StateAll> list(final UserContext userContext, int part_state) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, part_state);
		final ResultSet resultSet = ps.executeQuery();
		final List<StateAll> list = new ArrayList<StateAll>();
		while (resultSet.next()) {
			final StateAll item = new StateAll(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private static final String Q_INIT = "select * " +
			"from " + StateAllConst.TABLE_NAME + " " +
			"where " + StateAllConst.PART_STATE + " in (?,?,?,?) " +
			"order by " + StateAllConst.PART_STATE;

	public void initEnum(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_INIT);
		ps.setInt(1, RackStateConst.STATE_ALL_PART_STATE_RACK_STATE);        // -63 STATE_RACK
		ps.setInt(2, RackConst.STATE_ALL_PART_STATE_LOAD_SIDE);                // -64 LOAD_SIDE
		ps.setInt(3, RackShelfConst.STATE_ALL_PART_STATE_TYPE_SHELF);        // -67 TYPE_SHELF
		ps.setInt(4, RackConst.STATE_ALL_PART_STATE_TYPE_RACK);                // -68 TYPE_RACK
		final ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			final StateAll item = new StateAll(resultSet);
			switch (item.getPart_state()) {
				case RackStateConst.STATE_ALL_PART_STATE_RACK_STATE:
					final EStateRack stateRack = EStateRack.valueOf(item.getAbr_state());
					stateRack.setDesc(item.getState());
					stateRack.setColor(item.getDescription());
					break;
				case RackConst.STATE_ALL_PART_STATE_LOAD_SIDE:
					final LoadSide loadSide = LoadSide.valueOf(item.getAbr_state());
					loadSide.setDesc(item.getState());
					break;
				case RackConst.STATE_ALL_PART_STATE_TYPE_RACK:
					final ETypeRack typeRack = ETypeRack.valueOf(item.getAbr_state());
					typeRack.setDesc(item.getState());
					typeRack.setColor(item.getDescription());
					break;
				case RackShelfConst.STATE_ALL_PART_STATE_TYPE_SHELF:
					final TypeShelf typeShelf = TypeShelf.valueOf(item.getAbr_state());
					typeShelf.setDesc(item.getState());
					typeShelf.setColor(item.getDescription());
					break;
				case RackWaresConst.STATE_ALL_PART_STATE_TYPE_RACK_WARES:
					final TypeRackWares typeRackWares = TypeRackWares.valueOf(item.getAbr_state());
					typeRackWares.setDesc(item.getState());
					break;
			}
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static StateAllModel instance = new StateAllModel();

	public static StateAllModel getInstance() {
		return instance;
	}

	private StateAllModel() {
	}
}
