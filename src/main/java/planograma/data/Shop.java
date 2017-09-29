package planograma.data;


import com.google.gson.JsonObject;
import planograma.constant.data.ShopConst;
import planograma.utils.json.IJsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * точки продаж
 * Date: 26.02.12
 * Time: 3:51
 *
 * @author Alexandr Polyakov
 */
public class Shop implements IJsonObject {
	/**
	 * Код ТП
	 */
	private Integer code_shop;
	/**
	 * Название
	 */
	private String name_shop;

	public Shop(Integer code_shop, String name_shop) {
		this.code_shop = code_shop;
		this.name_shop = name_shop;
	}

	public Integer getCode_shop() {
		return code_shop;
	}

	public void setCode_shop(Integer code_shop) {
		this.code_shop = code_shop;
	}

	public String getName_shop() {
		return name_shop;
	}

	public void setName_shop(String name_shop) {
		this.name_shop = name_shop;
	}

	public Shop(final ResultSet resultSet) throws SQLException {
		code_shop = resultSet.getInt(ShopConst.CODE_SHOP);
		name_shop = resultSet.getString(ShopConst.NAME_SHOP);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(ShopConst.CODE_SHOP, code_shop);
		jsonObject.addProperty(ShopConst.NAME_SHOP, name_shop);
		return jsonObject;
	}
}
