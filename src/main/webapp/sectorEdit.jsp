<%@ page import="planograma.constant.SecurityConst" %>
<%@ page import="planograma.constant.data.RackConst" %>
<%@ page import="planograma.constant.data.SectorConst" %>
<%@ page import="planograma.data.*" %>
<%@ page import="planograma.servlet.rack.*" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateList" %>
<%@ page import="planograma.servlet.sector.SectorEdit" %>
<%@ page import="planograma.servlet.sector.SectorSave" %>
<%@ page import="planograma.utils.JspUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_sector_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_SECTOR_EDIT);
%>
<%-- TODO Отслеживать изменния размера окна --%>
<%-- TODO отмена действий --%>
<html>
<head>
	<title>Редактирование зала</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/utils/ruler.js"></script>
	<script type="text/javascript" src="js/draw/calcCoordinatesRack.js"></script>
	<script type="text/javascript" src="js/draw/drawRack.jsp"></script>
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
					<td><a href="sectorList.jsp">Залы торговых площадок</a></td>
					<td>&gt;</td>
					<td><i>Редактирование зала</i></td>
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
								<td><a href="#" onclick="return aOnClick(this, fSectorSave)" class="<%=access_sector_edit%>"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fSectorReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackAdd)" class="<%=access_sector_edit%>"><%=JspUtils.toMenuTitle("Добавить объект")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateAdd)" class="<%=access_sector_edit%>"><%=JspUtils.toMenuTitle("Добавить стандартный стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butRuler" onclick="return aOnClick(this, fRuler)"><%=JspUtils.toMenuTitle("Рулетка")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butFind" onclick="return aOnClick(this, fFind)"><%=JspUtils.toMenuTitle("Найти")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butPrint" onclick="return aOnClick(this, fRackPrint)" class="disabled"><%=JspUtils.toMenuTitle("Печать стеллажа")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butEdit" onclick="return aOnClick(this, fRackEdit)" class="disabled"><%=JspUtils.toMenuTitle("Редактировать стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butRackWaresPlacement" onclick="return aOnClick(this, fRackWaresPlacement)" class="disabled"><%=JspUtils.toMenuTitle("Расстановка товара")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" id="butRackSetStateInSectorA" onclick="return aOnClick(this, fRackSetStateInSectorA)" class="disabled"><%=JspUtils.toMenuTitle("Утвердить стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butRackSetStateInSectorPC" onclick="return aOnClick(this, fRackSetStateInSectorPC)" class="disabled"><%=JspUtils.toMenuTitle("Выполнить стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butRackSetStateA" onclick="return aOnClick(this, fRackSetStateA)" class="disabled"><%=JspUtils.toMenuTitle("Утвердить компоновку")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butRackSetStatePC" onclick="return aOnClick(this, fRackSetStatePC)" class="disabled"><%=JspUtils.toMenuTitle("Выполнить компоновку")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" id="butCopy" onclick="return aOnClick(this,fCopy)" class="disabled"><%=JspUtils.toMenuTitle("Копировать с товарами")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCut" onclick="return aOnClick(this,fCut)" class="disabled"><%=JspUtils.toMenuTitle("Вырезать")%></a></td>
							</tr>
							<tr>
								<td><a  href="#" id="butPaste" onclick="return aOnClick(this,fPaste)" class="disabled"><%=JspUtils.toMenuTitle("Вставить")%></a></td>
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
									<hr/>
								</td>
							</tr>
							<tr>
								<td>
									<table id="rulerPanel" width="100%" style="display: none;">
										<tr>
											<td>x1:<input id="ruler_ax" type="text" size="3" disabled="disabled"/>y1:<input id="ruler_ay" type="text" size="3" disabled="disabled"/></td>
										</tr>
										<tr>
											<td>x2:<input id="ruler_bx" type="text" size="3" disabled="disabled"/>y2:<input id="ruler_by" type="text" size="3" disabled="disabled"/><a href="#" onclick="window.ruler.state=0; drawEditCanvas(); $('#rulerPanel').hide();">Скрыть</a></td>
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
											<td colspan="2">свойства зала:</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td><input type="text" id="sectorName" onchange="changeSectorName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="sectorWidth" onchange="changeSectorWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">длина</td>
											<td><input type="text" id="sectorLength"
													   onchange="changeSectorLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="sectorHeight"
													   onchange="changeSectorHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
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
											<td align="right">тип</td>
											<td>
												<select id="rackType" onchange="changeRackType(this)">
													<%
														for (final ETypeRack typeRack: ETypeRack.values())
														{
															out.write("<option value=\"");
															out.write(typeRack.name());
															out.write("\">");
															out.write(typeRack.getDesc());
															out.write("</option>");
														}
													%>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td><input type="text" id="rackName"
													   onchange="changeShowcaseName(this)" onkeyup="changeShowcaseName(this)"/></td>
										</tr>
										<tr>
											<td align="right">штрихкод</td>
											<td><input type="text" id="rackBarcode" disabled="disabled"/></td>
										</tr>
										<tr>
											<td colspan="2">
												<input type="checkbox" id="rackLockMove" onchange="changeRackLockMove(this)" value="Y"/>
												<label for="rackLockMove">блокировать перемещение</label>
											</td>
										</tr>
										<tr>
											<td align="right">x</td>
											<td><input type="text" id="showcaseX" onchange="changeShowcaseX(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text" id="showcaseY" onchange="changeShowcaseY(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">поворот</td>
											<td>
												<input type="text" id="showcaseAngle" onchange="changeShowcaseAngle(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<input type="checkbox" id="rackLockSize" onchange="changeRackLockSize(this)" value="Y"/>
												<label for="rackLockSize">блокировать размер</label>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="showcaseWidth"
													   onchange="changeShowcaseWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">длина</td>
											<td><input type="text" id="showcaseLength"
													   onchange="changeShowcaseLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="showcaseHeight"
													   onchange="changeShowcaseHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">загрузка</td>
											<td>
												<select id="rackLoadSide" onchange="changeShowcaseOpen(this)">
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
										</tr>
                                        <tr>
                                            <td align="right">пересечение</td>
                                            <td>
                                                <select id="rackAllowIntersect" onchange="changeShowcaseAllowIntersect(this)">
                                                    <option value="0">Запрещено</option>
                                                    <option value="1">Разрешенно</option>
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

<jsp:include page="choiceRackTemplate.jsp"/>

<jsp:include page="sectorEditFindPanel.jsp"/>

<jsp:include page="choiceRackPrint.jsp"/>

<script type="text/javascript">

var edit_canvas;
var preview_canvas;
var edit_context;
var preview_context;

var edit_m;
var preview_m;

var km;
var kx;
var ky;
var editMove;
var previewMove;
var x;
var y;
var showcase;
var copyObject;

function loadComplete()
{
	var code_shop=getCookie('code_shop');
	var code_sector=getCookie('code_sector');
	if (code_sector!=null && code_sector.length>0)
	{
		postJson('<%=SectorEdit.URL%>', {code_sector:code_sector}, function (data) {
			window.sector=data.sector;
			window.showcaseList = data.rackList;
			window.rackStateList = data.rackStateList;
			window.rackStateInSectorList = data.rackStateInSectorList;
			loadComplete2();
		});
	}
	else
	{
		window.sector=<%=new Sector(null, null, "Наименование", 12000, 6000, 3000, null, null, null, null).toJsonObject()%>;
		window.sector.code_shop=code_shop;
	<%--window.showcaseList = [<%=new Rack(null, null, "Наименование", "",100, 100, 100, null, 100, 100, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, null, null).toJsonObject()%>];--%>
		window.showcaseList = [];
		window.rackStateList = [];
		window.rackStateInSectorList = [];
		loadComplete2();
	}
}

function loadComplete2()
{
	edit_canvas = document.getElementById("edit_canvas");
	edit_context = edit_canvas.getContext("2d");
	var edit_td = $('#edit_td');
	edit_canvas.width = edit_td.width() - 6;
	edit_canvas.height = edit_td.height() - 6;
	edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)

	preview_canvas = document.getElementById("preview_canvas");
	preview_context = preview_canvas.getContext("2d");
	var preview_td = $('#preview_td');
	preview_canvas.width = preview_td.width();// - 4;
	preview_canvas.height = preview_td.height();// - 4;
	preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);

 km = edit_m;
 kx = 0;
 ky = 0;
// draw sector
 window.rackAdd = false;
	window.rackTemplateAdd = null;
 editMove = 0;
 previewMove = false;
 x = 0;
 y = 0;
 showcase = null;
 copyObject=null;

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
	document.getElementById('sectorName').value = sector.name_sector;
	document.getElementById('sectorLength').value = sector.length;
	document.getElementById('sectorWidth').value = sector.width;
	document.getElementById('sectorHeight').value = sector.height;
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
	var view_mode=$('#view_mode').val();
	for (var i = 0; i < window.showcaseList.length; i++) {
		drawRack(window.showcaseList[i], window.rackStateList[i], window.rackStateInSectorList[i], view_mode, window.edit_context, window.kx, window.ky, window.km);
	}
	if (window.ruler.state>=2)
	{
		// рисуем линейку
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "BLUE";
		window.edit_context.beginPath();
		window.edit_context.moveTo((window.ruler.ax-window.kx) / window.km,
				(window.ruler.ay-window.ky) / window.km);
		window.edit_context.lineTo((window.ruler.bx-window.kx) / window.km,
				(window.ruler.by-window.ky) / window.km);
		window.edit_context.stroke();
	}
}

function drawPreviewCanvas() {
	window.preview_context.clearRect(0, 0, window.preview_canvas.width, window.preview_canvas.height);
	window.preview_context.lineWidth = 1;
	window.preview_context.strokeStyle = "BLACK";
	window.preview_context.strokeRect(0, 0, window.sector.length / window.preview_m, window.sector.width / window.preview_m);
	var view_mode=$('#view_mode').val();
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
		document.getElementById('rackType').value = showcase.type_rack;
		document.getElementById('rackName').value = showcase.name_rack;
		document.getElementById('rackBarcode').value = showcase.rack_barcode;
		if (showcase.lock_move == 'Y') {
			$('#rackLockMove').attr('checked', 'checked');
			$('#showcaseX').attr('disabled', 'disabled');
			$('#showcaseY').attr('disabled', 'disabled');
			$('#showcaseAngle').attr('disabled', 'disabled');
		}
		else
		{
			$('#rackLockMove').removeAttr('checked');
			$('#showcaseX').removeAttr('disabled');
			$('#showcaseY').removeAttr('disabled');
			$('#showcaseAngle').removeAttr('disabled');
		}
		document.getElementById('showcaseX').value = showcase.x_coord;
		document.getElementById('showcaseY').value = showcase.y_coord;
		document.getElementById('showcaseAngle').value = showcase.angle;
		if (showcase.lock_size == 'Y') {
			$('#rackLockSize').attr('checked', 'checked');
			$('#showcaseWidth').attr('disabled', 'disabled');
			$('#showcaseLength').attr('disabled', 'disabled');
			$('#showcaseHeight').attr('disabled', 'disabled');
			$('#rackLoadSide').attr('disabled', 'disabled');
		}
		else
		{
			$('#rackLockSize').removeAttr('checked');
			$('#showcaseWidth').removeAttr('disabled');
			$('#showcaseLength').removeAttr('disabled');
			$('#showcaseHeight').removeAttr('disabled');
			$('#rackLoadSide').removeAttr('disabled');
		}
		document.getElementById('showcaseWidth').value = showcase.width;
		document.getElementById('showcaseLength').value = showcase.length;
		document.getElementById('showcaseHeight').value = showcase.height;
		document.getElementById('rackLoadSide').value = showcase.load_side;
		if (showcase.code_rack != null && showcase.code_rack != '') {
			$('#butPrint').removeClass('disabled');
			$('#butEdit').removeClass('disabled');
			$('#butRackWaresPlacement').removeClass('disabled');
		} else {
			$('#butPrint').addClass('disabled');
			$('#butEdit').addClass('disabled');
			$('#butRackWaresPlacement').addClass('disabled');
		}
		if ('<%=access_sector_edit%>'!='disabled')
		{
			$('#butCopy').removeClass('disabled');
			$('#butCut').removeClass('disabled');
		}
        document.getElementById('rackAllowIntersect').value = showcase.allow_intersect;
	} else {
		document.getElementById('rackType').value = '';
		document.getElementById('rackName').value = '';
		document.getElementById('rackBarcode').value = '';
		$('#rackLockMove').removeAttr('checked');
		document.getElementById('showcaseX').value = '';
		document.getElementById('showcaseY').value = '';
		document.getElementById('showcaseAngle').value = '';
		$('#rackLockSize').removeAttr('checked');
		document.getElementById('showcaseWidth').value = '';
		document.getElementById('showcaseLength').value = '';
		document.getElementById('showcaseHeight').value = '';
		document.getElementById('rackLoadSide').value = '';
        document.getElementById('rackAllowIntersect').value = '0';
		$('#butPrint').addClass('disabled');
		$('#butEdit').addClass('disabled');
		$('#butRackWaresPlacement').addClass('disabled');
		$('#butRackSetStateA').addClass('disabled');
		$('#butRackSetStatePC').addClass('disabled');
		$('#butCopy').addClass('disabled');
		$('#butCut').addClass('disabled');
	}
}
function fCanSetState() {
	$('#butRackSetStateA').addClass('disabled');
	$('#butRackSetStateInSectorA').addClass('disabled');
	$('#butRackSetStatePC').addClass('disabled');
	$('#butRackSetStateInSectorPC').addClass('disabled');
	if (window.showcase != null && window.showcase.code_rack > 0) {
		postJson('<%=RackCanSetState.URL%>', {code_rack: window.showcase.code_rack}, function (data) {
			var changeState = false;
			for (var i = 0; i < window.rackStateInSectorList.length; i++) {
				if (window.rackStateInSectorList[i].code_rack == window.showcase.code_rack) {
					if (window.rackStateInSectorList[i].state_rack != data.rack_state_in_sector) {
						changeState = true;
						window.rackStateInSectorList[i].state_rack = data.rack_state_in_sector;
					}
					i = window.rackStateInSectorList.length;
				}
			}
			for (var i = 0; i < window.rackStateList.length; i++) {
				if (window.rackStateList[i].code_rack == window.showcase.code_rack) {
					if (window.rackStateList[i].state_rack != data.rack_state) {
						changeState = true;
						window.rackStateList[i].state_rack = data.rack_state;
					}
					i = window.rackStateList.length;
				}
			}
			if (changeState) {
				drawEditCanvas();
				drawPreviewCanvas();
			}
			if (data.canSetStateA)
				$('#butRackSetStateA').removeClass('disabled');
			if (data.canSetStateInSectorA)
				$('#butRackSetStateInSectorA').removeClass('disabled');
			if (data.canSetStatePC)
				$('#butRackSetStatePC').removeClass('disabled');
			if (data.canSetStateInSectorPC)
				$('#butRackSetStateInSectorPC').removeClass('disabled');
		}, 'butRackSetStateA');
	}
}
function roundRack(rack)
{
	if (rack!=null)
	{
		rack.x_coord=Math.round(rack.x_coord);
		rack.y_coord=Math.round(rack.y_coord);
		rack.width=Math.round(rack.width);
		rack.height=Math.round(rack.height);
		rack.length=Math.round(rack.length);
		rack.real_width=Math.round(rack.real_width);
		rack.real_height=Math.round(rack.real_height);
		rack.real_length=Math.round(rack.real_length);
		selectShowcase(rack);
		calcCoordinatesRack(rack);
		drawEditCanvas();
		drawPreviewCanvas();
	}
}

</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fSectorSave() {
		var noneError = true;
		if (window.sector.name_sector == null || window.sector.name_sector.length == 0) {
			noneError = false;
			selectShowcase(null);
			drawEditCanvas();
			drawPreviewCanvas();
			$('#sectorName').focus();
			alert('отсутствует наименование');
		}
		for (var i = 0; noneError && i < window.showcaseList.length; i++) {
			if (window.showcaseList[i].name_rack == null || window.showcaseList[i].name_rack.length == 0) {
				noneError = false;
				window.showcase = window.showcaseList[i];
				selectShowcase(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				$('#rackName').focus();
				alert('отсутствует наименование');
			}
		}
		if (noneError) {
			postJson('<%=SectorSave.URL%>', {sector: window.sector, rackList: window.showcaseList}, function (data) {

				if (data.errorField != null && data.errorField.length > 0) {
					var setFocus = null;
					var error_message = "";
					// TODO отображать все ошибки
					for (var i = 0; setFocus==null && i < data.errorField.length; i++) {
						error_message += data.errorField[i].message + '\n';
						if (setFocus == null) {
							switch (data.errorField[i].entityClass) {
								case '<%=Sector.class.getName()%>':
									if (data.errorField[i].fieldName != null) {
										switch (data.errorField[i].fieldName) {
											case '<%=SectorConst.WIDTH%>':
												setFocus = $('#sectorWidth');
												break;
											case '<%=SectorConst.HEIGHT%>':
												setFocus = $('#sectorHeight');
												break;
											case '<%=SectorConst.LENGTH%>':
												setFocus = $('#sectorLength');
												break;
										}
									}
									break;
								case '<%=Rack.class.getName()%>':
									window.showcase = window.showcaseList[data.errorField[i].entityIndex];
									selectShowcase(window.showcase);
									drawEditCanvas();
									drawPreviewCanvas();
									if (data.errorField[i].fieldName != null) {
										switch (data.errorField[i].fieldName) {
											case '<%=RackConst.HEIGHT%>':
												setFocus = $('#showcaseHeight');
												break;
											case '<%=RackConst.WIDTH%>':
												setFocus = $('#showcaseWidth');
												break;
											case '<%=RackConst.LENGTH%>':
												setFocus = $('#showcaseLength');
												break;
											case '<%=RackConst.REAL_HEIGHT%>':
												setFocus = $('#showcaseHeight');
												break;
											case '<%=RackConst.REAL_WIDTH%>':
												setFocus = $('#showcaseWidth');
												break;
											case '<%=RackConst.REAL_LENGTH%>':
												setFocus = $('#showcaseLength');
												break;
											case 'outside':
											case 'rack_overflow_shelf':
											case 'rack_overflow_wares':
											case 'rack_intersect':
												setFocus = $('#showcaseWidth');
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
				else {
					setCookie('<%=SectorConst.CODE_SECTOR%>', data.code_sector);
					postJson('<%=SectorEdit.URL%>', {code_sector: data.code_sector}, function (data) {
						window.sector = data.sector;
						setSectorProperty(sector);
						window.showcaseList = data.rackList;
						window.rackStateList = data.rackStateList;
						window.rackStateInSectorList = data.rackStateInSectorList;
						for (var i = 0; i < window.showcaseList.length; i++) {
							calcCoordinatesRack(window.showcaseList[i]);
						}
						drawEditCanvas();
						drawPreviewCanvas();
					});
				}
			});
		}
	}

	function fSectorReload()
	{
		window.location.reload();
	}

	function fRackAdd()
	{
		window.rackAdd=true;
	}

	function fRackTemplateAdd()
	{
		postJson('<%=RackTemplateList.URL%>', null, function (data) {
			var rackTemplateList = $('#rackTemplateList');
			rackTemplateList.empty();
			for (var i in data.rackTemplateList) {
				var item = data.rackTemplateList[i];
				var option = $('<option></option>')
				option.val(item.code_rack_template);
				option.text(item.name_rack_template);
				option.attr('json', $.toJSON(item));
				rackTemplateList.append(option)
			}
			var code_rack_template = getCookie('code_rack_template');
			if (code_rack_template != null && code_rack_template.length > 0) {
				var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
				if (option.length == 1) {
					option.attr('selected', 'selected');
				}
				selectRackTemplate(code_rack_template);
			}
			var rackTemplate=$('#rackTemplate');
			rackTemplate.animate({opacity:'show'},500);
		});
	}

	function fFind()
	{
		sectorEditFindShow();
	}

	function fRackPrint()
	{
		var code_rack = window.showcase.code_rack;
		choiceRackPrintShow(code_rack);
	}

	function fRackEdit()
	{
		if (window.showcase!=null)
		{
		var code_rack = window.showcase.code_rack;
		if (code_rack!=null && code_rack!='')
		{
			setCookie('code_rack', code_rack);
			window.location='rackEdit.jsp';
		}
		}
	}


	function fRackWaresPlacement()
	{
		if (window.showcase!=null)
		{
			var code_rack = window.showcase.code_rack;
			if (code_rack!=null && code_rack!='')
			{
				setCookie('code_rack', code_rack);
				window.location='rackWaresPlacement.jsp';
			}
		}
	}

	function fRackSetStateInSectorA() {
		postJson('<%=RackSetStateInSectorA.URL%>', {code_rack: window.showcase.code_rack}, function (data) {
			$('#butRackSetStateInSectorA').addClass('disabled');
			if (data.canSetStateInSectorA) {
				fCanSetState();
			}
			else {
				alert("Невозможно утвердить стеллажа в зале");
			}
		}, 'butRackSetStateInSectorA');
	}
	function fRackSetStateInSectorPC()
	{
		postJson('<%=RackSetStateInSectorPC.URL%>', {code_rack: window.showcase.code_rack}, function (data) {
			$('#butRackSetStateInSectorPC').addClass('disabled');
			if (data.canSetStateInSectorPC) {
				fCanSetState();
			}
			else {
				alert("Невозможно выполнить стеллажа в зале");
			}
		}, 'butRackSetStateInSectorPC');
	}
	function fRackSetStateA() {
		postJson('<%=RackSetStateA.URL%>', {code_rack: window.showcase.code_rack}, function (data) {
			$('#butRackSetStateA').addClass('disabled');
			if (data.canSetStateA) {
				fCanSetState();
			}
			else {
				alert("Невозможно утвердить компоновку стеллажа");
			}
		}, 'butRackSetStateA');
	}
	function fRackSetStatePC() {
		postJson('<%=RackSetStatePC.URL%>', {code_rack: window.showcase.code_rack}, function (data) {
			$('#butRackSetStatePC').addClass('disabled');
			if (data.canSetStatePC) {
				fCanSetState();
			}
			else {
				alert("Невозможно выполнить компоновку стеллажа");
			}
		}, 'butRackSetStatePC');
	}

	function fCopy()
	{
		if (window.showcase!=null)
		{
			window.copyObject=window.showcase;
			$('#butPaste').removeClass('disabled');
		}
	}

	function fCut() {
		if (window.showcase != null) {
			for (var i = 0; i < window.showcaseList.length; i++) {
				if (window.showcaseList[i] == window.showcase) {
					window.showcaseList.splice(i, 1);
					i = window.showcaseList.length;
				}
			}
			window.copyObject = window.showcase;
			$('#butPaste').removeClass('disabled');
			window.showcase = null;
			selectShowcase(window.showcase);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

	function fPaste() {
		if (window.copyObject != null) {
				window.showcase = clone(window.copyObject);
				if (window.showcase.code_rack != null && window.showcase.code_rack != '') {
					window.showcase.copy_from_code_rack = window.showcase.code_rack;
				}
			window.showcase.code_rack = '';
			window.showcase.name_rack = window.showcase.name_rack;
			// копия в право
			window.showcase.x_coord = window.copyObject.x_coord + window.copyObject.length+1;
			calcCoordinatesRack(window.showcase);
			if (rackBeyondSector(window.showcase)) {
				// в право нельзя копия в низ
				window.showcase.x_coord = window.copyObject.x_coord;
				window.showcase.y_coord = window.copyObject.y_coord + window.copyObject.width+1;
				calcCoordinatesRack(window.showcase);
				if (rackBeyondSector(window.showcase)) {
					// в низ нельзя копия в лево
					window.showcase.x_coord = window.copyObject.x_coord - window.copyObject.length-1;
					window.showcase.y_coord = window.copyObject.y_coord;
					calcCoordinatesRack(window.showcase);
					if (rackBeyondSector(window.showcase)) {
						// в лево нельзя копия в верх
						window.showcase.x_coord = window.copyObject.x_coord;
						window.showcase.y_coord = window.copyObject.y_coord - window.copyObject.width-1;
						calcCoordinatesRack(window.showcase);
						if (rackBeyondSector(window.showcase)) {
							// в верх нельзя копия на месте
							window.showcase.x_coord = window.copyObject.x_coord+5;
							window.showcase.y_coord = window.copyObject.y_coord+5;
							calcCoordinatesRack(window.showcase);
						}
					}
				}
			}
			window.showcase.lock_move = 'N';
			window.showcaseList.push(window.showcase);
			window.rackStateList.push(<%=new RackState(null, EStateRack.D, null, null, null, null, null, null).toJsonObject()%>);
			window.rackStateInSectorList.push(<%=new RackState(null, EStateRack.D, null, null, null, null, null, null).toJsonObject()%>);
			window.copyObject=window.showcase;
			selectShowcase(window.showcase);
			calcCoordinatesRack(window.showcase);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

	function fDel() {
		if (window.showcase != null) {
			for (var i = 0; i < window.showcaseList.length; i++) {
				if (window.showcaseList[i] == window.showcase) {
					window.showcaseList.splice(i, 1);
					i = window.showcaseList.length;
				}
			}
			window.showcase = null;
			selectShowcase(window.showcase);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}
</script>
<%--обработка событий редактора--%>
<script type="text/javascript">
	function editCanvasMouseListener()
	{
		window.edit_canvas.onmousedown = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx + evnt.offsetX * window.km;
			var sy = window.ky + evnt.offsetY * window.km;

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
			if (window.rackAdd==true)
			{
				if (sx>0 && sy>0 && sx<window.sector.length && sy<window.sector.width)
				{
				window.rackAdd=false;
				window.showcase =<%=new Rack(null, null, "", "", 1, 1, 1000, 0, 0, 0, LoadSide.F, null, false, false, ETypeRack.R, null, null, null, null, 1, 1, 1000, 0, 0, 0, 0).toJsonObject()%>;
				window.showcase.code_sector=window.sector.code_sector;
				window.showcase.x_coord=sx;
				window.showcase.y_coord=sy;
				window.showcaseList.push(window.showcase);
				window.rackStateList.push(<%=new RackState(null, EStateRack.D, null, null, null, null, null, null).toJsonObject()%>);
				window.rackStateInSectorList.push(<%=new RackState(null, EStateRack.D, null, null, null, null, null, null).toJsonObject()%>);


				selectShowcase(window.showcase);
				calcCoordinatesRack(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove=19;
				}
			}
			else if (window.rackTemplateAdd!=null)
			{
				if (sx>0 && sy>0 && sx<window.sector.length && sy<window.sector.width)
				{
				window.showcase=clone(window.rackTemplateAdd);
				window.showcase.code_sector=window.sector.code_sector;
				window.showcase.name_rack=window.rackTemplateAdd.name_rack_template;
				window.showcase.type_rack='<%=ETypeRack.R%>';
				window.showcase.rack_barcode='';
				window.showcase.lock_move='N';
				window.showcase.x_coord=sx;
				window.showcase.y_coord=sy;
				window.showcase.angle=0;
				window.showcase.lock_size='Y';
				window.showcase.allow_intersect=0;
				window.showcaseList.push(window.showcase);
				window.rackStateList.push(<%=new RackState(null, EStateRack.D, null, null, null, null, null, null).toJsonObject()%>);
				window.rackStateInSectorList.push(<%=new RackState(null, EStateRack.D, null, null, null, null, null, null).toJsonObject()%>);
				selectShowcase(window.showcase);
				calcCoordinatesRack(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove=1;
				window.rackTemplateAdd=null;
				}
			}
			else
			{
				window.showcase = null;
				var d1 = null;
				var d2 = null;
				var d3 = null;
				var d4 = null;
				for (var i = window.showcaseList.length-1; window.showcase == null && i >=0; i--) {
					d1 = distance(window.showcaseList[i].x1,window.showcaseList[i].y1,window.showcaseList[i].x2,window.showcaseList[i].y2,sx,sy);
					d2 = distance(window.showcaseList[i].x2,window.showcaseList[i].y2,window.showcaseList[i].x3,window.showcaseList[i].y3,sx,sy);
					d3 = distance(window.showcaseList[i].x3,window.showcaseList[i].y3,window.showcaseList[i].x4,window.showcaseList[i].y4,sx,sy);
					d4 = distance(window.showcaseList[i].x4,window.showcaseList[i].y4,window.showcaseList[i].x1,window.showcaseList[i].y1,sx,sy);
					if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
						window.showcase = window.showcaseList[i];
						fCanSetState();
					}
				}
				selectShowcase(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				if (window.showcase != null) {
					x = evnt.clientX;
					y = evnt.clientY;
					window.editMove = 1;
					if (Math.max(window.showcase.x_offset+window.showcase.real_length, Math.max(-window.showcase.x_offset, 0) + window.showcase.length) >= 7 * window.km
							&& Math.max(window.showcase.y_offset + window.showcase.real_width, Math.max(-window.showcase.y_offset, 0) + window.showcase.width) >= 7 * window.km) {
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
			window.editMove = 0;
			roundRack(window.showcase)
		}

		window.edit_canvas.onmouseout = function(e) {
			window.editMove = 0;
			roundRack(window.showcase)
		}

		window.edit_canvas.onmousemove = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx + evnt.offsetX * window.km;
			var sy = window.ky + evnt.offsetY * window.km;
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
			if (window.editMove != 0 && window.showcase != null) {
				var dx = (evnt.clientX - x) * window.km;
				var dy = (evnt.clientY - y) * window.km;

				if (dx != 0 || dy != 0) {
					// перемещение
					if (window.editMove == 1 && window.showcase.lock_move!='Y') {
						var oldx = window.showcase.x_coord;
						var oldy = window.showcase.y_coord;
						// перемещение
						window.showcase.x_coord = window.showcase.x_coord + dx;
						window.showcase.y_coord = window.showcase.y_coord + dy;
						calcCoordinatesRack(window.showcase);
						if (rackBeyondSector(window.showcase)) {
							showcase.x_coord = oldx;
							showcase.y_coord = oldy;
							calcCoordinatesRack(showcase);
						} else {
							document.getElementById('showcaseX').value = showcase.x_coord;
							document.getElementById('showcaseY').value = showcase.y_coord;
						}
					} else if (window.showcase.lock_size!='Y'){
						// изменение размера
						var oldX = showcase.x_coord;
						var oldY = showcase.y_coord;
						var oldLength = showcase.length;
						var oldWidth=showcase.width;
						var oldRealLength = showcase.real_length;
						var oldRealWidth=showcase.real_width;
						var dxy=dx*showcase.cos + dy*showcase.sin;
						if (editMove & 2) {
							// восток
							showcase.x_coord = showcase.x_coord + dxy * showcase.cos / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.sin / 2;
							showcase.length = showcase.length + dxy;
							if (showcase.code_rack>0){
								showcase.real_length = showcase.real_length + dxy;
							} else {
								showcase.real_length = showcase.length;
							}
						} else if (editMove & 8) {
							// запад
							showcase.x_coord = showcase.x_coord + dxy * showcase.cos / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.sin / 2;
							showcase.length = showcase.length - dxy;
							if (showcase.code_rack>0){
								showcase.real_length = showcase.real_length - dxy;
							} else {
								showcase.real_length = showcase.length;
							}
						}
						if (showcase.length < 7 * km) {
							showcase.length = 7 * km;
							showcase.x_coord = showcase.x_coord - dxy * showcase.cos / 2;
							showcase.y_coord = showcase.y_coord - dxy * showcase.sin / 2;
						}
						if (showcase.real_length < 7 * km) {
							showcase.real_length = showcase.length;
						}
						dxy=dy*showcase.cos - dx*showcase.sin;
						if (editMove & 4) {
							// север
							showcase.x_coord = showcase.x_coord - dxy * showcase.sin / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.cos / 2;
							showcase.width = showcase.width - dxy;
							if (showcase.code_rack>0){
								showcase.real_width = showcase.real_width - dxy;
							} else {
								showcase.real_width = showcase.width;
							}
						} else if (editMove & 16) {
							// юг
							showcase.x_coord = showcase.x_coord - dxy * showcase.sin / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.cos / 2;
							showcase.width = showcase.width + dxy;
							if (showcase.code_rack>0){
								showcase.real_width = showcase.real_width + dxy;
							} else {
								showcase.real_width = showcase.width;
							}
						}
						if (showcase.width < 7 * km) {
							showcase.width = 7 * km;
							showcase.x_coord = showcase.x_coord + dxy * showcase.sin / 2;
							showcase.y_coord = showcase.y_coord - dxy * showcase.cos / 2;
						}
						if (showcase.real_width < 7 * km) {
							showcase.real_width = showcase.width;
						}
						calcCoordinatesRack(showcase);
						if (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0 ||
								showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length ||
								showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0 ||
								showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width) {
							showcase.x_coord = oldX;
							showcase.y_coord = oldY;
							showcase.length = oldLength;
							showcase.width = oldWidth;
							showcase.real_length = oldRealLength;
							showcase.real_width = oldRealWidth;
							calcCoordinatesRack(showcase);
						} else {
							document.getElementById('showcaseX').value = showcase.x_coord;
							document.getElementById('showcaseY').value = showcase.y_coord;
							document.getElementById('showcaseLength').value = showcase.length;
							document.getElementById('showcaseWidth').value = showcase.width;
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
<%-- обработка событий окна навигации--%>
<script type="text/javascript">
	function previewCanvasMouseListener()
	{
	preview_canvas.onmousedown = function(e) {
		previewMove = true;
		var evnt = ie_event(e);
		x = evnt.clientX;
		y = evnt.clientY;
		kx = evnt.offsetX * preview_m - edit_canvas.width * km / 2;
		ky = evnt.offsetY * preview_m - edit_canvas.height * km / 2;
		checkKxKy();
		drawPreviewCanvas();
	};

	preview_canvas.onmouseup = function(e) {
		if (previewMove) {
			previewMove = false;
			drawEditCanvas();
		}
	};

	preview_canvas.onmouseover = function(e) {
		if (previewMove) {
			previewMove = false;
			drawEditCanvas();
		}
	};

	preview_canvas.onmousemove = function (e) {
		if (previewMove) {
			var evnt = ie_event(e);
			kx = kx + (evnt.clientX - x) * preview_m;
			ky = ky + (evnt.clientY - y) * preview_m;
			checkKxKy();
			x = evnt.clientX;
			y = evnt.clientY;
			drawPreviewCanvas();
		}
	};
	}

	function kMadd() {
		km = km * 1.5;
		if (km > edit_m)
			km = edit_m;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	};

	function kMsub() {
		km = km / 1.5;
		if (km < 0.1)
			km = 0.1;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	};

	function checkKxKy() {
		if (kx > sector.length - edit_canvas.width * km)
			kx = sector.length - edit_canvas.width * km;
		if (kx < 0)
			kx = 0;
		if (ky > sector.width - edit_canvas.height * km)
			ky = sector.width - edit_canvas.height * km;
		if (ky < 0)
			ky = 0;
	};
</script>
<%-- обработка событий панели зала--%>
<script type="text/javascript">
	function changeSectorName(sectorName) {
		window.sector.name_sector = sectorName.value;
	}
	function changeSectorLength(sectorLength) {
		var currentLength = Number(sectorLength.value);
		var maxLength = 0;
		for (var i = 0; i < showcaseList.length; i++) {
			if (maxLength < showcaseList[i].x1) {
				maxLength = showcaseList[i].x1;
			}
			if (maxLength < showcaseList[i].x2) {
				maxLength = showcaseList[i].x2;
			}
			if (maxLength < showcaseList[i].x3) {
				maxLength = showcaseList[i].x3;
			}
			if (maxLength < showcaseList[i].x4) {
				maxLength = showcaseList[i].x4;
			}
		}
		if (currentLength > maxLength) {
			sector.length = currentLength;
			edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
			preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
			km = edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentLength > 0) {
				sectorLength.value = maxLength;
				sector.length = maxLength;
				edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
				preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
				km = edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				sectorLength.value = sector.length;
		}
	}
	function changeSectorWidth(sectorWidth) {
		var currentWidth = Number(sectorWidth.value);
		var maxWidth = 0;
		for (var i = 0; i < showcaseList.length; i++) {
			if (maxWidth < showcaseList[i].y1) {
				maxWidth = showcaseList[i].y1;
			}
			if (maxWidth < showcaseList[i].y2) {
				maxWidth = showcaseList[i].y2;
			}
			if (maxWidth < showcaseList[i].y3) {
				maxWidth = showcaseList[i].y3;
			}
			if (maxWidth < showcaseList[i].y4) {
				maxWidth = showcaseList[i].y4;
			}
		}
		if (currentWidth > maxWidth) {
			sector.width = currentWidth;
			edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
			preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
			km = edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentWidth > 0) {
				sectorWidth.value = maxWidth;
				sector.width = maxWidth;
				edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
				preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
				km = edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				sectorWidth.value = sector.width;
		}
	}

	function changeSectorHeight(sectorHeight) {
		var height = Number(sectorHeight.value);
		var maxHeight = 0;
		for (var i = 0; i < showcaseList.length; i++) {
			var rackMaxHeight=Math.max(showcaseList[i].height, showcaseList[i].z_offset+showcaseList[i].real_height);
			if (maxHeight < rackMaxHeight) {
				maxHeight = rackMaxHeight;
			}
		}
		if (height > maxHeight) {
			sector.height = height;
		}
		else {
			if (height > 0) {
				sectorHeight.value = maxHeight;
				sector.height = maxHeight;
			}
			else
				sectorHeight.value = sector.height;
		}
	}
</script>
<%-- обработка событий панели стеллажа--%>
<script type="text/javascript">
	function changeRackType(rackType){
		if (showcase != null) {
			if (showcase.type_rack != rackType.value) {
				showcase.type_rack = rackType.value;
				drawEditCanvas();
				drawPreviewCanvas();
			}
		}
	}

	function changeShowcaseName(rackName) {
		if (showcase != null) {
			showcase.name_rack = rackName.value;
		}
	}

	function changeRackLockMove(rackLockMove) {
		if (showcase != null) {
			if (rackLockMove.checked) {
				$('#showcaseX').attr('disabled', 'disabled');
				$('#showcaseY').attr('disabled', 'disabled');
				$('#showcaseAngle').attr('disabled', 'disabled');
				showcase.lock_move = 'Y';
			}
			else {
				$('#showcaseX').removeAttr('disabled');
				$('#showcaseY').removeAttr('disabled');
				$('#showcaseAngle').removeAttr('disabled');
				showcase.lock_move = 'N';
			}
		}
	}

	function changeShowcaseX(showcaseX) {
		if (showcase != null) {
			var x = Number(showcaseX.value);
			if (x != null && !isNaN(x) && x != Infinity) {
				var oldx = showcase.x_coord;
				showcase.x_coord = x;
				calcCoordinatesRack(showcase);
				// не выходит за области сектора
				if ((showcase.x_coord < oldx && (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0)) ||
						(showcase.x_coord > oldx && (showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length))) {
					showcase.x_coord = oldx;
					calcCoordinatesRack(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseX.value = showcase.x_coord;
		}
	}

	function changeShowcaseY(showcaseY) {
		if (showcase != null) {
			var y = Number(showcaseY.value);
			if (y != null && !isNaN(y) && y != Infinity) {
				var oldy = showcase.y_coord;
				showcase.y_coord = y;
				calcCoordinatesRack(showcase);
				// не выходит за области сектора
				if ((showcase.y_coord < oldy && (showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0)) ||
						(showcase.y_coord > oldy && (showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width))) {
					showcase.y_coord = oldy;
					calcCoordinatesRack(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseY.value = showcase.y_coord;
		}
	}

	function changeShowcaseAngle(showcaseAngle) {
		// TODO не выходит за области сектора
		if (showcase != null) {
			var angle = Number(showcaseAngle.value);
			if (angle >= 0 && angle <= 360) {
				showcase.angle = angle;
				calcCoordinatesRack(showcase);
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else {
				showcaseAngle.value = showcase.angle;
			}
		}
	}

	function changeRackLockSize(rackLockSize)
	{
		if (showcase != null) {
			if (rackLockSize.checked) {
				$('#showcaseWidth').attr('disabled', 'disabled');
				$('#showcaseLength').attr('disabled', 'disabled');
				$('#showcaseHeight').attr('disabled', 'disabled');
				$('#rackLoadSide').attr('disabled', 'disabled');
				showcase.lock_size = 'Y';
			}
			else {
				$('#showcaseWidth').removeAttr('disabled');
				$('#showcaseLength').removeAttr('disabled');
				$('#showcaseHeight').removeAttr('disabled');
				$('#rackLoadSide').removeAttr('disabled');
				showcase.lock_size = 'N';
			}
		}
	}

	function changeShowcaseWidth(showcaseWidth) {
		if (showcase != null) {
			var width = Number(showcaseWidth.value);
			// не выходит за области сектора
			if (width > 0 && width != Infinity) {
				var oldWidth = showcase.width;
				showcase.width = width;
				showcase.real_width = showcase.real_width + width-oldWidth;
				calcCoordinatesRack(showcase);
				if (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0 ||
						showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length ||
						showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0 ||
						showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width) {
					showcase.width = oldWidth;
					showcase.real_width = showcase.real_width - width + oldWidth;
					calcCoordinatesRack(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseWidth.value = showcase.width;
		}
	}

	function changeShowcaseLength(showcaseLength) {
		if (showcase != null) {
			var length = Number(showcaseLength.value);
			// не выходит за области сектора
			if (length > 0 && length != Infinity) {
				var oldLength = showcase.length;
				showcase.length = length;
				if (showcase.code_rack>0){
					showcase.real_length = showcase.real_length + length - oldLength;
				}else{
					showcase.real_length = showcase.length;
				}

				calcCoordinatesRack(showcase);
				if (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0 ||
						showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length ||
						showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0 ||
						showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width) {
					showcase.length = oldLength;
					if (showcase.code_rack>0){
						showcase.real_length = showcase.real_length - length + oldLength;
					}else{
						showcase.real_length = showcase.length;
					}
					calcCoordinatesRack(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseLength.value = showcase.length;
		}
	}

	function changeShowcaseHeight(showcaseHeight) {
		if (showcase != null) {
			var height = Number(showcaseHeight.value);
			// не выходит за области сектора
			if (height > 0 && height <= sector.height && showcase.z_offset + showcase.real_height + height - showcase.height <= sector.height) {
				showcase.real_height = showcase.real_height + height - showcase.height;
				showcase.height = height;
			}
			else {
				showcaseHeight.value = showcase.height;
			}
		}
	}

	function changeShowcaseOpen(rackLoadSide) {
		if (window.showcase != null) {
			//TODO
//			if (window.showcase.code_rack_template==null
//					|| window.showcase.code_rack_template==''
//					|| window.showcase.load_side!='<%=LoadSide.U%>')
//			{
				// для шаблонного стеллажа нельзя меньть сторону загрузки "сверху" на другую
//				if (window.showcase!='<%=LoadSide.U%>' && rackLoadSide.value!='<%=LoadSide.U%>')
//				{
//				}
			window.showcase.load_side = rackLoadSide.value;
			drawEditCanvas();
			drawPreviewCanvas();
//			}
		}
	}

    function changeShowcaseAllowIntersect(rackAllowIntersect){
        if (window.showcase != null) {
            window.showcase.allow_intersect=rackAllowIntersect.value;
        }
    }

	function rackBeyondSector(rack)
	{
		return rack.x1 < 0
				|| rack.x2 < 0
				|| rack.x3 < 0
				|| rack.x4 < 0
				|| rack.y1 < 0
				|| rack.y2 < 0
				|| rack.y3 < 0
				|| rack.y4 < 0
				|| rack.x1 > window.sector.length
				|| rack.x2 > window.sector.length
				|| rack.x3 > window.sector.length
				|| rack.x4 > window.sector.length
				|| rack.y1 > window.sector.width
				|| rack.y2 > window.sector.width
				|| rack.y3 > window.sector.width
				|| rack.y4 > window.sector.width;
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