<%@ page import="planograma.servlet.rack.print.RackWaresPlacemntPrint" %>
<%@ page import="planograma.servlet.rack.print.RackPrintStateA" %>
<%@ page import="planograma.servlet.rack.print.RackPrintStatePC" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
	#choiceRackPrint {
		display: none;
		position: absolute;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		background-color: rgba(255, 255, 255, 0.5);
	}

	#choiceRackPrintPanel {
		background-color: rgba(255, 255, 255, 1);
		border: 1px solid black;
	}
</style>

<table id="choiceRackPrint">
	<tr>
		<td align="center" valign="middle">
			<table id="choiceRackPrintPanel">
				<tr>
					<td class="path">
						<i>Выбор печатной формы стеллажа</i>
					</td>
					<td class="path" align="right">
						<a href="#" onclick="choiceRackPrintCancel()">&#x2715;</a>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="rackPrint" target="pdf" onclick="choiceRackPrintCancel()">
							Текущее состояние</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="rackPrintA" target="pdf" onclick="choiceRackPrintCancel()">
							На момент утверждения компановки</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="rackPrintPC" target="pdf" onclick="choiceRackPrintCancel()">
							На момент выполнения компановки</a>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="path" colspan="2" align="right">
						<a href="#" onclick="choiceRackPrintCancel()">Отмена</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function choiceRackPrintShow(code_rack) {
		if (code_rack > 0) {
			var choiceRackPrint = $('#choiceRackPrint');
			choiceRackPrint.animate({opacity: 'show'}, 500);
			$('#rackPrint').attr('href', '<%=RackWaresPlacemntPrint.URL%>' + code_rack);
			$('#rackPrintA').attr('href', '<%=planograma.servlet.rack.print.RackPrintStateA.URL%>' + code_rack);
			$('#rackPrintPC').attr('href', '<%=planograma.servlet.rack.print.RackPrintStatePC.URL%>' + code_rack);
		}
		else {
			$('#rackPrint').attr('href', '#');
			$('#rackPrintA').attr('href', '#');
			$('#rackPrintPC').attr('href', '#');
		}
	}
	function choiceRackPrintCancel() {
		var choiceRackPrint = $('#choiceRackPrint');
		choiceRackPrint.animate({opacity: 'hide'}, 500);
	}
</script>