<%@ page import="planograma.servlet.sector.SectorFindRackContainsWares" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
	#sectorEditFind {
		display: none;
		position: absolute;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		background-color: rgba(255, 255, 255, 0.5);
	}

	#sectorEditFindPanel {
		background-color: rgba(255, 255, 255, 1);
		border: 1px solid black;
	}
</style>

<table id="sectorEditFind">
	<tr>
		<td align="center" valign="middle">
			<table id="sectorEditFindPanel">
				<tr>
					<td class="path">
						<i>Поиск</i>
					</td>
					<td class="path" align="right">
						<a href="#" onclick="sectorEditFindCancel()">&#x2715;</a>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center">Введите штрихкод стеллажа</td>
				</tr>
				<tr>
					<td colspan="2">
						<form onsubmit="sectorEditFindRack()">
							&nbsp;&nbsp;&nbsp;<input type="text" id="sectorEditFindRackBarcode"/>
							<input type="submit" value="Поиск">
						</form>
					</td>
				</tr>
				<tr>
					<td align="center">Введите код товара</td>
				</tr>
				<tr>
					<td>
						<form onsubmit="sectorEditFindWares()">
							&nbsp;&nbsp;&nbsp;<input type="text" id="sectorEditFindWaresCode"/>
							<input type="submit" value="Поиск">
						</form>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="path" colspan="2" align="right">
						<a href="#" onclick="sectorEditFindCancel()">Отмена</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function sectorEditFindShow() {
		var choiceSectorPrint = $('#sectorEditFind');
		choiceSectorPrint.animate({opacity: 'show'}, 500);
	}

	function sectorEditFindCancel() {
		var choiceSectorPrint = $('#sectorEditFind');
		choiceSectorPrint.animate({opacity: 'hide'}, 500);
	}

	function sectorEditFindRack() {
		var barcode = $('#sectorEditFindRackBarcode').val();
		if (barcode != null && barcode.length > 0) {
			window.showcase = null;
			for (var i = window.showcaseList.length - 1; window.showcase == null && i >= 0; i--) {
				if (window.showcaseList[i].rack_barcode == barcode) {
					window.showcase = window.showcaseList[i];
					window.editMove = 0;
				}
			}
			selectShowcase(window.showcase);
			drawEditCanvas();
			drawPreviewCanvas();
			if (window.showcase == null) {
				alert("Стеллажа с таким штрихкодом не найден")
			} else {
				sectorEditFindCancel();
			}
		}
	}

	function sectorEditFindWares() {
		var code_wares = $('#sectorEditFindWaresCode').val();
		postJson('<%=SectorFindRackContainsWares.URL%>', {code_sector: window.sector.code_sector, code_wares: code_wares}, function (data) {
			if (data != null && data.findBarcodeRackList != null && data.findBarcodeRackList.length > 0) {
				// отрисовать только стелажи в которых есть искомый товар
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
					for (var j=0; j<data.findBarcodeRackList.length; j++)
					{
						if (window.showcaseList[i].rack_barcode==data.findBarcodeRackList[j])
						{
							drawRack(window.showcaseList[i], window.rackStateList[i], window.rackStateInSectorList[i], view_mode, window.edit_context, window.kx, window.ky, window.km);
							j=data.findBarcodeRackList.length;
						}
					}
				}
				sectorEditFindCancel();
			}
			else {
				alert("Стеллажа с таким товаром не найден")
			}
		});
	}

</script>