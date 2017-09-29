package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.AbstractRackConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Date: 11.01.13
 * Time: 14:25
 *
 * @author Alexandr Polyakov
 */
public abstract class AbstractRack implements IJsonObject {
	/**
	 * Длина, см
	 */
	private Integer length;
	/**
	 * Ширина, см
	 */
	private Integer width;
	/**
	 * Высота, см
	 */
	private Integer height;
	/**
	 * Сторона загрузи
	 */
	private LoadSide load_side;
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
	/**
	 * Доступная длина, мм
	 */
	private Integer real_length;
	/**
	 * Доступная ширина, мм
	 */
	private Integer real_width;
	/**
	 * Доступная высота, мм
	 */
	private Integer real_height;
	/**
	 * Смещение доступной области относительно левой стороны обекта (со стороны загрузки)
	 */
	private Integer x_offset;
	/**
	 * Смещение доступной области относительно низа обекта (со стороны загрузки)
	 */
	private Integer y_offset;
	/**
	 * Смещение доступной области относительно дальней стенки (со стороны загрузки)
	 */
	private Integer z_offset;

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public LoadSide getLoad_side() {
		return load_side;
	}

	public String getLoad_sideAtStr() {
		return (load_side != null) ? load_side.name() : null;
	}

	public void setLoad_side(LoadSide load_side) {
		this.load_side = load_side;
	}

	public void setLoad_side(String load_side) {
		this.load_side = (load_side != null) ? LoadSide.valueOf(load_side) : null;
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

	public Integer getReal_length() {
		return real_length;
	}

	public void setReal_length(Integer real_length) {
		this.real_length = real_length;
	}

	public Integer getReal_width() {
		return real_width;
	}

	public void setReal_width(Integer real_width) {
		this.real_width = real_width;
	}

	public Integer getReal_height() {
		return real_height;
	}

	public void setReal_height(Integer real_height) {
		this.real_height = real_height;
	}

	public Integer getX_offset() {
		return x_offset;
	}

	public void setX_offset(Integer x_offset) {
		this.x_offset = x_offset;
	}

	public Integer getY_offset() {
		return y_offset;
	}

	public void setY_offset(Integer y_offset) {
		this.y_offset = y_offset;
	}

	public Integer getZ_offset() {
		return z_offset;
	}

	public void setZ_offset(Integer z_offset) {
		this.z_offset = z_offset;
	}

	public AbstractRack(Integer length, Integer width, Integer height, LoadSide load_side, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer real_length, Integer real_width, Integer real_height, Integer x_offset, Integer y_offset, Integer z_offset) {
		this.length = length;
		this.width = width;
		this.height = height;
		this.load_side = load_side;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
		this.real_length = real_length;
		this.real_width = real_width;
		this.real_height = real_height;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.z_offset = z_offset;
	}

	public AbstractRack(final ResultSet resultSet) throws SQLException {
		length = resultSet.getInt(AbstractRackConst.LENGTH);
		width = resultSet.getInt(AbstractRackConst.WIDTH);
		height = resultSet.getInt(AbstractRackConst.HEIGHT);
		setLoad_side(resultSet.getString(AbstractRackConst.LOAD_SIDE));
		user_insert = resultSet.getInt(AbstractRackConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(AbstractRackConst.DATE_INSERT);
		user_update = resultSet.getInt(AbstractRackConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(AbstractRackConst.DATE_UPDATE);
		real_length = resultSet.getInt(AbstractRackConst.REAL_LENGTH);
		real_width = resultSet.getInt(AbstractRackConst.REAL_WIDTH);
		real_height = resultSet.getInt(AbstractRackConst.REAL_HEIGHT);
		x_offset = resultSet.getInt(AbstractRackConst.X_OFFSET);
		y_offset = resultSet.getInt(AbstractRackConst.Y_OFFSET);
		z_offset = resultSet.getInt(AbstractRackConst.Z_OFFSET);
	}

	public AbstractRack(final JsonObject rackJson) {
		length = JsonUtils.getInteger(rackJson, AbstractRackConst.LENGTH);
		width = JsonUtils.getInteger(rackJson, AbstractRackConst.WIDTH);
		height = JsonUtils.getInteger(rackJson, AbstractRackConst.HEIGHT);
		setLoad_side(JsonUtils.getString(rackJson, AbstractRackConst.LOAD_SIDE));
		user_insert = JsonUtils.getInteger(rackJson, AbstractRackConst.USER_INSERT);
		date_insert = JsonUtils.getDate(rackJson, AbstractRackConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(rackJson, AbstractRackConst.USER_UPDATE);
		date_update = JsonUtils.getDate(rackJson, AbstractRackConst.DATE_UPDATE);
		real_length = JsonUtils.getInteger(rackJson, AbstractRackConst.REAL_LENGTH);
		real_width = JsonUtils.getInteger(rackJson, AbstractRackConst.REAL_WIDTH);
		real_height = JsonUtils.getInteger(rackJson, AbstractRackConst.REAL_HEIGHT);
		x_offset = JsonUtils.getInteger(rackJson, AbstractRackConst.X_OFFSET);
		y_offset = JsonUtils.getInteger(rackJson, AbstractRackConst.Y_OFFSET);
		z_offset = JsonUtils.getInteger(rackJson, AbstractRackConst.Z_OFFSET);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(AbstractRackConst.LENGTH, length);
		jsonObject.addProperty(AbstractRackConst.WIDTH, width);
		jsonObject.addProperty(AbstractRackConst.HEIGHT, height);
		jsonObject.addProperty(AbstractRackConst.LOAD_SIDE, (load_side != null) ? load_side.name() : null);
		jsonObject.addProperty(AbstractRackConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, AbstractRackConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(AbstractRackConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, AbstractRackConst.DATE_UPDATE, date_update);
		jsonObject.addProperty(AbstractRackConst.REAL_LENGTH, real_length);
		jsonObject.addProperty(AbstractRackConst.REAL_WIDTH, real_width);
		jsonObject.addProperty(AbstractRackConst.REAL_HEIGHT, real_height);
		jsonObject.addProperty(AbstractRackConst.X_OFFSET, x_offset);
		jsonObject.addProperty(AbstractRackConst.Y_OFFSET, y_offset);
		jsonObject.addProperty(AbstractRackConst.Z_OFFSET, z_offset);
		return jsonObject;
	}
}
