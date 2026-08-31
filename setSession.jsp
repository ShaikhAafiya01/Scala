<%
    String user = request.getParameter("username");

    session.setAttribute("username", user);
%>

<html>
<head>
    <title>Session Stored</title>
</head>
<body>

<h2>Session Created Successfully!</h2>

<a href="getSession.jsp">Display Session Value</a>

</body>
</html>