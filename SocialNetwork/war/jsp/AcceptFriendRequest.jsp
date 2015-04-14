<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Accept Friend Request Page</title>
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

<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "You Are Now Friends";
  document.forms[formTag].submit();
}

</script>

 
    
<h1>Friend Requests</h1>
<br> 

<table>
  <tr>
    <th> Friend Requests List</th>
  </tr>
  
    <c:forEach items = "${it.requests}" var="sender">
    <tr>
    <td> 
     <c:out value = "${sender.email}"></c:out>
    <c:forEach items = "${it.mail}" var="user">
<form action="/social/ResponseAcceptFriendRequest" method="post">
<input type="hidden" name="friendEmail" value = "${sender.email}"> 
<input type="hidden" name="myEmail" value ="${user.email}">
  <input type="submit" value="Accept Friend Request" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>
  </form>
</c:forEach>
   </td>
  </tr>
    </c:forEach>
    
  
</table>

<br>
<form action="/social/ResponseRetrieveFriendRequests" method="post">
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.email}">
</c:forEach>
<input type="submit" value="Refresh">
</form>

<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>




</body>
</html>