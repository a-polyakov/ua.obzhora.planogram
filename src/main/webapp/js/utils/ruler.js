// линейка
//(0 не выбрана, 1 выбор первой точки, 2 выбор второй точки, 3 линейка задана)
window.ruler = {state:0, ax:0, ay:0, bx:0, by:0};

// выбор пункта меню
function fRuler() {
	window.ruler.state = 1;
	$('#rulerPanel').show();
	$('#ruler_dx').val(0);
	$('#ruler_dy').val(0);
	$('#ruler_l').val(0);
}

function setRuler() {
	if (window.ruler.state == 1) {
		$('#ruler_ax').val(window.ruler.ax);
		$('#ruler_ay').val(window.ruler.ay);
		$('#ruler_bx').val(window.ruler.bx);
		$('#ruler_by').val(window.ruler.by);
	}
	if (window.ruler.state >= 2) {
		$('#ruler_bx').val(window.ruler.bx);
		$('#ruler_by').val(window.ruler.by);
		var dx = Math.abs(window.ruler.bx - window.ruler.ax);
		$('#ruler_dx').val(dx);
		var dy = Math.abs(window.ruler.by - window.ruler.ay);
		$('#ruler_dy').val(dy);
		$('#ruler_l').val(Math.sqrt(dx * dx + dy * dy));
	}
}

function rulerMoveA(ax, ay) {
	window.ruler.ax = Math.round(ax);
	window.ruler.ay = Math.round(ay);
	window.ruler.bx = Math.round(ax);
	window.ruler.by = Math.round(ay);
	setRuler();
}

function rulerMoveB(bx, by) {
	window.ruler.bx = Math.round(bx);
	window.ruler.by = Math.round(by);
	setRuler();
}