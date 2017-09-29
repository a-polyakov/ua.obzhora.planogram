<%@ page import="planograma.servlet.wares.WaresList" %>
<%@ page import="planograma.servlet.wares.WaresListSearch" %>
<%@ page import="planograma.constant.data.WaresConst" %>
<%@ page import="planograma.constant.data.AdditionUnitConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
	#treeview {
		padding: 0;
		clear: both;
		font-family: monospace;
	}
	#treeview ul
	{
		margin: 0; padding: 0 0 1.5em 0;
		list-style-type: none;
	}
	#treeview ul ul {
		/*width: auto; */
		margin: 0; padding: 0 0 0 0.75em; }
		/* класс для ul после которых нет li в родительских ветках */
	#treeview ul.l {
		border-left: 1px solid;
		margin-left: -1px;
		border-color: white;
	}
	#treeview li.cl ul { display: none; }
	#treeview li { margin: 0; padding: 0; }
	#treeview li li { margin: 0 0 0 0.5em; border-left: 1px dotted;  padding: 0; }
	#treeview li div { position: relative;
		/*height: 1.5em; */
		min-height: 16px; //height: 1.3em; }
	#treeview li li div { border-bottom: 1px dotted; }
	#treeview li p
	{
		position: absolute; z-index: 1; top: 0.7em; //top: 0.65em; left: 1.75em;
		width: 100%;
		margin: 0;
		padding: 0;
	}
	#treeview li p.selectNode{
		color: white;
		background: #d3d3d3;
	}
	#treeview a { padding: 0.1em 0.2em; white-space: nowrap; //height: 1px; text-decoration: none;}
	#treeview a.sc
	{
		color: black;
		position: absolute; top: 0.06em;
		margin-left: -1em;
		text-decoration: none;
	}

	/* colors */
	#treeview li p,
	#treeview .sc
	{ background: white; }
	#treeview ul li li,
	#treeview ul li li div
	{ border-color: #999999; }
	#treeview a
	{ color: #5555bb; }
	#treeview a:hover
	{
		color: white;
		background: #808080;
	}
</style>

<table id="choiceWares">
    <colgroup>
        <col width="35%"/>
        <col width="65%"/>
    </colgroup>
    <tbody>
	<tr>
		<td colspan="2" class="path">
			<table style="width: 100%">
                <tbody>
				<tr>
					<td><i>Выбор товара</i></td>
					<td width="23px"><a href="#" onclick="choiceWaresCancel()">&#x2715;</a></td>
				</tr>
                </tbody>
			</table>
		</td>
	</tr>
	<tr>
        <td rowspan="2">
            <div id="treeview" style="overflow-y: scroll; height: 100%;">
                <ul id="tree">
                </ul>
            </div>
        </td>
        <td height="24" style="height: 32px; min-width: 150px;">
            <table>
                <tr>
                    <td>
                        <input type="checkbox" id="selectAll" title="Выделить всё"
                               onchange="if (this.checked) {$('input:checkbox[name=code_wares]').attr('checked','checked')}else{$('input:checkbox[name=code_wares]').removeAttr('checked')}">
                    </td>
                    <td>
                        <input type="text" id="searchText"/>
                    </td>
                    <td>
                        <select id="searchBy">
                            <option value="<%=WaresConst.CODE_WARES%>">Код</option>
                            <option value="<%=WaresConst.NAME_WARES%>">Название</option>
                            <option value="<%=AdditionUnitConst.BAR_CODE%>">Штрих код</option>
                        </select>
                    </td>
                    <td>
                        <a href="#" title="Поиск" onclick="search1()"><img src="img/icon/iconSearch.png" alt="Поиск"/></a>
                        <a href="#" title="Поиск по всем группам" onclick="search2()"><img src="img/icon/iconSearch2.png" alt="Поиск по иерархии"/></a>
                    </td>
                    <td>
                        &nbsp;&nbsp;&nbsp;
                    </td>
                    <td>
                        <label for="forShop" title="с учетом асортиментной матрицы">
                            с учетом АМ<input id="forShop" type="checkbox">
                        </label>
                    </td>
                </tr>
            </table>
        </td>
	</tr>
    <tr>
        <td style="height: 100%;">
            <div style="overflow-y: scroll; height: 100%;">
            <table id="list_wares" width="100%">
                <tbody>

                </tbody>
            </table>
            </div>
        </td>
    </tr>
	<tr>
		<td colspan="2" class="path">
			<table style="width: 100%">
				<tr>
					<td width="100%"></td>
					<td><a href="#" onclick="choiceWaresCancel()">Отмена</a></td>
					<td>&nbsp;</td>
					<td><a href="#" onclick="choiceWaresOk()">Ок</a></td>
				</tr>
			</table>
		</td>
	</tr>
    </tbody>
</table>

<script type="text/javascript">

	window.choiceWaresList={};

	function treeToHtml(tree) {
		var nodeHtml = ''
		for (var i = 0; i < tree.length; i++) {
			var node = tree[i];
			nodeHtml += '<li><div><p id="'+node.code_group_wares+'">';
			if (node.children.length > 0) {
				nodeHtml += '<a href="#" class="sc" onclick="return UnHide(this)">&#9660;</a>';
			}
			nodeHtml += '<a href="#" onclick="selectWaresGroup(this)">' + node.name + '</a>';
			nodeHtml += '</p></div>'
			if (node.children.length > 0) {
				nodeHtml += '<ul';
				if(i==tree.length-1)
					nodeHtml += ' class="l"';
				nodeHtml += '>';
				nodeHtml += treeToHtml(node.children)
				nodeHtml += '</ul>';
			}
			nodeHtml += '</li>';
		}
		return nodeHtml;
	}

	function listWaresToHtml(list_wares) {
//		code_group = resultSet.getInt(WaresConst.CODE_GROUP);
//		code_unit = resultSet.getInt(AdditionUnitConst.CODE_UNIT);
		var table_list_wares = $('#list_wares');
		table_list_wares.find('input[name=code_wares]:not(:checked)').each(function () {
			var tr = $(this).closest('tr');
			tr.remove();
		});
		var i = 0
		for (; i < list_wares.length; i++) {
			var wares = list_wares[i];
			window.choiceWaresList[wares.code_wares]=wares;
			if (table_list_wares.find('input[value=' + wares.code_wares + ']').length == 0) {
				var rowHtml = '<tr>';
				rowHtml += '<td><input type="checkbox" name="code_wares" value="' + wares.code_wares + '"></td>';
				rowHtml += '<td style="min-width: 100px" align="center">';
				var k=100/Math.max(wares.width, wares.height)
				if (wares.code_image != 0) {
					rowHtml += '<img width="'+(k*wares.width)+'" height="'+(k*wares.height)+'" src="image/' + wares.code_image + '" >';
				}
				rowHtml += '</td>';
				rowHtml += '<td>&nbsp;</td>';
				rowHtml += '<td width="100%">';
				rowHtml += wares.code_wares + '<br>';
				rowHtml += wares.name_wares + '<br>';
				rowHtml += wares.abr_unit + '<br>';
				rowHtml += wares.bar_code + '<br>';
				rowHtml += wares.width + 'x' + wares.height + 'x' + wares.length + '<br>';
				rowHtml += '</td>';
				rowHtml += '</tr>';
				table_list_wares.append(rowHtml)
			}
		}
		i = 0;
		table_list_wares.find('tr').each(function () {
			$(this).attr('bgcolor', (i % 2 == 0) ? '#d3d3d3' : '#FFFFF');
			i++;
		})

	}

	function selectWaresGroup(obj)
	{
		$('#treeview p.selectNode').removeClass('selectNode');
		var p=$(obj).parent();
		p.addClass('selectNode');
		var code_group_wares= p.attr('id');

		var data = {code_group_wares:code_group_wares};
		if ($('#forShop').prop("checked")) {
			var code_shop = getCookie('code_shop');
			data.code_shop = code_shop;
		}
		postJson('<%=WaresList.URL%>', data, function(data){
			listWaresToHtml(data.waresList);
		})
	}

	function search1() {
		var searchText = $('#searchText').val();
		if (searchText != null && searchText.length > 0) {
			var searchBy = $('#searchBy').val();
			var code_group_wares = $('#treeview p.selectNode').attr('id')
			var data = {searchText:searchText, searchBy:searchBy, code_group_wares:code_group_wares};
			if ($('#forShop').prop("checked")) {
				var code_shop = getCookie('code_shop');
				data.code_shop = code_shop;
			}
			postJson('<%=WaresListSearch.URL%>', data,
					function (data) {
						listWaresToHtml(data.waresList);
					});
		}
	}
	function search2() {
		var searchText = $('#searchText').val();
		if (searchText != null && searchText.length > 0) {
			var searchBy = $('#searchBy').val();
			var data = {searchText:searchText, searchBy:searchBy};
			if ($('#forShop').prop("checked")) {
				var code_shop = getCookie('code_shop');
				data.code_shop = code_shop;
			}
			postJson('<%=WaresListSearch.URL%>', {searchText:searchText, searchBy:searchBy},
					function (data) {
						listWaresToHtml(data.waresList);
					});
		}
	}
</script>

<script type="text/javascript">
	function choiceWaresCancel()
	{
		$('#choiceWares').hide();
	}
	function choiceWaresOk()
	{
		var table_list_wares = $('#list_wares');
		table_list_wares.find('input[name=code_wares]:checked').each(function () {
			window.basket[this.value]=window.choiceWaresList[this.value];
		});
		var choiceWares = $('#choiceWares');
		choiceWares.animate({opacity:'hide'}, 500);
		basketToHtml(window.basket);
	}
	function UnHide( eThis ){
		if( eThis.innerHTML.charCodeAt(0) == 9658 ){
			eThis.innerHTML = '&#9660;'
			eThis.parentNode.parentNode.parentNode.className = '';
		}else{
			eThis.innerHTML = '&#9658;'
			eThis.parentNode.parentNode.parentNode.className = 'cl';
		}
		return false;
	}
</script>