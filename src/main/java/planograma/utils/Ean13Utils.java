package planograma.utils;

/**
 * Date: 19.10.12
 * Time: 11:19
 *
 * @author Alexandr Polyakov
 */
public class Ean13Utils {
	public static String getEAN13(long code) {
		//В случае если префикс или код слишком большие то будет выдана ошибка:
		String result = "ERROR_TOOLONG";
		if (code < 1000000000000L) {
			//Получаем строку символов (цифр).
			final StringBuilder nabor = new StringBuilder();
			nabor.append(code);
			while (nabor.length() < 12)
				nabor.insert(0, '0');
			//Сумма по чётным позициям.
			int s1 = getNumber(nabor, 1)
					+ getNumber(nabor, 3)
					+ getNumber(nabor, 5)
					+ getNumber(nabor, 7)
					+ getNumber(nabor, 9)
					+ getNumber(nabor, 11);
			//Сумма по нечётным позициям.
			int s2 = getNumber(nabor, 0)
					+ getNumber(nabor, 2)
					+ getNumber(nabor, 4)
					+ getNumber(nabor, 6)
					+ getNumber(nabor, 8)
					+ getNumber(nabor, 10);
			//Контрольная сумма и контрольный разряд.
			s1 = s1 * 3 + s2;
			s1 = s1 % 10;
			if (!(s1 == 0)) {
				s1 = 10 - s1;
			}
			result = nabor.append(s1).toString();
		}
		return result;
	}

	public static String get2EAN13(long code) {
		//В случае если префикс или код слишком большие то будет выдана ошибка:
		String result = "ERROR_TOOLONG";
		if (code < 1000000000000L) {
			//Получаем строку символов (цифр).
			final StringBuilder nabor = new StringBuilder();
			nabor.append(code);
			while (nabor.length() < 12)
				nabor.insert(0, '0');

			int s = 0;
			while (code > 0) {
				s += (code % 10) * 3;
				code /= 10;
				s += (code % 10);
				code /= 10;
			}
			s %= 10;
			if (!(s == 0)) {
				s = 10 - s;
			}
			result = nabor.append(s).toString();
		}
		return result;
	}

	private static int getNumber(StringBuilder code, int index) {
		return code.charAt(index) - '0';
	}

	public static void main(String args[]) {
		System.out.println(getEAN13(0));
		System.out.println(get2EAN13(0));
		System.out.println(getEAN13(1));
		System.out.println(get2EAN13(1));
		System.out.println(getEAN13(4080));
		System.out.println(get2EAN13(4080));
		System.out.println(getEAN13(123456789012L));
		System.out.println(get2EAN13(123456789012L));
		System.out.println(getEAN13(1234567890123L));
		System.out.println(get2EAN13(1234567890123L));

		long time = System.currentTimeMillis();
		for (int i = 0; i < 500000000L; i++) {
			getEAN13(0);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("getEAN13 " + time);
		time = System.currentTimeMillis();
		for (int i = 0; i < 500000000L; i++) {
			get2EAN13(0);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("get2EAN13 " + time);

	}
}
