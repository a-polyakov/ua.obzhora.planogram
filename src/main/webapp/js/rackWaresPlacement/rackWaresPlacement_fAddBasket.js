function fAddBasket() {
	// вернуть в корзину
	if (window.selectRackWaresList.length > 0) {
		for (var i in window.selectRackWaresList) {
			var selectRackWares = window.selectRackWaresList[i];
			console.log(selectRackWares);
			console.log(window.basket);
			if (window.basket[selectRackWares.code_wares] == null) {
				window.basket[selectRackWares.code_wares] = {
					code_group: '',
					code_wares: selectRackWares.code_wares,
					code_unit: selectRackWares.code_unit,
					code_image: selectRackWares.code_image,
					name_wares: selectRackWares.name_wares,
					abr_unit: selectRackWares.abr_unit,
					length: selectRackWares.wares_length,
					width: selectRackWares.wares_width,
					height: selectRackWares.wares_height,
					bar_code: selectRackWares.bar_code
				};
			}
		}
		basketToHtml(window.basket);
	}
}