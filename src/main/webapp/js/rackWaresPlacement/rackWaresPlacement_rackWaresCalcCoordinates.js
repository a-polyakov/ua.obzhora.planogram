/**
 * определить координаты каждого угла объекта
 * @param rackWares полка стеллажа
 */
function rackWaresCalcCoordinates(rackWares)
{
	// поворот объекта
	// TODO angle
//		rackWares.cos = Math.cos(-rackWares.angle*Math.PI/180);
//		rackWares.sin = Math.sin(-rackWares.angle*Math.PI/180);
	rackWares.cos = 1;
	rackWares.sin = 0;
	// правый верхний угол
	var x = rackWares.wares_width / 2;
	var y = rackWares.wares_height / 2;
	// относительно сцены
	rackWares.p1 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
		rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
	// правый нижний угол
	y = -y;
	// относительно сцены
	rackWares.p2 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
		rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
	// левый нижний угол
	x = -x;
	// относительно сцены
	rackWares.p3 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
		rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
	// левый верхний угол
	y = -y;
	// относительно сцены
	rackWares.p4 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
		rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
}
