<%@ page import="javax.servlet.http.Cookie" %>

<%
    String user = request.getParameter("username");

    Cookie c = new Cookie("username", user);
    c.setMaxAge(60 * 60); // Cookie valid for 1 hour
    response.addCookie(c);
%>

<html>
<head>
    <title>Cookie Stored</title>
</head>
<body>

<h2>Cookie Stored Successfully!</h2>

<a href="getCookie.jsp">Display Cookie Value</a>

</body>
</html>