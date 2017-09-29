function fDel() {
	if (canRackShelfEdit != 'disabled' && window.shelf != null) {
		// удаление полки
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (window.rackShelfList[i] == window.shelf) {
				window.rackShelfList.splice(i, 1);
				i = window.rackShelfList.length;
			}
		}
		window.shelf = null;
	} else {
		// удаление товаров
		for (var i in window.selectRackWaresList) {
			var selectRackWares = window.selectRackWaresList[i];
			for (var j in window.rackWaresList) {
				if (selectRackWares == window.rackWaresList[j]) {
					window.rackWaresList.splice(j, 1);
				}
			}
		}
		window.selectRackWaresList = [];
	}
	fRackWaresPlacementSelect();
	drawEditCanvas();
	drawPreviewCanvas();
}