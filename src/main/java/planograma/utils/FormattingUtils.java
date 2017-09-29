package planograma.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date: 26.02.12
 * Time: 3:04
 *
 * @author Alexandr Polyakov
 */
public class FormattingUtils {
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	/**
	 * Преобразование даты-времени в строку
	 *
	 * @param date дата
	 * @return строковое представление
	 */
	public static String datetime2String(Date date) {
		return (date != null) ? DATE_TIME_FORMAT.format(date) : "";
	}
}
