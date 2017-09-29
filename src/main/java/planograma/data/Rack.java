package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackConst;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * стеллаж
 * Date: 18.03.12
 * Time: 9:39
 *
 * @author Alexandr Polyakov
 */
public class Rack extends AbstractRack {
	/**
	 * Код Этажа
	 */
	private Integer code_sector;
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Название
	 */
	private String name_rack;
	/**
	 * Штрих код стеллажа
	 */
	private String rack_barcode;
	/**
	 * Код шаблонна стеллажа
	 */
	private Integer code_rack_template;
	/**
	 * Положение на этаже по длине
	 */
	private Integer x_coord;
	/**
	 * Положение на этаже по ширине
	 */
	private Integer y_coord;
	/**
	 * Угол поворота
	 */
	private Integer angle;
	/**
	 * Запрет изменения размера стеллажа
	 */
	private boolean lock_size;
	/**
	 * Запрет перемещения стеллажа
	 */
	private boolean lock_move;
	/**
	 * Тип стеллажа (Стеллаж, касса...)
	 */
	private ETypeRack type_rack;
	/**
	 * Разрешение пересекать другие обекты в зале
	 */
	private Integer allow_intersect;


	public Rack(Integer code_sector, Integer code_rack, String name_rack, String rack_barcode, Integer length, Integer width, Integer height, Integer x_coord, Integer y_coord, Integer angle, LoadSide load_side, Integer code_rack_template, boolean lock_size, boolean lock_move, ETypeRack type_rack, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer real_length, Integer real_width, Integer real_height, Integer x_offset, Integer y_offset, Integer z_offset, Integer allow_intersect) {
		super(length, width, height, load_side, user_insert, date_insert, user_update, date_update, real_length, real_width, real_height, x_offset, y_offset, z_offset);
		this.code_sector = code_sector;
		this.code_rack = code_rack;
		this.name_rack = name_rack;
		this.rack_barcode = rack_barcode;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.angle = angle;
		this.code_rack_template = code_rack_template;
		this.lock_size = lock_size;
		this.lock_move = lock_move;
		this.type_rack = type_rack;
		this.allow_intersect = allow_intersect;
	}

	public Integer getCode_sector() {
		return code_sector;
	}

	public void setCode_sector(Integer code_sector) {
		this.code_sector = code_sector;
	}

	public Integer getCode_rack() {
		return code_rack;
	}

	public void setCode_rack(Integer code_rack) {
		this.code_rack = code_rack;
	}

	public String getName_rack() {
		return name_rack;
	}

	public void setName_rack(String name_rack) {
		this.name_rack = name_rack;
	}

	public String getRack_barcode() {
		return rack_barcode;
	}

	public void setRack_barcode(String rack_barcode) {
		this.rack_barcode = rack_barcode;
	}

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

	public Integer getAngle() {
		return angle;
	}

	public void setAngle(Integer angle) {
		this.angle = angle;
	}

	public Integer getCode_rack_template() {
		return code_rack_template;
	}

	public void setCode_rack_template(Integer code_rack_template) {
		this.code_rack_template = code_rack_template;
	}

	public boolean isLock_size() {
		return lock_size;
	}

	public void setLock_size(boolean lock_size) {
		this.lock_size = lock_size;
	}

	public void setLock_size(String lock_size) {
		this.lock_size = "Y".equals(lock_size);
	}

	public boolean isLock_move() {
		return lock_move;
	}

	public void setLock_move(boolean lock_move) {
		this.lock_move = lock_move;
	}

	public void setLock_move(String lock_move) {
		this.lock_move = "Y".equals(lock_move);
	}

	public ETypeRack getType_rack() {
		return type_rack;
	}

	public String getType_rackAtStr() {
		return (type_rack != null) ? type_rack.name() : null;
	}

	public void setType_rack(ETypeRack type_rack) {
		this.type_rack = type_rack;
	}

	public void setType_rack(String type_rack) {
		this.type_rack = (type_rack != null) ? ETypeRack.valueOf(type_rack) : null;
	}

	public Integer getAllow_intersect() {
		return allow_intersect;
	}

	public void setAllow_intersect(Integer allow_intersect) {
		this.allow_intersect = allow_intersect;
	}

	public Rack(final ResultSet resultSet) throws SQLException {
		super(resultSet);
		code_sector = resultSet.getInt(RackConst.CODE_SECTOR);
		code_rack = resultSet.getInt(RackConst.CODE_RACK);
		name_rack = resultSet.getString(RackConst.NAME_RACK);
		rack_barcode = resultSet.getString(RackConst.RACK_BARCODE);
		x_coord = resultSet.getInt(RackConst.X_COORD);
		y_coord = resultSet.getInt(RackConst.Y_COORD);
		angle = resultSet.getInt(RackConst.ANGLE);
		code_rack_template = resultSet.getInt(RackConst.CODE_RACK_TEMPLATE);
		setLock_size(resultSet.getString(RackConst.LOCK_SIZE));
		setLock_move(resultSet.getString(RackConst.LOCK_MOVE));
		setType_rack(resultSet.getString(RackConst.TYPE_RACK));
		allow_intersect = resultSet.getInt(RackConst.ALLOW_INTERSECT);
	}

	public Rack(final JsonObject rackJson) {
		super(rackJson);
		code_sector = JsonUtils.getInteger(rackJson, RackConst.CODE_SECTOR);
		code_rack = JsonUtils.getInteger(rackJson, RackConst.CODE_RACK);
		name_rack = JsonUtils.getString(rackJson, RackConst.NAME_RACK);
		rack_barcode = JsonUtils.getString(rackJson, RackConst.RACK_BARCODE);
		x_coord = JsonUtils.getInteger(rackJson, RackConst.X_COORD);
		y_coord = JsonUtils.getInteger(rackJson, RackConst.Y_COORD);
		angle = JsonUtils.getInteger(rackJson, RackConst.ANGLE);
		code_rack_template = JsonUtils.getInteger(rackJson, RackConst.CODE_RACK_TEMPLATE);
		lock_size = JsonUtils.getBoolean(rackJson, RackConst.LOCK_SIZE);
		lock_move = JsonUtils.getBoolean(rackJson, RackConst.LOCK_MOVE);
		setType_rack(JsonUtils.getString(rackJson, RackConst.TYPE_RACK));
		if (type_rack != ETypeRack.R) {
			setReal_length(getLength());
			setReal_width(getWidth());
			setReal_height(getHeight());
			setX_offset(0);
			setY_offset(0);
			setZ_offset(0);
		}
		allow_intersect = JsonUtils.getInteger(rackJson, RackConst.ALLOW_INTERSECT);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = super.toJsonObject();
		jsonObject.addProperty(RackConst.CODE_SECTOR, code_sector);
		jsonObject.addProperty(RackConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackConst.NAME_RACK, name_rack);
		jsonObject.addProperty(RackConst.RACK_BARCODE, rack_barcode);
		jsonObject.addProperty(RackConst.X_COORD, x_coord);
		jsonObject.addProperty(RackConst.Y_COORD, y_coord);
		jsonObject.addProperty(RackConst.ANGLE, angle);
		jsonObject.addProperty(RackConst.CODE_RACK_TEMPLATE, code_rack_template);
		jsonObject.addProperty(RackConst.LOCK_SIZE, (lock_size) ? "Y" : "N");
		jsonObject.addProperty(RackConst.LOCK_MOVE, (lock_move) ? "Y" : "N");
		jsonObject.addProperty(RackConst.TYPE_RACK, getType_rackAtStr());
		jsonObject.addProperty(RackConst.ALLOW_INTERSECT, allow_intersect);
		return jsonObject;
	}
}
