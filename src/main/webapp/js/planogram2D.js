function Point2D(x, y) {
	this.x = x;
	this.y = y;
	this.insideSelectRectangle = function (sr) {
		return this.x >= sr.p1.x &&
			this.x <= sr.p2.x &&
			this.y >= sr.p1.y &&
			this.y <= sr.p2.y;
	}
	this.insidePolygon = function (polygon)
	{
		var flagMinus=true;
		var flagPlus=true;
		for (var i=0;(flagMinus||flagPlus) && i<polygon.pList.length-1;i++)
		{
			var d=new Segment2D(polygon.pList[i],polygon.pList[i+1]).distance(this);
			if (d<0)
				flagPlus=false;
			else if (d>0)
				flagMinus=false;
		}
		if (flagMinus||flagPlus)
		{
			var d=new Segment2D(polygon.pList[polygon.pList.length-1],polygon.pList[0]).distance(this);
			if (d<0)
				flagPlus=false;
			else if (d>0)
				flagMinus=false;
		}
		return (flagMinus||flagPlus);
	}
	this.distance = function(p)
	{
		return Math.sqrt((x- p.x)*(x- p.x)+(y- p.y)*(y- p.y));
	}
}
function Segment2D(p1, p2) {
	this.p1 = p1;
	this.p2 = p2;
	/**
	 * растояние от линии заданой отрезком до точки
	 * @param p точка
	 * @return {Number} растояние
	 */
	this.distance = function (p) {
		var x21 = p2.x - p1.x;
		var y21 = p2.y - p1.y;
		return (x21 * p.y - y21 * p.x + (p1.x * p2.y - p2.x * p1.y)) / (Math.sqrt(x21 * x21 + y21 * y21));
	}
	/**
	 * находжение точки пересечения двух отрезков
	 * @param l отрезок
	 * @return {x,y}
	 */
	this.intersectsSegment=function (l){
		var x21 = this.p2.x - this.p1.x;
		var y21 = this.p2.y - this.p1.y;
		var x43 = l.p2.x - l.p1.x;
		var y43 = l.p2.y - l.p1.y;
		// отрезки параллельны
		if (x21 * y43 == x43 * y21)
			return null;
		// точка пересечения линий
		var y = ((this.p2.x * this.p1.y - this.p1.x * this.p2.y) * y43 + y21 * (l.p1.x * l.p2.y - l.p2.x * l.p1.y)) / (x21 * y43 - x43 * y21);
		var x;
		if (y21 == 0) {
			x = (y * x43 + l.p1.x * l.p2.y - l.p2.x * l.p1.y) / y43
		}
		else {
			x = (y * x21 + this.p1.x * this.p2.y - this.p2.x * this.p1.y) / y21
		}
		// точка пересерения линий в пределах отрезков
		if (((this.p1.x<=x*1.0001 && x*0.9999<=this.p2.x) || (this.p1.x>=x*0.9999 && x*1.0001>=this.p2.x))&&
			((l.p1.x<=x*1.0001 && x*0.9999<=l.p2.x) || (l.p1.x>=x*0.9999 && x*1.0001>=l.p2.x))&&
			((this.p1.y<=y*1.0001 && y*0.9999<=this.p2.y) || (this.p1.y>=y*0.9999 && y*1.0001>=this.p2.y))&&
			((l.p1.y<=y*1.0001 && y*0.9999<=l.p2.y) || (l.p1.y>=y*0.9999 && y*1.0001>=l.p2.y)))
			return {x:x, y:y};
		else
			return null;
	}
	/**
	 * отрезок пересекает прямоугольную область выделения
	 * @param sr
	 * @return {Boolean}
	 */
	this.intersectsSelectRectangle = function (sr) {
		var srp3=new Point2D(sr.p1.x, sr.p2.y);
		var srp4=new Point2D(sr.p2.x, sr.p1.y);
		return this.intersectsSegment(new Segment2D(sr.p1, srp3))!=null ||
			this.intersectsSegment(new Segment2D(srp3, sr.p2))!=null ||
			this.intersectsSegment(new Segment2D(sr.p2, srp4))!=null ||
			this.intersectsSegment(new Segment2D(srp4, sr.p1))!=null;
	}
	this.intersectsPolygon = function (polygon)
	{
		var result=false;
		for (var i=0;!result && i<polygon.pList.length-1;i++)
		{
			result=this.intersectsSegment(new Segment2D(polygon.pList[i],polygon.pList[i+1]))!=null;
		}
		if (!result)
		{
			result=this.intersectsSegment(new Segment2D(polygon.pList[polygon.pList.length-1],polygon.pList[0]))!=null;
		}
		return result;
	}
}
function Triangle2D(p1, p2, p3) {
	this.p1 = p1;
	this.p2 = p2;
	this.p3 = p3;
}

/**
 * прямоугольник стороны которого паралельны осям системы координат
 * @param p1
 * @param p2
 * @constructor
 */
function SelectRectangle2D(p1, p2) {
	this.p1 = p1;
	this.p2 = p2;

	this.intersectsSelectRectangle=function (sr)
	{
		var result=
			((sr.p1.x>this.p1.x && sr.p1.x<this.p2.x) || (sr.p2.x>this.p1.x && sr.p2.x<this.p2.x)||
				(this.p1.x>sr.p1.x && this.p1.x<sr.p2.x) || (this.p2.x>sr.p1.x && this.p2.x<sr.p2.x)) &&
			((sr.p1.y>this.p1.y && sr.p1.y<this.p2.y) || (sr.p2.y>this.p1.y && sr.p2.y<this.p2.y) ||
				(this.p1.y>sr.p1.y && this.p1.y<sr.p2.y) || (this.p2.y>sr.p1.y && this.p2.y<sr.p2.y))
		return result;
	}

}
/**
 * многоугольник
 * @param p... набор точек
 * @constructor
 */
function Polygon2D() {
	this.pList = arguments;

	this.getDescribedRectangle=function ()
	{
		var minX=this.pList[0].x;
		var maxX=this.pList[0].x;
		var minY=this.pList[0].y;
		var maxY=this.pList[0].y;
		for (var i=1;i<this.pList.length;i++)
		{
			minX=Math.min(minX,this.pList[i].x);
			maxX=Math.max(maxX,this.pList[i].x);
			minY=Math.min(minY,this.pList[i].y);
			maxY=Math.max(maxY,this.pList[i].y);
		}
		return new SelectRectangle2D(new Point2D(minX, minY), new Point2D(maxX, maxY));
	}
	this.insideSelectRectangle = function (sr) {
		var result = true;
		for (var i = 0; result && i < this.pList.length; i++) {
			var p = this.pList[i];
			result = p.insideSelectRectangle(sr);
		}
		return result;
	}
	this.intersectsPolygon=function(polygon)
	{
		var result=false;
		var srA=this.getDescribedRectangle();
		var srB=polygon.getDescribedRectangle();
		if (srA.intersectsSelectRectangle(srB))
		{
			for (var i=0;!result && i<this.pList.length;i++)
			{
				result=this.pList[i].insidePolygon(polygon);
			}
			for (var i=0;!result && i<polygon.pList.length;i++)
			{
				result=polygon.pList[i].insidePolygon(this);
			}
			for (var i=0;!result && i<this.pList.length-1;i++)
			{
				var segment=new Segment2D(this.pList[i], this.pList[i+1]);
				result=segment.intersectsPolygon(polygon);
			}
			if (!result)
			{
				var segment=new Segment2D(this.pList[this.pList.length-1], this.pList[0]);
				result=segment.intersectsPolygon(polygon);
			}
		}
		return result;
	}
}

function drawQuadrangle(map, width, height, p1, p2, p3, p4) {
	var intersects = false;
	var t;
	// сортировка вершин
	if (p4.y >= p3.y && p4.y >= p2.y && p4.y > p1.y) {
		t = p1;
		p1 = p4;
		p4 = p3;
		p3 = p2
		p2 = t;
	}
	else if (p3.y >= p4.y && p3.y >= p2.y && p3.y > p1.y) {
		t = p1;
		p1 = p3;
		p3 = t;
		t = p2;
		p2 = p4
		p4 = t;
	}
	else if (p2.y >= p4.y && p2.y >= p3.y && p2.y > p1.y) {
		t = p1;
		p1 = p2;
		p2 = p3;
		p3 = p4
		p4 = t;
	}
	if (p2.y < p4.y) {
		t = p2;
		p2 = p4;
		p4 = t;
	}
	var sy;
	var x1, x2;
	// нижний треугольник
	for (sy = Math.max(0, p3.y); sy < p2.y && sy < p4.y && sy < height; sy++) {
		x1 = p3.x + (sy - p3.y) * (p2.x - p3.x) / (p2.y - p3.y);
		x2 = p3.x + (sy - p3.y) * (p4.x - p3.x) / (p4.y - p3.y);
		intersects = drawHorizontalLine(map, width, sy, x1, x2) || intersects;
	}
	// средний четырехугольник
	for (sy = Math.max(0, Math.min(p2.y, p4.y)); (sy < p2.y || sy < p4.y) && sy < height; sy++) {
		if (p1.y == p4.y)
			x1 = p4.x;
		else
			x1 = p4.x + (sy - p4.y) * (p1.x - p4.x) / (p1.y - p4.y);
		if (p3.y == p2.y)
			x2 = p2.x;
		else
			x2 = p2.x + (sy - p2.y) * (p3.x - p2.x) / (p3.y - p2.y);
		intersects = drawHorizontalLine(map, width, sy, x1, x2) || intersects;
	}
	// верхний треугольник
	for (sy = Math.max(0, p2.y, p4.y); sy <= p1.y && sy < height; sy++) {
		if (p4.y == p1.y) {
			x1 = p4.x;
		}
		else {
			x1 = p1.x + (sy - p1.y) * (p4.x - p1.x) / (p4.y - p1.y);
		}
		if (p2.y == p1.y) {
			x2 = p2.x;
		}
		else {
			x2 = p1.x + (sy - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
		}
		intersects = drawHorizontalLine(map, width, sy, x1, x2) || intersects;
	}
	return intersects;
}
function drawHorizontalLine(map, width, sy, x1, x2) {
	var intersects = false;
	if (x1 > x2) {
		var t = x1;
		x1 = x2;
		x2 = t;
	}
	sy=Math.round(sy);
	x1=Math.round(x1);
	x2=Math.round(x2);
	for (var x = Math.max(0, x1); x < x2 && x < width; x++) {
		var i = sy * width + x;
		if (map[i] == 1) {
			intersects = true;
		}
		else {
			map[i] = 1;
		}
	}
	return intersects;
}

function drawTestMap(canvas, context, width, height, map) {
	canvas.width = width;
	canvas.height = height;
	var image1 = context.getImageData(0, 0, width, height);
	var imageData1 = image1.data;
	for (var y = 0; y < height; y++) {
		var i = y * width;
		for (var x = 0; x < width; x++) {
			if (map[i + x] == 1) {
				var j = 4 * (i + x);
				imageData1[j] = 255;
				imageData1[j + 3] = 255; // alpha
			}
		}
	}
	context.putImageData(image1, 0, 0);
}