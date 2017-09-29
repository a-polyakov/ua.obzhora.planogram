<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.ETypeRack" %><%@ page import="planograma.data.EStateRack"%>
		<%@ page contentType="application/javascript;charset=UTF-8" language="java" %>
function drawRack(rack, rackState, rackStateInSector, view_mode, context, kx, ky, m) {

	var x1 = (rack.x1 - kx) / m;
	var y1 = (rack.y1 - ky) / m;
	var x2 = (rack.x2 - kx) / m;
	var y2 = (rack.y2 - ky) / m;
	var x3 = (rack.x3 - kx) / m;
	var y3 = (rack.y3 - ky) / m;
	var x4 = (rack.x4 - kx) / m;
	var y4 = (rack.y4 - ky) / m;

	if (window.showcase == rack) {
		context.strokeStyle = "BLUE";
	}
	else {
		context.strokeStyle = "BLACK";
	}

	switch (view_mode){
		case 'type_rack':
			switch (rack.type_rack) {
<%
				for (final ETypeRack typeRack : ETypeRack.values()) {
					out.print("case '");
					out.print(typeRack.name());
					out.print("': context.fillStyle = '");
					out.print(typeRack.getColor());
					out.println("'; break;");
				}
%>
			}
			break;
		case 'state_rack_in_sector':
			switch (rackStateInSector.state_rack) {
					<%
					for (final EStateRack item : EStateRack.values()) {
						out.print("case '");
						out.print(item.name());
						out.print("': context.fillStyle = '");
						out.print(item.getColor());
						out.println("'; break;");
					}
					%>
			}
			break;
		case 'state_rack':
			switch (rackState.state_rack) {
					<%
					for (final EStateRack item : EStateRack.values()) {
						out.print("case '");
						out.print(item.name());
						out.print("': context.fillStyle = '");
						out.print(item.getColor());
						out.println("'; break;");
					}
					%>
			}
			break;
	}


	context.beginPath();
	if (rack.type_rack == '<%=ETypeRack.R%>' && rack.load_side == '<%=LoadSide.U%>') {
		context.lineWidth = 4;
	}
	else {
		context.lineWidth = 1;
	}
	context.moveTo(x1, y1);
	context.lineTo(x2, y2);
	context.lineTo(x3, y3);
	context.lineTo(x4, y4);
	context.closePath();
	context.stroke();
	context.fill();

	if (rack.type_rack == '<%=ETypeRack.R%>' && rack.load_side == '<%=LoadSide.F%>') {
		context.beginPath();
		context.lineWidth = 4;
		context.moveTo(x1, y1);
		context.lineTo(x4, y4);
		context.stroke();
	}
}