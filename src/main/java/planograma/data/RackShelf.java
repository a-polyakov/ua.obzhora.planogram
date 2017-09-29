package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackShelfConst;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * полка стеллажа
 * Date: 19.03.12
 * Time: 5:11
 *
 * @author Alexandr Polyakov
 */
public class RackShelf extends AbstractRackShelf {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Код полки
	 */
	private Integer code_shelf;

	public RackShelf(Integer code_rack, Integer code_shelf, Integer x_coord, Integer y_coord, Integer shelf_height, Integer shelf_width, Integer shelf_length, Integer angle, TypeShelf type_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update) {
		super(x_coord, y_coord, shelf_height, shelf_width, shelf_length, angle, type_shelf, user_insert, date_insert, user_update, date_update);
		this.code_rack = code_rack;
		this.code_shelf = code_shelf;
	}

	public Integer getCode_rack() {
		return code_rack;
	}

	public void setCode_rack(Integer code_rack) {
		this.code_rack = code_rack;
	}

	public Integer getCode_shelf() {
		return code_shelf;
	}

	public void setCode_shelf(Integer code_shelf) {
		this.code_shelf = code_shelf;
	}

	public RackShelf(final ResultSet resultSet) throws SQLException {
		super(resultSet);
		code_rack = resultSet.getInt(RackShelfConst.CODE_RACK);
		code_shelf = resultSet.getInt(RackShelfConst.CODE_SHELF);
	}

	public RackShelf(final JsonObject rackShelfJson) throws SQLException {
		super(rackShelfJson);
		code_rack = JsonUtils.getInteger(rackShelfJson, RackShelfConst.CODE_RACK);
		code_shelf = JsonUtils.getInteger(rackShelfJson, RackShelfConst.CODE_SHELF);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = super.toJsonObject();
		jsonObject.addProperty(RackShelfConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackShelfConst.CODE_SHELF, code_shelf);
		return jsonObject;
	}
}
