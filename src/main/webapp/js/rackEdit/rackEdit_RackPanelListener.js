// обработка событий панели стеллажа
function changeRackName(rackName) {
	window.rack.name_rack = rackName.value;
}
function changeRackWidth(rackWidth) {
	// TODO  RackRealWidth
	// TODO  min_x max_x
	var currentWidth = Number(rackWidth.value);
	var maxWidth = 0;
	for (var i = 0; i < window.rackShelfList.length; i++) {
		if (maxWidth < window.rackShelfList[i].x1) {
			maxWidth = window.rackShelfList[i].x1;
		}
		if (maxWidth < window.rackShelfList[i].x2) {
			maxWidth = window.rackShelfList[i].x2;
		}
		if (maxWidth < window.rackShelfList[i].x3) {
			maxWidth = window.rackShelfList[i].x3;
		}
		if (maxWidth < window.rackShelfList[i].x4) {
			maxWidth = window.rackShelfList[i].x4;
		}
	}
	if (currentWidth > maxWidth) {
		window.rack.width = currentWidth;
		window.max_x=Math.max(window.offset_rack_x + window.rack.width, window.offset_real_rack_x+window.rack.real_width);
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
			rackWidth.value = maxWidth;
			window.rack.width = maxWidth;
			window.max_x=Math.max(window.offset_rack_x + window.rack.width, window.offset_real_rack_x+window.rack.real_width);
			// масштаб в окне редактирования
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			// масштаб в окне навигации
			window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else
			rackWidth.value = window.rack.width;
	}
}
function changeRackHeight(rackHeight) {
	// TODO  RackRealHeight
	// TODO  min_y max_y
	var currentHeight = Number(rackHeight.value);
	var maxHeight = 0;
	for (var i = 0; i < window.rackShelfList.length; i++) {
		if (maxHeight < window.rackShelfList[i].y1) {
			maxHeight = window.rackShelfList[i].y1;
		}
		if (maxHeight < window.rackShelfList[i].y2) {
			maxHeight = window.rackShelfList[i].y2;
		}
		if (maxHeight < window.rackShelfList[i].y3) {
			maxHeight = window.rackShelfList[i].y3;
		}
		if (maxHeight < window.rackShelfList[i].y4) {
			maxHeight = window.rackShelfList[i].y4;
		}
	}
	if (currentHeight > maxHeight) {
		window.rack.height = currentHeight;
		window.max_y=Math.max(window.offset_rack_y + window.rack.height, window.offset_real_rack_y+window.rack.real_height);
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
			rackHeight.value = maxHeight;
			window.rack.height = maxHeight;
			window.max_y=Math.max(window.offset_rack_y + window.rack.height, window.offset_real_rack_y+window.rack.real_height);
			// масштаб в окне редактирования
			window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
			// масштаб в окне навигации
			window.preview_m = Math.max(window.max_x / preview_canvas.width,  window.max_y / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else
			rackHeight.value = window.rack.height;
	}
}
function changeRackLength(rackLength) {
	// TODO  RackRealLength
	var currentLength = Number(rackLength.value);
	var maxLength = 0;
	for (var i = 0; i < window.rackShelfList.length; i++) {
		if (maxLength < window.rackShelfList[i].shelf_length) {
			maxLength = window.rackShelfList[i].shelf_length;
		}
	}
	if (currentLength > maxLength) {
		window.rack.length = currentLength;
	}
	else {
		if (currentLength > 0) {
			rackLength.value = maxLength;
			window.rack.length = maxLength;
		}
		else
			rackLength.value = window.rack.length;
	}
}
function changeRackRealWidth(realWidth) {
	var currentWidth = Number(realWidth.value);
	if (currentWidth > 0) {
		window.rack.real_width = currentWidth;
		window.max_x = Math.max(window.offset_rack_x + window.rack.width, window.offset_real_rack_x + window.rack.real_width);
		window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
		window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
		window.km = window.edit_m
		drawEditCanvas();
		drawPreviewCanvas();
	}
	else {
		realWidth.value = window.rack.real_width;
	}
}

function changeRackRealHeight(realHeight) {
	var currentHeight = Number(realHeight.value);
	if (currentHeight > 0) {
		window.rack.real_height = currentHeight;
		window.max_y = Math.max(window.offset_rack_y + window.rack.height, window.offset_real_rack_y + window.rack.real_height);
		window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
		window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
		window.km = window.edit_m
		drawEditCanvas();
		drawPreviewCanvas();
	}
	else {
		realHeight.value = window.rack.real_height;
	}
}

function changeRackRealLength(realLength) {
	var currentLength = Number(realLength.value);
	if (currentLength > 0) {
		window.rack.real_length = currentLength;
	}
	else {
		realLength.value = window.rack.real_length;
	}
}

function changeRackLoadSide(rackLoadSide) {
	window.rack.load_side = rackLoadSide.value;
}

function changeRackX_offset(xOffset) {
	if (!isNaN(xOffset.value)) {
		window.rack.x_offset = Number(xOffset.value);
		// смещение стеллажа
		window.offset_rack_x = -Math.min(0, window.rack.x_offset);
		// смещение полезного обема
		window.offset_real_rack_x = Math.max(0, window.rack.x_offset);
		// определение максимальных габаритов
		window.max_x = Math.max(window.offset_rack_x + window.rack.width, window.offset_real_rack_x + window.rack.real_width);
		// масштаб в окне редактирования
		window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
		// масштаб в окне навигации
		window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
		window.km = window.edit_m
		drawEditCanvas();
		drawPreviewCanvas();
	}
	else {
		xOffset.value = window.rack.x_offset;
	}
}

function changeRackY_offset(yOffset) {
	if (!isNaN(yOffset.value)) {
		window.rack.y_offset = Number(yOffset.value);
		// смещение стеллажа
		window.offset_rack_y = -Math.min(0, window.rack.y_offset);
		// смещение полезного обема
		window.offset_real_rack_y = Math.max(0, window.rack.y_offset);
		// определение максимальных габаритов
		window.max_y = Math.max(window.offset_rack_y + window.rack.height, window.offset_real_rack_y + window.rack.real_height);
		// масштаб в окне редактирования
		window.edit_m = Math.max(window.max_x / edit_canvas.width, window.max_y / edit_canvas.height);
		// масштаб в окне навигации
		window.preview_m = Math.max(window.max_x / preview_canvas.width, window.max_y / preview_canvas.height);
		window.km = window.edit_m
		drawEditCanvas();
		drawPreviewCanvas();
	}
	else {
		yOffset.value = window.rack.y_offset;
	}
}

function changeRackZ_offset(zOffset) {
	if (!isNaN(zOffset.value)) {
		window.rack.z_offset = Number(zOffset.value);
	}
	else {
		zOffset.value = window.rack.z_offset;
	}
}
