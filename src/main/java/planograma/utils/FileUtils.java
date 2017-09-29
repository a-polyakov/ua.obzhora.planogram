package planograma.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Date: 26.04.12
 * Time: 6:01
 *
 * @author Alexandr Polyakov
 */
public class FileUtils {
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	public static void delete(final File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (final File f : file.listFiles())
					delete(f);
				file.delete();
			} else {
				file.delete();
			}
		}
	}

	public static long copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
		byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
		long size = 0;
		int n = inputStream.read(buffer);
		while (n > 0) {
			outputStream.write(buffer, 0, n);
			size += n;
			n = inputStream.read(buffer);
		}
		inputStream.close();
		outputStream.close();
		return size;
	}
}
