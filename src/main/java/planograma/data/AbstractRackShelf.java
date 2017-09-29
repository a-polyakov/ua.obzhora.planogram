package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.AbstractRackShelfConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Date: 11.01.13
 * Time: 11:51
 *
 * @author Alexandr Polyakov
 */
public abstract class AbstractRackShelf implements IJsonObject {

	/**
	 * Положение на полке по ширине
	 */
	private Integer x_coord;
	/**
	 * Положение на полке по высоте
	 */
	private Integer y_coord;
	/**
	 * Высота, см
	 */
	private Integer shelf_height;
	/**
	 * Ширина, см
	 */
	private Integer shelf_width;
	/**
	 * Глубина, см
	 */
	private Integer shelf_length;
	/**
	 * Угол поворота
	 */
	private Integer angle;
	/**
	 * Тип полки, mz.state_all.part_state = -67 (?)
	 */
	private TypeShelf type_shelf;
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

	public Integer getX_coord() {
		return x_coord;
	}

	public void setX_coord(Integer x_coord) {
		this.x_coord = x_coord;
	}

	public Integer getY_coord() {
		return y_coord;
	}

	public void setY_coord(Integer y_coord) {
		this.y_coord = y_coord;
	}

	public Integer getShelf_height() {
		return shelf_height;
	}

	public void setShelf_height(Integer shelf_height) {
		this.shelf_height = shelf_height;
	}

	public Integer getShelf_width() {
		return shelf_width;
	}

	public void setShelf_width(Integer shelf_width) {
		this.shelf_width = shelf_width;
	}

	public Integer getShelf_length() {
		return shelf_length;
	}

	public void setShelf_length(Integer shelf_length) {
		this.shelf_length = shelf_length;
	}

	public Integer getAngle() {
		return angle;
	}

	public void setAngle(Integer angle) {
		this.angle = angle;
	}

	public TypeShelf getType_shelf() {
		return type_shelf;
	}

	public String getType_shelfAtStr() {
		return (type_shelf != null) ? type_shelf.name() : null;
	}

	public void setType_shelf(TypeShelf type_shelf) {
		this.type_shelf = type_shelf;
	}

	public void setType_shelf(String type_shelf) {
		this.type_shelf = (type_shelf != null) ? TypeShelf.valueOf(type_shelf) : null;
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

	public AbstractRackShelf(Integer x_coord, Integer y_coord, Integer shelf_height, Integer shelf_width, Integer shelf_length, Integer angle, TypeShelf type_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update) {
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.shelf_height = shelf_height;
		this.shelf_width = shelf_width;
		this.shelf_length = shelf_length;
		this.angle = angle;
		this.type_shelf = type_shelf;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
	}

	public AbstractRackShelf(final ResultSet resultSet) throws SQLException {
		x_coord = resultSet.getInt(AbstractRackShelfConst.X_COORD);
		y_coord = resultSet.getInt(AbstractRackShelfConst.Y_COORD);
		shelf_height = resultSet.getInt(AbstractRackShelfConst.SHELF_HEIGHT);
		shelf_width = resultSet.getInt(AbstractRackShelfConst.SHELF_WIDTH);
		shelf_length = resultSet.getInt(AbstractRackShelfConst.SHELF_LENGTH);
		angle = resultSet.getInt(AbstractRackShelfConst.ANGLE);
		setType_shelf(resultSet.getString(AbstractRackShelfConst.TYPE_SHELF));
		user_insert = resultSet.getInt(AbstractRackShelfConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(AbstractRackShelfConst.DATE_INSERT);
		user_update = resultSet.getInt(AbstractRackShelfConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(AbstractRackShelfConst.DATE_UPDATE);
	}

	public AbstractRackShelf(final JsonObject rackShelfJson) {
		x_coord = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.X_COORD);
		y_coord = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.Y_COORD);
		shelf_height = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.SHELF_HEIGHT);
		shelf_width = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.SHELF_WIDTH);
		shelf_length = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.SHELF_LENGTH);
		angle = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.ANGLE);
		setType_shelf(JsonUtils.getString(rackShelfJson, AbstractRackShelfConst.TYPE_SHELF));
		user_insert = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.USER_INSERT);
		date_insert = JsonUtils.getDate(rackShelfJson, AbstractRackShelfConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(rackShelfJson, AbstractRackShelfConst.USER_UPDATE);
		date_update = JsonUtils.getDate(rackShelfJson, AbstractRackShelfConst.DATE_UPDATE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(AbstractRackShelfConst.X_COORD, x_coord);
		jsonObject.addProperty(AbstractRackShelfConst.Y_COORD, y_coord);
		jsonObject.addProperty(AbstractRackShelfConst.SHELF_HEIGHT, shelf_height);
		jsonObject.addProperty(AbstractRackShelfConst.SHELF_WIDTH, shelf_width);
		jsonObject.addProperty(AbstractRackShelfConst.SHELF_LENGTH, shelf_length);
		jsonObject.addProperty(AbstractRackShelfConst.ANGLE, angle);
		jsonObject.addProperty(AbstractRackShelfConst.TYPE_SHELF, (type_shelf != null) ? type_shelf.name() : null);
		jsonObject.addProperty(AbstractRackShelfConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, AbstractRackShelfConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(AbstractRackShelfConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, AbstractRackShelfConst.DATE_UPDATE, date_update);
		return jsonObject;
	}

}
