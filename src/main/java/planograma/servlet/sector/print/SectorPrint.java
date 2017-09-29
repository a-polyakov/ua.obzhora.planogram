package planograma.servlet.sector.print;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.log4j.Logger;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.data.*;
import planograma.data.geometry.Rack2D;
import planograma.model.RackModel;
import planograma.model.SectorModel;
import planograma.model.ShopModel;
import planograma.utils.FormattingUtils;
import planograma.utils.geometry.Point2D;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 07.06.12
 * Time: 3:50
 *
 * @author Alexandr Polyakov
 */
@WebServlet("/" + UrlConst.URL_SECTOR_PRINT + "*")
public class SectorPrint extends HttpServlet {
	public static final String URL = UrlConst.URL_SECTOR_PRINT;

	private static final Logger LOG = Logger.getLogger(SectorPrint.class);

	private static final float marginLeft = 28;
	private static final float marginRight = 28;
	private static final float marginTop = 28;
	private static final float marginTitle = 28;
	private static final float marginBottom = 28;

	private ShopModel shopModel;
	private SectorModel sectorModel;
	private RackModel rackModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		shopModel = ShopModel.getInstance();
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		long time = System.currentTimeMillis();
		try {
			int code_sector = Integer.parseInt(request.getPathInfo().substring(1));
			final HttpSession session = request.getSession(false);
			final UserContext userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);

			final Sector sector = sectorModel.select(userContext, code_sector);
			final Shop shop = shopModel.select(userContext, sector.getCode_shop());
			final List<Rack> list = rackModel.list(userContext, code_sector);
			final List<Rack2D> rackList = new ArrayList<Rack2D>(list.size());
			for (final Rack rack : list) {
				rackList.add(new Rack2D(rack));
			}
			resp.setContentType("application/pdf");

			final BaseFont baseFont = BaseFont.createFont(getServletContext().getRealPath("font/FreeSerif.ttf"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			final Font font = new Font(baseFont, Font.DEFAULTSIZE, Font.NORMAL);
			final Rectangle pageSize;
			if (sector.getLength() > sector.getWidth()) {
				pageSize = PageSize.A4.rotate();
			} else {
				pageSize = PageSize.A4;
			}
			final Document document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
			final PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());
			document.open();
			final PdfContentByte cb = writer.getDirectContent();
			final float m = Math.max(sector.getLength() / (pageSize.getWidth() - marginLeft - marginRight), sector.getWidth() / (pageSize.getHeight() - marginTop - marginTitle - marginBottom));

			final String title = shop.getName_shop() + " (" + sector.getName_sector() + ") от " + FormattingUtils.datetime2String(sector.getDate_update());
			Paragraph p = new Paragraph(title, font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			drawSector(cb, sector, m, pageSize);

			for (int index = 0; index < rackList.size(); index++) {
				final Rack2D rack2D = rackList.get(index);
				drawRack2D(cb, rack2D, index + 1, m, pageSize, baseFont);
			}
			document.setPageSize(PageSize.A4);
			document.newPage();

			final PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);

			int widths[] = {5, 35, 18, 9, 9, 9, 8, 7};
			table.setWidths(widths);
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph("№", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Наименование", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Штрихкод", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Сторона загрузки", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Тип", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Ширина", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Высота", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Длина", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			for (int index = 0; index < rackList.size(); index++) {
				final Rack2D rack2D = rackList.get(index);
				if (rack2D.getRack().getType_rack() != ETypeRack.DZ) {
					table.addCell(new PdfPCell(new Paragraph(String.valueOf(index + 1), font)));
					table.addCell(new PdfPCell(new Paragraph(rack2D.getRack().getName_rack(), font)));

					try {
						BarcodeEAN codeEAN = new BarcodeEAN();
						codeEAN.setCode(rack2D.getRack().getRack_barcode());
						cell = new PdfPCell(codeEAN.createImageWithBarcode(cb, null, null));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					} catch (Exception e) {
						table.addCell(new PdfPCell(new Paragraph(rack2D.getRack().getRack_barcode(), font)));
					}

					table.addCell(new PdfPCell(new Paragraph(rack2D.getRack().getLoad_side().getDesc(), font)));
					table.addCell(new PdfPCell(new Paragraph(rack2D.getRack().getType_rack().getDesc(), font)));
					table.addCell(new PdfPCell(new Paragraph(String.valueOf(rack2D.getRack().getWidth()), font)));
					table.addCell(new PdfPCell(new Paragraph(String.valueOf(rack2D.getRack().getHeight()), font)));
					table.addCell(new PdfPCell(new Paragraph(String.valueOf(rack2D.getRack().getLength()), font)));
				}
			}
			document.add(table);
			document.close();
		} catch (Exception e) {
			LOG.error("Error print sector", e);
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private void drawSector(final PdfContentByte cb, final Sector sector, final float m, final Rectangle pageSize) {
		cb.saveState();
		cb.setColorStroke(GrayColor.BLACK);
		cb.rectangle(marginLeft, pageSize.getHeight() - marginTop - marginTitle - sector.getWidth() / m, sector.getLength() / m, sector.getWidth() / m);
		cb.stroke();
		cb.restoreState();
	}

	private void drawRack2D(final PdfContentByte cb, final Rack2D rack2D, final int index, final float m, final Rectangle pageSize, final BaseFont baseFont) {
		cb.saveState();
		if (rack2D.getRack().getType_rack() == ETypeRack.DZ) {
			cb.setColorFill(GrayColor.GRAY);
		} else {
			cb.setColorStroke(GrayColor.BLACK);
		}
		final Point2D p1 = rack2D.getP1().clone();
		p1.setY(-p1.getY());
		p1.scale(m);
		p1.move(marginLeft, pageSize.getHeight() - marginTop - marginTitle);
		final Point2D p2 = rack2D.getP2().clone();
		p2.setY(-p2.getY());
		p2.scale(m);
		p2.move(marginLeft, pageSize.getHeight() - marginTop - marginTitle);
		final Point2D p3 = rack2D.getP3().clone();
		p3.setY(-p3.getY());
		p3.scale(m);
		p3.move(marginLeft, pageSize.getHeight() - marginTop - marginTitle);
		final Point2D p4 = rack2D.getP4().clone();
		p4.setY(-p4.getY());
		p4.scale(m);
		p4.move(marginLeft, pageSize.getHeight() - marginTop - marginTitle);

		cb.moveTo(p1.getX(), p1.getY());
		cb.lineTo(p2.getX(), p2.getY());
		cb.lineTo(p3.getX(), p3.getY());
		cb.lineTo(p4.getX(), p4.getY());
		if (rack2D.getRack().getType_rack() == ETypeRack.DZ) {
			cb.fill();
		} else {
			cb.closePathStroke();
		}
		if (rack2D.getRack().getType_rack() != ETypeRack.DZ) {
			cb.beginText();

			float minSize = Math.min(rack2D.getRack().getWidth(), rack2D.getRack().getLength()) / m;
			final String indexStr = String.valueOf(index);
			float wM = minSize / baseFont.getWidthPoint(indexStr, Font.DEFAULTSIZE);
			float hM = minSize / Font.DEFAULTSIZE;
			float x = marginLeft + rack2D.getRack().getX_coord() / m;
			float y = pageSize.getHeight() - marginTop - marginTitle - rack2D.getRack().getY_coord() / m;
			cb.moveText(x - minSize / 2, y - minSize / 3F);
			cb.setFontAndSize(baseFont, Font.DEFAULTSIZE * Math.min(wM, hM));

			cb.showText(indexStr);
			cb.endText();
		}
		cb.restoreState();
	}
}
