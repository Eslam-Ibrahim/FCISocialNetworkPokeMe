<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liked Pages</title>
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


<h1>  Liked Pages </h1>
<br>
<table>
  <tr>
    <th> page List</th>
    <th> Action </th>
  </tr>
  
    <c:forEach items = "${it.likedPages}" var="pageObject">
    <tr>
    <td> 
     <c:out value = "${pageObject.pageName}"></c:out>
     </td>
  <td>
    
<form action="/social/pageTimeLineController/ResponseLoadPageTimeLine" method="post">
<input type="hidden" name="pageOwner" value ="${pageObject.pageOwner}">
<input type="hidden" name="pageID" value ="${pageObject.pageID}">
<input type="hidden" name="pageName" value ="${pageObject.pageName}">
  <input type="submit" value="Access Page TimeLine">
  </form>
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