function fCopy() {
	if (canRackShelfEdit != 'disabled' && window.shelf != null) {
		window.copyShelf = window.shelf;
		$('#butPaste').removeClass('disabled');
	} else {
	window.copyObjectList=[];
	for (var i in window.selectRackWaresList)
	{
		window.copyObjectList[i]=clone(window.selectRackWaresList[i]);
	}
	if (window.copyObjectList.length>0)
	{
		$('#butPaste').removeClass('disabled');
		}
	}
}