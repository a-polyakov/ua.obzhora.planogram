function roundShelf(shelf)
{
	shelf.x_coord=Math.round(shelf.x_coord);
	shelf.y_coord=Math.round(shelf.y_coord);
	shelf.shelf_width=Math.round(shelf.shelf_width);
	shelf.shelf_height=Math.round(shelf.shelf_height);
	shelf.shelf_length=Math.round(shelf.shelf_length);
	selectShelf(shelf);
	rackShelfCalcCoordinates(shelf);
	drawEditCanvas();
	drawPreviewCanvas();
}