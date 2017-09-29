package planograma;

import java.util.ResourceBundle;

/**
 * Date: 18.09.12
 * Time: 8:37
 *
 * @author Alexandr Polyakov
 */
public class PlanogramMessage {

	public static String SECTOR_HEIGHT_TOO_LITTLE() {
		return getMessage("SECTOR_HEIGHT_TOO_LITTLE");
	}

	public static String SECTOR_WIDTH_TOO_LITTLE() {
		return getMessage("SECTOR_WIDTH_TOO_LITTLE");
	}

	public static String SECTOR_LENGTH_TOO_LITTLE() {
		return getMessage("SECTOR_LENGTH_TOO_LITTLE");
	}

	public static String RACK_HEIGHT_TOO_LITTLE() {
		return getMessage("RACK_HEIGHT_TOO_LITTLE");
	}

	public static String RACK_WIDTH_TOO_LITTLE() {
		return getMessage("RACK_WIDTH_TOO_LITTLE");
	}

	public static String RACK_LENGTH_TOO_LITTLE() {
		return getMessage("RACK_LENGTH_TOO_LITTLE");
	}

	public static String RACK_REAL_HEIGHT_TOO_LITTLE() {
		return getMessage("RACK_REAL_HEIGHT_TOO_LITTLE");
	}

	public static String RACK_REAL_WIDTH_TOO_LITTLE() {
		return getMessage("RACK_REAL_WIDTH_TOO_LITTLE");
	}

	public static String RACK_REAL_LENGTH_TOO_LITTLE() {
		return getMessage("RACK_REAL_LENGTH_TOO_LITTLE");
	}

	public static String RACK_OUTSIDE_SECTOR() {
		return getMessage("RACK_OUTSIDE_SECTOR");
	}

	public static String RACK_OVERFLOW_SHELF() {
		return getMessage("RACK_OVERFLOW_SHELF");
	}

	public static String RACK_OVERFLOW_WARES() {
		return getMessage("RACK_OVERFLOW_WARES");
	}

	public static String RACK_INTERSECT() {
		return getMessage("RACK_INTERSECT");
	}

	public static String RACK_SHELF_HEIGHT_TOO_LITTLE() {
		return getMessage("RACK_SHELF_HEIGHT_TOO_LITTLE");
	}

	public static String RACK_SHELF_WIDTH_TOO_LITTLE() {
		return getMessage("RACK_SHELF_WIDTH_TOO_LITTLE");
	}

	public static String RACK_SHELF_LENGTH_TOO_LITTLE() {
		return getMessage("RACK_SHELF_LENGTH_TOO_LITTLE");
	}

	public static String RACK_SHELF_OUTSIDE_RACK() {
		return getMessage("RACK_SHELF_OUTSIDE_RACK");
	}

	public static String RACK_SHELF_INTERSECT_WARES() {
		return getMessage("RACK_SHELF_INTERSECT_WARES");
	}

	public static String RACK_WARES_OUTSIDE_RACK() {
		return getMessage("RACK_WARES_OUTSIDE_RACK");
	}

	public static String RACK_WARES_INTERSECT_SHELF() {
		return getMessage("RACK_WARES_INTERSECT_SHELF");
	}

	public static String RACK_WARES_INTERSECT_WARES() {
		return getMessage("RACK_WARES_INTERSECT_WARES");
	}


	private static String getMessage(final String name) {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle(PlanogramMessage.class.getSimpleName());
		final String result = resourceBundle.getString(name);
		return (result != null) ? result : "Message for " + name + " not found!";
	}
}
