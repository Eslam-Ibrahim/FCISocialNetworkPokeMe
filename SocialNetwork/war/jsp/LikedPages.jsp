<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liked Pages</title>

<style>
     <%@ include file="style.css"%>
</style>

</head>

<body>


<body  bgcolor="#181819">
    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
              <h1>  Liked Pages </h1>
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			<table border="5">
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