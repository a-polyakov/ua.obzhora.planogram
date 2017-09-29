package planograma.exception;

/**
 * Date: 23.02.12
 * Time: 2:29
 *
 * @author Alexandr Polyakov
 */
public class UnauthorizedException extends Exception {
	private static UnauthorizedException instance = new UnauthorizedException();

	public static UnauthorizedException getInstance() {
		return instance;
	}

	private UnauthorizedException() {
		super("Unauthorized");
	}
}
