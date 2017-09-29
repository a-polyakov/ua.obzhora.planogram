// обработка событий панели товара
function changeWaresX(waresX)
{
	if (window.selectRackWaresList.length==1) {
		var x = Number(waresX.value);
		if (x != null && !isNaN(x) && x != Infinity) {
//				var oldx = window.selectRackWaresList[0].position_x;
			window.selectRackWaresList[0].position_x = x;
			roundRackWares(window.selectRackWaresList[0]);
			//TODO
			// не выходит за области сектора
//				if ((window.shelf.x_coord < oldx
//						&& (window.shelf.x1 < 0
//						|| window.shelf.x2 < 0
//						|| window.shelf.x3 < 0
//						|| window.shelf.x4 < 0))
//						|| (window.shelf.x_coord > oldx
//						&& (window.shelf.x1 > window.rack.width
//						|| window.shelf.x2 > window.rack.width
//						|| window.shelf.x3 > window.rack.width
//						|| window.shelf.x4 > window.rack.width))) {
//					window.shelf.x_coord = oldx;
//					rackShelfCalcCoordinates(window.shelf);
//				}
//				else {
			drawEditCanvas();
			drawPreviewCanvas();
//				}
		}
		waresX.value =window.selectRackWaresList[0].position_x;
	}
}
function changeWaresY(waresY)
{
	if (window.selectRackWaresList.length==1) {
		var y = Number(waresY.value);
		if (y != null && !isNaN(x) && y != Infinity) {
//				var oldY = window.selectRackWaresList[0].position_y;
			window.selectRackWaresList[0].position_y = y;
			roundRackWares(window.selectRackWaresList[0]);
			//TODO
			// не выходит за области сектора
//				if ((window.shelf.x_coord < oldY
//						&& (window.shelf.x1 < 0
//						|| window.shelf.x2 < 0
//						|| window.shelf.x3 < 0
//						|| window.shelf.x4 < 0))
//						|| (window.shelf.x_coord > oldY
//						&& (window.shelf.x1 > window.rack.width
//						|| window.shelf.x2 > window.rack.width
//						|| window.shelf.x3 > window.rack.width
//						|| window.shelf.x4 > window.rack.width))) {
//					window.shelf.position_y = oldY;
//					rackShelfCalcCoordinates(window.shelf);
//				}
//				else {
			drawEditCanvas();
			drawPreviewCanvas();
//				}
		}
		waresY.value =window.selectRackWaresList[0].position_y;
	}
}
function changeWaresCountInLength(waresCountInLength) {
	if (window.selectRackWaresList.length == 1) {
		var value = Number(waresCountInLength.value);
		var max = Math.floor(window.rack.length / window.selectRackWaresList[0].wares_length);
		if (value > 0 && value <= max) {
			window.selectRackWaresList[0].count_length_on_shelf = value;
		}
		waresCountInLength.value = window.selectRackWaresList[0].count_length_on_shelf;
	}
}