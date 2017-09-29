package planograma.data.wrapper;

import com.google.gson.JsonObject;
import planograma.constant.data.AdditionUnitConst;
import planograma.constant.data.UnitDimensionConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresImageConst;
import planograma.utils.json.IJsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 27.04.12
 * Time: 5:58
 *
 * @author Alexandr Polyakov
 */
public class WaresWrapper implements IJsonObject {
	private final Integer code_group;
	private final Integer code_wares;
	private final Integer code_unit;
	private final String name_wares;
	private final String abr_unit;
	private final Integer length;
	private final Integer width;
	private final Integer height;
	private final String bar_code;
	private final Integer code_image;

	public WaresWrapper(Integer code_group, Integer code_wares, Integer code_unit, Integer code_image,
						String name_wares, String abr_unit,
						Integer length, Integer width, Integer height, String bar_code) {
		this.code_group = code_group;
		this.code_wares = code_wares;
		this.code_unit = code_unit;
		this.name_wares = name_wares;
		this.abr_unit = abr_unit;
		this.length = length;
		this.width = width;
		this.height = height;
		this.bar_code = bar_code;
		this.code_image = code_image;
	}

	public Integer getCode_wares() {
		return code_wares;
	}

	public Integer getCode_unit() {
		return code_unit;
	}

	public String getName_wares() {
		return name_wares;
	}

	public Integer getLength() {
		return length;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public String getBar_code() {
		return bar_code;
	}

	public Integer getCode_image() {
		return code_image;
	}

	public WaresWrapper(final ResultSet resultSet) throws SQLException {
		code_group = resultSet.getInt(WaresConst.CODE_GROUP);
		code_wares = resultSet.getInt(WaresConst.CODE_WARES);
		code_unit = resultSet.getInt(AdditionUnitConst.CODE_UNIT);
		code_image = resultSet.getInt(WaresImageConst.CODE_IMAGE);
		name_wares = resultSet.getString(WaresConst.NAME_WARES);
		abr_unit = resultSet.getString(UnitDimensionConst.ABR_UNIT);
		length = resultSet.getInt(AdditionUnitConst.LENGTH);
		width = resultSet.getInt(AdditionUnitConst.WIDTH);
		height = resultSet.getInt(AdditionUnitConst.HEIGHT);
		bar_code = resultSet.getString(AdditionUnitConst.BAR_CODE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(WaresConst.CODE_GROUP, code_group);
		jsonObject.addProperty(WaresConst.CODE_WARES, code_wares);
		jsonObject.addProperty(UnitDimensionConst.CODE_UNIT, code_unit);
		jsonObject.addProperty(WaresImageConst.CODE_IMAGE, code_image);
		jsonObject.addProperty(WaresConst.NAME_WARES, name_wares);
		jsonObject.addProperty(UnitDimensionConst.ABR_UNIT, abr_unit);
		jsonObject.addProperty(AdditionUnitConst.LENGTH, length);
		jsonObject.addProperty(AdditionUnitConst.WIDTH, width);
		jsonObject.addProperty(AdditionUnitConst.HEIGHT, height);
		jsonObject.addProperty(AdditionUnitConst.BAR_CODE, (bar_code != null) ? bar_code : "");
		return jsonObject;
	}
}
