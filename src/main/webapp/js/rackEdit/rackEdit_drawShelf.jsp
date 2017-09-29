<%@ page import="planograma.data.TypeShelf" %>
<%@ page contentType="application/javascript;charset=UTF-8" language="java" %>
// прорисовка элементов
// TODO
<%--<script type="text/javascript">--%>
	function drawShelf(shelf, canvas, context, kx, ky, m) {
		//TODO
		context.lineWidth = 1;
		if (window.shelf == shelf) {
			context.strokeStyle = "BLUE";
		}
		else {
			context.strokeStyle = "BLACK";
		}
		switch (shelf.type_shelf) {
				<%
				for (final TypeShelf typeShelf: TypeShelf.values())
				 {
					 out.print("case '");
					 out.print(typeShelf.name());
					 out.print("': context.fillStyle = '");
					 out.print(typeShelf.getColor());
					 out.println("'; break;");
					 }
	%>
		}
		context.beginPath();
		var x1 = (shelf.x1 - kx+window.offset_rack_x) / m;
		var y1 = canvas.height - (shelf.y1 - ky+ window.offset_rack_y) / m;
		var x2 = (shelf.x2 - kx+window.offset_rack_x) / m;
		var y2 = canvas.height - (shelf.y2 - ky+ window.offset_rack_y) / m;
		var x3 = (shelf.x3 - kx+window.offset_rack_x) / m;
		var y3 = canvas.height - (shelf.y3 - ky+ window.offset_rack_y) / m;
		var x4 = (shelf.x4 - kx+window.offset_rack_x) / m;
		var y4 = canvas.height - (shelf.y4 - ky+ window.offset_rack_y) / m;
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.lineTo(x3, y3);
		context.lineTo(x4, y4);
		context.closePath();
		context.stroke();
		context.fill();
	}
<%--</script>--%>