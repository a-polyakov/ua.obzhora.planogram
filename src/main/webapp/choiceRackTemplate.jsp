<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateEdit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="rackTemplate">
	<tr>
		<td class="path">
			<table>
				<tr>
					<td><i>Выбор стандартного стеллажа</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="rackTemplateCancel()">&#x2715;</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="frame">
				<tr>
					<td>
						<select id="rackTemplateList" size="20" style="width: 200px; height: 100%;" onchange="selectRackTemplate(value)"/>
					</td>
					<td id="choice_rack_template_preview_td" valign="top" width="100%">
						<canvas id="choice_rack_template_preview_canvas" style="display: none; border: 1px solid black;" width="150" height="150">canvas not supported</canvas>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="path">
			<table>
				<tr>
					<td width="100%"></td>
					<td><a href="#" onclick="rackTemplateCancel()">Отмена</a></td>
					<td>&nbsp;</td>
					<td><a href="#" onclick="rackTemplateOk()">Ок</a></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<%-- обработка событий окно выбора стандартного стеллажа --%>
<script type="text/javascript">

	function selectRackTemplate(code_rack_template)
	{
		$('#choice_rack_template_preview_canvas').hide();
		if (code_rack_template != null && code_rack_template != '') {
			var rackTemplateList = $('#rackTemplateList');
			var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
			if (option.length == 1) {
				option.attr('selected', 'selected');
				setCookie('code_rack_template', code_rack_template);
				postJson('<%=RackTemplateEdit.URL%>', {code_rack_template:code_rack_template}, function (data) {
					var rackTemplate=data.rackTemplate;
					switch (rackTemplate.load_side)
					{
						case '<%=LoadSide.U%>':
							var temp=rackTemplate.width;
							rackTemplate.width=rackTemplate.length;
							rackTemplate.length=rackTemplate.height;
							rackTemplate.height=temp;

							temp=rackTemplate.real_width;
							rackTemplate.real_width=rackTemplate.real_length;
							rackTemplate.real_length=rackTemplate.real_height;
							rackTemplate.real_height=temp;
							break;
						case '<%=LoadSide.F%>':
							var temp=rackTemplate.width;
							rackTemplate.width=rackTemplate.length;
							rackTemplate.length=temp;

							temp=rackTemplate.real_width;
							rackTemplate.real_width=rackTemplate.real_length;
							rackTemplate.real_length=temp;

							temp=rackTemplate.y_offset;
							rackTemplate.y_offset=rackTemplate.z_offset;
							rackTemplate.z_offset=temp;
							break;
					}
					selectRackTemplate2(rackTemplate, data.rackShelfTemplateList);
				}, 'choice_rack_template_preview_td');
			}
			else {
				setCookie('code_rack_template', '');
			}
		}
		else {
			setCookie('code_rack_template', '');
		}
	}

	function selectRackTemplate2(rackTemplate, rackShelfTemplateList)
	{
		var choice_rack_template_preview_canvas = document.getElementById("choice_rack_template_preview_canvas");
		var choice_rack_template_preview_context = choice_rack_template_preview_canvas.getContext("2d");
		var choice_rack_template_preview_td = $('#choice_rack_template_preview_td');
		var width=choice_rack_template_preview_td.width() - 4;
		var height= choice_rack_template_preview_td.height() - 4;

		// смещение стеллажа
		var offset_rack_x=-Math.min(0, rackTemplate.x_offset);
		var offset_rack_y=-Math.min(0, rackTemplate.y_offset);
		// смещение полезного обема
		var offset_real_rack_x=Math.max(0, rackTemplate.x_offset);
		var offset_real_rack_y=Math.max(0, rackTemplate.y_offset);
		// определение максимальных габаритов
		var max_x=Math.max(offset_rack_x + rackTemplate.width, offset_real_rack_x+rackTemplate.real_width);
		var max_y=Math.max(offset_rack_y + rackTemplate.height, offset_real_rack_y+rackTemplate.real_height);

		// масштаб в окне навигации
		var choice_rack_template_preview_m = Math.max(max_x / width,  max_y / height);
		choice_rack_template_preview_canvas.width = max_x/choice_rack_template_preview_m;
		choice_rack_template_preview_canvas.height = max_y/choice_rack_template_preview_m;

		choice_rack_template_preview_context.lineWidth = 1;
		choice_rack_template_preview_context.strokeStyle = "BLACK";
		choice_rack_template_preview_context.strokeRect(
				offset_rack_x / choice_rack_template_preview_m,
				choice_rack_template_preview_canvas.height - offset_rack_y / choice_rack_template_preview_m,
				rackTemplate.width / choice_rack_template_preview_m,
				-rackTemplate.height / choice_rack_template_preview_m);
		choice_rack_template_preview_context.strokeStyle = "GREEN";
		choice_rack_template_preview_context.strokeRect(
				offset_real_rack_x / choice_rack_template_preview_m,
				choice_rack_template_preview_canvas.height - offset_real_rack_y / choice_rack_template_preview_m,
				rackTemplate.real_width / choice_rack_template_preview_m,
				-rackTemplate.real_height / choice_rack_template_preview_m);

		for (var i = 0; i < rackShelfTemplateList.length; i++) {
			calcCoordinatesRackShelfTemplate(rackShelfTemplateList[i]);
			drawRackShelfTemplate(rackShelfTemplateList[i], choice_rack_template_preview_canvas, choice_rack_template_preview_context, -offset_rack_x, -offset_rack_y, choice_rack_template_preview_m);
		}

		var title=rackTemplate.name_rack_template;
		title+='\n'+rackTemplate.width+'x'+rackTemplate.height+'x'+rackTemplate.length;
		$('#choice_rack_template_preview_canvas').attr('title', title);
		$('#choice_rack_template_preview_canvas').show();
	}

	function rackTemplateCancel() {
		var rackTemplate = $('#rackTemplate');
		rackTemplate.animate({opacity:'hide'}, 500);
	}
	function rackTemplateOk() {
		var rackTemplate = $('#rackTemplate');
		rackTemplate.animate({opacity:'hide'}, 500);
		var rackTemplateList = $('#rackTemplateList');
		var code_rack_template = rackTemplateList.val();
		if (code_rack_template != null && code_rack_template.length > 0) {
			setCookie('code_rack_template', code_rack_template);
			var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
			if (option.length == 1) {
				window.rackAdd=false;
				window.rackTemplateAdd = $.evalJSON(option.attr('json'));
			}
		}
	}
</script>