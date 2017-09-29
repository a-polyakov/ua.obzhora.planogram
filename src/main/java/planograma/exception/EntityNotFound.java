package planograma.exception;

import java.sql.SQLException;

/**
 * Date: 21.03.12
 * Time: 20:53
 *
 * @author Alexandr Polyakov
 */
public class EntityNotFound extends SQLException {
	public EntityNotFound(String entityName, int entityCode) {
		super("Entity \"" + entityName + "\" not found. Code: " + entityCode);
	}
}
