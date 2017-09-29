<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.data.RackTemplate" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateEdit" %>
<%@ page import="planograma.constant.data.RackTemplateConst" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateSave" %>
<%@ page import="planograma.data.RackShelfTemplate" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page import="planograma.constant.data.RackShelfTemplateConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_rack_template_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_TEMPLATE_EDIT);
%>
<html>
<head>
	<title>Редактирование стеллажа стандартного типа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/utils/ruler.js"></script>
	<script type="text/javascript" src="js/draw/calcCoordinatesRackShelfTemplate.js"></script>
	<script type="text/javascript" src="js/draw/drawRackShelfTemplate.jsp"></script>
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
					<td><a href="rackTemplateList.jsp">Типовые стеллажи</a></td>
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
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateSave)" class="<%=access_rack_template_edit%>"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateShelfAdd)" class="<%=access_rack_template_edit%>"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
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
						<canvas id='edit_canvas' width="150" height="150">canvas not supported</canvas>
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table class="frame">
										<tr>
											<td id="preview_td" colspan="2" align="center" valign="middle">
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
											<td colspan="3">свойства стеллажа:</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td colspan="2">
												<input type="text" id="rackTemplateName"
													   onchange="changeRackTemplateName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">загрузка</td>
											<td>
												<select id="rackTemplateLoadSide" onchange="changeRackTemplateLoadSide(this)">
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
											<td>объем</td>
											<td>стеллажа</td>
											<td>полезный</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="rackTemplateWidth" size="4"
													   onchange="changeRackTemplateWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackTemplateRealWidth" size="4"
													   onchange="changeRackTemplateRealWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="rackTemplateHeight" size="4"
													   onchange="changeRackTemplateHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackTemplateRealHeight" size="4"
													   onchange="changeRackTemplateRealHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="rackTemplateLength" size="4"
													   onchange="changeRackTemplateLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackTemplateRealLength" size="4"
													   onchange="changeRackTemplateRealLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right" title="Смещение полезного обема относительно нижнего левого дальнего угла стеллажа">смещение</td>
											<td align="right">слева</td>
											<td><input type="text" id="rackTemplateX_offset" size="4"
													   onchange="changeRackTemplateX_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td></td>
											<td align="right">снизу</td>
											<td><input type="text" id="rackTemplateY_offset" size="4"
													   onchange="changeRackTemplateY_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td></td>
											<td align="right">сзади</td>
											<td><input type="text" id="rackTemplateZ_offset" size="4"
													   onchange="changeRackTemplateZ_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
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

	var canRackTemplateEdit='<%=access_rack_template_edit%>';

	function loadComplete()
	{
		var code_rack_template=getCookie('code_rack_template');
		if (code_rack_template!=null && code_rack_template.length>0)
		{
			postJson('<%=RackTemplateEdit.URL%>', {code_rack_template:code_rack_template}, function (data) {
				window.rackTemplate=data.rackTemplate;
				switch (window.rackTemplate.load_side)
				{
					case '<%=LoadSide.U%>':
						var temp=window.rackTemplate.width;
						window.rackTemplate.width=window.rackTemplate.length;
						window.rackTemplate.length=window.rackTemplate.height;
						window.rackTemplate.height=temp;

						temp=window.rackTemplate.real_width;
						window.rackTemplate.real_width=window.rackTemplate.real_length;
						window.rackTemplate.real_length=window.rackTemplate.real_height;
						window.rackTemplate.real_height=temp;
						break;
					case '<%=LoadSide.F%>':
						var temp=window.rackTemplate.width;
						window.rackTemplate.width=window.rackTemplate.length;
						window.rackTemplate.length=temp;

						temp=window.rackTemplate.real_width;
						window.rackTemplate.real_width=window.rackTemplate.real_length;
						window.rackTemplate.real_length=temp;

						temp=window.rackTemplate.y_offset;
						window.rackTemplate.y_offset=window.rackTemplate.z_offset;
						window.rackTemplate.z_offset=temp;
						break;
				}
				window.rackShelfTemplateList = data.rackShelfTemplateList;
				loadComplete2();
			});
		}
		else
		{
			window.rackTemplate=<%=new RackTemplate(null, null, null, 1000, 1000, 1000, LoadSide.F, null,null, null,null,null,null, 1000,1000,1000,0,0,0).toJsonObject()%>;
			window.rackShelfTemplateList = [];
			loadComplete2();
		}
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
		window.preview_canvas.width = preview_td.width();// - 4;
		window.preview_canvas.height = preview_td.height();// - 4;

		// смещение стеллажа
		window.offset_rack_x=-Math.min(0, window.rackTemplate.x_offset);
		window.offset_rack_y=-Math.min(0, window.rackTemplate.y_offset);
		// смещение полезного обема
		window.offset_real_rack_x=Math.max(0, window.rackTemplate.x_offset);
		window.offset_real_rack_y=Math.max(0, window.rackTemplate.y_offset);
		// определение максимальных габаритов
		window.max_x=Math.max(window.offset_rack_x + window.rackTemplate.width, window.offset_real_rack_x+window.rackTemplate.real_width);
		window.max_y=Math.max(window.offset_rack_y + window.rackTemplate.height, window.offset_real_rack_y+window.rackTemplate.real_height);

		// масштаб в окне редактирования
		window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
		// масштаб в окне навигации
		window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
		// камера
		window.km = edit_m;
		window.kx = 0;
		window.ky = 0;

		window.shelfAdd = false;
		window.editMove = 0;
		window.previewMove = false;
		x = 0;
		y = 0;
		window.shelf = null;
		window.copyObject=null;

		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			calcCoordinatesRackShelfTemplate(window.rackShelfTemplateList[i]);
		}
		drawEditCanvas();
		drawPreviewCanvas();

		setRackTemplate(window.rackTemplate);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}

	function setRackTemplate(rackTemplate)
	{
		document.getElementById('rackTemplateName').value = window.rackTemplate.name_rack_template;
		document.getElementById('rackTemplateLoadSide').value =window.rackTemplate.load_side;
		document.getElementById('rackTemplateWidth').value =window.rackTemplate.width;
		document.getElementById('rackTemplateHeight').value =window.rackTemplate.height;
		document.getElementById('rackTemplateLength').value =window.rackTemplate.length;
		document.getElementById('rackTemplateRealWidth').value =window.rackTemplate.real_width;
		document.getElementById('rackTemplateRealHeight').value =window.rackTemplate.real_height;
		document.getElementById('rackTemplateRealLength').value =window.rackTemplate.real_length;
		document.getElementById('rackTemplateX_offset').value =window.rackTemplate.x_offset;
		document.getElementById('rackTemplateY_offset').value =window.rackTemplate.y_offset;
		document.getElementById('rackTemplateZ_offset').value =window.rackTemplate.z_offset;

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
			if (canRackTemplateEdit!='disabled')
			{
				$('#butCopy').removeClass('disabled');
				$('#butCut').removeClass('disabled');
			}
		} else {
			document.getElementById('shelfX').value = '';
			document.getElementById('shelfY').value = '';
			document.getElementById('shelfAngle').value = '';
			document.getElementById('shelfWidth').value = '';
			document.getElementById('shelfHeight').value = '';
			document.getElementById('shelfLength').value = '';
			document.getElementById('shelfType').value = '';
			$('#butCopy').addClass('disabled');
			$('#butCut').addClass('disabled');
		}
	}

	function ie_event(e) {
		if (e === undefined) {
			return window.event;
		}
		return e;
	}

	function roundShelf(shelf)
	{
		shelf.x_coord=Math.round(shelf.x_coord);
		shelf.y_coord=Math.round(shelf.y_coord);
		shelf.shelf_width=Math.round(shelf.shelf_width);
		shelf.shelf_height=Math.round(shelf.shelf_height);
		shelf.shelf_length=Math.round(shelf.shelf_length);
		selectShelf(shelf);
		calcCoordinatesRackShelfTemplate(shelf);
		drawEditCanvas();
		drawPreviewCanvas();
	}
</script>
<%--прорисовка элементов--%>
<script type="text/javascript">
	function drawEditCanvas() {
		window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "BLACK";
		window.edit_context.strokeRect(
				(-window.kx + window.offset_rack_x) / window.km,
				window.edit_canvas.height + (window.ky - window.offset_rack_y) / window.km,
				window.rackTemplate.width / window.km,
				- window.rackTemplate.height / window.km);
		window.edit_context.strokeStyle = "GREEN";
		window.edit_context.strokeRect(
				(-window.kx + window.offset_real_rack_x) / window.km,
				window.edit_canvas.height + (window.ky - window.offset_real_rack_y) / window.km,
				window.rackTemplate.real_width / window.km,
				-window.rackTemplate.real_height / window.km);
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			drawRackShelfTemplate( window.rackShelfTemplateList[i], window.edit_canvas, window.edit_context, window.kx - window.offset_rack_x, window.ky - window.offset_rack_y, window.km);
		}
		if (window.ruler.state>=2)
		{
			// рисуем линейку
			window.edit_context.lineWidth = 1;
			window.edit_context.strokeStyle = "BLUE";
			window.edit_context.beginPath();
			window.edit_context.moveTo((window.ruler.ax-window.kx + window.offset_rack_x) / window.km,
					window.edit_canvas.height + (-window.ruler.ay+window.ky - window.offset_rack_y) / window.km);
			window.edit_context.lineTo((window.ruler.bx-window.kx + window.offset_rack_x) / window.km,
					window.edit_canvas.height + (-window.ruler.by+window.ky - window.offset_rack_y) / window.km);
			window.edit_context.stroke();
	}
	}
	function drawPreviewCanvas()
	{
		window.preview_context.clearRect(0, 0, window.preview_canvas.width, window.preview_canvas.height);
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLACK";
		window.preview_context.strokeRect(
				window.offset_rack_x / window.preview_m,
				window.preview_canvas.height - window.offset_rack_y / window.preview_m,
				window.rackTemplate.width / window.preview_m,
				-window.rackTemplate.height / window.preview_m);
		window.preview_context.strokeStyle = "GREEN";
		window.preview_context.strokeRect(
				window.offset_real_rack_x / window.preview_m,
				window.preview_canvas.height - window.offset_real_rack_y / window.preview_m,
				window.rackTemplate.real_width / window.preview_m,
				-window.rackTemplate.real_height / window.preview_m);
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			drawRackShelfTemplate(window.rackShelfTemplateList[i], window.preview_canvas, window.preview_context, -window.offset_rack_x , -window.offset_rack_y, window.preview_m);
		}
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLUE";
		window.preview_context.strokeRect(window.kx / window.preview_m,
				window.preview_canvas.height - window.ky / window.preview_m,
				window.edit_canvas.width * window.km / window.preview_m,
				- window.edit_canvas.height * window.km / window.preview_m);
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackTemplateSave() {
		var noneError = true;
		if (window.rackTemplate.name_rack_template == null || window.rackTemplate.name_rack_template.length == 0) {
			noneError = false;
			$('#rackTemplateName').focus();
			alert('отсутствует наименование');
		}
		if (noneError) {
			switch (window.rackTemplate.load_side) {
				case '<%=LoadSide.U%>':
					var temp = window.rackTemplate.width;
					window.rackTemplate.width = window.rackTemplate.height;
					window.rackTemplate.height = window.rackTemplate.length;
					window.rackTemplate.length = temp;

					temp=window.rackTemplate.real_width;
					window.rackTemplate.real_width=window.rackTemplate.real_height;
					window.rackTemplate.real_height=window.rackTemplate.real_length;
					window.rackTemplate.real_length=temp;
					break;
				case '<%=LoadSide.F%>':
					var temp = window.rackTemplate.width;
					window.rackTemplate.width = window.rackTemplate.length;
					window.rackTemplate.length = temp;

					temp=window.rackTemplate.real_width;
					window.rackTemplate.real_width=window.rackTemplate.real_length;
					window.rackTemplate.real_length=temp;

					temp=window.rackTemplate.y_offset;
					window.rackTemplate.y_offset=window.rackTemplate.z_offset;
					window.rackTemplate.z_offset=temp;
					break;
			}

			postJson('<%=RackTemplateSave.URL%>', {rackTemplate:window.rackTemplate, rackShelfTemplateList:window.rackShelfTemplateList}, function (data) {
				if (data.errorField!=null && data.errorField.length>0) {
					switch (window.rackTemplate.load_side) {
						case '<%=LoadSide.U%>':
							var temp = window.rackTemplate.width;
							window.rackTemplate.width = window.rackTemplate.length;
							window.rackTemplate.length = window.rackTemplate.height;
							window.rackTemplate.height = temp;

							temp = window.rackTemplate.real_width;
							window.rackTemplate.real_width = window.rackTemplate.real_length;
							window.rackTemplate.real_length = window.rackTemplate.real_height;
							window.rackTemplate.real_height = temp;
							break;
						case '<%=LoadSide.F%>':
							var temp = window.rackTemplate.width;
							window.rackTemplate.width = window.rackTemplate.length;
							window.rackTemplate.length = temp;

							temp = window.rackTemplate.real_width;
							window.rackTemplate.real_width = window.rackTemplate.real_length;
							window.rackTemplate.real_length = temp;

							temp = window.rackTemplate.y_offset;
							window.rackTemplate.y_offset = window.rackTemplate.z_offset;
							window.rackTemplate.z_offset = temp;
							break;
					}
					var setFocus = null;
					var error_message = "";
					for (var i = 0; i < data.errorField.length; i++) {
						error_message += data.errorField[i].message + '\n';
						if (setFocus == null) {
							switch (data.errorField[i].entityClass) {
								case '<%=RackTemplate.class.getName()%>':
									if (data.errorField[i].fieldName != null) {
										switch (data.errorField[i].fieldName) {
											case '<%=RackTemplateConst.HEIGHT%>':
												setFocus = $('#rackTemplateHeight');
												break;
											case '<%=RackTemplateConst.WIDTH%>':
												setFocus = $('#rackTemplateWidth');
												break;
											case '<%=RackTemplateConst.LENGTH%>':
												setFocus = $('#rackTemplateLength');
												break;
											case '<%=RackTemplateConst.REAL_HEIGHT%>':
												setFocus = $('#rackTemplateRealHeight');
												break;
											case '<%=RackTemplateConst.REAL_WIDTH%>':
												setFocus = $('#rackTemplateRealWidth');
												break;
											case '<%=RackTemplateConst.REAL_LENGTH%>':
												setFocus = $('#rackTemplateRealLength');
												break;
										}
									}
									break;
								case '<%=RackShelfTemplate.class.getName()%>':
									window.shelf = window.rackShelfTemplateList[data.errorField[i].entityIndex];
									selectShelf(window.shelf);
									if (data.errorField[i].fieldName != null) {
										switch (data.errorField[i].fieldName) {
											case '<%=RackShelfTemplateConst.SHELF_WIDTH%>':
												setFocus = $('#shelfWidth');
												break;
											case '<%=RackShelfTemplateConst.SHELF_HEIGHT%>':
												setFocus = $('#shelfHeight');
												break;
											case '<%=RackShelfTemplateConst.SHELF_LENGTH%>':
												setFocus = $('#shelfLength');
												break;
											case 'outside':
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
				setCookie('<%=RackTemplateConst.CODE_RACK_TEMPLATE%>', data.code_rack_template);
				loadComplete();
				}
			});
		}
	}
	function fRackTemplateReload()
	{
		window.location.reload();
	}
	function fRackTemplateShelfAdd()
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
			for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
				if (window.rackShelfTemplateList[i] == window.shelf) {
					window.rackShelfTemplateList.splice(i, 1);
					i = window.rackShelfTemplateList.length;
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
		if (window.copyObject != null) {
			window.shelf = clone(window.copyObject);
			window.shelf.code_rack = '';
			window.shelf.x_coord = window.copyObject.x_coord + window.copyObject.shelf_width;
			window.shelf.y_coord;

			calcCoordinatesRackShelfTemplate(window.shelf);
			if (shelfBeyondRack(window.shelf))
			{
				window.shelf.x_coord = window.copyObject.x_coord;
				window.shelf.y_coord = window.copyObject.y_coord + window.copyObject.shelf_height;
				calcCoordinatesRackShelfTemplate(window.shelf);
				if (shelfBeyondRack(window.shelf)){
					window.shelf.x_coord = window.copyObject.x_coord;
					window.shelf.y_coord = window.copyObject.y_coord;
					calcCoordinatesRackShelfTemplate(window.shelf);
				}
			}

			window.rackShelfTemplateList.push(window.shelf);
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
			for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
				if (window.rackShelfTemplateList[i] == window.shelf) {
					window.rackShelfTemplateList.splice(i, 1);
					i = window.rackShelfTemplateList.length;
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
		var sx = window.kx - window.offset_rack_x + evnt.offsetX * window.km;
		var sy = window.ky - window.offset_rack_y + (window.edit_canvas.height - evnt.offsetY) * window.km;
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
			if (sx>0 && sy>0 && sx<window.rackTemplate.width && sy<window.rackTemplate.height)
			{
			window.shelfAdd=false;
			window.shelf=<%=new RackShelfTemplate(null, null, 0, 0, 1,1, 1, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>;
			window.shelf.shelf_length=window.rackTemplate.length;
			window.shelf.code_rack_template=window.rackTemplate.code_rack_template;
			window.shelf.x_coord=sx;
			window.shelf.y_coord=sy;
			rackShelfTemplateList.push(window.shelf);
			selectShelf(window.shelf);
			calcCoordinatesRackShelfTemplate(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			x = evnt.clientX;
			y = evnt.clientY;
			window.editMove=7;
			}
		}
		else
		{
			window.shelf = null;
			var d1 = null;
			var d2 = null;
			var d3 = null;
			var d4 = null;
			for (var i = window.rackShelfTemplateList.length-1; window.shelf == null && i >=0; i--) {
				d1 = distance(window.rackShelfTemplateList[i].x1,window.rackShelfTemplateList[i].y1,
						window.rackShelfTemplateList[i].x2,window.rackShelfTemplateList[i].y2,
						sx,sy);
				d2 = distance(window.rackShelfTemplateList[i].x2,window.rackShelfTemplateList[i].y2,
						window.rackShelfTemplateList[i].x3,window.rackShelfTemplateList[i].y3,
						sx,sy);
				d3 = distance(window.rackShelfTemplateList[i].x3,window.rackShelfTemplateList[i].y3,
						window.rackShelfTemplateList[i].x4,window.rackShelfTemplateList[i].y4,
						sx,sy);
				d4 = distance(window.rackShelfTemplateList[i].x4,window.rackShelfTemplateList[i].y4,
						window.rackShelfTemplateList[i].x1,window.rackShelfTemplateList[i].y1,
						sx,sy);
				if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
					window.shelf = window.rackShelfTemplateList[i];
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
					calcCoordinatesRackShelfTemplate(window.shelf);
					if (shelfBeyondRack(window.shelf))
					{
						window.shelf.x_coord = oldx;
					}
					window.shelf.y_coord = window.shelf.y_coord + dy;
					calcCoordinatesRackShelfTemplate(window.shelf);
					if (shelfBeyondRack(window.shelf))
					{
						window.shelf.y_coord = oldy;
						calcCoordinatesRackShelfTemplate(window.shelf);
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
					calcCoordinatesRackShelfTemplate(window.shelf);
					if (window.shelf.x1 < 0 || window.shelf.x2 < 0 || window.shelf.x3 < 0 || window.shelf.x4 < 0 ||
							window.shelf.x1 > window.rackTemplate.width || window.shelf.x2 > window.rackTemplate.width || window.shelf.x3 > window.rackTemplate.width || window.shelf.x4 > window.rackTemplate.width ||
							window.shelf.y1 < 0 || window.shelf.y2 < 0 || window.shelf.y3 < 0 || window.shelf.y4 < 0 ||
							window.shelf.y1 > window.rackTemplate.height || window.shelf.y2 > window.rackTemplate.height || window.shelf.y3 > window.rackTemplate.height || window.shelf.y4 > window.rackTemplate.height) {
						window.shelf.x_coord = oldX;
						window.shelf.y_coord = oldY;
						window.shelf.shelf_width = oldWidth;
						window.shelf.shelf_height = oldHeight;
						calcCoordinatesRackShelfTemplate(window.shelf);
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
<%-- обработка событий окна навигации --%>
<script type="text/javascript">
	function previewCanvasMouseListener() {
		window.preview_canvas.onmousedown = function (e) {
			window.previewMove = true;
			var evnt = ie_event(e);
			x = evnt.clientX;
			y = evnt.clientY;
			window.kx = evnt.offsetX * window.preview_m - window.edit_canvas.width * window.km / 2;
			window.ky = (window.preview_canvas.height-evnt.offsetY) * window.preview_m - window.edit_canvas.height * window.km / 2;
			checkKxKy();
			drawPreviewCanvas();
		};

		preview_canvas.onmouseup = function (e) {
			if (window.previewMove) {
				window.previewMove = false;
				drawEditCanvas();
			}
		};

		preview_canvas.onmouseover = function (e) {
			if (window.previewMove) {
				window.previewMove = false;
				drawEditCanvas();
			}
		};

		preview_canvas.onmousemove = function (e) {
			if (window.previewMove) {
				var evnt = ie_event(e);
				window.kx = window.kx + (evnt.clientX - x) * window.preview_m;
				window.ky = window.ky - (evnt.clientY - y) * window.preview_m;
				checkKxKy();
				x = evnt.clientX;
				y = evnt.clientY;
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
		if (window.kx > window.rackTemplate.width - window.edit_canvas.width * window.km)
			window.kx = window.rackTemplate.width - window.edit_canvas.width * window.km;
		if (window.kx < 0)
			window.kx = 0;
		if (window.ky > window.rackTemplate.height - window.edit_canvas.height * window.km)
			window.ky = window.rackTemplate.height - window.edit_canvas.height * window.km;
		if (window.ky < 0)
			window.ky = 0;
	}
</script>
<%-- обработка событий панели стеллажа --%>
<script type="text/javascript">
	function changeRackTemplateName(rackTemplateName) {
		window.rackTemplate.name_rack_template = rackTemplateName.value;
	}
	function changeRackTemplateWidth(rackTemplateWidth) {
		//TODO
		var currentWidth = Number(rackTemplateWidth.value);
		var maxWidth = 0;
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			if (maxWidth < window.rackShelfTemplateList[i].x1) {
				maxWidth = window.rackShelfTemplateList[i].x1;
			}
			if (maxWidth < window.rackShelfTemplateList[i].x2) {
				maxWidth = window.rackShelfTemplateList[i].x2;
			}
			if (maxWidth < window.rackShelfTemplateList[i].x3) {
				maxWidth = window.rackShelfTemplateList[i].x3;
			}
			if (maxWidth < window.rackShelfTemplateList[i].x4) {
				maxWidth = window.rackShelfTemplateList[i].x4;
			}
		}
		if (currentWidth > maxWidth) {
			window.rackTemplate.width = currentWidth;
			window.max_x=Math.max(window.offset_rack_x + window.rackTemplate.width, window.offset_real_rack_x+window.rackTemplate.real_width);
			// масштаб в окне редактирования
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			// масштаб в окне навигации
			window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentWidth > 0) {
				rackTemplateWidth.value = maxWidth;
				window.rackTemplate.width = maxWidth;
				window.max_x=Math.max(window.offset_rack_x + window.rackTemplate.width, window.offset_real_rack_x+window.rackTemplate.real_width);
				// масштаб в окне редактирования
				window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
				// масштаб в окне навигации
				window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
				window.km = window.edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				rackTemplateWidth.value = window.rackTemplate.width;
		}
	}

	function changeRackTemplateHeight(rackTemplateHeight) {
		//TODO
		var currentHeight = Number(rackTemplateHeight.value);
		var maxHeight = 0;
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			if (maxHeight < window.rackShelfTemplateList[i].y1) {
				maxHeight = window.rackShelfTemplateList[i].y1;
			}
			if (maxHeight < window.rackShelfTemplateList[i].y2) {
				maxHeight = window.rackShelfTemplateList[i].y2;
			}
			if (maxHeight < window.rackShelfTemplateList[i].y3) {
				maxHeight = window.rackShelfTemplateList[i].y3;
			}
			if (maxHeight < window.rackShelfTemplateList[i].y4) {
				maxHeight = window.rackShelfTemplateList[i].y4;
			}
		}
		if (currentHeight > maxHeight) {
			window.rackTemplate.height = currentHeight;
			window.max_y=Math.max(window.offset_rack_y + window.rackTemplate.height, window.offset_real_rack_y+window.rackTemplate.real_height);
			// масштаб в окне редактирования
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			// масштаб в окне навигации
			window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentHeight > 0) {
				rackTemplateHeight.value = maxHeight;
				window.rackTemplate.height = maxHeight;
				window.max_y=Math.max(window.offset_rack_y + window.rackTemplate.height, window.offset_real_rack_y+window.rackTemplate.real_height);
				// масштаб в окне редактирования
				window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
				// масштаб в окне навигации
				window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
				window.km = window.edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				rackTemplateHeight.value = window.rackTemplate.height;
		}
	}
	function changeRackTemplateLength(rackTemplateLength) {
		//TODO
		var currentLength = Number(rackTemplateLength.value);
		var maxLength = 0;
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			if (maxLength < window.rackShelfTemplateList[i].shelf_length) {
				maxLength = window.rackShelfTemplateList[i].shelf_length;
			}
		}
		if (currentLength > maxLength) {
			window.rackTemplate.length = currentLength;
		}
		else {
			if (currentLength > 0) {
				rackTemplateLength.value = maxLength;
				window.rackTemplate.length = maxLength;
			}
			else
				rackTemplateLength.value = window.rackTemplate.length;
		}
	}

	function changeRackTemplateRealWidth(realWidth) {
		var currentWidth = Number(realWidth.value);
		if (currentWidth > 0) {
			window.rackTemplate.real_width = currentWidth;
			window.max_x = Math.max(window.offset_rack_x + window.rackTemplate.width, window.offset_real_rack_x + window.rackTemplate.real_width);
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			realWidth.value = window.rackTemplate.real_width;
		}
	}

	function changeRackTemplateRealHeight(realHeight) {
		var currentHeight = Number(realHeight.value);
		if (currentHeight > 0) {
			window.rackTemplate.real_height = currentHeight;
			window.max_y = Math.max(window.offset_rack_y + window.rackTemplate.height, window.offset_real_rack_y + window.rackTemplate.real_height);
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			realHeight.value = window.rackTemplate.real_height;
		}
	}

	function changeRackTemplateRealLength(realLength) {
		var currentLength = Number(realLength.value);
		if (currentLength > 0) {
			window.rackTemplate.real_length = currentLength;
		}
		else {
			realLength.value = window.rackTemplate.real_length;
		}
	}

	function changeRackTemplateX_offset(xOffset) {
		if (!isNaN(xOffset.value)) {
			window.rackTemplate.x_offset = Number(xOffset.value);
			// смещение стеллажа
			window.offset_rack_x = -Math.min(0, window.rackTemplate.x_offset);
			// смещение полезного обема
			window.offset_real_rack_x = Math.max(0, window.rackTemplate.x_offset);
			// определение максимальных габаритов
			window.max_x = Math.max(window.offset_rack_x + window.rackTemplate.width, window.offset_real_rack_x + window.rackTemplate.real_width);
			// масштаб в окне редактирования
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			// масштаб в окне навигации
			window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			xOffset.value = window.rackTemplate.x_offset;
		}
	}

	function changeRackTemplateY_offset(yOffset) {
		if (!isNaN(yOffset.value)) {
			window.rackTemplate.y_offset = Number(yOffset.value);
			// смещение стеллажа
			window.offset_rack_y = -Math.min(0, window.rackTemplate.y_offset);
			// смещение полезного обема
			window.offset_real_rack_y = Math.max(0, window.rackTemplate.y_offset);
			// определение максимальных габаритов
			window.max_y = Math.max(window.offset_rack_y + window.rackTemplate.height, window.offset_real_rack_y + window.rackTemplate.real_height);
			// масштаб в окне редактирования
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			// масштаб в окне навигации
			window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			yOffset.value = window.rackTemplate.y_offset;
		}
	}

	function changeRackTemplateZ_offset(zOffset) {
		if (!isNaN(zOffset.value)) {
			window.rackTemplate.z_offset = Number(zOffset.value);
		}
		else {
			zOffset.value = window.rackTemplate.z_offset;
		}
	}

	function changeRackTemplateLoadSide(rackTemplateLoadSide) {
		window.rackTemplate.load_side = rackTemplateLoadSide.value;
	}
</script>
<%-- обработка событий панели полки --%>
<script type="text/javascript">
	function changeShelfX(shelfX) {
		if (window.shelf != null) {
			var x = Number(shelfX.value);
			if (x != null && !isNaN(x) && x != Infinity) {
				var oldx = window.shelf.x_coord;
				window.shelf.x_coord = x;
				calcCoordinatesRackShelfTemplate(window.shelf);
				// не выходит за области сектора
				if ((window.shelf.x_coord < oldx
						&& (window.shelf.x1 < 0
							|| window.shelf.x2 < 0
							|| window.shelf.x3 < 0
							|| window.shelf.x4 < 0))
					|| (window.shelf.x_coord > oldx
						&& (window.shelf.x1 > window.rackTemplate.width
							|| window.shelf.x2 > window.rackTemplate.width
							|| window.shelf.x3 > window.rackTemplate.width
							|| window.shelf.x4 > window.rackTemplate.width))) {
					window.shelf.x_coord = oldx;
					calcCoordinatesRackShelfTemplate(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfX.value = window.shelf.x_coord;
		}
	}
	function changeShelfY(shelfY) {
		if (window.shelf != null) {
			var y = Number(shelfY.value);
			if (y != null && !isNaN(y) && y != Infinity) {
				var oldY = window.shelf.y_coord;
				window.shelf.y_coord = y;
				calcCoordinatesRackShelfTemplate(window.shelf);
				// не выходит за области сектора
				if ((window.shelf.y_coord < oldY
						&& (window.shelf.y1 < 0
						|| window.shelf.y2 < 0
						|| window.shelf.y3 < 0
						|| window.shelf.y4 < 0))
						|| (window.shelf.y_coord > oldY
						&& (window.shelf.y1 > window.rackTemplate.height
						|| window.shelf.y2 > window.rackTemplate.height
						|| window.shelf.y3 > window.rackTemplate.height
						|| window.shelf.y4 > window.rackTemplate.height))) {
					window.shelf.y_coord = oldY;
					calcCoordinatesRackShelfTemplate(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfY.value = window.shelf.y_coord;
		}
	}
	function changeShelfAngle(shelfAngle) {
		if (window.shelf != null) {
			var angle = Number(shelfAngle.value);
			if (angle >= 0 && angle <= 360) {
				var oldAngle = window.shelf.angle;
				window.shelf.angle = angle;
				calcCoordinatesRackShelfTemplate(window.shelf);
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.angle = oldAngle;
					calcCoordinatesRackShelfTemplate(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfAngle.value = window.shelf.angle;
		}
	}
	function changeShelfWidth(shelfWidth) {
		if (window.shelf != null) {
			var value = Number(shelfWidth.value);
			if (value != null && value>0 && value != Infinity) {
				var oldValue = window.shelf.shelf_width;
				window.shelf.shelf_width = value;
				calcCoordinatesRackShelfTemplate(window.shelf);
				// не выходит за области сектора
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.shelf_width = oldValue;
					calcCoordinatesRackShelfTemplate(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfWidth.value = window.shelf.shelf_width;
		}
	}
	function changeShelfHeight(shelfHeight) {
		if (window.shelf != null) {
			var value = Number(shelfHeight.value);
			if (value != null && value>0 && value != Infinity) {
				var oldValue = window.shelf.shelf_height;
				window.shelf.shelf_height = value;
				calcCoordinatesRackShelfTemplate(window.shelf);
				// не выходит за области сектора
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.shelf_height = oldValue;
					calcCoordinatesRackShelfTemplate(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfHeight.value = window.shelf.shelf_height;
		}
	}
	function changeShelfLength(shelfLength) {
		if (window.shelf != null) {
			var value = Number(shelfLength.value);
			// не выходит за области сектора
			if (value > 0 && value <= window.rackTemplate.length) {
				window.shelf.shelf_length = value;
			}
			else {
				shelfLength.value = window.shelf.shelf_length;
			}
		}
	}
	function changeShelfType(shelfType) {
	}

	function shelfBeyondRack(shelf) {
		return shelf.x1 < 0
				|| shelf.x2 < 0
				|| shelf.x3 < 0
				|| shelf.x4 < 0
				|| shelf.y1 < 0
				|| shelf.y2 < 0
				|| shelf.y3 < 0
				|| shelf.y4 < 0
				|| shelf.x1 > rackTemplate.width
				|| shelf.x2 > rackTemplate.width
				|| shelf.x3 > rackTemplate.width
				|| shelf.x4 > rackTemplate.width
				|| shelf.y1 > rackTemplate.height
				|| shelf.y2 > rackTemplate.height
				|| shelf.y3 > rackTemplate.height
				|| shelf.y4 > rackTemplate.height;
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