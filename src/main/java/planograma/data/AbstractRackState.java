package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.AbstractRackStateConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Состояние стеллажа
 * Date: 18.03.12
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public abstract class AbstractRackState implements IJsonObject {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Состояние стеллажа
	 */
	private EStateRack state_rack;
	/**
	 * дата изъятия для редактирования
	 */
	private Date date_draft;
	/**
	 * пользователь изъявший для редактирования
	 */
	private Integer user_draft;
	/**
	 * дата утверждения (редактирование окончено)
	 */
	private Date date_active;
	/**
	 * пользователь утвердивший стеллаж
	 */
	private Integer user_active;
	/**
	 * дата выполнения
	 */
	private Date date_complete;
	/**
	 * пользователь выполнивший стеллаж
	 */
	private Integer user_complete;

	public AbstractRackState(Integer code_rack, EStateRack state_rack, Date date_draft, Integer user_draft, Date date_active, Integer user_active, Date date_complete, Integer user_complete) {
		this.code_rack = code_rack;
		this.state_rack = state_rack;
		this.date_draft = date_draft;
		this.user_draft = user_draft;
		this.date_active = date_active;
		this.user_active = user_active;
		this.date_complete = date_complete;
		this.user_complete = user_complete;
	}

	public Integer getCode_rack() {
		return code_rack;
	}

	public void setCode_rack(Integer code_rack) {
		this.code_rack = code_rack;
	}

	public EStateRack getState_rack() {
		return state_rack;
	}

	public String getState_rackAtStr() {
		return (state_rack != null) ? state_rack.name() : null;
	}

	public void setState_rack(EStateRack state_rack) {
		this.state_rack = state_rack;
	}

	public void setState_rack(String state_rack) {
		this.state_rack = (state_rack != null) ? EStateRack.valueOf(state_rack) : null;
	}

	public Integer getUser_draft() {
		return user_draft;
	}

	public void setUser_draft(Integer user_draft) {
		this.user_draft = user_draft;
	}

	public Date getDate_draft() {
		return date_draft;
	}

	public void setDate_draft(Date date_draft) {
		this.date_draft = date_draft;
	}

	public Date getDate_active() {
		return date_active;
	}

	public void setDate_active(Date date_active) {
		this.date_active = date_active;
	}

	public Integer getUser_active() {
		return user_active;
	}

	public void setUser_active(Integer user_active) {
		this.user_active = user_active;
	}

	public Date getDate_complete() {
		return date_complete;
	}

	public void setDate_complete(Date date_complete) {
		this.date_complete = date_complete;
	}

	public Integer getUser_complete() {
		return user_complete;
	}

	public void setUser_complete(Integer user_complete) {
		this.user_complete = user_complete;
	}

	public AbstractRackState(final ResultSet resultSet) throws SQLException {
		code_rack = resultSet.getInt(AbstractRackStateConst.CODE_RACK);
		setState_rack(resultSet.getString(AbstractRackStateConst.STATE_RACK));
		date_draft = resultSet.getTimestamp(AbstractRackStateConst.DATE_DRAFT);
		user_draft = resultSet.getInt(AbstractRackStateConst.USER_DRAFT);
		date_active = resultSet.getTimestamp(AbstractRackStateConst.DATE_ACTIVE);
		user_active = resultSet.getInt(AbstractRackStateConst.USER_ACTIVE);
		date_complete = resultSet.getTimestamp(AbstractRackStateConst.DATE_COMPLETE);
		user_complete = resultSet.getInt(AbstractRackStateConst.USER_COMPLETE);
	}

	public AbstractRackState(final JsonObject rackJson) {
		code_rack = JsonUtils.getInteger(rackJson, AbstractRackStateConst.CODE_RACK);
		setState_rack(JsonUtils.getString(rackJson, AbstractRackStateConst.STATE_RACK));
		date_draft = JsonUtils.getDate(rackJson, AbstractRackStateConst.DATE_DRAFT);
		user_draft = JsonUtils.getInteger(rackJson, AbstractRackStateConst.USER_DRAFT);
		date_active = JsonUtils.getDate(rackJson, AbstractRackStateConst.DATE_ACTIVE);
		user_active = JsonUtils.getInteger(rackJson, AbstractRackStateConst.USER_ACTIVE);
		date_complete = JsonUtils.getDate(rackJson, AbstractRackStateConst.DATE_COMPLETE);
		user_complete = JsonUtils.getInteger(rackJson, AbstractRackStateConst.USER_COMPLETE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(AbstractRackStateConst.CODE_RACK, code_rack);
		jsonObject.addProperty(AbstractRackStateConst.STATE_RACK, getState_rackAtStr());
		JsonUtils.set(jsonObject, AbstractRackStateConst.DATE_DRAFT, date_draft);
		jsonObject.addProperty(AbstractRackStateConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, AbstractRackStateConst.DATE_ACTIVE, date_active);
		jsonObject.addProperty(AbstractRackStateConst.USER_ACTIVE, user_active);
		JsonUtils.set(jsonObject, AbstractRackStateConst.DATE_COMPLETE, date_complete);
		jsonObject.addProperty(AbstractRackStateConst.USER_COMPLETE, user_complete);
		return jsonObject;
	}
}
