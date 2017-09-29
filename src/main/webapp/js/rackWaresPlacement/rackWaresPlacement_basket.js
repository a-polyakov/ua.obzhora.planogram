// обработка событий корзины

function basketWaresSelect(image)
{
	var image=$(image);
	if (image.attr('type')=='image')
	{
	if (image.hasClass('basketSelect'))
	{
		image.removeClass('basketSelect');
		image.addClass('basket');
		window.selectBasket=null;
		$('input[type=radio]:checked').removeAttr('checked');
	}
	else
	{
		$('input.basketSelect').removeClass('basketSelect').addClass('basket');
		image.removeClass('basket');
		image.addClass('basketSelect');
		var code_wares=image.attr('id');
		$('input[type=radio][value='+code_wares+']').attr('checked','checked');
		window.selectBasket=window.basket[code_wares];
	}
	}else
	{
		$('input.basketSelect').removeClass('basketSelect').addClass('basket');
		var code_wares= image.val();
		$('#'+code_wares).removeClass('basket').addClass('basketSelect');
		window.selectBasket=window.basket[code_wares];
	}
}

function basketWaresDelete(code_wares)
{
	delete window.basket[code_wares];
	basketToHtml(window.basket);
}