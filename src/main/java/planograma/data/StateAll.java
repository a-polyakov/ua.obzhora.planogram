package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.StateAllConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 20.03.12
 * Time: 23:31
 *
 * @author Alexandr Polyakov
 */
public class StateAll implements IJsonObject {
	/**
	 * группа
	 */
	private int part_state;
	/**
	 * ключь
	 */
	private String abr_state;
	/**
	 * значение
	 */
	private String state;
	private char sing_activity;
	/**
	 * дополнительный параметр
	 */
	private String description;
	private Integer part_state_parents;
	private String is_version_29;

	public StateAll(int part_state, String abr_state, String state, char sing_activity, String description, Integer part_state_parents, String is_version_29) {
		this.part_state = part_state;
		this.abr_state = abr_state;
		this.state = state;
		this.sing_activity = sing_activity;
		this.description = description;
		this.part_state_parents = part_state_parents;
		this.is_version_29 = is_version_29;
	}

	public int getPart_state() {
		return part_state;
	}

	public void setPart_state(int part_state) {
		this.part_state = part_state;
	}

	public String getAbr_state() {
		return abr_state;
	}

	public void setAbr_state(String abr_state) {
		this.abr_state = abr_state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public char getSing_activity() {
		return sing_activity;
	}

	public void setSing_activity(char sing_activity) {
		this.sing_activity = sing_activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPart_state_parents() {
		return part_state_parents;
	}

	public void setPart_state_parents(Integer part_state_parents) {
		this.part_state_parents = part_state_parents;
	}

	public String getIs_version_29() {
		return is_version_29;
	}

	public void setIs_version_29(String is_version_29) {
		this.is_version_29 = is_version_29;
	}

	public StateAll(final ResultSet resultSet) throws SQLException {
		part_state = resultSet.getInt(StateAllConst.PART_STATE);
		abr_state = resultSet.getString(StateAllConst.ABR_STATE).trim();
		state = resultSet.getString(StateAllConst.STATE);
		sing_activity = resultSet.getString(StateAllConst.SIGN_ACTIVITY).charAt(0);
		description = resultSet.getString(StateAllConst.DESCRIPTION);
		part_state_parents = resultSet.getInt(StateAllConst.PART_STATE_PARENTS);
		is_version_29 = resultSet.getString(StateAllConst.IS_VERSION_29);
	}

	public StateAll(final JsonObject jsonObject) {
		part_state = JsonUtils.getInteger(jsonObject, StateAllConst.PART_STATE);
		abr_state = JsonUtils.getString(jsonObject, StateAllConst.ABR_STATE);
		state = JsonUtils.getString(jsonObject, StateAllConst.STATE);
		sing_activity = JsonUtils.getString(jsonObject, StateAllConst.SIGN_ACTIVITY).charAt(0);
		description = JsonUtils.getString(jsonObject, StateAllConst.DESCRIPTION);
		part_state_parents = JsonUtils.getInteger(jsonObject, StateAllConst.PART_STATE_PARENTS);
		is_version_29 = JsonUtils.getString(jsonObject, StateAllConst.IS_VERSION_29);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(StateAllConst.PART_STATE, part_state);
		jsonObject.addProperty(StateAllConst.ABR_STATE, abr_state);
		jsonObject.addProperty(StateAllConst.STATE, state);
		jsonObject.addProperty(StateAllConst.SIGN_ACTIVITY, sing_activity);
		jsonObject.addProperty(StateAllConst.DESCRIPTION, description);
		jsonObject.addProperty(StateAllConst.PART_STATE_PARENTS, part_state_parents);
		jsonObject.addProperty(StateAllConst.IS_VERSION_29, is_version_29);
		return jsonObject;
	}
}
