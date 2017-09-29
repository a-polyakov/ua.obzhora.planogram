package planograma.data;

import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Состояние стеллажа в зале (отслеживать изменение положения стеллажа в зале)
 * Date: 18.03.12
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class RackStateInSector extends AbstractRackState {
	public RackStateInSector(Integer code_rack, EStateRack state_rack, Date date_draft, Integer user_draft, Date date_active, Integer user_active, Date date_complete, Integer user_complete) {
		super(code_rack, state_rack, date_draft, user_draft, date_active, user_active, date_complete, user_complete);
	}

	public RackStateInSector(ResultSet resultSet) throws SQLException {
		super(resultSet);
	}

	public RackStateInSector(JsonObject rackJson) {
		super(rackJson);
	}
}
