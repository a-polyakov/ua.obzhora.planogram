package planograma.exception;

/**
 * Date: 01.06.12
 * Time: 5:21
 *
 * @author Alexandr Polyakov
 */
public class NotAccessException extends Exception {
	private static NotAccessException instance = new NotAccessException();

	public static NotAccessException getInstance() {
		return instance;
	}

	private NotAccessException() {
		super("У вас недостаточно прав");
	}
}
