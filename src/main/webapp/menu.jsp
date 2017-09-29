<%@ page import="planograma.constant.SessionConst" %>
<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.image.ImageCleanCache" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Планограммы супермаркетов</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<link rel="stylesheet"href="css/planograma.css"/>
</head>
<body>
<table class="frame">
	<tr>
		<td class="path">
			<table width="100%">
				<tr>
					<td><i>Меню</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="logout()">Выход</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table class="frame">
				<tr>
					<td>
						<table class="menu">
							<tr>
								<td><a href="sectorList.jsp"><%=JspUtils.toMenuTitle("Залы торговых площадок")%></a></td>
							</tr>
							<tr>
								<td><a href="rackTemplateList.jsp"><%=JspUtils.toMenuTitle("Типовые стеллажи")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fCleanCache)" class="<%=JspUtils.actionAccess(session, SecurityConst.ACCESS_IMAGE_CLEAN_CACHE)%>"><%=JspUtils.toMenuTitle("Очистить кеш изображений")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="logout()"><%=JspUtils.toMenuTitle("Выход")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td width="100%" valign="top" align="left">
						<%--<font color="red">По техническим причинам форма растанновки товара может работать некорректно</font>--%>
						<%--<br/>--%>
						Рекомендуется при работе использовать полноэкранный режим (F11)
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	function fMenuReload()
	{
		window.location.reload();
	}
	function fCleanCache()
	{
		postJson('<%=ImageCleanCache.URL%>',
				null,
				function ()
					{alert('Кеш очищен')}
		)
	}
	<%
	if (session.getAttribute(SessionConst.SESSION_USER)==null)
	{
		out.write("oldPost={url:null, data:null, success:fMenuReload};");
		out.write("loginShow();\n");
	}
	%>
</script>
</html>