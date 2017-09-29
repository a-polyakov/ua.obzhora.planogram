// прорисовка панели редактирования
function drawEditCanvas() {
	window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
	window.edit_context.lineWidth = 1;
	window.edit_context.strokeStyle = "BLACK";
	window.edit_context.strokeRect(
		(-window.kx + window.offset_rack_x) / window.km,
		window.edit_canvas.height + (window.ky - window.offset_rack_y) / window.km,
		window.rack.width / window.km,
		- window.rack.height / window.km);
	window.edit_context.strokeStyle = "GREEN";
	window.edit_context.strokeRect(
		(-window.kx + window.offset_real_rack_x) / window.km,
		window.edit_canvas.height + (window.ky - window.offset_real_rack_y) / window.km,
		window.rack.real_width / window.km,
		-window.rack.real_height / window.km);
	for (var i = 0; i < window.rackShelfList.length; i++) {
		drawShelf( window.rackShelfList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
	}
	for (var i = 0; i < window.rackWaresList.length; i++) {
		drawWares(window.rackWaresList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
	}
	if (window.flagPaste==2)
	{
		for (var i = 0; i < window.copyObjectList.length; i++) {
			drawWares(window.copyObjectList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
		}
	}
	else if (window.m_select==1)
	{
		// select
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "rgba(128, 128, 128, 1)";
		window.edit_context.strokeRect(
			0.5+(window.m_x_begin - window.kx+ window.offset_rack_x)/ window.km,
			0.5+window.edit_canvas.height - (window.m_y_begin - window.ky+window.offset_rack_y) / window.km,
			(window.m_x_end - window.m_x_begin) / window.km,
			(window.m_y_begin - window.m_y_end) / window.km);
		if (window.selectBasket!=null)
		{
			var m_x_begin2 = Math.round(Math.min(window.m_x_end, window.m_x_begin));
			var m_x_end2 = Math.round(Math.max(window.m_x_end, window.m_x_begin));
			var m_y_begin2 = Math.round(Math.min(window.m_y_end, window.m_y_begin));
			var m_y_end2 = Math.round(Math.max(window.m_y_end, window.m_y_begin));
			var m_x_d=window.selectBasket.width / window.km;
			var m_y_d=-window.selectBasket.height / window.km;
			var dx=window.selectBasket.width+window.d_wares_width;
			if (dx%2!=0)
				dx++;
			var dy=window.selectBasket.height+window.d_wares_height;
			if (dy%2!=0)
				dy++;
			for (var i=m_x_begin2; i<m_x_end2-dx; i=i+dx)
			{
				for (var j=m_y_begin2; j<m_y_end2-dy; j=j+dy)
				{
					if (window.selectBasket.code_image>0)
					{
						window.edit_context.drawImage(getImage(window.selectBasket.code_image),
							0.5+(i - window.kx+ window.offset_rack_x)/ window.km,
							0.5+window.edit_canvas.height - (j - window.ky+window.offset_rack_y) / window.km,
							m_x_d ,
							m_y_d );
					}
					window.edit_context.strokeRect(
						0.5+(i - window.kx+ window.offset_rack_x)/ window.km,
						0.5+window.edit_canvas.height - (j - window.ky+window.offset_rack_y) / window.km,
						m_x_d ,
						m_y_d );
				}
			}
		}
	}
	if (window.ruler.state>=2)
	{
		// рисуем линейку
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "BLUE";
		window.edit_context.beginPath();
		window.edit_context.moveTo((window.ruler.ax-window.kx + window.offset_rack_x) / window.km,
			window.edit_canvas.height + (-window.ruler.ay+window.ky - window.offset_rack_y) / window.km);
		window.edit_context.lineTo((window.ruler.bx-window.kx + window.offset_rack_x) / window.km,
			window.edit_canvas.height + (-window.ruler.by+window.ky - window.offset_rack_y) / window.km);
		window.edit_context.stroke();
	}
}
