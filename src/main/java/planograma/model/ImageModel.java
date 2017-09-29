package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.ImageConst;
import planograma.data.UserContext;
import planograma.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 26.04.12
 * Time: 5:33
 *
 * @author Alexandr Polyakov
 */
public class ImageModel {

	private static final Logger LOG = Logger.getLogger(ImageModel.class);

	private static final boolean RESIZE_IMAGE = true;
	private static final int IMAGE_MIN_WIDTH = 200;
	private static final int IMAGE_MIN_HEIGHT = 200;

	private final File tempDir;
	private static final String Q_SELECT = "select" +
			" " + ImageConst.IMAGE_THUMBNAIL + " " +
			"from " + ImageConst.TABLE_NAME + " " +
			"where " + ImageConst.CODE_IMAGE + " = ?";

	public InputStream select(final UserContext userContext, final int code_image) throws SQLException, IOException {
		long time = System.currentTimeMillis();
		InputStream in = null;
		File file = new File(tempDir, String.valueOf(code_image));
		if (!file.isFile()) {
			final Connection connection = userContext.getConnection();
			final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
			ps.setInt(1, code_image);
			final ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				InputStream dbis = resultSet.getBinaryStream(1);
				if (RESIZE_IMAGE) {
					// resize image
					BufferedImage image = ImageIO.read(dbis);
					int oldWidth = image.getWidth();
					int oldHeight = image.getHeight();
					int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
					int newWidth;
					int newHeight;
					if (oldWidth - IMAGE_MIN_WIDTH > oldHeight - IMAGE_MIN_HEIGHT) {
						newWidth = IMAGE_MIN_WIDTH;
						newHeight = IMAGE_MIN_WIDTH * oldHeight / oldWidth;
					} else {
						newWidth = IMAGE_MIN_HEIGHT * oldWidth / oldHeight;
						newHeight = IMAGE_MIN_HEIGHT;
					}
					BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
					Graphics2D g = resizedImage.createGraphics();
					// ANTIALIASING
					g.setComposite(AlphaComposite.Src);
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g.setRenderingHint(RenderingHints.KEY_RENDERING,
							RenderingHints.VALUE_RENDER_QUALITY);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					// DRAW IMAGE
					g.drawImage(image, 0, 0, newWidth, newHeight, null);
					g.dispose();

					FileOutputStream fos = new FileOutputStream(file);
					ImageIO.write(resizedImage, "jpg", fos);
					fos.close();
				} else {
					FileOutputStream fos = new FileOutputStream(file);
					FileUtils.copy(dbis, fos);
					fos.close();
				}
			} else {
				throw new SQLException("Empty result select image");
			}
		}
		in = new FileInputStream(file);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return in;
	}

	public void clearCache() {
		long time = System.currentTimeMillis();
		if (tempDir != null) {
			for (final File f : tempDir.listFiles())
				FileUtils.delete(f);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static ImageModel instance = new ImageModel();

	public static ImageModel getInstance() {
		return instance;
	}

	private ImageModel() {
		final String path = System.getProperty("java.io.tmpdir");
		tempDir = new File(path, "planogram/image/");
		tempDir.mkdirs();
	}

	@Override
	protected void finalize() throws Throwable {
		FileUtils.delete(tempDir);
	}
}
