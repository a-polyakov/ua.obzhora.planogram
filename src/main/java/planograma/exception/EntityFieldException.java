package planograma.exception;

import com.google.gson.JsonObject;

/**
 * Date: 18.09.12
 * Time: 8:22
 *
 * @author Alexandr Polyakov
 */
public class EntityFieldException extends Exception {
	private String entityClass;
	private Integer entityIndex;
	private Integer entityId;
	private String fieldName;

	public EntityFieldException(String message, Class entityClass, Integer entityIndex, Integer entityId, String fieldName) {
		super(message);
		this.entityClass = entityClass.getName();
		this.entityIndex = entityIndex;
		this.entityId = entityId;
		this.fieldName = fieldName;
	}

	public JsonObject toJSON() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("message", getMessage());
		jsonObject.addProperty("entityClass", entityClass);
		jsonObject.addProperty("entityIndex", entityIndex);
		jsonObject.addProperty("entityId", entityId);
		jsonObject.addProperty("fieldName", fieldName);
		return jsonObject;
	}
}
