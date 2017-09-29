<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page import="planograma.servlet.rack.history.RackHistoryGetMark" %>
<%@ page import="planograma.servlet.wares.WaresEdit" %>
<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.rack.history.RackHistoryView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>История стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/planogram2D.js"></script>
	<script type="text/javascript" src="js/utils/ruler.js"></script>
	<script type="text/javascript" src="js/common/RackShelfPanelListener.js"></script>
	<script type="text/javascript" src="js/common/RackShelfRound.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_drawEditCanvas.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_drawPreviewCanvas.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_drawWares.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_image.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_PreviewPanelListener.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_rackWaresCalcCoordinates.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body onload="loadComplete();">
<table class="frame">
	<tr>
		<td class="path">
			<table>
				<tr>
					<td><a href="menu.jsp">Меню</a></td>
					<td>&gt;</td>
					<td><a href="sectorList.jsp">Залы торговых площадок</a></td>
					<td>&gt;</td>
					<td><a href="sectorEdit.jsp">Редактирование зала</a></td>
					<td>&gt;</td>
					<td><a href="rackWaresPlacement.jsp">Товары стеллажа</a></td>
					<td>&gt;</td>
					<td><i>История стеллажа</i></td>
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
									   onclick="return aOnClick(this, fRackHistoryView)"><%=JspUtils.toMenuTitle("Просмотреть")%>
								</a></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td><a href="#" id="butRuler" onclick="return aOnClick(this, fRuler)"><%=JspUtils.toMenuTitle("Рулетка")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td id="edit_td" width="100%">
						<canvas id='edit_canvas' width="320" height="200">canvas not supported</canvas>
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table class="frame">
										<tr>
											<td id="preview_td" colspan="2">
												<canvas id='preview_canvas'>canvas not supported</canvas>
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
											<td>x1:<input id="ruler_ax" type="text" size="3" disabled="disabled"/>y1:<input id="ruler_ay" type="text" size="3" disabled="disabled"/>x2:<input id="ruler_bx" type="text" size="3" disabled="disabled"/>y2:<input id="ruler_by" type="text" size="3" disabled="disabled"/></td>
										</tr>
										<tr>
											<td>dx:<input id="ruler_dx" type="text" size="3" disabled="disabled"/>dy:<input id="ruler_dy" type="text" size="3" disabled="disabled"/>&nbsp;&nbsp;&nbsp;l:<input id="ruler_l" type="text" size="3" disabled="disabled"/>&nbsp;<a href="#" onclick="window.ruler.state=0; drawEditCanvas(); $('#rulerPanel').hide();">Скрыть</a></td>
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
									<table width="100%">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td align="right">стеллаж&nbsp;</td>
											<td id="rackName"></td>
										</tr>
										<tr>
											<td align="right">габариты&nbsp;</td>
											<td id="rackSize"></td>
										</tr>
										<tr>
											<td align="right">загрузка&nbsp;</td>
											<td id="rackLoadSide"></td>
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
									<table id="shelfPanel" width="100%" style="display: none;">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства полки:</td>
										</tr>
										<tr>
											<td align="right">x&nbsp;</td>
											<td id="shelfX"></td>
										</tr>
										<tr>
											<td align="right">y&nbsp;</td>
											<td id="shelfY"></td>
										</tr>
										<tr>
											<td align="right">угол&nbsp;</td>
											<td id="shelfAngle"></td>
										</tr>
										<tr>
											<td align="right">ширина&nbsp;</td>
											<td id="shelfWidth"></td>
										</tr>
										<tr>
											<td align="right">высота&nbsp;</td>
											<td id="shelfHeight"></td>
										</tr>
										<tr>
											<td align="right">глубина&nbsp;</td>
											<td id="shelfLength"></td>
										</tr>
										<tr>
											<td align="right">тип&nbsp;</td>
											<td id="shelfType"></td>
										</tr>
									</table>
									<table id="waresPanel" style="display: none;">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td align="right">товар&nbsp;</td>
											<td id="waresName"></td>
										</tr>
										<tr>
											<td align="right">код&nbsp;</td>
											<td id="waresCode"></td>
										</tr>
										<tr>
											<td align="right" title="Единица измерения">е.и.&nbsp;</td>
											<td id="waresUnit"></td>
										</tr>
										<tr>
											<td align="right">штрихкод&nbsp;</td>
											<td id="waresBarcode"></td>
										</tr>
										<tr>
											<td align="right">x&nbsp;</td>
											<td id="waresX"></td>
										</tr>
										<tr>
											<td align="right">y&nbsp;</td>
											<td id="waresY"></td>
										</tr>
										<tr>
											<td align="right">габариты&nbsp;</td>
											<td>
												<span id="waresWidth" title="ширина"></span>x<span id="waresHeight" title="высота"></span>x<span id="waresLength" title="глубина"></span>
											</td>
										</tr>
										<tr>
											<td align="right" title="количество товара в глубину стеллажа">кол-во&nbsp;</td>
											<td id="waresCountInLength"></td>
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
/**
 * выделеные товары
 * @type {Array}
 */
window.selectRackWaresList = [];

	function loadComplete() {
		var code_rack = getCookie('code_rack');
		postJson('<%=RackHistoryGetMark.URL%>', {code_rack: code_rack}, function (data) {
			var dateMark=$('#dateMark');
			dateMark.empty();
			dateMark.append("<option></option>");
			if (data!=null && data.rackHistoryMark!=null && data.rackHistoryMark.length>0)
			{
				for (var i=data.rackHistoryMark.length-1; i>=0; i--)
				{
					dateMark.append($("<option></option>")
							.attr("value",data.rackHistoryMark[i])
							.text(dateTimeToString(data.rackHistoryMark[i])));
				}
			}
		});
	}

function fRackHistoryView()
{
	var date = $('#dateMark').val();
	if (date > 0) {
		var code_rack = getCookie('code_rack');
	postJson('<%=RackHistoryView.URL%>', {code_rack:code_rack, date:date}, function (data) {
		window.rack = data.rack;
		switch (window.rack.load_side)
		{
			case '<%=LoadSide.U%>':
				var temp=window.rack.width;
				window.rack.width=window.rack.length;
				window.rack.length=window.rack.height;
				window.rack.height=temp;

				temp=window.rack.real_width;
				window.rack.real_width=window.rack.real_length;
				window.rack.real_length=window.rack.real_height;
				window.rack.real_height=temp;
				break;
			case '<%=LoadSide.F%>':
				var temp=window.rack.width;
				window.rack.width=window.rack.length;
				window.rack.length=temp;

				temp=window.rack.real_width;
				window.rack.real_width=window.rack.real_length;
				window.rack.real_length=temp;

				temp=window.rack.y_offset;
				window.rack.y_offset=window.rack.z_offset;
				window.rack.z_offset=temp;
				break;
		}
		window.rackShelfList = data.rackShelfList;
		window.rackWaresList = data.rackWaresList;
		loadComplete2();
	});        }
}

	function loadComplete2()
	{
		window.edit_canvas = document.getElementById("edit_canvas");
		window.edit_context = edit_canvas.getContext("2d");
		var edit_td = $('#edit_td');
		window.edit_canvas.width = edit_td.width() - 6;
		window.edit_canvas.height = edit_td.height() - 6;

		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		window.preview_canvas.width = preview_td.width();
		window.preview_canvas.height = preview_td.height();

		// смещение стеллажа
		window.offset_rack_x=-Math.min(0, window.rack.x_offset);
		window.offset_rack_y=-Math.min(0, window.rack.y_offset);
		// смещение полезного обема
		window.offset_real_rack_x=Math.max(0, window.rack.x_offset);
		window.offset_real_rack_y=Math.max(0, window.rack.y_offset);
		// определение максимальных габаритов
		window.max_x=Math.max(window.offset_rack_x + window.rack.width, window.offset_real_rack_x+window.rack.real_width);
		window.max_y=Math.max(window.offset_rack_y + window.rack.height, window.offset_real_rack_y+window.rack.real_height);

		// масштаб в окне редактирования
		window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
		// масштаб в окне навигации
		window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
		// камера
		window.km = window.edit_m;
		window.kx = 0;
		window.ky = 0;

		x = 0;
		y = 0;
		window.shelf = null;

		for (var i = 0; i < window.rackShelfList.length; i++) {
			rackShelfCalcCoordinates(window.rackShelfList[i]);
		}

		for (var i = 0; i < window.rackWaresList.length; i++) {
			rackWaresCalcCoordinates(window.rackWaresList[i]);
		}
		rackWaresLoadImage();

		setRack(window.rack);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}



	function setRack(rack)
	{
		$('#rackName').text(window.rack.name_rack);
		$('#rackSize').text(window.rack.width + 'x' + window.rack.height + 'x' + window.rack.length);
		switch (window.rack.load_side) {
			<%
			for (final LoadSide loadSide:LoadSide.values())
			{
			%>
			case '<%=loadSide.name()%>':
				$('#rackLoadSide').text('<%=loadSide.getDesc()%>');
				break;
			<%
			}
			%>
		}
	}

	function fRackWaresPlacementSelect() {
		if (window.selectRackWaresList.length > 0) {
			if (window.selectRackWaresList.length == 1) {
				var rackWares = window.selectRackWaresList[0];
				$('#waresCode').text(rackWares.code_wares);
				if (rackWares.name_wares.length > 30) {
					$('#waresName').text(rackWares.name_wares.substring(0, 29) + '...');
				}
				else {
					$('#waresName').text(rackWares.name_wares);
				}
				$('#waresName').attr('title', rackWares.name_wares);
				$('#waresUnit').text(rackWares.abr_unit);
				$('#waresBarcode').text(rackWares.bar_code);
				$('#waresX').text(rackWares.position_x);
				$('#waresY').text(rackWares.position_y);
				$('#waresWidth').text(rackWares.wares_width);
				$('#waresHeight').text(rackWares.wares_height);
				$('#waresLength').text(rackWares.wares_length);
				$('#waresCountInLength').text(rackWares.count_length_on_shelf);
				$('#shelfPanel').hide();
				$('#waresPanel').show();
			} else {
				$('#waresPanel').hide();
			}
		}
		else {
			$('#waresPanel').hide();
			if (window.shelf != null) {
				$('#shelfX').text(shelf.x_coord);
				$('#shelfY').text(shelf.y_coord);
				$('#shelfAngle').text(shelf.angle);
				$('#shelfWidth').text(shelf.shelf_width);
				$('#shelfHeight').text(shelf.shelf_height);
				$('#shelfLength').text(shelf.shelf_length);
				$('#shelfType').text(shelf.type_shelf);
				$('#shelfPanel').show();
			} else {
				$('#shelfPanel').hide();
			}
		}
	}

function ie_event(e) {
	if (e === undefined) {
		return window.event;
	}
	return e;
}
</script>
<%--прорисовка элементов--%>
<script type="text/javascript">
	function drawShelf(shelf, canvas, context, kx, ky, m) {
		context.lineWidth = 1;
		if (window.shelf==shelf)
		{
			context.strokeStyle = "BLUE";
		}
		else
		{
			context.strokeStyle = "BLACK";
		}
		switch (shelf.type_shelf)
		{
				<%
			 for (final TypeShelf typeShelf:TypeShelf.values())
			 {
				 out.print("case '");
				 out.print(typeShelf.name());
				 out.print("': context.fillStyle = '");
				 out.print(typeShelf.getColor());
				 out.println("'; break;");
			 }
			 %>
		}
		context.beginPath();
		var x1 = (shelf.p1.x - kx+window.offset_rack_x) / m;
		var y1 = canvas.height - (shelf.p1.y - ky+ window.offset_rack_y) / m;
		var x2 = (shelf.p2.x - kx+window.offset_rack_x) / m;
		var y2 = canvas.height - (shelf.p2.y - ky+ window.offset_rack_y) / m;
		var x3 = (shelf.p3.x - kx+window.offset_rack_x) / m;
		var y3 = canvas.height - (shelf.p3.y - ky+ window.offset_rack_y) / m;
		var x4 = (shelf.p4.x - kx+window.offset_rack_x) / m;
		var y4 = canvas.height - (shelf.p4.y - ky+ window.offset_rack_y) / m;
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.lineTo(x3, y3);
		context.lineTo(x4, y4);
		context.closePath();
		context.stroke();
		context.fill();
	}

</script>

<%--обработка событий редактора--%>
<script type="text/javascript">
	function editCanvasMouseListener() {

		window.edit_canvas.onmousedown = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx - window.offset_rack_x + evnt.offsetX * window.km;
			var sy = window.ky - window.offset_rack_y + (window.edit_canvas.height - evnt.offsetY) * window.km;

			var oldSelectRackWaresList = window.selectRackWaresList;

			var findRackWares = null;
			window.shelf = null;
			window.selectRackWaresList = [];
			if (window.ruler.state==1)
			{
				rulerMoveA(sx,sy);
				window.ruler.state=2;
			}
			else
			if (window.ruler.state==2)
			{
				rulerMoveB(sx,sy);
				window.ruler.state=3;
			} else {
				// поиск товара под указателем
				for (var i = window.rackWaresList.length - 1; findRackWares == null && i >= 0; i--) {
					if (pointInsideRackWares(window.rackWaresList[i], sx, sy)) {
						findRackWares = window.rackWaresList[i];
					}
				}
				if (findRackWares != null) {
					// перемещение товара
					if ($.inArray(findRackWares, oldSelectRackWaresList) < 0) {
						window.selectRackWaresList.push(findRackWares);
					}
					else {
						window.selectRackWaresList = oldSelectRackWaresList;
					}
					x = evnt.clientX;
					y = evnt.clientY;
				}
				else {
					// поиск полки под курсором
					var d1 = null;
					var d2 = null;
					var d3 = null;
					var d4 = null;
					var pClick=new Point2D(sx, sy);
					for (var i = window.rackShelfList.length - 1; window.shelf == null && i >= 0; i--) {
						var p1=new Point2D(window.rackShelfList[i].x1,window.rackShelfList[i].y1);
						var p2=new Point2D(window.rackShelfList[i].x2,window.rackShelfList[i].y2);
						var p3=new Point2D(window.rackShelfList[i].x3,window.rackShelfList[i].y3);
						var p4=new Point2D(window.rackShelfList[i].x4,window.rackShelfList[i].y4);
						d1 = new Segment2D(p1,p2).distance(pClick);
						d2 = new Segment2D(p2,p3).distance(pClick);
						d3 = new Segment2D(p3,p4).distance(pClick);
						d4 = new Segment2D(p4,p1).distance(pClick);
						if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
							window.shelf = window.rackShelfList[i];
						}
					}
					if (window.shelf == null)
					{
						// начало области выделения
						window.m_select = 1;
						window.m_x_begin = sx;
						window.m_y_begin = sy;
						window.m_x_end = sx;
						window.m_y_end = sy;
					}
				}
			}
			fRackWaresPlacementSelect();
			drawEditCanvas();
			drawPreviewCanvas();
		}

		window.edit_canvas.onmouseup = function(e) {
			if (window.m_select==1)
			{
				// области выделения
				{
					window.selectRackWaresList=[];
					// выделение нескольких товаров
					// поиск товара в области
					for (var i= window.rackWaresList.length-1; i>=0 ;i--)
					{
						var rackWares=window.rackWaresList[i];
						if (window.m_x_begin>window.m_x_end)
						{
							var t=window.m_x_begin;
							window.m_x_begin=window.m_x_end;
							window.m_x_end=t;
						}
						if (window.m_y_begin>window.m_y_end)
						{
							var t=window.m_y_begin;
							window.m_y_begin=window.m_y_end;
							window.m_y_end=t;
						}
						// если хоть одна из вершин лежит в области выделения
						var sr=new SelectRectangle2D(new Point2D(window.m_x_begin, window.m_y_begin), new Point2D(window.m_x_end,window.m_y_end));
						if (rackWares.p1.insideSelectRectangle(sr)||
							rackWares.p2.insideSelectRectangle(sr)||
							rackWares.p3.insideSelectRectangle(sr)||
							rackWares.p4.insideSelectRectangle(sr))
						{
							window.selectRackWaresList.push(rackWares);
						}
						else
						{
							// если одна из граней пересекается с гранью области выделения
							if (
								new Segment2D(rackWares.p1, rackWares.p2).intersectsSelectRectangle(sr) ||
								new Segment2D(rackWares.p2, rackWares.p3).intersectsSelectRectangle(sr) ||
								new Segment2D(rackWares.p3, rackWares.p4).intersectsSelectRectangle(sr) ||
								new Segment2D(rackWares.p4, rackWares.p1).intersectsSelectRectangle(sr)
							)
							{
								window.selectRackWaresList.push(rackWares);
							}
						}
					}
				}
				window.m_select=0;
			}
			fRackWaresPlacementSelect();
			drawEditCanvas();
			drawPreviewCanvas();
		}
		window.edit_canvas.onmouseout = window.edit_canvas.onmouseup

		window.edit_canvas.onmousemove = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx - window.offset_rack_x + evnt.offsetX * window.km;
			var sy = window.ky - window.offset_rack_y + (window.edit_canvas.height - evnt.offsetY) * window.km;

			if (window.ruler.state==1) {
				rulerMoveA(sx,sy);
				drawEditCanvas();
			} else if (window.ruler.state==2) {
				rulerMoveB(sx,sy);
				drawEditCanvas();
			} else if (window.m_select==1) {
				// изменение области выделения
				window.m_x_end=window.kx - window.offset_rack_x + evnt.offsetX * window.km;
				window.m_y_end = window.ky - window.offset_rack_y + (window.edit_canvas.height - evnt.offsetY) * window.km;
				drawEditCanvas();
			}
		}
	}

	function pointInsideRackWares(rackWares, x, y)
	{
		var f=Math.abs(x-rackWares.position_x)<rackWares.wares_width/2;
		f=f && Math.abs(y-rackWares.position_y)<rackWares.wares_height/2;
		return f;
	}
</script>

</body>
</html>