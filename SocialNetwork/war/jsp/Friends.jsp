<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends</title>

<style>
     <%@ include file="style.css"%>
</style>


</head>

<body  bgcolor="#181819">
    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
               <h1>  Friends </h1>
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			<br><br>
			<table border="5">
  <tr>
    <th> Friends List</th>
    <th> Action </th>
  </tr>
  
    <c:forEach items = "${it.mails}" var="friend">
    <tr>
    <td> 
     <c:out value = "${friend.email}"></c:out>
     </td>
  <td>
    <c:forEach items = "${it.mail}" var="user">
<form action="/social/userTimeLine/ResponseLoadTimeLine" method="post">
<input type="hidden" name="visitingLocation" value = "${friend.email}">
<input type="hidden" name="visitorMail" value ="${user.email}">
  <input type="submit" value="Access TimeLine">
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
            </div> <!--end of text inside header 2 -->
            
        </div> <!--end of header2 -->

        
        <br /><br /><br /><br />
        <br /><br /><br /><br />

        <br /><br /><br /><br />
        <br /><br /><br /><br />


        <br />
        <br />

    </div> <!--end of container-->
    <br />
    <br />

</body>


</html>