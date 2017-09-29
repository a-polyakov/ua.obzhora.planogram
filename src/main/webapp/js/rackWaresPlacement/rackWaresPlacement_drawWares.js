// прорисовка панели навигации
function drawWares(rackWares, canvas, context, kx, ky, m) {
	var movx = (rackWares.position_x - kx+ window.offset_rack_y) / m;
	var movy = canvas.height - (rackWares.position_y - ky+ window.offset_rack_y) / m;
	context.translate(movx, movy);
	// TODO angle
//			context.rotate(45*Math.PI/180);
	var x1 = -rackWares.wares_width / m / 2;
	var y1 = -rackWares.wares_height / m / 2;
	var w1 = rackWares.wares_width / m;
	var h1 = rackWares.wares_height / m;
	if (rackWares.code_image > 0) {
		context.drawImage(getImage(rackWares.code_image), x1, y1, w1, h1);
	}
	else
	{
		// написать на товарах артикул (если нет изображения)
		context.fillStyle = "BLACK";
		context.font = Math.min(2 * rackWares.wares_width/(rackWares.code_wares+'').length, rackWares.wares_height)/m+"px sans-serif";
		context.textBaseline = "middle";
		context.fillText(rackWares.code_wares, x1, 0, rackWares.wares_width / m);
	}
	context.lineWidth = 1;
	if ($.inArray(rackWares, window.selectRackWaresList) >= 0) {
		context.strokeStyle = "BLUE";
	}
	else {
		context.strokeStyle = "BLACK";
	}
	context.strokeRect(x1, y1, w1, h1);
//			context.rotate(-45*Math.PI/180);
	context.translate(-movx, -movy);
}
