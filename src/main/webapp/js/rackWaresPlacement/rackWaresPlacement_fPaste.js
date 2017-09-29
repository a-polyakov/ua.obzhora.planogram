function fPaste() {
	if (canRackShelfEdit!='disabled' && window.copyShelf != null) {
		window.shelf = clone(window.copyShelf);
		window.shelf.code_rack = '';
		// копия в право
		window.shelf.x_coord = window.copyShelf.x_coord + window.copyShelf.shelf_width;
		rackShelfCalcCoordinates(window.shelf);
		if (shelfBeyondRack(window.shelf)) {
			// в право нельзя копия в низ
			window.shelf.x_coord = window.copyShelf.x_coord;
			window.shelf.y_coord = window.copyShelf.y_coord - window.copyShelf.shelf_height;
			rackShelfCalcCoordinates(window.shelf);
			if (shelfBeyondRack(window.shelf)) {
				// в низ нельзя копия в лево
				window.shelf.x_coord = window.copyShelf.x_coord - window.copyShelf.shelf_width;
				window.shelf.y_coord = window.copyShelf.y_coord;
				rackShelfCalcCoordinates(window.shelf);
				if (shelfBeyondRack(window.shelf)) {
					// в лево нельзя копия в верх
					window.shelf.x_coord = window.copyShelf.x_coord;
					window.shelf.y_coord = window.copyShelf.y_coord + window.copyShelf.shelf_height;
					rackShelfCalcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf)) {
						// в верх нельзя копия на месте
						window.shelf.x_coord = window.copyShelf.x_coord;
						window.shelf.y_coord = window.copyShelf.y_coord;
						rackShelfCalcCoordinates(window.shelf);
					}
				}
			}
		}
		window.rackShelfList.push(window.shelf);
		window.copyShelf = window.shelf;
		fRackWaresPlacementSelect();
		drawEditCanvas();
		drawPreviewCanvas();
	}
	else if (window.copyObjectList.length > 0) {
		window.flagPaste = 1;
	}
}