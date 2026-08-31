<%@ page import="javax.servlet.http.Cookie" %>

<html>
<head>
    <title>All Cookies</title>
</head>
<body>

<h2>Cookies Stored on Client</h2>

<table border="1">
<tr>
    <th>Cookie Name</th>
    <th>Cookie Value</th>
</tr>

<%
Cookie cookies[] = request.getCookies();

if(cookies != null)
{
    for(Cookie c : cookies)
    {
%>

<tr>
    <td><%= c.getName() %></td>
    <td><%= c.getValue() %></td>
</tr>

<%
    }
}
else
{
%>

<tr>
    <td colspan="2">No Cookies Found</td>
</tr>

<%
}
%>

</table>

</body>
</html>