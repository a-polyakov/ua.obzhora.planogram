package planograma.servlet.rack.print;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.log4j.Logger;
import planograma.constant.SessionConst;
import planograma.data.*;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.model.SectorModel;
import planograma.model.ShopModel;
import planograma.utils.FormattingUtils;
import planograma.utils.geometry.Point2D;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 08.06.12
 * Time: 4:25
 *
 * @author Alexandr Polyakov
 */
public abstract class AbstractRackPrint extends HttpServlet {

	private static final float marginLeft = 28;
	private static final float marginRight = 28;
	private static final float marginTop = 28;
	private static final float marginTitle = 28;
	private static final float marginBottom = 28;

	private ShopModel shopModel;
	private SectorModel sectorModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		shopModel = ShopModel.getInstance();
		sectorModel = SectorModel.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		long time = System.currentTimeMillis();
		try {
			int code_rack = Integer.parseInt(request.getPathInfo().substring(1));
			final HttpSession session = request.getSession(false);
			final UserContext userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);

			final Date date = getDate(userContext, code_rack);
			if (date != null) {
				final Rack rack = getRack(userContext, code_rack, date);
				final List<RackShelf> rackShelfList = getRackShelfList(userContext, code_rack, date);
				final List<RackWares> rackWaresList = getRackWaresList(userContext, code_rack, date);
				final Sector sector = sectorModel.select(userContext, rack.getCode_sector());
				final Shop shop = shopModel.select(userContext, sector.getCode_shop());

				if (rack.getLoad_side() == LoadSide.U) {
					final Integer t = rack.getWidth();
					rack.setWidth(rack.getLength());
					rack.setLength(rack.getHeight());
					rack.setHeight(t);
				} else {
					final Integer t = rack.getWidth();
					rack.setWidth(rack.getLength());
					rack.setLength(t);
				}

				final List<RackShelf2D> rackShelf2DList = new ArrayList<RackShelf2D>(rackShelfList.size());
				for (final RackShelf rackShelf : rackShelfList) {
					rackShelf2DList.add(new RackShelf2D(rackShelf));
				}
				final List<RackWares2D> rackWares2DList = new ArrayList<RackWares2D>(rackWaresList.size());
				for (final RackWares rackWares : rackWaresList) {
					rackWares2DList.add(new RackWares2D(rackWares));
				}
				resp.setContentType("application/pdf");

				final BaseFont baseFont = BaseFont.createFont(getServletContext().getRealPath("font/FreeSerif.ttf"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				final Font font = new Font(baseFont, Font.DEFAULTSIZE, Font.NORMAL);
				final Rectangle pageSize;
				if (rack.getWidth() > rack.getHeight()) {
					pageSize = PageSize.A4.rotate();
				} else {
					pageSize = PageSize.A4;
				}
				final Document document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
				final PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());
				document.open();
				final PdfContentByte cb = writer.getDirectContent();
				final float m = Math.max(rack.getWidth() / (pageSize.getWidth() - marginLeft - marginRight), rack.getHeight() / (pageSize.getHeight() - marginTop - marginTitle - marginBottom));
				final float y0 = pageSize.getHeight() - marginTop - marginTitle - rack.getHeight() / m;
				// максимальная дата изменения
				Date date_update = null;
				for (final RackShelf2D rackShelf2D : rackShelf2DList) {
					if (date_update == null || rackShelf2D.getRackShelf().getDate_update().after(date_update)) {
						date_update = rackShelf2D.getRackShelf().getDate_update();
					}
				}
				for (final RackWares2D rackWares2D : rackWares2DList) {
					if (date_update == null || rackWares2D.getRackWares().getDate_update().after(date_update)) {
						date_update = rackWares2D.getRackWares().getDate_update();
					}
				}
				final String title = shop.getName_shop() + " (" + sector.getName_sector() + ") стеллаж " + rack.getRack_barcode() + " от " + FormattingUtils.datetime2String(date_update);
				Paragraph p = new Paragraph(title, font);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				// стеллаж
				drawRack(cb, rack, m, y0);
				// полки
				for (final RackShelf2D rackShelf2D : rackShelf2DList) {
					drawRackShelf2D(cb, rackShelf2D, m, y0);
				}
				// товары
				for (final RackWares2D rackWares2D : rackWares2DList) {
					drawRackWares2D(cb, rackWares2D, m, y0, baseFont);
				}

				document.setPageSize(PageSize.A4);
				document.newPage();

				final PdfPTable table = new PdfPTable(6);
				table.setWidthPercentage(100);
				int widths[] = new int[6];
				widths[0] = 6;
				widths[1] = 14;
				widths[2] = 46;
				widths[3] = 8;
				widths[4] = 18;
				widths[5] = 8;
				table.setWidths(widths);
				PdfPCell cell;
				cell = new PdfPCell(new Paragraph("№", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Код товара", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Название", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Е.И.", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Штрихкод", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Кол-во", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				RackWares2D oldRackWares = null;
				int count = 0;
				for (final RackWares2D rackWares2D : rackWares2DList) {
					if (oldRackWares == null) {
						oldRackWares = rackWares2D;
						count += oldRackWares.getRackWares().getCount_length_on_shelf();
					} else if (!oldRackWares.getRackWares().getOrder_number_on_rack().equals(rackWares2D.getRackWares().getOrder_number_on_rack())) {
						fillTable(cb, table, oldRackWares, count, font);
						oldRackWares = rackWares2D;
						count = rackWares2D.getRackWares().getCount_length_on_shelf();
					} else {
						count += rackWares2D.getRackWares().getCount_length_on_shelf();
					}
				}
				if (oldRackWares != null) {
					fillTable(cb, table, oldRackWares, count, font);
				}
				document.add(table);
				document.close();
			} else {
				final Document document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
				PdfWriter.getInstance(document, resp.getOutputStream());
				document.open();
				final String title = "Указаный стеллаж не найден";
				final BaseFont baseFont = BaseFont.createFont(getServletContext().getRealPath("font/FreeSerif.ttf"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				final Font font = new Font(baseFont, Font.DEFAULTSIZE, Font.NORMAL);
				Paragraph p = new Paragraph(title, font);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				document.close();
			}

		} catch (Exception e) {
			getLog().error("Error print rack", e);
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		time = System.currentTimeMillis() - time;
		getLog().debug(time + " ms");
	}

	protected Logger getLog() {
		return Logger.getLogger(this.getClass());
	}

	public abstract Date getDate(final UserContext userContext, int code_rack) throws SQLException;

	public abstract Rack getRack(final UserContext userContext, int code_rack, Date date) throws SQLException;

	public abstract List<RackShelf> getRackShelfList(final UserContext userContext, int code_rack, Date date) throws SQLException;

	public abstract List<RackWares> getRackWaresList(final UserContext userContext, int code_rack, Date date) throws SQLException;

	private static void drawRack(final PdfContentByte cb, final Rack rack, final float m, final float y0) {
		cb.saveState();
		cb.setColorStroke(GrayColor.BLACK);
		cb.rectangle(marginLeft, y0, rack.getWidth() / m, rack.getHeight() / m);
		cb.stroke();
		cb.restoreState();
	}

	private static void drawRackShelf2D(final PdfContentByte cb, final RackShelf2D rackShelf2D, final float m, final float y0) {
		cb.saveState();
		cb.setColorFill(GrayColor.GRAY);
		final Point2D p1 = rackShelf2D.getP1();
		p1.scale(m);
		p1.move(marginLeft, y0);
		final Point2D p2 = rackShelf2D.getP2();
		p2.scale(m);
		p2.move(marginLeft, y0);
		final Point2D p3 = rackShelf2D.getP3();
		p3.scale(m);
		p3.move(marginLeft, y0);
		final Point2D p4 = rackShelf2D.getP4();
		p4.scale(m);
		p4.move(marginLeft, y0);
		cb.moveTo(p1.getX(), p1.getY());
		cb.lineTo(p2.getX(), p2.getY());
		cb.lineTo(p3.getX(), p3.getY());
		cb.lineTo(p4.getX(), p4.getY());
		cb.fill();
		cb.restoreState();
	}

	private static void drawRackWares2D(final PdfContentByte cb, final RackWares2D rackWares2D, final float m, final float y0, final BaseFont baseFont) {
		cb.saveState();
		cb.setColorStroke(GrayColor.BLACK);
		final Point2D p1 = rackWares2D.getP1();
		p1.scale(m);
		p1.move(marginLeft, y0);
		final Point2D p2 = rackWares2D.getP2();
		p2.scale(m);
		p2.move(marginLeft, y0);
		final Point2D p3 = rackWares2D.getP3();
		p3.scale(m);
		p3.move(marginLeft, y0);
		final Point2D p4 = rackWares2D.getP4();
		p4.scale(m);
		p4.move(marginLeft, y0);
		cb.moveTo(p1.getX(), p1.getY());
		cb.lineTo(p2.getX(), p2.getY());
		cb.lineTo(p3.getX(), p3.getY());
		cb.lineTo(p4.getX(), p4.getY());
		cb.closePathStroke();

		cb.beginText();
		final String indexStr = String.valueOf(rackWares2D.getRackWares().getOrder_number_on_rack());
		float w = rackWares2D.getRackWares().getWares_width() / m - 1;
		float h = rackWares2D.getRackWares().getWares_height() / m - 1;
		float mW = w / baseFont.getWidthPoint(indexStr, Font.DEFAULTSIZE);
		float mH = h / Font.DEFAULTSIZE;
		float mFontSsize = Math.min(mW, mH);
		float x = marginLeft + rackWares2D.getRackWares().getPosition_x() / m;
		float y = y0 + rackWares2D.getRackWares().getPosition_y() / m;
		cb.moveText(x + 0.5f - w / 2, y - 0.5f - h / 3F);
		cb.setFontAndSize(baseFont, Font.DEFAULTSIZE * mFontSsize);
		cb.showText(indexStr);
		cb.endText();

		cb.restoreState();
	}

	private static void fillTable(final PdfContentByte cb, final PdfPTable table, final RackWares2D oldRackWares, final int count, final Font font) {
		// №
		table.addCell(new PdfPCell(new Paragraph(String.valueOf(oldRackWares.getRackWares().getOrder_number_on_rack()), font)));
		// Код товара
		table.addCell(new PdfPCell(new Paragraph(String.valueOf(oldRackWares.getRackWares().getCode_wares()), font)));
		// Название
		table.addCell(new PdfPCell(new Paragraph(oldRackWares.getRackWares().getName_wares(), font)));
		// Е.И.
		table.addCell(new PdfPCell(new Paragraph(oldRackWares.getRackWares().getAbr_unit(), font)));
		// Штрихкод
		try {
			BarcodeEAN codeEAN = new BarcodeEAN();
			codeEAN.setCode(oldRackWares.getRackWares().getBar_code());
			PdfPCell cell = new PdfPCell(codeEAN.createImageWithBarcode(cb, null, null));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		} catch (Exception e) {
			table.addCell(new PdfPCell(new Paragraph(oldRackWares.getRackWares().getBar_code(), font)));
		}
		// Кол-во
		table.addCell(new PdfPCell(new Paragraph(String.valueOf(count), font)));
	}
}
