function fCut() {
	if (canRackShelfEdit != 'disabled' && window.shelf != null) {
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (window.rackShelfList[i] == window.shelf) {
				window.rackShelfList.splice(i, 1);
				i = window.rackShelfList.length;
			}
		}
		window.copyShelf = window.shelf;
		window.shelf = null;
	} else {
	window.copyObjectList=[];
	for (var i in window.selectRackWaresList)
	{
		window.copyObjectList[i]=clone(window.selectRackWaresList[i]);
		var selectRackWares=window.selectRackWaresList[i];
		for (var j in window.rackWaresList)
		{
			if (selectRackWares==window.rackWaresList[j])
			{
				window.rackWaresList.splice(j,1);
			}
		}
	}
	window.selectRackWaresList=[];
	}
	fRackWaresPlacementSelect();
	drawEditCanvas();
	drawPreviewCanvas();
}
