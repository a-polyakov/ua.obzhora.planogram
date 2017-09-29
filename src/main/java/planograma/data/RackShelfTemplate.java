package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackShelfTemplateConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * полка шаблонного стеллажа
 * Date: 19.03.12
 * Time: 5:11
 *
 * @author Alexandr Polyakov
 */
public class RackShelfTemplate extends AbstractRackShelf implements IJsonObject {
	/**
	 * Код шаблонного стеллажа
	 */
	private Integer code_rack_template;
	/**
	 * Код полки
	 */
	private Integer code_shelf_template;

	public RackShelfTemplate(Integer code_rack_template, Integer code_shelf_template, Integer x_coord, Integer y_coord, Integer shelf_height, Integer shelf_width, Integer shelf_length, Integer angle, TypeShelf type_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update) {
		super(x_coord, y_coord, shelf_height, shelf_width, shelf_length, angle, type_shelf, user_insert, date_insert, user_update, date_update);
		this.code_rack_template = code_rack_template;
		this.code_shelf_template = code_shelf_template;
	}

	public Integer getCode_rack_template() {
		return code_rack_template;
	}

	public void setCode_rack_template(Integer code_rack_template) {
		this.code_rack_template = code_rack_template;
	}

	public Integer getCode_shelf_template() {
		return code_shelf_template;
	}

	public void setCode_shelf_template(Integer code_shelf_template) {
		this.code_shelf_template = code_shelf_template;
	}


	public RackShelfTemplate(final ResultSet resultSet) throws SQLException {
		super(resultSet);
		code_rack_template = resultSet.getInt(RackShelfTemplateConst.CODE_RACK_TEMPLATE);
		code_shelf_template = resultSet.getInt(RackShelfTemplateConst.CODE_SHELF_TEMPLATE);
	}

	public RackShelfTemplate(final JsonObject rackShelfTemplateJson) {
		super(rackShelfTemplateJson);
		code_rack_template = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.CODE_RACK_TEMPLATE);
		code_shelf_template = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.CODE_SHELF_TEMPLATE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = super.toJsonObject();
		jsonObject.addProperty(RackShelfTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		jsonObject.addProperty(RackShelfTemplateConst.CODE_SHELF_TEMPLATE, code_shelf_template);
		return jsonObject;
	}
}
