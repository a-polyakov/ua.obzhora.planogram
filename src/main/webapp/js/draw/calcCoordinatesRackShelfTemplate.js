/**
 * определить координаты каждого угла объекта
 * @param rackShelfTemplate полка шаблонного сталлажа
 */
function calcCoordinatesRackShelfTemplate(rackShelfTemplate)
{
	// поворот объекта
	rackShelfTemplate.cos = Math.cos(-rackShelfTemplate.angle*Math.PI/180);
	rackShelfTemplate.sin = Math.sin(-rackShelfTemplate.angle*Math.PI/180);
	// правый верхний угол
	var x = rackShelfTemplate.shelf_width / 2;
	var y = rackShelfTemplate.shelf_height / 2;
	// относительно сцены
	rackShelfTemplate.x1 = rackShelfTemplate.x_coord + x * rackShelfTemplate.cos - y * rackShelfTemplate.sin;
	rackShelfTemplate.y1 = rackShelfTemplate.y_coord + x * rackShelfTemplate.sin + y * rackShelfTemplate.cos;
	// правый нижний угол
	y = -y;
	// относительно сцены
	rackShelfTemplate.x2 = rackShelfTemplate.x_coord + x * rackShelfTemplate.cos - y * rackShelfTemplate.sin;
	rackShelfTemplate.y2 = rackShelfTemplate.y_coord + x * rackShelfTemplate.sin + y * rackShelfTemplate.cos;
	// левый нижний угол
	x = -x;
	// относительно сцены
	rackShelfTemplate.x3 = rackShelfTemplate.x_coord + x * rackShelfTemplate.cos - y * rackShelfTemplate.sin;
	rackShelfTemplate.y3 = rackShelfTemplate.y_coord + x * rackShelfTemplate.sin + y * rackShelfTemplate.cos;
	// левый верхний угол
	y = -y;
	// относительно сцены
	rackShelfTemplate.x4 = rackShelfTemplate.x_coord + x * rackShelfTemplate.cos - y * rackShelfTemplate.sin;
	rackShelfTemplate.y4 = rackShelfTemplate.y_coord + x * rackShelfTemplate.sin + y * rackShelfTemplate.cos;
}