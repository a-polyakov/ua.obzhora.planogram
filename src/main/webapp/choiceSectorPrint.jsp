<%@ page import="planograma.servlet.sector.print.SectorPrint" %>
<%@ page import="planograma.servlet.sector.print.SectorPrintRackStateInSectorA" %>
<%@ page import="planograma.servlet.sector.print.SectorPrintRackStateInSectorPC" %>
<%@ page import="planograma.servlet.sector.print.SectorPrintWithEditor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
	#choiceSectorPrint {
		display: none;
		position: absolute;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		background-color: rgba(255, 255, 255, 0.5);
	}

	#choiceSectorPrintPanel {
		background-color: rgba(255, 255, 255, 1);
		border: 1px solid black;
	}
</style>

<table id="choiceSectorPrint">
	<tr>
		<td align="center" valign="middle">
			<table id="choiceSectorPrintPanel">
				<tr>
					<td class="path">
						<i>Выбор печатной формы зала</i>
					</td>
					<td class="path" align="right">
						<a href="#" onclick="choiceSectorPrintCancel()">&#x2715;</a>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="sectorPrintCurrentWithEditor" target="pdf" onclick="choiceSectorPrintCancel()">
							Текущее состояние</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="sectorPrintCurrent" target="pdf" onclick="choiceSectorPrintCancel()">
							Текущее состояние с габаритами</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="sectorPrintA" target="pdf" onclick="choiceSectorPrintCancel()">
							На момент утверждения стеллажа в зале (включая выполненые)</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;<a href="#" id="sectorPrintPC" target="pdf" onclick="choiceSectorPrintCancel()">
							На момент выполнения стеллажа в зале</a>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="path" colspan="2" align="right">
						<a href="#" onclick="choiceSectorPrintCancel()">Отмена</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function choiceSectorPrintShow() {
		var choiceSectorPrint = $('#choiceSectorPrint');
		choiceSectorPrint.animate({opacity: 'show'}, 500);
	}
	function choiceSectorPrintSetCodeSector(code_sector) {
		if (code_sector > 0) {
			$('#sectorPrintCurrent').attr('href', '<%=SectorPrint.URL%>' + code_sector);
			$('#sectorPrintCurrentWithEditor').attr('href', '<%=SectorPrintWithEditor.URL%>' + code_sector);
			$('#sectorPrintA').attr('href', '<%=SectorPrintRackStateInSectorA.URL%>' + code_sector);
			$('#sectorPrintPC').attr('href', '<%=SectorPrintRackStateInSectorPC.URL%>' + code_sector);
		}
		else {
			$('#sectorPrintCurrent').attr('href', '#');
			$('#sectorPrintCurrentWithEditor').attr('href', '#');
			$('#sectorPrintActive').attr('href', '#');
			$('#sectorPrintPC').attr('href', '#');
		}
	}
	function choiceSectorPrintCancel() {
		var choiceSectorPrint = $('#choiceSectorPrint');
		choiceSectorPrint.animate({opacity: 'hide'}, 500);
	}
</script>