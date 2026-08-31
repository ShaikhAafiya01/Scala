<html>
<head>
    <title>Display Session</title>
</head>
<body>

<h2>Session Value</h2>

<%
    String user = (String)session.getAttribute("username");

    if(user != null)
    {
        out.println("User Name : " + user);
    }
    else
    {
        out.println("Session Not Found");
    }
%>

</body>
</html>