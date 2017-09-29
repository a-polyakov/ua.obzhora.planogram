<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateList" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateRemove" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateEdit" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_rack_template_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_TEMPLATE_EDIT);
%>
<html>
<head>
	<title>Стантартные типы стеллажей</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/draw/calcCoordinatesRackShelfTemplate.js"></script>
	<script type="text/javascript" src="js/draw/drawRackShelfTemplate.jsp"></script>
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
					<td><i>Типовые стеллажи</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="logout()">Выход</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="frame">
				<colgroup>
					<col/>
					<col/>
					<col width="100%"/>
				</colgroup>
				<tr>
					<td>
						<table class="menu">
							<tr>
								<td><a href="#" id="rackTemplateAdd" onclick="return aOnClick(this, fRackTemplateAdd)" class="<%=access_rack_template_edit%>"><%=JspUtils.toMenuTitle("Добавить стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateViewHistory" onclick="return aOnClick(this, fRackTemplateViewHistory)" class="disabled"><%=JspUtils.toMenuTitle("Просмотр истории стеллажа")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateEdit" onclick="return aOnClick(this, fRackTemplateEdit)" class="disabled"><%=JspUtils.toMenuTitle("Редактировать стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateActive" onclick="return aOnClick(this, fRackTemplateActive)" class="disabled"><%=JspUtils.toMenuTitle("Активировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateNotActive" onclick="return aOnClick(this, fRackTemplateNotActive)" class="disabled"><%=JspUtils.toMenuTitle("Заблокировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateRemove" onclick="return aOnClick(this, fRackTemplateRemove)" class="disabled"><%=JspUtils.toMenuTitle("Удалить стеллаж")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td>
						<select id="rackTemplateList" size="20" style="width: 250px; height: 100%;" onchange="selectRackTemplate(value)">
						</select>
					</td>
					<td id="preview_td" valign="top">
						<canvas id="preview_canvas" style="display: none;" width="150" height="150">canvas not supported</canvas>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">

	var canRackTemplateEdit='<%=access_rack_template_edit%>';

	function fRackTemplateAdd()
	{
		setCookie('code_rack_template', '');
		window.location='rackTemplateEdit.jsp';
	}
	function fRackTemplateViewHistory()
	{
		//TODO
	}
	function fRackTemplateEdit()
	{
		var code_rack_template = $('#rackTemplateList').val();
		if (code_rack_template!=null && code_rack_template.length>0)
		{
			setCookie('code_rack_template', code_rack_template);
			window.location='rackTemplateEdit.jsp';
		}
	}
	function fRackTemplateActive()
	{
		//TODO
	}
	function fRackTemplateNotActive()
	{
		//TODO
	}
	function fRackTemplateRemove()
	{
		var code_rack_template = $('#rackTemplateList').val();
		if (code_rack_template!=null && code_rack_template.length>0)
		{
			postJson(
					'<%=RackTemplateRemove.URL%>',
					{code_rack_template:code_rack_template},
					function () {
						loadComplete();
					})
		}
	}
</script>
<script type="text/javascript">
	function selectRackTemplate(code_rack_template) {
		$('#preview_canvas').hide();
		if (code_rack_template != null && code_rack_template != '') {
			var rackTemplateList = $('#rackTemplateList');
			var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
			if (option.length == 1) {
				option.attr('selected', 'selected');
				$('#rackTemplateView').removeClass('disabled');
				$('#rackTemplateEdit').removeClass('disabled');
				$('#rackTemplateUnlock').removeClass('disabled');
				if (canRackTemplateEdit!='disabled')
				{
					$('#rackTemplateRemove').removeClass('disabled');
				}
				setCookie('code_rack_template', code_rack_template);
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
					selectRackTemplate2();
				}, 'preview_td');
			}
			else {
				setCookie('code_rack_template', '');
			}
		}
		else {
			setCookie('code_rack_template', '');
		}
	}

	function selectRackTemplate2()
	{
		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = window.preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		var width=preview_td.width() - 4;
		var height= preview_td.height() - 4;

		// смещение стеллажа
		window.offset_rack_x=-Math.min(0, window.rackTemplate.x_offset);
		window.offset_rack_y=-Math.min(0, window.rackTemplate.y_offset);
		// смещение полезного обема
		window.offset_real_rack_x=Math.max(0, window.rackTemplate.x_offset);
		window.offset_real_rack_y=Math.max(0, window.rackTemplate.y_offset);
		// определение максимальных габаритов
		window.max_x=Math.max(window.offset_rack_x + window.rackTemplate.width, window.offset_real_rack_x+window.rackTemplate.real_width);
		window.max_y=Math.max(window.offset_rack_y + window.rackTemplate.height, window.offset_real_rack_y+window.rackTemplate.real_height);

		// масштаб в окне навигации
		window.preview_m = Math.max(window.max_x / width,  window.max_y / height);
		window.preview_canvas.width = window.max_x/window.preview_m;
		window.preview_canvas.height = window.max_y/window.preview_m;

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
			calcCoordinatesRackShelfTemplate(window.rackShelfTemplateList[i]);
			drawRackShelfTemplate(window.rackShelfTemplateList[i], window.preview_canvas, window.preview_context, -window.offset_rack_x, -window.offset_rack_y, window.preview_m);
		}

		var title=window.rackTemplate.name_rack_template;
		title+='\n'+window.rackTemplate.width+'x'+window.rackTemplate.height+'x'+window.rackTemplate.length;
		$('#preview_canvas').attr('title', title);
		$('#preview_canvas').show();
	}
</script>
<script type="text/javascript">
	function loadComplete()
	{
	postJson('<%=RackTemplateList.URL%>', null, function (data) {
		var rackTemplateList = $('#rackTemplateList');
		rackTemplateList.empty();
		for (var i in data.rackTemplateList) {
			var item = data.rackTemplateList[i];
			var option = $('<option></option>')
			option.val(item.code_rack_template);
			option.text(item.name_rack_template);
			rackTemplateList.append(option)
		}
		var code_rack_template = getCookie('code_rack_template');
		if (code_rack_template != null && code_rack_template.length > 0) {
			selectRackTemplate(code_rack_template);
		}
	});
	}
</script>
</body>
</html>