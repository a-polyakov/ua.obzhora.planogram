<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.data.Rack" %>
<%@ page import="planograma.servlet.rack.RackEdit" %>
<%@ page import="planograma.constant.data.RackConst" %>
<%@ page import="planograma.servlet.rack.RackSave" %>
<%@ page import="planograma.data.RackShelf" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page import="planograma.constant.data.RackShelfConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_rack_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_EDIT);
%>
<html>
<head>
	<title>Редактирование стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/planogram2D.js"></script>
	<script type="text/javascript" src="js/utils/ruler.js"></script>
	<script type="text/javascript" src="js/common/RackShelfRound.js"></script>
	<script type="text/javascript" src="js/rackEdit/rackEdit_drawEditCanvas.js"></script>
	<script type="text/javascript" src="js/rackEdit/rackEdit_drawPreviewCanvas.js"></script>
	<script type="text/javascript" src="js/rackEdit/rackEdit_drawShelf.jsp"></script>
	<script type="text/javascript" src="js/rackEdit/rackEdit_PreviewPanelListener.js"></script>
	<script type="text/javascript" src="js/rackEdit/rackEdit_RackPanelListener.js"></script>
	<script type="text/javascript" src="js/common/RackShelfPanelListener.js"></script>
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
					<td><a href="sectorEdit.jsp">Редактирование зала</a></td>
					<td>&gt;</td>
					<td><i>Редактирование стеллажа</i></td>
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
								<td><a href="#" onclick="return aOnClick(this, fRackSave)" class="<%=access_rack_edit%>"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackShelfAdd)" class="<%=access_rack_edit%>"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
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
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td id="edit_td" width="100%">
						<canvas id='edit_canvas' width="640" height="480">canvas not supported</canvas>
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table class="frame">
										<tr>
											<td id="preview_td" colspan="2">
												<canvas id="preview_canvas" width="150" height="150">canvas not supported</canvas>
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
									<table id="rulerPanel" width="100%" style="display: none;">
										<tr>
											<td>x1:<input id="ruler_ax" type="text" size="3" disabled="disabled"/>y1:<input id="ruler_ay" type="text" size="3" disabled="disabled"/></td>
										</tr>
										<tr>
											<td>x2:<input id="ruler_bx" type="text" size="3" disabled="disabled"/>y2:<input id="ruler_by" type="text" size="3" disabled="disabled"/>&nbsp;<a href="#" onclick="window.ruler.state=0; drawEditCanvas(); $('#rulerPanel').hide();">Скрыть</a></td>
										</tr>
										<tr>
											<td>dx:<input id="ruler_dx" type="text" size="3" disabled="disabled"/>dy:<input id="ruler_dy" type="text" size="3" disabled="disabled"/>&nbsp;&nbsp;&nbsp;l:<input id="ruler_l" type="text" size="3" disabled="disabled"/></td>
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
											<td align="right">стеллаж</td>
											<td colspan="2">
												<input type="text" id="rackName"
													   onchange="changeRackName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">загрузка</td>
											<td>
												<select id="rackLoadSide" onchange="changeRackLoadSide(this)">
													<%
														for (final LoadSide loadSide:LoadSide.values())
														{
															out.write("<option value=\"");
															out.write(loadSide.name());
															out.write("\">");
															out.write(loadSide.getDesc());
															out.write("</option>");
														}
													%>
												</select>
											</td>
											<td>
											</td>
										</tr>
										<tr>
											<td align="right">штрихкод</td>
											<td colspan="2"><input type="text" id="rackBarcode" disabled="disabled"/></td>
										</tr>
										<tr>
											<td>объем</td>
											<td>стеллажа</td>
											<td>полезный</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="rackWidth" size="4"
													   onchange="changeRackWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackRealWidth" size="4"
													   onchange="changeRackRealWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="rackHeight" size="4"
													   onchange="changeRackHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackRealHeight" size="4"
													   onchange="changeRackRealHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="rackLength" size="4"
													   onchange="changeRackLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackRealLength" size="4"
													   onchange="changeRackRealLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right" title="Смещение полезного обема относительно нижнего левого дальнего угла стеллажа">смещение</td>
											<td align="right">слева</td>
											<td><input type="text" id="rackX_offset" size="4"
													   onchange="changeRackX_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td></td>
											<td align="right">снизу</td>
											<td><input type="text" id="rackY_offset" size="4"
													   onchange="changeRackY_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td></td>
											<td align="right">сзади</td>
											<td><input type="text" id="rackZ_offset" size="4"
													   onchange="changeRackZ_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
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
									<table id="shelfPanel" style="display: none;">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства полки:</td>
										</tr>
										<tr>
											<td align="right">x</td>
											<td><input type="text" id="shelfX" onchange="changeShelfX(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text" id="shelfY" onchange="changeShelfY(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">поворот</td>
											<td>
												<input type="text" id="shelfAngle" onchange="changeShelfAngle(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="shelfWidth" onchange="changeShelfWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="shelfHeight" onchange="changeShelfHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="shelfLength" onchange="changeShelfLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">тип</td>
											<td>
												<select id="shelfType" onchange="changeShelfType(this)">
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

	var canRackEdit='<%=access_rack_edit%>';

	function loadComplete() {
		var code_rack = getCookie('code_rack');
		postJson('<%=RackEdit.URL%>', {code_rack:code_rack}, function (data) {
			window.rack = data.rack;
			// разворот лицом со стороны загрузки
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
			loadComplete2();
		});
	}
	function loadComplete2()
	{
		// инициализация окна редактирования
		window.edit_canvas = document.getElementById("edit_canvas");
		window.edit_context = edit_canvas.getContext("2d");
		var edit_td = $('#edit_td');
		window.edit_canvas.width = edit_td.width() - 6;
		window.edit_canvas.height = edit_td.height() - 6;

		// инициализация окна навигации
		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		window.preview_canvas.width = preview_td.width();// - 4;
		window.preview_canvas.height = preview_td.height();// - 4;

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

		x = 0;
		y = 0;
		window.shelf = null;
		window.copyObject=null;

		for (var i = 0; i < window.rackShelfList.length; i++) {
			rackShelfCalcCoordinates(window.rackShelfList[i]);
		}
		drawEditCanvas();
		drawPreviewCanvas();

		setRack(window.rack);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}

	function setRack(rack)
	{
		document.getElementById('rackName').value = window.rack.name_rack;
		document.getElementById('rackLoadSide').value = window.rack.load_side;
		document.getElementById('rackBarcode').value = window.rack.rack_barcode;
		document.getElementById('rackWidth').value =window.rack.width;
		document.getElementById('rackRealWidth').value =window.rack.real_width;
		document.getElementById('rackHeight').value =window.rack.height;
		document.getElementById('rackRealHeight').value =window.rack.real_height;
		document.getElementById('rackLength').value =window.rack.length;
		document.getElementById('rackRealLength').value =window.rack.real_length;
		document.getElementById('rackX_offset').value =window.rack.x_offset;
		document.getElementById('rackY_offset').value =window.rack.y_offset;
		document.getElementById('rackZ_offset').value =window.rack.z_offset;
	}

	function selectShelf(shelf) {
		if (shelf != null) {
			document.getElementById('shelfX').value = shelf.x_coord;
			document.getElementById('shelfY').value = shelf.y_coord;
			document.getElementById('shelfAngle').value = shelf.angle;
			document.getElementById('shelfWidth').value = shelf.shelf_width;
			document.getElementById('shelfHeight').value = shelf.shelf_height;
			document.getElementById('shelfLength').value = shelf.shelf_length;
			document.getElementById('shelfType').value = shelf.type_shelf;
			if (canRackEdit!='disabled')
			{
				$('#butCopy').removeClass('disabled');
				$('#butCut').removeClass('disabled');
			}
			$('#shelfPanel').show();
		} else {
			$('#butCopy').addClass('disabled');
			$('#butCut').addClass('disabled');
			$('#shelfPanel').hide();
		}
	}

	function ie_event(e) {
		if (e === undefined) {
			return window.event;
		}
		return e;
	}

</script>

<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackSave()
	{
		switch (window.rack.load_side)
		{
			case '<%=LoadSide.U%>':
				var temp=window.rack.width;
				window.rack.width=window.rack.height;
				window.rack.height=window.rack.length;
				window.rack.length=temp;

				temp=window.rack.real_width;
				window.rack.real_width=window.rack.real_height;
				window.rack.real_height=window.rack.real_length;
				window.rack.real_length=temp;
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

		postJson('<%=RackSave.URL%>', {rack:window.rack, rackShelfList:window.rackShelfList}, function (data) {
			if (data.errorField!=null && data.errorField.length>0) {
				switch (window.rack.load_side) {
					case '<%=LoadSide.U%>':
						var temp = window.rack.width;
						window.rack.width = window.rack.length;
						window.rack.length = window.rack.height;
						window.rack.height = temp;

						temp = window.rack.real_width;
						window.rack.real_width = window.rack.real_length;
						window.rack.real_length = window.rack.real_height;
						window.rack.real_height = temp;
						break;
					case '<%=LoadSide.F%>':
						var temp = window.rack.width;
						window.rack.width = window.rack.length;
						window.rack.length = temp;

						temp = window.rack.real_width;
						window.rack.real_width = window.rack.real_length;
						window.rack.real_length = temp;

						temp = window.rack.y_offset;
						window.rack.y_offset = window.rack.z_offset;
						window.rack.z_offset = temp;
						break;
				}
				var setFocus = null;
				var error_message = "";
				for (var i = 0;  setFocus == null && i < data.errorField.length; i++) {
					error_message += data.errorField[i].message + '\n';
					if (setFocus == null) {
						switch (data.errorField[i].entityClass) {
							case '<%=Rack.class.getName()%>':
								if (data.errorField[i].fieldName != null) {
									switch (data.errorField[i].fieldName) {
										case '<%=RackConst.HEIGHT%>':
											setFocus = $('#rackHeight');
											break;
										case '<%=RackConst.WIDTH%>':
											setFocus = $('#rackWidth');
											break;
										case '<%=RackConst.LENGTH%>':
											setFocus = $('#rackLength');
											break;
										case '<%=RackConst.REAL_HEIGHT%>':
											setFocus = $('#rackRealHeight');
											break;
										case '<%=RackConst.REAL_WIDTH%>':
											setFocus = $('#rackRealWidth');
											break;
										case '<%=RackConst.REAL_LENGTH%>':
											setFocus = $('#rackRealLength');
											break;
										case 'rack_intersect':
										case 'rack_overflow_wares':
											setFocus = $('#rackWidth');
											break;
									}
								}
								break;
							case '<%=RackShelf.class.getName()%>':
								window.shelf = window.rackShelfList[data.errorField[i].entityIndex];
								selectShelf(window.shelf);
								if (data.errorField[i].fieldName != null) {
									switch (data.errorField[i].fieldName) {
										case '<%=RackShelfConst.SHELF_WIDTH%>':
											setFocus = $('#shelfWidth');
											break;
										case '<%=RackShelfConst.SHELF_HEIGHT%>':
											setFocus = $('#shelfHeight');
											break;
										case '<%=RackShelfConst.SHELF_LENGTH%>':
											setFocus = $('#shelfLength');
											break;
										case 'outside':
										case 'shelf_intersect_wares':
											setFocus = $('#shelfWidth');
											break;
									}
								}
								break;
						}
					}
				}
				alert(error_message);
				if (setFocus != null) {
					setFocus.focus();
				}
			}
			else
			{
				setCookie('<%=RackConst.CODE_RACK%>', data.code_rack);
				loadComplete();
			}
		});
	}
	function fRackReload()
	{
		window.location.reload();
	}
	function fRackShelfAdd()
	{
		window.shelfAdd=true;
	}
	function fCopy()
	{
		if (window.shelf!=null)
		{
			window.copyObject=window.shelf;
			$('#butPaste').removeClass('disabled');
		}
	}
	function fCut()
	{
		if (window.shelf!=null)
		{
			for (var i = 0; i < window.rackShelfList.length; i++) {
				if (window.rackShelfList[i] == window.shelf) {
					window.rackShelfList.splice(i, 1);
					i = window.rackShelfList.length;
				}
			}
			window.copyObject = window.shelf;
			$('#butPaste').removeClass('disabled');
			window.shelf = null;
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}
	function fPaste()
	{
		//TODO
		if (window.copyObject != null) {
			window.shelf = clone(window.copyObject);
			window.shelf.code_rack = '';
			// копия в право
			window.shelf.x_coord = window.copyObject.x_coord + window.copyObject.shelf_width;
			rackShelfCalcCoordinates(window.shelf);
			if (shelfBeyondRack(window.shelf))
			{
				// в право нельзя копия в низ
				window.shelf.x_coord = window.copyObject.x_coord;
				window.shelf.y_coord = window.copyObject.y_coord - window.copyObject.shelf_height;
				rackShelfCalcCoordinates(window.shelf);
				if (shelfBeyondRack(window.shelf)){
					// в низ нельзя копия в лево
					window.shelf.x_coord = window.copyObject.x_coord - window.copyObject.shelf_width;
					window.shelf.y_coord = window.copyObject.y_coord;
					rackShelfCalcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf)){
						// в лево нельзя копия в верх
						window.shelf.x_coord = window.copyObject.x_coord;
						window.shelf.y_coord = window.copyObject.y_coord + window.copyObject.shelf_height;
						rackShelfCalcCoordinates(window.shelf);
						if (shelfBeyondRack(window.shelf)){
							// в верх нельзя копия на месте
							window.shelf.x_coord = window.copyObject.x_coord;
							window.shelf.y_coord = window.copyObject.y_coord;
							rackShelfCalcCoordinates(window.shelf);
						}
					}
				}
			}

			window.rackShelfList.push(window.shelf);
			window.copyObject=window.shelf;
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

	function fDel()
	{
		if (window.shelf!=null)
		{
			for (var i = 0; i < window.rackShelfList.length; i++) {
				if (window.rackShelfList[i] == window.shelf) {
					window.rackShelfList.splice(i, 1);
					i = window.rackShelfList.length;
				}
			}
			window.shelf = null;
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

</script>
<%--обработка событий редактора--%>
<script type="text/javascript">

	function editCanvasMouseListener()
	{
		// TODO
	window.edit_canvas.onmousedown = function (e) {
		var evnt = ie_event(e);
		var sx = window.kx - window.offset_rack_x +evnt.offsetX * window.km;
		var sy = window.ky - window.offset_rack_y +(window.edit_canvas.height - evnt.offsetY) * window.km;
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
		if (window.shelfAdd==true)
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
		{
			// поиск полки под курсором
			window.shelf = null;
			var d1 = null;
			var d2 = null;
			var d3 = null;
			var d4 = null;
			var pClick=new Point2D(sx, sy);
			for (var i = window.rackShelfList.length-1; window.shelf == null && i >=0; i--) {
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
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			if (window.shelf != null) {
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove = 1;
				if (window.shelf.shelf_width >= 7 * window.km && window.shelf.shelf_height >= 7 * window.km) {
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
		}
	}

		window.edit_canvas.onmouseup = function(e) {
			if (window.shelf!=null)
			{
				roundShelf(window.shelf);
			}
		window.editMove = 0;
	}

		window.edit_canvas.onmouseout = function(e) {
			if (window.shelf!=null)
			{
				roundShelf(window.shelf);
			}
		window.editMove = 0;
	}

		window.edit_canvas.onmousemove = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx - window.offset_rack_x +evnt.offsetX * window.km;
			var sy = window.ky - window.offset_rack_y +(window.edit_canvas.height - evnt.offsetY) * window.km;
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
		if (window.editMove != 0 && window.shelf != null) {

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
	}
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