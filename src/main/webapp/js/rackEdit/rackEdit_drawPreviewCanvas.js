// прорисовка панели навигации
function drawPreviewCanvas() {
	//TODO
	window.preview_context.clearRect(0, 0, window.preview_canvas.width, window.preview_canvas.height);
	window.preview_context.lineWidth = 1;

	window.preview_context.strokeStyle = "BLACK";
	window.preview_context.strokeRect(
		window.offset_rack_x / window.preview_m,
		window.preview_canvas.height - window.offset_rack_y / window.preview_m,
		window.rack.width / window.preview_m,
		-window.rack.height / window.preview_m);
	window.preview_context.strokeStyle = "GREEN";
	window.preview_context.strokeRect(
		window.offset_real_rack_x / window.preview_m,
		window.preview_canvas.height - window.offset_real_rack_y / window.preview_m,
		window.rack.real_width / window.preview_m,
		-window.rack.real_height / window.preview_m);
	for (var i = 0; i < window.rackShelfList.length; i++) {
		drawShelf(window.rackShelfList[i], window.preview_canvas, window.preview_context, 0, 0, window.preview_m);
	}
	window.preview_context.lineWidth = 1;
	window.preview_context.strokeStyle = "BLUE";
	window.preview_context.strokeRect(window.kx / window.preview_m,
		window.preview_canvas.height - window.ky / window.preview_m,
		window.edit_canvas.width * window.km / window.preview_m,
		-window.edit_canvas.height * window.km / window.preview_m);
}
