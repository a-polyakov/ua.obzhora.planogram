/**
 * определить координаты каждого угла объекта
 * @param rack стеллаж
 */
function calcCoordinatesRack(rack)
{
	// поворот объекта
	rack.cos = Math.cos(rack.angle*Math.PI/180);
	rack.sin = Math.sin(rack.angle*Math.PI/180);
	// правый верхний угол
	var x = rack.length / 2;
	var y = rack.width / 2;
	var rx = Math.max(rack.x_offset + rack.real_length-rack.length,0);
	var lx = Math.max(-rack.x_offset,0);
	var ty = Math.max(rack.y_offset + rack.real_width-rack.width,0);
	var by = Math.max(-rack.y_offset,0);
	// относительно сцены
	rack.x1 = rack.x_coord + (x+rx) * rack.cos - (y+ty) * rack.sin;
	rack.y1 = rack.y_coord + (x+rx) * rack.sin + (y+ty) * rack.cos;
	// правый нижний угол
	// относительно сцены
	rack.x2 = rack.x_coord + (x+rx) * rack.cos + (y+by) * rack.sin;
	rack.y2 = rack.y_coord + (x+rx) * rack.sin - (y+by) * rack.cos;
	// левый нижний угол
	// относительно сцены
	rack.x3 = rack.x_coord - (x+lx) * rack.cos + (y+by) * rack.sin;
	rack.y3 = rack.y_coord - (x+lx) * rack.sin - (y+by) * rack.cos;
	// левый верхний угол
	// относительно сцены
	rack.x4 = rack.x_coord - (x+lx) * rack.cos - (y+ty) * rack.sin;
	rack.y4 = rack.y_coord - (x+lx) * rack.sin + (y+ty) * rack.cos;
}