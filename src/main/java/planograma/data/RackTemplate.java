package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackTemplateConst;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Шаблон стеллажа
 * Date: 18.03.12
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class RackTemplate extends AbstractRack {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack_template;
	/**
	 * Состояние стеллажа
	 */
	private EStateRack state_rack_template;
	/**
	 * Название
	 */
	private String name_rack_template;
	/**
	 * Пользователь изъявший для редактирования
	 */
	private Integer user_draft;
	/**
	 * Дата изъятия для редактирования
	 */
	private Date date_draft;

	public RackTemplate(Integer code_rack_template, EStateRack state_rack_template, String name_rack_template, Integer length, Integer width, Integer height, LoadSide load_side, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer user_draft, Date date_draft, Integer real_length, Integer real_width, Integer real_height, Integer x_offset, Integer y_offset, Integer z_offset) {
		super(length, width, height, load_side, user_insert, date_insert, user_update, date_update, real_length, real_width, real_height, x_offset, y_offset, z_offset);
		this.code_rack_template = code_rack_template;
		this.state_rack_template = state_rack_template;
		this.name_rack_template = name_rack_template;
		this.user_draft = user_draft;
		this.date_draft = date_draft;
	}

	public Integer getCode_rack_template() {
		return code_rack_template;
	}

	public void setCode_rack_template(Integer code_rack_template) {
		this.code_rack_template = code_rack_template;
	}

	public EStateRack getState_rack_template() {
		return state_rack_template;
	}

	public void setState_rack_template(EStateRack state_rack_template) {
		this.state_rack_template = state_rack_template;
	}

	public void setState_rack_template(String state_rack_template) {
		this.state_rack_template = (state_rack_template != null) ? EStateRack.valueOf(state_rack_template) : null;
	}

	public String getName_rack_template() {
		return name_rack_template;
	}

	public void setName_rack_template(String name_rack_template) {
		this.name_rack_template = name_rack_template;
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

	public RackTemplate(final ResultSet resultSet) throws SQLException {
		super(resultSet);
		code_rack_template = resultSet.getInt(RackTemplateConst.CODE_RACK_TEMPLATE);
		setState_rack_template(resultSet.getString(RackTemplateConst.STATE_RACK_TEMPLATE));
		name_rack_template = resultSet.getString(RackTemplateConst.NAME_RACK_TEMPLATE);
		code_rack_template = resultSet.getInt(RackTemplateConst.CODE_RACK_TEMPLATE);
		user_draft = resultSet.getInt(RackTemplateConst.USER_DRAFT);
		date_draft = resultSet.getTimestamp(RackTemplateConst.DATE_DRAFT);
	}

	public RackTemplate(final JsonObject rackJson) {
		super(rackJson);
		code_rack_template = JsonUtils.getInteger(rackJson, RackTemplateConst.CODE_RACK_TEMPLATE);
		setState_rack_template(JsonUtils.getString(rackJson, RackTemplateConst.STATE_RACK_TEMPLATE));
		name_rack_template = JsonUtils.getString(rackJson, RackTemplateConst.NAME_RACK_TEMPLATE);
		user_draft = JsonUtils.getInteger(rackJson, RackTemplateConst.USER_DRAFT);
		date_draft = JsonUtils.getDate(rackJson, RackTemplateConst.DATE_DRAFT);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = super.toJsonObject();
		jsonObject.addProperty(RackTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		jsonObject.addProperty(RackTemplateConst.STATE_RACK_TEMPLATE, (state_rack_template != null) ? state_rack_template.name() : null);
		jsonObject.addProperty(RackTemplateConst.NAME_RACK_TEMPLATE, name_rack_template);
		jsonObject.addProperty(RackTemplateConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, RackTemplateConst.DATE_DRAFT, date_draft);
		return jsonObject;
	}
}
