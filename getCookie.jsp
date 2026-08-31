<%@ page import="javax.servlet.http.Cookie" %>

<html>
<head>
    <title>Display Cookie</title>
</head>
<body>

<h2>Cookie Value</h2>

<%
    Cookie cookies[] = request.getCookies();

    if(cookies != null)
    {
        for(Cookie c : cookies)
        {
            if(c.getName().equals("username"))
            {
                out.println("User Name : " + c.getValue());
            }
        }
    }
    else
    {
        out.println("No Cookie Found");
    }
%>

</body>
</html>
