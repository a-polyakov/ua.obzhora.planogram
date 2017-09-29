package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.OptionsConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Настройка системы
 *
 * @author Alexandr Polyakov
 */
public class Options implements IJsonObject {
	/**
	 * Уникальное наименование настройки
	 */
	private String name_option;
	/**
	 * Значение настройки
	 */
	private Integer value;

	public Options(String name_option, Integer value) {
		this.name_option = name_option;
		this.value = value;
	}

	public String getName_option() {
		return name_option;
	}

	public void setName_option(String name_option) {
		this.name_option = name_option;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Options(final ResultSet resultSet) throws SQLException {
		name_option = resultSet.getString(OptionsConst.NAME_OPTION);
		value = resultSet.getInt(OptionsConst.VALUE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		JsonUtils.set(jsonObject, OptionsConst.NAME_OPTION, name_option);
		JsonUtils.set(jsonObject, OptionsConst.VALUE, value);
		return jsonObject;
	}
}
