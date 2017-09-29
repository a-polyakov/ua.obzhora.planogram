package planograma.data;


import com.google.gson.JsonObject;
import planograma.constant.data.SectorConst;
import planograma.utils.json.IJsonObject;
import planograma.utils.json.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Сектор (этаж)
 * Date: 26.02.12
 * Time: 4:14
 *
 * @author Alexandr Polyakov
 */
public class Sector implements IJsonObject {
	/**
	 * Код ТП
	 */
	private Integer code_shop;
	/**
	 * Код сектора
	 */
	private Integer code_sector;
	/**
	 * Название
	 */
	private String name_sector;
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

	public Sector(Integer code_shop, Integer code_sector, String name_sector,
				  Integer length, Integer width, Integer height,
				  Integer user_insert, Date date_insert,
				  Integer user_update, Date date_update
	) {
		this.code_shop = code_shop;
		this.code_sector = code_sector;
		this.name_sector = name_sector;
		this.length = length;
		this.width = width;
		this.height = height;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
	}

	public Integer getCode_shop() {
		return code_shop;
	}

	public void setCode_shop(Integer code_shop) {
		this.code_shop = code_shop;
	}

	public Integer getCode_sector() {
		return code_sector;
	}

	public void setCode_sector(Integer code_sector) {
		this.code_sector = code_sector;
	}

	public String getName_sector() {
		return name_sector;
	}

	public void setName_sector(String name_sector) {
		this.name_sector = name_sector;
	}

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

	public Sector(final ResultSet resultSet) throws SQLException {
		code_shop = resultSet.getInt(SectorConst.CODE_SHOP);
		code_sector = resultSet.getInt(SectorConst.CODE_SECTOR);
		name_sector = resultSet.getString(SectorConst.NAME_SECTOR);
		length = resultSet.getInt(SectorConst.LENGTH);
		width = resultSet.getInt(SectorConst.WIDTH);
		height = resultSet.getInt(SectorConst.HEIGHT);
		user_insert = resultSet.getInt(SectorConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(SectorConst.DATE_INSERT);
		user_update = resultSet.getInt(SectorConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(SectorConst.DATE_UPDATE);
	}

	public Sector(final JsonObject sectorJson) {
		code_shop = JsonUtils.getInteger(sectorJson, SectorConst.CODE_SHOP);
		code_sector = JsonUtils.getInteger(sectorJson, SectorConst.CODE_SECTOR);
		name_sector = JsonUtils.getString(sectorJson, SectorConst.NAME_SECTOR);
		length = JsonUtils.getInteger(sectorJson, SectorConst.LENGTH);
		width = JsonUtils.getInteger(sectorJson, SectorConst.WIDTH);
		height = JsonUtils.getInteger(sectorJson, SectorConst.HEIGHT);
		user_insert = JsonUtils.getInteger(sectorJson, SectorConst.USER_INSERT);
		date_insert = JsonUtils.getDate(sectorJson, SectorConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(sectorJson, SectorConst.USER_UPDATE);
		date_update = JsonUtils.getDate(sectorJson, SectorConst.DATE_UPDATE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(SectorConst.CODE_SHOP, code_shop);
		jsonObject.addProperty(SectorConst.CODE_SECTOR, code_sector);
		jsonObject.addProperty(SectorConst.NAME_SECTOR, name_sector);
		jsonObject.addProperty(SectorConst.LENGTH, length);
		jsonObject.addProperty(SectorConst.WIDTH, width);
		jsonObject.addProperty(SectorConst.HEIGHT, height);
		jsonObject.addProperty(SectorConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, SectorConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(SectorConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, SectorConst.DATE_UPDATE, date_update);
		return jsonObject;
	}
}
