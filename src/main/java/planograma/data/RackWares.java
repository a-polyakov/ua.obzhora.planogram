package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.*;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * товар стеллажа
 * Date: 18.03.12
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class RackWares implements IJsonObject {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Код товара
	 */
	private Integer code_wares;
	/**
	 * Код единицы измерения
	 */
	private Integer code_unit;
	/**
	 * Код товара на стеллаже (первичный ключ)
	 */
	private Integer code_wares_on_rack;
	/**
	 * Тип товара
	 */
	private TypeRackWares type_wares_on_rack;
	/**
	 * Номер по порядку па стеллаже
	 */
	private Integer order_number_on_rack;
	/**
	 * Положение на стеллаже по ширине
	 */
	private Integer position_x;
	/**
	 * Положение на стеллаже по высоте
	 */
	private Integer position_y;
	/**
	 * Длина(глубина), см
	 */
	private Integer wares_length;
	/**
	 * Ширина, см
	 */
	private Integer wares_width;
	/**
	 * Высота, см
	 */
	private Integer wares_height;
	/**
	 * Количество товара выстроеное в длину (глубину)
	 * расчитывается автоматически, но может редактироваться
	 */
	private Integer count_length_on_shelf;
	/**
	 * Автор
	 */
	private Integer user_insert;
	/**
	 * Дата создания
	 */
	private Date date_insert;
	/**
	 * Редактор
	 */
	private Integer user_update;
	/**
	 * Дата изменения
	 */
	private Date date_update;
	// из связных сущностей
	/**
	 * Код изображения
	 */
	private final Integer code_image;
	/**
	 * Название товара
	 */
	private final String name_wares;
	/**
	 * Краткое название единицы измерения товара
	 */
	private final String abr_unit;
	/**
	 * Штрихкод товара
	 */
	private final String bar_code;


	public RackWares(Integer code_rack, Integer code_wares, Integer code_unit, Integer code_wares_on_rack, TypeRackWares type_wares_on_rack, Integer order_number_on_rack, Integer position_x, Integer position_y, Integer wares_length, Integer wares_width, Integer wares_height, Integer count_length_on_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer code_image, String name_wares, String abr_unit, String bar_code) {
		this.code_rack = code_rack;
		this.code_wares = code_wares;
		this.code_unit = code_unit;
		this.code_wares_on_rack = code_wares_on_rack;
		this.type_wares_on_rack = type_wares_on_rack;
		this.order_number_on_rack = order_number_on_rack;
		this.position_x = position_x;
		this.position_y = position_y;
		this.wares_length = wares_length;
		this.wares_width = wares_width;
		this.wares_height = wares_height;
		this.count_length_on_shelf = count_length_on_shelf;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
		this.code_image = code_image;
		this.name_wares = name_wares;
		this.abr_unit = abr_unit;
		this.bar_code = bar_code;
	}

	public Integer getCode_rack() {
		return code_rack;
	}

	public void setCode_rack(Integer code_rack) {
		this.code_rack = code_rack;
	}

	public Integer getCode_wares() {
		return code_wares;
	}

	public void setCode_wares(Integer code_wares) {
		this.code_wares = code_wares;
	}

	public Integer getCode_unit() {
		return code_unit;
	}

	public void setCode_unit(Integer code_unit) {
		this.code_unit = code_unit;
	}

	public Integer getCode_wares_on_rack() {
		return code_wares_on_rack;
	}

	public void setCode_wares_on_rack(Integer code_wares_on_rack) {
		this.code_wares_on_rack = code_wares_on_rack;
	}

	public TypeRackWares getType_wares_on_rack() {
		return type_wares_on_rack;
	}

	public void setType_wares_on_rack(TypeRackWares type_wares_on_rack) {
		this.type_wares_on_rack = type_wares_on_rack;
	}

	public String getType_wares_on_rackAtStr() {
		return (type_wares_on_rack != null) ? type_wares_on_rack.name() : "";
	}

	public void setType_wares_on_rack(String type_wares_on_rack) {
		this.type_wares_on_rack = TypeRackWares.valueOf(type_wares_on_rack);
	}

	public Integer getOrder_number_on_rack() {
		return order_number_on_rack;
	}

	public void setOrder_number_on_rack(Integer order_number_on_rack) {
		this.order_number_on_rack = order_number_on_rack;
	}

	public Integer getPosition_x() {
		return position_x;
	}

	public void setPosition_x(Integer position_x) {
		this.position_x = position_x;
	}

	public Integer getPosition_y() {
		return position_y;
	}

	public void setPosition_y(Integer position_y) {
		this.position_y = position_y;
	}

	public Integer getWares_length() {
		return wares_length;
	}

	public void setWares_length(Integer wares_length) {
		this.wares_length = wares_length;
	}

	public Integer getWares_width() {
		return wares_width;
	}

	public void setWares_width(Integer wares_width) {
		this.wares_width = wares_width;
	}

	public Integer getWares_height() {
		return wares_height;
	}

	public void setWares_height(Integer wares_height) {
		this.wares_height = wares_height;
	}

	public Integer getCount_length_on_shelf() {
		return count_length_on_shelf;
	}

	public void setCount_length_on_shelf(Integer count_length_on_shelf) {
		this.count_length_on_shelf = count_length_on_shelf;
	}

	public Integer getUser_insert() {
		return user_insert;
	}

	public void setUser_insert(Integer user_insert) {
		this.user_insert = user_insert;
	}

	public Date getDate_insert() {
		return date_insert;
	}

	public void setDate_insert(Date date_insert) {
		this.date_insert = date_insert;
	}

	public Integer getUser_update() {
		return user_update;
	}

	public void setUser_update(Integer user_update) {
		this.user_update = user_update;
	}

	public Date getDate_update() {
		return date_update;
	}

	public void setDate_update(Date date_update) {
		this.date_update = date_update;
	}

	public Integer getCode_image() {
		return code_image;
	}

	public String getName_wares() {
		return name_wares;
	}

	public String getAbr_unit() {
		return abr_unit;
	}

	public String getBar_code() {
		return bar_code;
	}

	public RackWares(final ResultSet resultSet) throws SQLException {
		code_rack = resultSet.getInt(RackWaresConst.CODE_RACK);
		code_wares = resultSet.getInt(RackWaresConst.CODE_WARES);
		code_unit = resultSet.getInt(RackWaresConst.CODE_UNIT);
		code_wares_on_rack = resultSet.getInt(RackWaresConst.CODE_WARES_ON_RACK);
		setType_wares_on_rack(resultSet.getString(RackWaresConst.TYPE_WARES_ON_RACK));
		order_number_on_rack = resultSet.getInt(RackWaresConst.ORDER_NUMBER_ON_RACK);
		position_x = resultSet.getInt(RackWaresConst.POSITION_X);
		position_y = resultSet.getInt(RackWaresConst.POSITION_Y);
		wares_length = resultSet.getInt(RackWaresConst.WARES_LENGTH);
		wares_width = resultSet.getInt(RackWaresConst.WARES_WIDTH);
		wares_height = resultSet.getInt(RackWaresConst.WARES_HEIGHT);
		count_length_on_shelf = resultSet.getInt(RackWaresConst.COUNT_LENGTH_ON_SHELF);
		user_insert = resultSet.getInt(RackWaresConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(RackWaresConst.DATE_INSERT);
		user_update = resultSet.getInt(RackWaresConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(RackWaresConst.DATE_UPDATE);
		code_image = resultSet.getInt(WaresImageConst.CODE_IMAGE);
		name_wares = resultSet.getString(WaresConst.NAME_WARES);
		abr_unit = resultSet.getString(UnitDimensionConst.ABR_UNIT);
		bar_code = resultSet.getString(AdditionUnitConst.BAR_CODE);
	}

	public RackWares(final JsonObject jsonObject) {
		code_rack = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_RACK);
		code_wares = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_WARES);
		code_unit = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_UNIT);
		code_wares_on_rack = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_WARES_ON_RACK);
		setType_wares_on_rack(JsonUtils.getString(jsonObject, RackWaresConst.TYPE_WARES_ON_RACK));
		order_number_on_rack = JsonUtils.getInteger(jsonObject, RackWaresConst.ORDER_NUMBER_ON_RACK);
		position_x = JsonUtils.getInteger(jsonObject, RackWaresConst.POSITION_X);
		position_y = JsonUtils.getInteger(jsonObject, RackWaresConst.POSITION_Y);
		wares_length = JsonUtils.getInteger(jsonObject, RackWaresConst.WARES_LENGTH);
		wares_width = JsonUtils.getInteger(jsonObject, RackWaresConst.WARES_WIDTH);
		wares_height = JsonUtils.getInteger(jsonObject, RackWaresConst.WARES_HEIGHT);
		count_length_on_shelf = JsonUtils.getInteger(jsonObject, RackWaresConst.COUNT_LENGTH_ON_SHELF);
		user_insert = JsonUtils.getInteger(jsonObject, RackWaresConst.USER_INSERT);
		date_insert = JsonUtils.getDate(jsonObject, RackWaresConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(jsonObject, RackWaresConst.USER_UPDATE);
		date_update = JsonUtils.getDate(jsonObject, RackWaresConst.DATE_UPDATE);
		code_image = JsonUtils.getInteger(jsonObject, WaresImageConst.CODE_IMAGE);
		name_wares = JsonUtils.getString(jsonObject, WaresConst.NAME_WARES);
		abr_unit = JsonUtils.getString(jsonObject, UnitDimensionConst.ABR_UNIT);
		bar_code = JsonUtils.getString(jsonObject, AdditionUnitConst.BAR_CODE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackWaresConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackWaresConst.CODE_WARES, code_wares);
		jsonObject.addProperty(RackWaresConst.CODE_UNIT, code_unit);
		jsonObject.addProperty(RackWaresConst.CODE_WARES_ON_RACK, code_wares_on_rack);
		jsonObject.addProperty(RackWaresConst.TYPE_WARES_ON_RACK, getType_wares_on_rackAtStr());
		jsonObject.addProperty(RackWaresConst.ORDER_NUMBER_ON_RACK, order_number_on_rack);
		jsonObject.addProperty(RackWaresConst.POSITION_X, position_x);
		jsonObject.addProperty(RackWaresConst.POSITION_Y, position_y);
		jsonObject.addProperty(RackWaresConst.WARES_LENGTH, wares_length);
		jsonObject.addProperty(RackWaresConst.WARES_WIDTH, wares_width);
		jsonObject.addProperty(RackWaresConst.WARES_HEIGHT, wares_height);
		jsonObject.addProperty(RackWaresConst.COUNT_LENGTH_ON_SHELF, count_length_on_shelf);
		jsonObject.addProperty(RackWaresConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, RackWaresConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(RackWaresConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, RackWaresConst.DATE_UPDATE, date_update);
		jsonObject.addProperty(WaresImageConst.CODE_IMAGE, code_image);
		jsonObject.addProperty(WaresConst.NAME_WARES, name_wares);
		jsonObject.addProperty(UnitDimensionConst.ABR_UNIT, abr_unit);
		jsonObject.addProperty(AdditionUnitConst.BAR_CODE, bar_code);
		return jsonObject;
	}
}
