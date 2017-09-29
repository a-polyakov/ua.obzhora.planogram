<%@ page import="planograma.data.TypeShelf" %>
<%@ page contentType="application/javascript;charset=UTF-8" language="java" %>
<%--<script type="text/javascript">--%>
	function drawRackShelfTemplate(shelfTemplate, canvas, context, kx, ky, m) {
		context.lineWidth = 1;
		if (window.shelf == shelfTemplate) {
			context.strokeStyle = "BLUE";
		}
		else {
			context.strokeStyle = "BLACK";
		}
		switch (shelfTemplate.type_shelf) {
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
		var x1 = (shelfTemplate.x1 - kx) / m;
		var y1 = canvas.height - (shelfTemplate.y1 - ky) / m;
		var x2 = (shelfTemplate.x2 - kx) / m;
		var y2 = canvas.height - (shelfTemplate.y2 - ky) / m;
		var x3 = (shelfTemplate.x3 - kx) / m;
		var y3 = canvas.height - (shelfTemplate.y3 - ky) / m;
		var x4 = (shelfTemplate.x4 - kx) / m;
		var y4 = canvas.height - (shelfTemplate.y4 - ky) / m;
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.lineTo(x3, y3);
		context.lineTo(x4, y4);
		context.closePath();
		context.stroke();
		context.fill();
	}
<%--</script>--%>