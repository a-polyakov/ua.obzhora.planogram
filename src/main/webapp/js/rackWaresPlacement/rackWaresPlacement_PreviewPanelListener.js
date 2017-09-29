// обработка событий окна навигации
function previewCanvasMouseListener() {

	window.previewMove = false;

	window.preview_canvas.onmousedown = function (e) {
		window.previewMove = true;
		var evnt = ie_event(e);
		x = evnt.clientX;
		y = evnt.clientY;
		window.kx = evnt.offsetX * window.preview_m - window.edit_canvas.width * window.km / 2;
		window.ky = (window.preview_canvas.height-evnt.offsetY) * window.preview_m - window.edit_canvas.height * window.km / 2;
		checkKxKy();
		drawPreviewCanvas();
	};

	preview_canvas.onmouseup = function (e) {
		if (window.previewMove) {
			window.previewMove = false;
			drawEditCanvas();
		}
	};

	preview_canvas.onmouseover = function (e) {
		if (window.previewMove) {
			window.previewMove = false;
			drawEditCanvas();
		}
	};

	preview_canvas.onmousemove = function (e) {
		if (window.previewMove) {
			var evnt = ie_event(e);
			window.kx = window.kx + (evnt.clientX - x) * window.preview_m;
			window.ky = window.ky - (evnt.clientY - y) * window.preview_m;
			checkKxKy();
			x = evnt.clientX;
			y = evnt.clientY;
			drawPreviewCanvas();
		}
	};
}

function kMadd() {
	window.km = window.km * 1.5;
	if (window.km > window.edit_m)
		window.km = window.edit_m;
	checkKxKy();
	drawEditCanvas();
	drawPreviewCanvas();
	basketToHtml(window.basket);
}

function kMsub() {
	window.km = window.km / 1.5;
	if (window.km < 0.1)
		window.km = 0.1;
	checkKxKy();
	drawEditCanvas();
	drawPreviewCanvas();
	basketToHtml(window.basket);
}

function checkKxKy() {
	if (window.kx > window.max_x - window.edit_canvas.width * window.km)
		window.kx = window.max_x - window.edit_canvas.width * window.km;
	if (window.kx < 0)
		window.kx = 0;
	if (window.ky > window.max_y - window.edit_canvas.height * window.km)
		window.ky = window.max_y - window.edit_canvas.height * window.km;
	if (window.ky < 0)
		window.ky = 0;
}