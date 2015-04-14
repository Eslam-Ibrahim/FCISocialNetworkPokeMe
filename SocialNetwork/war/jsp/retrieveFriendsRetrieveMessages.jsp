<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Retrieve Messages</title>
</head>
<style>
table, td, th {
    border: 1px solid gray;
}

th {
    background-color: blue;
    color: white;
}
h1
{
text-align:center;
font-family:courier;
color: #cf9118;
}
</style>
<body>


<h1> Messages</h1>
<br>
<table>
  <tr>
    <th> Friends List</th>
    <th> Chat</th>
  </tr>
  
    <c:forEach items = "${it.mails}" var="sender">
    <tr>
    <td> 
     <c:out value = "${sender.email}"></c:out>
     </td>
  <td>
    <c:forEach items = "${it.mail}" var="user">
<form action="/social/ResponseRetriveMessageHistory" method="post"> 
<input type="hidden" name="senderMail" value = "${sender.email}">
<input type="hidden" name="receiverMail" value ="${user.email}">
  <input type="submit" value="Chat Now !">
  </form>
</c:forEach>
   </td>
  </tr>
    </c:forEach>
    
  
</table>






<br>

<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get"> 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>
</body>
</html>