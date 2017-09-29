package planograma.exception;

import java.sql.SQLException;

/**
 * Date: 03.03.12
 * Time: 0:23
 *
 * @author Alexandr Polyakov
 */
public class InvalidLoginOrPassword extends SQLException {

	private static InvalidLoginOrPassword instance = new InvalidLoginOrPassword();

	public static InvalidLoginOrPassword getInstance() {
		return instance;
	}

	private InvalidLoginOrPassword() {
		super("Invalid login or password");
	}
}
