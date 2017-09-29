var oldPost=null;

function postJson(url, data, success, disableElementId) {
	loadingShow(disableElementId);
	var dataJson=null;
	if (data != null)
		dataJson = $.toJSON(data);
	$.ajax(
		url, {
			type:'post',
			data:{data:dataJson},
			dataType:'json',
			success:success,
			error:function (errorData, textStatus) {
				switch (errorData.status) {
					case 401:
						loginShow();
						oldPost={url:url, data:data, success:success};
						break;
					case 403:
						alert('У вас нет прав на выполнение операции');
						break;
					case 500:
						alert('Ошибка: ' + errorData.responseText);
						break;
					default:
						alert('Ошибка соединения с сервером попробуйте позже');
						break;
				}
			},
			complete:function () {
				loadingHide();
			}
		});
}

function loginShow() {
	var loginTable = $('#login');
	if (loginTable[0] == null) {
		loginTable = $('<table id="login">\n' +
			'<tr>\n' +
			'<td align="center" valign="middle">\n' +
			'<form action="#" method="post" onsubmit="loginSend(); return false;">\n' +
			'<table>\n' +
			'<tr>\n' +
			'<td colspan="3">Войдите на сайт под своей учетной записью</td>\n' +
			'</tr>\n' +
			'<tr>\n' +
			'<td>Login</td>\n' +
			'<td><input name="login" type="text" tabindex="1"/></td>\n' +
			'<td rowspan="2"><input type="submit" value="Login" tabindex="3"/></td>\n' +
			'</tr>\n' +
			'<tr>\n' +
			'<td>Password</td>\n' +
			'<td><input name="password" type="password" tabindex="2"/></td>\n' +
			'</tr>\n' +
			'</table>\n' +
			'</form>\n' +
			'</td>\n' +
			'</tr>\n' +
			'</table>');
		$('body').append(loginTable);
	}
	loginTable.animate({opacity:'show'}, 500);
}

function loginSend() {
	var loginTable = $('#login');
	var login = loginTable.find('input[name=login]');
	var password = loginTable.find('input[name=password]');
	if (login.val() != '' && password.val() !='')
	{
		postJson('login',
			{login:login.val(), password:password.val()},
			function () {
				loginHide();
				if (oldPost!=null)
				{
					if (oldPost.url!=null)
					{
						postJson(oldPost.url, oldPost.data, oldPost.success);
					}
					else
					{
						oldPost.success();
					}
					oldPost=null;
				}
			});
	}
}

function loginHide() {
	$('#login').animate({opacity:'hide'}, 500);
}

function logout() {
	postJson('logout', null, loginShow)
}

var loadingIndex = 0;

function loadingShow(id) {
	var loading = $('#loading');
	if (loading[0] == null) {
		loading = $('<table id="loading">\n' +
			'<tr>\n' +
			'<td align="center" valign="middle">\n' +
			'Loading...\n' +
			'</td>\n' +
			'</tr>\n' +
			'</table>');
		$('body').append(loading);
	}
	var top =0;
	var left=0;
	var width = '100%';
	var height = '100%';
	if (id !== undefined || id != null)
	{
		var el=$('#'+id);
		if (el!=null)
		{
			top=el.offset().top;
			left=el.offset().left;
			width=el.width();
			height=el.height();
		}
	}
	loading.animate({opacity:'show', top:top, left:left,  width:width, height:height}, 100);
	loadingIndex++;
}

function loadingHide() {
	loadingIndex--;
	if (loadingIndex == 0)
		$('#loading').animate({opacity:'hide'}, 200);
}

function setCookie(name, value) {
	if (name) {
		document.cookie = name + '=' + encodeURIComponent(value);
	}
}

function getCookie(name) {
	var pattern = "(?:; )?" + name + "=([^;]*);?";
	var regexp = new RegExp(pattern);
	if (regexp.test(document.cookie)) {
		return decodeURIComponent(RegExp["$1"]);
	}
	else {
		return null;
	}
}

function clone(o){
	var no = {};
	for (var k in o) {
		no[k] = o[k];
	}
	return no;
}

/**
 * растояние от дочки (x,y) до прямой (заданой 2 точками x1,y1,x2,y2)
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @param x
 * @param y
 */
function distance(x1, y1, x2, y2, x, y) {
	return ((y1-y2)*x+(x2-x1)*y+(x1*y2- x2*y1))/(Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
}

/**
 * находит точку пересечения двух отрезков
 * @param x1
 * @param y1
 * @param x2
 * @param y2 первый отрезок
 * @param x3
 * @param y3
 * @param x4
 * @param y4 второй отрезок
 * TODO не проверено
 */
function intersectionTwoSegment(x1, y1, x2, y2, x3, y3, x4, y4) {
	var x21 = x2 - x1;
	var y21 = y2 - y1;
	var x43 = x4 - x3;
	var y43 = y4 - y3;
	// отрезки параллельны
	if (x21 * y43 == x43 * y21)
		return null;
	// точка пересечения линий
	var y = ((x2 * y1 - x1 * y2) * y43 + y21 * (x3 * y4 - x4 * y3)) / (x21 * y43 - x43 * y21);
	var x;
	if (y21 == 0) {
		x = (y * x43 + x3 * y4 - x4 * y3) / y43
	}
	else {
		x = (y * x21 + x1 * y2 - x2 * y1) / y21
	}
	// точка пересерения линий в пределах отрезков
	if (((x1<=x && x<=x2) || (x1>=x && x>=x2))&&
		((x3<=x && x<=x4) || (x3>=x && x>=x4))&&
		((y1<=y && y<=y2) || (y1>=y && y>=y2))&&
		((y3<=y && y<=y4) || (y3>=y && y>=y4)))
		return {x:x, y:y};
	else
		return null;
}
/**
 * пересекаются ли четырехугольники
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @param x3
 * @param y3
 * @param x4
 * @param y4 первый четырехугольник
 * @param x5
 * @param y5
 * @param x6
 * @param y6
 * @param x7
 * @param y7
 * @param x8
 * @param y8 второй четырехугольник
 * @return {Boolean}
 * TODO не проверено
 */
function intersectionQuadrangle(x1,y1, x2,y2, x3,y3, x4,y4,  x5,y5, x6,y6, x7,y7, x8,y8) {
	var result=intersectionTwoSegment(x1,y1, x2,y2, x5,y5, x6,y6)!=null ||
		intersectionTwoSegment(x1,y1, x2,y2, x6,y6, x7,y7)!=null ||
		intersectionTwoSegment(x1,y1, x2,y2, x7,y7, x8,y8)!=null ||
		intersectionTwoSegment(x1,y1, x2,y2, x5,y5, x8,y8)!=null ||
		intersectionTwoSegment(x2,y2, x3,y3, x5,y5, x6,y6)!=null ||
		intersectionTwoSegment(x2,y2, x3,y3, x6,y6, x7,y7)!=null ||
		intersectionTwoSegment(x2,y2, x3,y3, x7,y7, x8,y8)!=null ||
		intersectionTwoSegment(x2,y2, x3,y3, x5,y5, x8,y8)!=null ||
		intersectionTwoSegment(x3,y3, x4,y4, x5,y5, x6,y6)!=null ||
		intersectionTwoSegment(x3,y3, x4,y4, x6,y6, x7,y7)!=null ||
		intersectionTwoSegment(x3,y3, x4,y4, x7,y7, x8,y8)!=null ||
		intersectionTwoSegment(x3,y3, x4,y4, x5,y5, x8,y8)!=null ||
		intersectionTwoSegment(x1,y1, x4,y4, x5,y5, x6,y6)!=null ||
		intersectionTwoSegment(x1,y1, x4,y4, x6,y6, x7,y7)!=null ||
		intersectionTwoSegment(x1,y1, x4,y4, x7,y7, x8,y8)!=null ||
		intersectionTwoSegment(x1,y1, x4,y4, x5,y5, x8,y8)!=null;
	console.log(result);
	return result;

}

function aOnClick(anchor, action) {
	var anchor = $(anchor);
	var res=!anchor.hasClass('disabled');
	if (res) {
		if (action != null) {
			action();
		}
	}
	return res;
}

function numberFieldKeyDown(event, obj) {
	if (event.ctrlKey) {
		var value = Number(obj.value);
		if (value != null && !isNaN(x) && value!=Infinity) {
			switch (event.keyCode) {
				// up
				case 38:
					value = Math.ceil(value);
					value++;
					obj.value = value;
					$(obj).change();
					break;
				// down
				case 40:
					value = Math.ceil(value);
					value--;
					obj.value = value;
					$(obj).change();
					break;
			}
		}
	}
}

/**
 * определить координаты каждого угла объекта
 * @param shelf полка стеллажа
 */
function rackShelfCalcCoordinates(shelf)
{
	// поворот объекта
	shelf.cos = Math.cos(-shelf.angle*Math.PI/180);
	shelf.sin = Math.sin(-shelf.angle*Math.PI/180);
	// правый верхний угол
	var x = shelf.shelf_width / 2;
	var y = shelf.shelf_height / 2;
	// относительно сцены
	shelf.x1 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
	shelf.y1 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
	shelf.p1=new Point2D(shelf.x1, shelf.y1);
	// правый нижний угол
	y = -y;
	// относительно сцены
	shelf.x2 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
	shelf.y2 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
	shelf.p2=new Point2D(shelf.x2, shelf.y2);
	// левый нижний угол
	x = -x;
	// относительно сцены
	shelf.x3 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
	shelf.y3 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
	shelf.p3=new Point2D(shelf.x3, shelf.y3);
	// левый верхний угол
	y = -y;
	// относительно сцены
	shelf.x4 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
	shelf.y4 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
	shelf.p4=new Point2D(shelf.x4, shelf.y4);
}

function dateTimeToString(date) {
	if (!(date instanceof Date)) {
		date = new Date(date);
	}

	var f00 = function (i) {
		if (i < 10)
			i = '0' + i;
		return i;
	}
	return f00(date.getDate()) + '.' +
		f00(date.getMonth() + 1) + '.' +
		date.getFullYear() + ' ' +
		f00(date.getHours()) + ':' +
		f00(date.getMinutes()) + ':' +
		f00(date.getSeconds());
}

