// обработка событий панели полки
function changeShelfX(shelfX) {
	if (window.shelf != null) {
		var x = Number(shelfX.value);
		if (x != null && !isNaN(x) && x != Infinity) {
			var oldx = window.shelf.x_coord;
			window.shelf.x_coord = x;
			rackShelfCalcCoordinates(window.shelf);
			// не выходит за области сектора
			if ((window.shelf.x_coord < oldx
				&& (window.shelf.x1 < 0
				|| window.shelf.x2 < 0
				|| window.shelf.x3 < 0
				|| window.shelf.x4 < 0))
				|| (window.shelf.x_coord > oldx
				&& (window.shelf.x1 > window.rack.width
				|| window.shelf.x2 > window.rack.width
				|| window.shelf.x3 > window.rack.width
				|| window.shelf.x4 > window.rack.width))) {
				window.shelf.x_coord = oldx;
				rackShelfCalcCoordinates(window.shelf);
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
			rackShelfCalcCoordinates(window.shelf);
			// не выходит за области сектора
			if ((window.shelf.y_coord < oldY
				&& (window.shelf.y1 < 0
				|| window.shelf.y2 < 0
				|| window.shelf.y3 < 0
				|| window.shelf.y4 < 0))
				|| (window.shelf.y_coord > oldY
				&& (window.shelf.y1 > window.rack.height
				|| window.shelf.y2 > window.rack.height
				|| window.shelf.y3 > window.rack.height
				|| window.shelf.y4 > window.rack.height))) {
				window.shelf.y_coord = oldY;
				rackShelfCalcCoordinates(window.shelf);
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
			rackShelfCalcCoordinates(window.shelf);
			if (shelfBeyondRack(window.shelf)) {
				window.shelf.angle = oldAngle;
				rackShelfCalcCoordinates(window.shelf);
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
		if (value != null && value > 0 && value != Infinity) {
			var oldValue = window.shelf.shelf_width;
			window.shelf.shelf_width = value;
			rackShelfCalcCoordinates(window.shelf);
			// не выходит за области сектора
			if (shelfBeyondRack(window.shelf)) {
				window.shelf.shelf_width = oldValue;
				rackShelfCalcCoordinates(window.shelf);
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
		if (value != null && value > 0 && value != Infinity) {
			var oldValue = window.shelf.shelf_height;
			window.shelf.shelf_height = value;
			rackShelfCalcCoordinates(window.shelf);
			// не выходит за области сектора
			if (shelfBeyondRack(window.shelf)) {
				window.shelf.shelf_height = oldValue;
				rackShelfCalcCoordinates(window.shelf);
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
		if (value > 0 && value <= window.rack.length) {
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
		|| shelf.x1 > rack.width
		|| shelf.x2 > rack.width
		|| shelf.x3 > rack.width
		|| shelf.x4 > rack.width
		|| shelf.y1 > rack.height
		|| shelf.y2 > rack.height
		|| shelf.y3 > rack.height
		|| shelf.y4 > rack.height;
}