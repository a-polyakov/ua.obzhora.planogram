<%@ page import="planograma.servlet.sector.SectorEdit" %>
<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.sector.history.SectorHistoryGetMark" %>
<%@ page import="planograma.servlet.sector.history.SectorHistoryView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- TODO Отслеживать изменния размера окна --%>
<html>
<head>
	<title>История зала</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/utils/ruler.js"></script>
	<script type="text/javascript" src="js/draw/calcCoordinatesRack.js"></script>
	<script type="text/javascript" src="js/draw/drawRack.jsp"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body onload="loadComplete();" style="overflow-x:hidden;">
<table class="frame">
<tr>
	<td class="path">
		<table>
			<tr>
				<td><a href="menu.jsp">Меню</a></td>
				<td>&gt;</td>
				<td><a href="sectorList.jsp">Залы торговых площадок</a></td>
				<td>&gt;</td>
				<td><i>История зала</i></td>
				<td width="100%"></td>
				<td><a href="#" onclick="logout()">Выход</a></td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table class="frame">
			<tr>
				<td>
					<table class="menu">
						<tr>
							<td>
								<select id="dateMark"/>
							</td>
						</tr>
						<tr>
							<td><a href="#"
								   onclick="return aOnClick(this, fSectorHistoryView)"><%=JspUtils.toMenuTitle("Просмотреть")%>
							</a></td>
						</tr>
						<tr>
							<td></td>
						</tr>
						<tr>
							<td><a href="#" id="butRuler"
								   onclick="return aOnClick(this, fRuler)"><%=JspUtils.toMenuTitle("Рулетка")%>
							</a></td>
						</tr>
						<tr>
							<td height="100%"></td>
						</tr>
						<tr>
							<td>раскраска
								<select id="view_mode" onchange="drawEditCanvas();drawPreviewCanvas();">
									<option value="type_rack">тип</option>
									<option value="state_rack_in_sector">состояние в зале</option>
									<option value="state_rack">состояние компоновки</option>
								</select>
							</td>
						</tr>
					</table>
				</td>
				<td id="edit_td" width="100%">
					<canvas id='edit_canvas' width="150" height="150">canvas not supported</canvas>
				</td>
				<td>
					<table class="frame">
						<tr>
							<td>
								<table class="frame">
									<tr>
										<td id="preview_td" colspan="2" align="center" valign="middle">
											<canvas id="preview_canvas" width="150" height="150">canvas not
												supported
											</canvas>
										</td>
									</tr>
									<tr>
										<td onclick="kMadd()" class="scale">
											-
										</td>
										<td onclick="kMsub()" class="scale">
											+
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<hr/>
							</td>
						</tr>
						<tr>
							<td>
								<table id="rulerPanel" width="100%" style="display: none;">
									<tr>
										<td>x1:<input id="ruler_ax" type="text" size="3"
													  disabled="disabled"/>y1:<input id="ruler_ay" type="text"
																					 size="3" disabled="disabled"/>
										</td>
									</tr>
									<tr>
										<td>x2:<input id="ruler_bx" type="text" size="3"
													  disabled="disabled"/>y2:<input id="ruler_by" type="text"
																					 size="3"
																					 disabled="disabled"/><a
												href="#"
												onclick="window.ruler.state=0; drawEditCanvas(); $('#rulerPanel').hide();">Скрыть</a>
										</td>
									</tr>
									<tr>
										<td>dx:<input id="ruler_dx" type="text" size="3"
													  disabled="disabled"/>dy:<input id="ruler_dy" type="text"
																					 size="3" disabled="disabled"/>&nbsp;&nbsp;&nbsp;l:<input
												id="ruler_l" type="text" size="3" disabled="disabled"/></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<hr/>
							</td>
						</tr>
						<tr>
							<td>
								<table>
									<colgroup>
										<col width="70"/>
									</colgroup>
									<tr>
										<td colspan="2">свойства зала:</td>
									</tr>
									<tr>
										<td align="right">название&nbsp;</td>
										<td id="sectorName"><input type="text"/></td>
									</tr>
									<tr>
										<td align="right">ширина&nbsp;</td>
										<td id="sectorWidth"></td>
									</tr>
									<tr>
										<td align="right">длина&nbsp;</td>
										<td id="sectorLength"></td>
									</tr>
									<tr>
										<td align="right">высота&nbsp;</td>
										<td id="sectorHeight"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<hr/>
							</td>
						</tr>
						<tr>
							<td>
								<table id="showcaseAttr">
									<colgroup>
										<col width="70"/>
									</colgroup>
									<tr>
										<td colspan="2">свойства объекта:</td>
									</tr>
									<tr>
										<td align="right">тип&nbsp;</td>
										<td id="rackType"></td>
									</tr>
									<tr>
										<td align="right">название&nbsp;</td>
										<td id="rackName"></td>
									</tr>
									<tr>
										<td align="right">штрихкод&nbsp;</td>
										<td id="rackBarcode"></td>
									</tr>
									<tr>
										<td align="right">x&nbsp;</td>
										<td id="rackX"></td>
									</tr>
									<tr>
										<td align="right">y&nbsp;</td>
										<td id="rackY"></td>
									</tr>
									<tr>
										<td align="right">поворот&nbsp;</td>
										<td id="rackAngle"></td>
									</tr>
									<tr>
										<td align="right">ширина&nbsp;</td>
										<td id="rackWidth"></td>
									</tr>
									<tr>
										<td align="right">длина&nbsp;</td>
										<td id="rackLength"></td>
									</tr>
									<tr>
										<td align="right">высота&nbsp;</td>
										<td id="rackHeight"></td>
									</tr>
									<tr>
										<td align="right">загрузка&nbsp;</td>
										<td id="rackLoadSide"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="100%"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>

<script type="text/javascript">

	function loadComplete() {
		var code_sector = getCookie('code_sector');
		postJson('<%=SectorHistoryGetMark.URL%>', {code_sector: code_sector}, function (data) {
			var dateMark=$('#dateMark');
			dateMark.empty();
			dateMark.append("<option></option>");
			if (data!=null && data.sectorHistoryMark!=null && data.sectorHistoryMark.length>0)
			{
				for (var i=data.sectorHistoryMark.length-1; i>=0; i--)
				{
				dateMark.append($("<option></option>")
						.attr("value",data.sectorHistoryMark[i])
						.text(dateTimeToString(data.sectorHistoryMark[i])));
				}
			}
		});
	}

	function fSectorHistoryView() {
		var date = $('#dateMark').val();
		if (date > 0) {
			var code_sector = getCookie('code_sector');
			postJson('<%=SectorHistoryView.URL%>', {code_sector: code_sector, date:date}, function (data) {
				window.sector = data.sector;
				window.showcaseList = data.rackList;
				window.rackStateList = data.rackStateList;
				window.rackStateInSectorList = data.rackStateInSectorList;
				loadComplete2();
			});
		}
	}

	function loadComplete2() {
		window.edit_canvas = document.getElementById("edit_canvas");
		window.edit_context = window.edit_canvas.getContext("2d");
		var edit_td = $('#edit_td');
		window.edit_canvas.width = edit_td.width() - 6;
		window.edit_canvas.height = edit_td.height() - 6;
		window.edit_m = Math.max(window.sector.length / window.edit_canvas.width, window.sector.width / window.edit_canvas.height)

		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = window.preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		window.preview_canvas.width = preview_td.width();// - 4;
		window.preview_canvas.height = preview_td.height();// - 4;
		window.preview_m = Math.max(window.sector.length / window.preview_canvas.width, window.sector.width / window.preview_canvas.height);

		window.km = edit_m;
		window.kx = 0;
		window.ky = 0;
// draw sector
		window.rackAdd = false;
		window.rackTemplateAdd = null;
		window.previewMove = false;
		window.showcase = null;

		for (var i = 0; i < window.showcaseList.length; i++) {
			calcCoordinatesRack(window.showcaseList[i]);
		}

		drawEditCanvas();

		drawPreviewCanvas();

		setSectorProperty(sector);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}

	function setSectorProperty(sector) {
		$('#sectorName').text(sector.name_sector);
		$('#sectorLength').text(sector.length);
		$('#sectorWidth').text(sector.width);
		$('#sectorHeight').text(sector.height);
	}

	function ie_event(e) {
		if (e === undefined) {
			return window.event;
		}
		return e;
	}

	function drawEditCanvas() {
		window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "BLACK";
		window.edit_context.strokeRect(
				-window.kx / window.km,
				-window.ky / window.km,
				window.sector.length / window.km,
				window.sector.width / window.km);
		var view_mode = $('#view_mode').val();
		for (var i = 0; i < window.showcaseList.length; i++) {
			drawRack(window.showcaseList[i], window.rackStateList[i], window.rackStateInSectorList[i], view_mode, window.edit_context, window.kx, window.ky, window.km);
		}
		if (window.ruler.state >= 2) {
			// рисуем линейку
			window.edit_context.lineWidth = 1;
			window.edit_context.strokeStyle = "BLUE";
			window.edit_context.beginPath();
			window.edit_context.moveTo((window.ruler.ax - window.kx) / window.km,
					(window.ruler.ay - window.ky) / window.km);
			window.edit_context.lineTo((window.ruler.bx - window.kx) / window.km,
					(window.ruler.by - window.ky) / window.km);
			window.edit_context.stroke();
		}
	}

	function drawPreviewCanvas() {
		window.preview_context.clearRect(0, 0, window.preview_canvas.width, window.preview_canvas.height);
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLACK";
		window.preview_context.strokeRect(0, 0, window.sector.length / window.preview_m, window.sector.width / window.preview_m);
		var view_mode = $('#view_mode').val();
		for (var i = 0; i < window.showcaseList.length; i++) {
			drawRack(window.showcaseList[i], window.rackStateList[i], window.rackStateInSectorList[i], view_mode, window.preview_context, 0, 0, window.preview_m);
		}
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLUE";
		window.preview_context.strokeRect(window.kx / window.preview_m,
				window.ky / window.preview_m,
				window.edit_canvas.width * window.km / window.preview_m,
				window.edit_canvas.height * window.km / window.preview_m);
	}
</script>

<script type="text/javascript">
	function selectShowcase(showcase) {
		if (showcase != null) {
			$('#rackType').text(showcase.type_rack);
			$('#rackName').text(showcase.name_rack);
			$('#rackBarcode').text(showcase.rack_barcode);
			$('#rackX').text(showcase.x_coord);
			$('#rackY').text(showcase.y_coord);
			$('#rackAngle').text(showcase.angle);
			$('#rackWidth').text(showcase.width);
			$('#rackLength').text(showcase.length);
			$('#rackHeight').text(showcase.height);
			$('#rackLoadSide').text(showcase.load_side);
		} else {
			$('#rackType').text('');
			$('#rackName').text('');
			$('#rackBarcode').text('');
			$('#rackX').text('');
			$('#rackY').text('');
			$('#rackAngle').text('');
			$('#rackWidth').text('');
			$('#rackLength').text('');
			$('#rackHeight').text('');
			$('#rackLoadSide').text('');
		}
	}
</script>

<%--обработка событий редактора--%>
<script type="text/javascript">
	function editCanvasMouseListener() {
		window.edit_canvas.onmousedown = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx + evnt.offsetX * window.km;
			var sy = window.ky + evnt.offsetY * window.km;

			if (window.ruler.state == 1) {
				rulerMoveA(sx, sy);
				window.ruler.state = 2;
			}
			else if (window.ruler.state == 2) {
				rulerMoveB(sx, sy);
				window.ruler.state = 3;
			}
			else {
				window.showcase = null;
				var d1 = null;
				var d2 = null;
				var d3 = null;
				var d4 = null;
				for (var i = window.showcaseList.length - 1; window.showcase == null && i >= 0; i--) {
					d1 = distance(window.showcaseList[i].x1, window.showcaseList[i].y1, window.showcaseList[i].x2, window.showcaseList[i].y2, sx, sy);
					d2 = distance(window.showcaseList[i].x2, window.showcaseList[i].y2, window.showcaseList[i].x3, window.showcaseList[i].y3, sx, sy);
					d3 = distance(window.showcaseList[i].x3, window.showcaseList[i].y3, window.showcaseList[i].x4, window.showcaseList[i].y4, sx, sy);
					d4 = distance(window.showcaseList[i].x4, window.showcaseList[i].y4, window.showcaseList[i].x1, window.showcaseList[i].y1, sx, sy);
					if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
						window.showcase = window.showcaseList[i];
					}
				}
				selectShowcase(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
			}
		}

		window.edit_canvas.onmousemove = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx + evnt.offsetX * window.km;
			var sy = window.ky + evnt.offsetY * window.km;
			if (window.ruler.state == 1) {
				rulerMoveA(sx, sy);
				drawEditCanvas();
			} else if (window.ruler.state == 2) {
				rulerMoveB(sx, sy);
				drawEditCanvas();
			}
		}
	}
</script>
<%-- обработка событий окна навигации--%>
<script type="text/javascript">
	function previewCanvasMouseListener() {
		window.preview_canvas.onmousedown = function (e) {
			window.previewMove = true;
			var evnt = ie_event(e);
			window.x = evnt.clientX;
			window.y = evnt.clientY;
			window.kx = evnt.offsetX * window.preview_m - window.edit_canvas.width * window.km / 2;
			window.ky = evnt.offsetY * window.preview_m - window.edit_canvas.height * window.km / 2;
			checkKxKy();
			drawPreviewCanvas();
		};

		window.preview_canvas.onmouseup = function (e) {
			if (window.previewMove) {
				window.previewMove = false;
				drawEditCanvas();
			}
		};

		window.preview_canvas.onmouseover = function (e) {
			if (window.previewMove) {
				window.previewMove = false;
				drawEditCanvas();
			}
		};

		window.preview_canvas.onmousemove = function (e) {
			if (window.previewMove) {
				var evnt = ie_event(e);
				window.kx = window.kx + (evnt.clientX - window.x) * window.preview_m;
				window.ky = window.ky + (evnt.clientY - window.y) * window.preview_m;
				checkKxKy();
				window.x = evnt.clientX;
				window.y = evnt.clientY;
				drawPreviewCanvas();
			}
		};
	}

	function kMadd() {
		window.km = window.km * 1.5;
		if (window.km > window.edit_m)
			window.km = window.edit_m;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	}

	function kMsub() {
		window.km = window.km / 1.5;
		if (window.km < 0.1)
			window.km = 0.1;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	}

	function checkKxKy() {
		if (window.kx > window.sector.length - window.edit_canvas.width * km)
			window.kx = window.sector.length - window.edit_canvas.width * km;
		if (window.kx < 0)
			window.kx = 0;
		if (window.ky > window.sector.width - window.edit_canvas.height * km)
			window.ky = window.sector.width - window.edit_canvas.height * km;
		if (window.ky < 0)
			window.ky = 0;
	}
</script>
</body>
</html>