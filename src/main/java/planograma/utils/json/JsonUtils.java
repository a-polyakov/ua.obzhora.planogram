package planograma.utils.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;

/**
 * Date: 23.03.12
 * Time: 5:01
 *
 * @author Alexandr Polyakov
 */
public class JsonUtils {

	public static Integer getInteger(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement == null || jsonElement.isJsonNull())
			return null;
		String s = jsonElement.getAsString();
		int index = s.indexOf('.');
		if (index >= 0) {
			s = s.substring(0, index);
		} else {
			index = s.indexOf(',');
			if (index >= 0) {
				s = s.substring(0, index);
			}
		}
		return (s.isEmpty()) ? null : Integer.valueOf(s);
	}

	public static String getString(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement == null || jsonElement.isJsonNull())
			return null;
		return jsonElement.getAsString();
	}

	//TODO proverit
	public static boolean getBoolean(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement == null || jsonElement.isJsonNull())
			return false;
		return ("Y".equals(jsonElement.getAsString())) ? true : jsonElement.getAsBoolean();
	}

	public static Date getDate(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement == null || jsonElement.isJsonNull())
			return null;
		return new Date(jsonElement.getAsLong());
	}

	public static void set(final JsonObject jsonObject, final String key, final Number value) {
		jsonObject.addProperty(key, value);
	}

	public static void set(final JsonObject jsonObject, final String key, final String value) {
		jsonObject.addProperty(key, value);
	}

	public static void set(final JsonObject jsonObject, final String key, final Date value) {
		jsonObject.addProperty(key, (value != null) ? value.getTime() : null);
	}
}
