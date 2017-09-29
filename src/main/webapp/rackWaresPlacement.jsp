<%@ page import="planograma.constant.SecurityConst" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.RackShelf" %>
<%@ page import="planograma.data.TypeRackWares" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page import="planograma.servlet.wares.buffer.BufferGet" %>
<%@ page import="planograma.servlet.wares.buffer.BufferSet" %>
<%@ page import="planograma.servlet.wares.RackWaresPlacementSave" %>
<%@ page import="planograma.servlet.wares.WaresEdit" %>
<%@ page import="planograma.servlet.wares.WaresGroupTree" %>
<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.wares.basket.BasketSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_rack_wares_placement=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_WARES_PLACEMENT);
	final String access_rack_shelf_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_WARES_PLACEMENT_AND_RACK_SHELF_EDIT);
%>
<html>
<head>
	<title>Товары стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/planogram2D.js"></script>
	<script type="text/javascript" src="js/utils/ruler.js"></script>
	<script type="text/javascript" src="js/common/RackShelfPanelListener.js"></script>
	<script type="text/javascript" src="js/common/RackShelfRound.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_basket.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_drawEditCanvas.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_drawPreviewCanvas.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_drawWares.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_fCopy.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_fCut.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_fDel.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_fPaste.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_image.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_PreviewPanelListener.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_rackWaresCalcCoordinates.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_WaresPanelListener.js"></script>
	<script type="text/javascript" src="js/rackWaresPlacement/rackWaresPlacement_fAddBasket.js"></script>
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
					<td><i>Товары стеллажа</i></td>
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
								<td><a href="#" onclick="return aOnClick(this, fRackWaresPlacementSave)" class="<%=access_rack_wares_placement%>"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackWaresPlacementReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackViewHistory)"><%=JspUtils.toMenuTitle("Просмотр истории стеллажа")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackShelfAdd)" class="<%=access_rack_shelf_edit%>"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fAddWares)" class="<%=access_rack_wares_placement%>"><%=JspUtils.toMenuTitle("Добавить товар")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butRuler" onclick="return aOnClick(this, fRuler)"><%=JspUtils.toMenuTitle("Рулетка")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCopy" onclick="return aOnClick(this, fCopy)" class="disabled"><%=JspUtils.toMenuTitle("Копировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCut" onclick="return aOnClick(this, fCut)" class="disabled"><%=JspUtils.toMenuTitle("Вырезать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butPaste" onclick="return aOnClick(this, fPaste)" class="disabled"><%=JspUtils.toMenuTitle("Вставить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCopyBuffer" onclick="return aOnClick(this, fCopyBuffer)" class="disabled"><%=JspUtils.toMenuTitle("Копировать в буфер")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butPasteBuffer" onclick="return aOnClick(this, fPasteBuffer)"><%=JspUtils.toMenuTitle("Вставить из буфера")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butAddBasket" onclick="return aOnClick(this, fAddBasket)"><%=JspUtils.toMenuTitle("Вернуть в корзину")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
							<tr>
								<td>
									вид корзины:<a href="#" onclick="fRackWaresPlacementBasketTable()">Список</a> <a href="#" onclick="fRackWaresPlacementBasketTile()">Плитка</a>
								</td>
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
											<td><input type="text" id="shelfX" onchange="changeShelfX(this)" onkeydown="numberFieldKeyDown(event, this)" disabled="<%=access_rack_shelf_edit%>"/></td>
										</tr>
										<tr>
											<td align="right">y&nbsp;</td>
											<td><input type="text" id="shelfY" onchange="changeShelfY(this)" onkeydown="numberFieldKeyDown(event, this)" disabled="<%=access_rack_shelf_edit%>"/></td>
										</tr>
										<tr>
											<td align="right">угол&nbsp;</td>
											<td><input type="text" id="shelfAngle" onchange="changeShelfAngle(this)" onkeydown="numberFieldKeyDown(event, this)" disabled="<%=access_rack_shelf_edit%>"/></td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="shelfWidth" onchange="changeShelfWidth(this)" onkeydown="numberFieldKeyDown(event, this)" disabled="<%=access_rack_shelf_edit%>"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="shelfHeight" onchange="changeShelfHeight(this)" onkeydown="numberFieldKeyDown(event, this)" disabled="<%=access_rack_shelf_edit%>"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="shelfLength" onchange="changeShelfLength(this)" onkeydown="numberFieldKeyDown(event, this)" disabled="<%=access_rack_shelf_edit%>"/></td>
										</tr>
										<tr>
											<td align="right">тип</td>
											<td >
												<select id="shelfType" onchange="changeShelfType(this)" disabled="<%=access_rack_shelf_edit%>">
													<%
														for (final TypeShelf typeShelf:TypeShelf.values())
														{
															out.write("<option value=\"");
															out.write(typeShelf.name());
															out.write("\">");
															out.write(typeShelf.getDesc());
															out.write("</option>");
														}
													%>
												</select>
											</td>
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
											<td>
												<input type="text" id="waresX" onchange="changeWaresX(this)" onkeydown="numberFieldKeyDown(event, this)" size="5"/>
												&nbsp;&nbsp;y&nbsp;<input type="text" id="waresY" onchange="changeWaresY(this)" onkeydown="numberFieldKeyDown(event, this)" size="5"/>
											</td>
										</tr>
										<tr>
											<td align="right">габариты&nbsp;</td>
											<td>
												<span id="waresWidth" title="ширина"></span>x<span id="waresHeight" title="высота"></span>x<span id="waresLength" title="глубина"></span>
											</td>
										</tr>
										<tr>
											<td align="right" title="количество товара в глубину стеллажа">кол-во&nbsp;</td>
											<td><input type="text" id="waresCountInLength" onchange="changeWaresCountInLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
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
				<%--корзина--%>
				<tr>
					<td colspan="3" style="height:250px; border-top: 1px solid black;">
						<div id="basketPanel">
						</div>
						<div id="basketPanelTable">
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<jsp:include page="choiceWares.jsp"/>
<%--<%@include file="choiceWares.jsp"%>--%>

<script type="text/javascript">
/**
 * выделеные товары
 * @type {Array}
 */
window.selectRackWaresList = [];
/**
 * товар выбраный в корзине
 * @type {*}
 */
window.selectBasket = null;

/**
* скопированая полка
* @type {*}
 */
window.copyShelf = null;
/**
 * скопированые товары
 * @type {Array}
 */
window.copyObjectList = [];
/**
 * растояние по ширине между товарами при вставке группами
 * @type {Number}
 */
window.d_wares_width = 1;
/**
 * растояние по высоте между товарами при вставке группами
 * @type {Number}
 */
window.d_wares_height = 1;

var canRackWaresPlacement='<%=access_rack_wares_placement%>';
var canRackShelfEdit='<%=access_rack_shelf_edit%>';

	function loadComplete() {
		var code_rack = getCookie('code_rack');
		postJson('<%=WaresEdit.URL%>', {code_rack:code_rack}, function (data) {
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
			// товары в корзине
			window.basket = data.basket;
			loadComplete2();
		});
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

		window.shelfAdd = false;
		window.editMove = 0;
		window.flagPaste=0;

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
//		drawEditCanvas();
//		drawPreviewCanvas();

		basketToHtml(window.basket);

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
			if (canRackWaresPlacement!='disabled')
			{
				$('#butCopy').removeClass('disabled');
				$('#butCut').removeClass('disabled');
				$('#butCopyBuffer').removeClass('disabled');
			}
			if (window.selectRackWaresList.length == 1) {
				var rackWares = window.selectRackWaresList[0];
				$('#waresCode').text(rackWares.code_wares);
				if (rackWares.name_wares.length > 30) {
					$('#waresName').text(rackWares.name_wares.substring(0, 29)+'...');
				}
				else {
					$('#waresName').text(rackWares.name_wares);
				}
				$('#waresName').attr('title', rackWares.name_wares);
				$('#waresUnit').text(rackWares.abr_unit);
				$('#waresBarcode').text(rackWares.bar_code);
				$('#waresX').val(rackWares.position_x);
				$('#waresY').val(rackWares.position_y);
				$('#waresWidth').text(rackWares.wares_width);
				$('#waresHeight').text(rackWares.wares_height);
				$('#waresLength').text(rackWares.wares_length);
				$('#waresCountInLength').val(rackWares.count_length_on_shelf);
				$('#shelfPanel').hide();
				$('#waresPanel').show();
			} else {
				$('#waresPanel').hide();
			}
		}
		else {
			$('#waresPanel').hide();
			if (window.shelf != null) {
				if (canRackShelfEdit!='disabled')
				{
					$('#butCopy').removeClass('disabled');
					$('#butCut').removeClass('disabled');
					$('#butCopyBuffer').removeClass('disabled');
				}
				$('#shelfX').val(shelf.x_coord);
				$('#shelfY').val(shelf.y_coord);
				$('#shelfAngle').val(shelf.angle);
				$('#shelfWidth').val(shelf.shelf_width);
				$('#shelfHeight').val(shelf.shelf_height);
				$('#shelfLength').val( shelf.shelf_length);
				$('#shelfType').val(shelf.type_shelf);
				$('#shelfPanel').show();
			} else {
				$('#shelfPanel').hide();
			$('#butCopy').addClass('disabled');
			$('#butCut').addClass('disabled');
			$('#butCopyBuffer').addClass('disabled');
		}
	}

		if (window.copyObjectList.length > 0 || window.copyShelf!=null)
			$('#butPaste').removeClass('disabled');
	}

	function selectShelf(shelf) {
		fRackWaresPlacementSelect();
				}

	function ie_event(e) {
		if (e === undefined) {
			return window.event;
		}
		return e;
	}
	function roundRackWares(rackWares)
	{
		rackWares.position_x=Math.round(rackWares.position_x);
		rackWares.position_y=Math.round(rackWares.position_y);
		rackWaresCalcCoordinates(rackWares);
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

	function basketToHtml(basket)
	{
		postJson('<%=BasketSet.URL%>', basket, null);
		var basketPanel=$('#basketPanel');
		basketPanel.empty();

		var basketPanelTable=$('#basketPanelTable');
		basketPanelTable.empty();
		btHtml='<table border="1">'+
				'<tr>' +
				'<th></th>' +
				'<th>Код</th>' +
				'<th>Название</th>' +
				'<th>Е.И.</th>' +
				'<th>Штрихкод</th>' +
				'<th>Габариты</th>' +
				'</tr>';
		for (var i in basket)
		{
			var w=basket[i];
			var wHtml='<input id="'+i+'" type="image"';
			if (w.code_image>0)
			{
				wHtml=wHtml+' src="image/'+ w.code_image+'"';
			}
			wHtml=wHtml+' width="'+ w.width/window.km+'"';
			wHtml=wHtml+' height="'+ w.height/window.km+'"';
			wHtml=wHtml+' title="'+ w.code_wares+'\n'+ w.name_wares+'\n'+ w.abr_unit+'"';
			wHtml=wHtml+' class="basket"';
			wHtml=wHtml+' onclick="basketWaresSelect(this)">';
			basketPanel.append(wHtml);
			btHtml+='<tr>' +
					'<td><input type="radio" name="code_wares" value="'+w.code_wares+'" onclick="basketWaresSelect(this)"></td>'+
					'<td>&nbsp;'+w.code_wares+'&nbsp;</td>'+
					'<td>&nbsp;'+w.name_wares+'&nbsp;</td>'+
					'<td>&nbsp;'+w.abr_unit+'&nbsp;</td>'+
					'<td>&nbsp;'+w.bar_code+'&nbsp;</td>'+
					'<td>&nbsp;'+w.width + 'x' + w.height + 'x' + w.length+'&nbsp;</td>'+
					'</tr>';
		}
		btHtml+='</table>'
		basketPanelTable.append(btHtml);
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackWaresPlacementSave() {
		// проверить выход товара за стеллаж
		var rackWaresOutside = [];
		var sr = new SelectRectangle2D(new Point2D(window.rack.x_offset, window.rack.y_offset), new Point2D(window.rack.x_offset+window.rack.real_width, window.rack.y_offset + window.rack.real_height));
		for (var i in window.rackWaresList) {
			var rackWares = window.rackWaresList[i];
			if (!rackWares.p1.insideSelectRectangle(sr) ||
					!rackWares.p2.insideSelectRectangle(sr) ||
					!rackWares.p3.insideSelectRectangle(sr) ||
					!rackWares.p4.insideSelectRectangle(sr)) {
				rackWaresOutside.push(rackWares);
			}
		}
		if (rackWaresOutside.length == 0 || confirm("Все товары выходящие за пределы стеллажа будут удалены.")) {
			for (var i in rackWaresOutside) {
				window.rackWaresList.splice(window.rackWaresList.indexOf(rackWaresOutside[i]), 1);
			}
			// проверить пересечение товаров между собой и полками
			var rackWaresIntersects = [];
			var map = new Array(window.rack.width * window.rack.height);
			for (var i in window.rackShelfList) {
				var rackShelf = window.rackShelfList[i];
				drawQuadrangle(map, window.rack.width, window.rack.height,
						rackShelf.p1,
						rackShelf.p2,
						rackShelf.p3,
						rackShelf.p4)
			}
			for (var i in window.rackWaresList) {
				var rackWares = window.rackWaresList[i];
				if (drawQuadrangle(map, window.rack.width, window.rack.height,
						rackWares.p1,
						rackWares.p2,
						rackWares.p3,
						rackWares.p4)) {
					rackWaresIntersects.push(rackWares);
				}
			}

//			drawTestMap(window.edit_canvas, window.edit_context, window.rack.width, window.rack.height, map);
//			return;

			if (rackWaresIntersects.length > 0) {
				alert("Сохранение отменено.\n Существуют товары которые пересекаются.\n Переместите их или удалите.");
				window.selectRackWaresList = rackWaresIntersects;
				fRackWaresPlacementSelect();
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else {
				var data = {code_rack: window.rack.code_rack, rackWaresList: window.rackWaresList};
				if (canRackShelfEdit != 'disabled') {
					data.rackShelfList = window.rackShelfList;
				}
				postJson('<%=RackWaresPlacementSave.URL%>', data,
						function (data) {
							if (data.errorField != null && data.errorField.length > 0) {
								// есть ошибки
								var setFocus = null;
								var error_message = "";
								for (var i = 0; setFocus == null && i < data.errorField.length; i++) {
									error_message += data.errorField[i].message + '\n';
									if (setFocus == null) {
										switch (data.errorField[i].entityClass) {
											case '<=RackShelf.class.getName()%>':
												window.shelf = window.rackShelfList[data.errorField[i].entityIndex];
												selectShelf(window.shelf);
												switch (data.errorField[i].fieldName) {
													case '<=RackShelfConst.SHELF_WIDTH%>':
														setFocus = $('#shelfWidth');
														break;
													case '<=RackShelfConst.SHELF_HEIGHT%>':
														setFocus = $('#shelfHeight');
														break;
													case '<=RackShelfConst.SHELF_LENGTH%>':
														setFocus = $('#shelfLength');
														break;
													case 'outside':
														setFocus = $('#shelfWidth');
														break;
												}
												break;
											case '<=RackWares.class.getName()%>':
												window.selectRackWaresList.push( window.rackWaresList[data.errorField[i].entityIndex]);
												fRackWaresPlacementSelect();
												drawEditCanvas();
												drawPreviewCanvas();
												switch (data.errorField[i].fieldName) {
													case 'wares_outside_rack':
														setFocus = $('#shelfWidth');
														break;
													case 'wares_intersect_shelf':
														setFocus = $('#shelfWidth');
														break;
													case 'wares_intersect_shelf':
														setFocus = $('#shelfWidth');
														break;
												}
												break;
										}
									}
								}
								alert(error_message);
								if (setFocus != null) {
									setFocus.focus();
								}
							} else {
								loadComplete();
							}
						});
			}
		}
		else {
			alert("Сохранение отменено");
			window.selectRackWaresList = rackWaresOutside;
			fRackWaresPlacementSelect();
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}
	function fRackWaresPlacementReload()
	{
		window.location.reload();
	}
	function fRackViewHistory()
	{
		window.location='rackHistory.jsp';
	}


	function fRackShelfAdd()
	{
		window.shelfAdd=true;
	}

	function fAddWares()
	{
		postJson('<%=WaresGroupTree.URL%>', null, function(data){
			$('#tree').append(treeToHtml(data.waresGroupTree));
			$('#choiceWares').show();
		})
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
			}
			else
			if (canRackShelfEdit!='disabled' && window.shelfAdd==true)
			{
				if (sx>0 && sy>0 && sx<window.rack.width && sy<window.rack.height){
					window.shelfAdd=false;
					window.shelf=<%=new RackShelf(null, null, 0, 0, 1,1, 1, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>;
					window.shelf.shelf_length=window.rack.length;
					window.shelf.code_rack=window.rack.code_rack;
					window.shelf.x_coord=sx;
					window.shelf.y_coord=sy;
					window.rackShelfList.push(window.shelf);
					selectShelf(window.shelf);
					rackShelfCalcCoordinates(window.shelf);
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
					window.editMove=7;
				}
			}
			else
			if (window.flagPaste == 1) {
				// найти центр
				var centerX = 0;
				var centerY = 0;
				for (var i in window.copyObjectList) {
					centerX += window.copyObjectList[i].position_x;
					centerY += window.copyObjectList[i].position_y;
				}
				centerX = sx - centerX / window.copyObjectList.length;
				centerY = sy - centerY / window.copyObjectList.length;
				for (var i in window.copyObjectList) {
					window.copyObjectList[i].position_x += centerX;
					window.copyObjectList[i].position_y += centerY;
				}
				window.flagPaste = 2;
				x = evnt.clientX;
				y = evnt.clientY;
			}
			else {
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

					window.editMove = 1;
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
					if (window.shelf != null)
					{
						x = evnt.clientX;
						y = evnt.clientY;
						window.editMove = 1;
						if (window.shelf.shelf_width >= 7 * window.km && window.shelf.shelf_height >= 7 * window.km && canRackShelfEdit!='disabled') {
							if (Math.abs(d1) < 3 * window.km) {
								// восток
								window.editMove += 2;
							} else if (Math.abs(d3) < 3 * window.km) {
								// запад
								window.editMove += 8;
							}
							if (Math.abs(d2) < 3 * window.km) {
								// север
								window.editMove += 4;
							} else if (Math.abs(d4) < 3 * window.km) {
								// юг
								window.editMove += 16;
							}
						}
					}
					else{
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
			if (window.shelf!=null && canRackShelfEdit!='disabled')
			{
				// adhesionShelf(window.shelf);
				roundShelf(window.shelf);
			}
			if (window.flagPaste==2)
			{
				window.selectRackWaresList=[];
				for (var i in window.copyObjectList)
				{
					var rackWares=clone(window.copyObjectList[i]);
					rackWares.code_wares_on_rack=null;
					roundRackWares(rackWares);
					window.selectRackWaresList.push(rackWares);
					window.rackWaresList.push(rackWares);
				}
				window.flagPaste=0;
			}
			else if (window.editMove>0)
			{
				// установка товара
				for (var i in window.selectRackWaresList)
				{
					var rackWares=window.selectRackWaresList[i];
					// округление координат
					roundRackWares(rackWares);
				}
				window.editMove=0;
			}
			else if (window.m_select==1)
			{
				// области выделения
				if (window.selectBasket!=null)
				{
					// добавление товара
					var m_x_begin2 = Math.round(Math.min(window.m_x_end, window.m_x_begin));
					var m_x_end2 = Math.round(Math.max(window.m_x_end, window.m_x_begin));
					var m_y_begin2 = Math.round(Math.min(window.m_y_end, window.m_y_begin));
					var m_y_end2 = Math.round(Math.max(window.m_y_end, window.m_y_begin));
					var countInsert=0;
					window.selectRackWaresList=[];
					var dx=window.selectBasket.width+window.d_wares_width;
					if (dx%2!=0)
						dx++;
					var dy=window.selectBasket.height+window.d_wares_height;
					if (dy%2!=0)
						dy++;
					for (var i=m_x_begin2; i<m_x_end2-dx; i=i+dx)
					{
						for (var j=m_y_begin2; j<m_y_end2-dy; j=j+dy)
						{
							var rackWares = {
								code_rack:window.rack.code_rack,
								code_wares:window.selectBasket.code_wares,
								code_unit:window.selectBasket.code_unit,
								type_wares_on_rack:'<%=TypeRackWares.NA.name()%>',
								order_number_on_rack:1,
								position_x:i+Math.ceil(window.selectBasket.width/2),
								position_y:j+Math.ceil(window.selectBasket.height/2),
								wares_length:window.selectBasket.length,
								wares_width:window.selectBasket.width,
								wares_height:window.selectBasket.height,
								count_length_on_shelf:Math.floor(window.rack.length/window.selectBasket.length),
								code_image: window.selectBasket.code_image,
								name_wares: window.selectBasket.name_wares,
								abr_unit: window.selectBasket.abr_unit,
								bar_code: window.selectBasket.bar_code
							};
							roundRackWares(rackWares);
							// TODO order_number_on_rack
							window.rackWaresList.push(rackWares);
							window.selectRackWaresList.push(rackWares);
							countInsert++;
						}
					}
					if (countInsert>0)
					{
						basketWaresDelete(window.selectBasket.code_wares);
						window.selectBasket=null;
					}
				}
				else
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

			if (window.ruler.state==1)
			{
				rulerMoveA(sx,sy);
				drawEditCanvas();
			}else
			if (window.ruler.state==2)
			{
				rulerMoveB(sx,sy);
				drawEditCanvas();
			}
			else
			if (window.editMove != 0 && window.shelf != null && canRackShelfEdit!='disabled') {

				var dx = (evnt.clientX - x) * window.km;
				var dy = -(evnt.clientY - y) * window.km;
				if (dx != 0 || dy != 0) {
					// перемещение
					if (window.editMove == 1) {
						var oldx = window.shelf.x_coord;
						var oldy = window.shelf.y_coord;
						// перемещение
						window.shelf.x_coord = window.shelf.x_coord + dx;
						rackShelfCalcCoordinates(window.shelf);
						if (shelfBeyondRack(window.shelf))
						{
							window.shelf.x_coord = oldx;
						}
						window.shelf.y_coord = window.shelf.y_coord + dy;
						rackShelfCalcCoordinates(window.shelf);
						if (shelfBeyondRack(window.shelf))
						{
							window.shelf.y_coord = oldy;
							rackShelfCalcCoordinates(window.shelf);
						}
						document.getElementById('shelfX').value = window.shelf.x_coord;
						document.getElementById('shelfY').value = window.shelf.y_coord;
					} else {
						var oldX = window.shelf.x_coord;
						var oldY = window.shelf.y_coord;
						var oldWidth=window.shelf.shelf_width;
						var oldHeight = window.shelf.shelf_height;
						var dxy=dx*window.shelf.cos + dy*window.shelf.sin;
						if (editMove & 2) {
							// восток
							window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.cos / 2;
							window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.sin / 2;
							window.shelf.shelf_width = window.shelf.shelf_width+ dxy;
						} else if (editMove & 8) {
							// запад
							window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.cos / 2;
							window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.sin / 2;
							window.shelf.shelf_width = window.shelf.shelf_width- dxy;
						}
						if (window.shelf.shelf_width< 8 * km) {
							window.shelf.shelf_width = 8 * km;
							window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.cos / 2;
							window.shelf.y_coord = window.shelf.y_coord - dxy * window.shelf.sin / 2;
						}
						dxy=dy*window.shelf.cos - dx*window.shelf.sin;
						if (editMove & 4) {
							// север
							window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.sin / 2;
							window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.cos / 2;
							window.shelf.shelf_height = window.shelf.shelf_height - dxy;
						} else if (editMove & 16) {
							// юг
							window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.sin / 2;
							window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.cos / 2;
							window.shelf.shelf_height = window.shelf.shelf_height + dxy;
						}
						if (window.shelf.shelf_height < 8 * km) {
							window.shelf.shelf_height = 8 * km
							window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.sin / 2;
							window.shelf.y_coord = window.shelf.y_coord - dxy * window.shelf.cos / 2;
						}
						rackShelfCalcCoordinates(window.shelf);
						if (window.shelf.x1 < 0 || window.shelf.x2 < 0 || window.shelf.x3 < 0 || window.shelf.x4 < 0 ||
								window.shelf.x1 > window.rack.width || window.shelf.x2 > window.rack.width || window.shelf.x3 > window.rack.width || window.shelf.x4 > window.rack.width ||
								window.shelf.y1 < 0 || window.shelf.y2 < 0 || window.shelf.y3 < 0 || window.shelf.y4 < 0 ||
								window.shelf.y1 > window.rack.height || window.shelf.y2 > window.rack.height || window.shelf.y3 > window.rack.height || window.shelf.y4 > window.rack.height) {
							window.shelf.x_coord = oldX;
							window.shelf.y_coord = oldY;
							window.shelf.shelf_width = oldWidth;
							window.shelf.shelf_height = oldHeight;
							rackShelfCalcCoordinates(window.shelf);
						} else {
							document.getElementById('shelfX').value = window.shelf.x_coord;
							document.getElementById('shelfY').value = window.shelf.y_coord;
							document.getElementById('shelfWidth').value = window.shelf.shelf_width;
							document.getElementById('shelfHeight').value = window.shelf.shelf_height;
						}
					}
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
				}
			}
			else
			if (window.flagPaste==2)
			{
				// перемещение товара
				var dx = (evnt.clientX - x) * window.km;
				var dy = -(evnt.clientY - y) * window.km;
				if (dx != 0 || dy != 0)
				{
					for(var i in window.copyObjectList)
					{
						var rackWares=window.copyObjectList[i];
						rackWares.position_x=rackWares.position_x+dx;
						rackWares.position_y=rackWares.position_y+dy;
						rackWaresCalcCoordinates(rackWares);
					}
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
				}
			}
			else if (window.editMove==1)
			{
				// перемещение товара
				var dx = (evnt.clientX - x) * window.km;
				var dy = -(evnt.clientY - y) * window.km;
				if (dx != 0 || dy != 0)
				{
					for (var i in window.selectRackWaresList)
					{
						var rackWares=window.selectRackWaresList[i];
						rackWares.position_x=rackWares.position_x+dx;
						rackWares.position_y=rackWares.position_y+dy;
						rackWaresCalcCoordinates(rackWares);
					}
					if (window.selectRackWaresList.length==1)
					{
						$('#waresX').val(window.selectRackWaresList[0].position_x);
						$('#waresY').val(window.selectRackWaresList[0].position_y);
					}
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
				}
			}
			else if (window.m_select==1)
			{
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

<%-- события веб буфера обмена --%>
<script type="text/javascript">

	function fRackWaresPlacementBasketTable()
	{
		$('#basketPanel').hide();
		$('#basketPanelTable').show();
	}
	function fRackWaresPlacementBasketTile()
	{
		$('#basketPanelTable').hide();
		$('#basketPanel').show();
	}

	function fCopyBuffer()
	{
		var copyObjectList=[];
		for (var i in window.selectRackWaresList)
		{
			copyObjectList[i]=clone(window.selectRackWaresList[i]);
		}
		postJson('<%=BufferSet.URL%>', {copyObjectList:copyObjectList}, null);
	}

	function fPasteBuffer()
	{
		postJson('<%=BufferGet.URL%>', null, function (data){
			if (data.copyObjectList != null) {
			window.copyObjectList=data.copyObjectList;
			} else {
				window.copyObjectList = [];
				alert("Буфер обмена пуст")
			}
			if (window.copyObjectList.length > 0) {
				window.flagPaste=1;
			}
		});
	}
</script>

<%-- обработка событий клавиатуры --%>
<script type="text/javascript">
	$(document).bind('keydown', function(e){
		var obj = $(e.target);
		if (!obj.is('input') && !obj.is('textarea'))
		{
			switch (e.keyCode)
			{
				case 67:
					if (e.ctrlKey)
						fCopy();
					break;
				case 88:
					if (e.ctrlKey)
						fCut();
					break;
				case 86:
					if (e.ctrlKey)
						fPaste();
					break;
				case 46:
					fDel();
					break;
			}
		}
	});
</script>
</body>
</html>