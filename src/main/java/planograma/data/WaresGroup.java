package planograma.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import planograma.constant.data.WaresGroupConst;
import planograma.utils.json.IJsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * группы товаров
 * Date: 27.04.12
 * Time: 3:49
 *
 * @author Alexandr Polyakov
 */
public class WaresGroup implements IJsonObject {
	/**
	 * Код группы
	 */
	private Integer code_group_wares;
	/**
	 * Код родительской группы
	 */
	private Integer code_parent_group_wares;
	/**
	 * Наименование группы
	 */
	private String name;

	private final List<WaresGroup> children = new ArrayList<WaresGroup>();

	public WaresGroup(Integer code_group_wares, Integer code_parent_group_wares, String name) {
		this.code_group_wares = code_group_wares;
		this.code_parent_group_wares = code_parent_group_wares;
		this.name = name;
	}

	public Integer getCode_group_wares() {
		return code_group_wares;
	}

	public void setCode_group_wares(Integer code_group_wares) {
		this.code_group_wares = code_group_wares;
	}

	public Integer getCode_parent_group_wares() {
		return code_parent_group_wares;
	}

	public void setCode_parent_group_wares(Integer code_parent_group_wares) {
		this.code_parent_group_wares = code_parent_group_wares;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WaresGroup> getChildren() {
		return children;
	}

	public WaresGroup(final ResultSet resultSet) throws SQLException {
		code_group_wares = resultSet.getInt(WaresGroupConst.CODE_GROUP_WARES);
		code_parent_group_wares = resultSet.getInt(WaresGroupConst.CODE_PARENT_GROUP_WARES);
		name = resultSet.getString(WaresGroupConst.NAME);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(WaresGroupConst.CODE_GROUP_WARES, code_group_wares);
		jsonObject.addProperty(WaresGroupConst.CODE_PARENT_GROUP_WARES, code_parent_group_wares);
		jsonObject.addProperty(WaresGroupConst.NAME, name);
		final JsonArray jsonArray = new JsonArray();
		for (final WaresGroup child : children) {
			jsonArray.add(child.toJsonObject());
		}
		jsonObject.add("children", jsonArray);
		return jsonObject;
	}
}
