<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pages</title>

<style>
     <%@ include file="style.css"%>
</style>
</head>

<body bgcolor="#181819">

<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Page Created Successfully";
  document.forms[formTag].submit();
  
}
</script>
<div id="container">
    <br />
    
    <div id="header1">
        <div id="textinsideheader1">
          
        <h1>Pages</h1>
        
        
        </div>

    </div> <!--end of id=header1 -->
    
    <br /><br />

    <div id="header2">  

        <div id="text_inside_header2">
		
		
<table border="5">
<tr>
<td>
<table >
  <tr bgcolor="black">
    <th><b> <font color="#ef4e01"><h2>Create Page</h2></font> </b> </th>
  </tr>
  <tr>
    <td>
    <form action="/social/pageController/ResponseCreatePage" method="post">
 <h3> <b> <font color="#ef4e01">Page Name : </font> </b> </h3>
 <input type="text" name="pageName"/>
 <br>
 <h3> <b> <font color="#ef4e01">Page Type : </font> </b> </h3>
<br>
<input type="radio" name="type" value="Local Business">Local Business<br>
<input type="radio" name="type" value="Organization">Organization<br>
<input type="radio" name="type" value="Brand/Product">Brand/Product<br>
<input type="radio" name="type" value="Artist/Band">Artist/Band<br>
 
 <h3> <b> <font color="#ef4e01">Page Category : </font> </b> </h3>
 <br>
 <select name="category">
  <option value="Bank">Local Business-Bank</option>
  <option value="Bar">Local Business-Bar</option>
  <option value="Book Store">Local Business-Book Store</option>
  <option value="Hotel">Local Business-Hotel</option>
  <option value="Company">Organization-Company</option>
  <option value="Internet/Software">Organization-Internet/Software</option>
  <option value="Non-Profit Organization">Organization-Non-Profit Organization</option>
  <option value="Appliance">Brand/Product-Appliance</option>
  <option value="Games/Toys">Brand/Product-Games/Toys</option>
  <option value="Jewelry/Watches">Brand/Product-Jewelry/Watches</option>
  <option value="Actor/Director">Artist/Band-Actor/Director</option>
  <option value="Athlete">Artist/Band-Athlete</option>
  <option value="Journalist">Artist/Band-Journalist</option>
  <option value="Musician/Band">Artist/Band-Musician/Band</option>
</select>
 <br><br>
 <c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.pageOwner}">
</c:forEach>
<input type="submit" value="Create Page" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>

</form>
     
    </td>
    
  </tr>
  
</table>

</td>
<td>

<table border="5">
  <tr bgcolor="black">
    <th><b> <font color="#ef4e01"><h3> Own Pages List</h3></font> </b></th>
    <th><b> <font color="#ef4e01"><h3>Action</h3></font> </b> </th>
    
  </tr>
  
    <c:forEach items = "${it.pagesForOwner}" var="pageObject">
    <tr>
    <td> 
     <c:out value = "${pageObject.pageName}"></c:out>
     </td>
  <td>
<form action="/social/pageTimeLineController/ResponseLoadPageTimeLine" method="post">
<input type="hidden" name="pageOwner" value ="${pageObject.pageOwner}">
<input type="hidden" name="pageID" value ="${pageObject.pageID}">
<input type="hidden" name="pageName" value ="${pageObject.pageName}">
<input type="submit" value="Access Page">
</form>
   </td>
   </tr>
    </c:forEach>
    
</table>
    <br>
    <br>
    <br>
       <br>
    <br>
    <br>
       <br>
    <br>
    <br>
    <br>
</td>
<td>
<table border="5">
  <tr bgcolor="black">
    <th><b> <font color="#ef4e01"><h2>Search Pages</h2></font> </b> </th>
  </tr>
  <tr>
    <td>
    <form action="/social/pageController/ResponseSearchPages" method="post">
 <h3> <b> <font color="#ef4e01">Page Type : </font> </b> </h3>
<br>
<input type="radio" name="type" value="Local Business">Local Business<br>
<input type="radio" name="type" value="Organization">Organization<br>
<input type="radio" name="type" value="Brand/Product">Brand/Product<br>
<input type="radio" name="type" value="Artist/Band">Artist/Band<br>
 <br>
 <h3> <b> <font color="#ef4e01">Page Category : </font> </b> </h3>
 <br>
 <select name="category">
  <option value="Bank">Local Business-Bank</option>
  <option value="Bar">Local Business-Bar</option>
  <option value="Book Store">Local Business-Book Store</option>
  <option value="Hotel">Local Business-Hotel</option>
  <option value="Company">Organization-Company</option>
  <option value="Internet/Software">Organization-Internet/Software</option>
  <option value="Non-Profit Organization">Organization-Non-Profit Organization</option>
  <option value="Appliance">Brand/Product-Appliance</option>
  <option value="Games/Toys">Brand/Product-Games/Toys</option>
  <option value="Jewelry/Watches">Brand/Product-Jewelry/Watches</option>
  <option value="Actor/Director">Artist/Band-Actor/Director</option>
  <option value="Athlete">Artist/Band-Athlete</option>
  <option value="Journalist">Artist/Band-Journalist</option>
  <option value="Musician/Band">Artist/Band-Musician/Band</option>
</select>
 <br><br>
  <c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.pageOwner}">
</c:forEach>
<%-- <input type="hidden" name="pagesForOwner" value ="${it.pagesForOwner}"> --%>
<input type="submit" value="Search Page">
</form>
<br>
  <c:forEach items = "${it.searchPages}" var="pageObject">
    <tr>
    <td> 
     <c:out value = "${pageObject.pageName}"></c:out>
     </td>
  <td>
   
	<form action="/social/pageTimeLineController/ResponseLoadPageTimeLineForUser" method="post">
<input type="hidden" name="pageID" value ="${pageObject.pageID}">
<input type="hidden" name="pageOwner" value ="${pageObject.pageOwner}">
<input type="hidden" name="pageName" value ="${pageObject.pageName}">
<input type="submit" value="Access Page">
</form>
   </td>
  </tr>
    </c:forEach>      
    </td>
  </tr>
</table>
</td>
</tr>
</table>

<br>

<table border="5">
<td>
<br>
<!-- view Liked Pages  -->
<form action="/social/pageController/ResponseRetrieveLikedPages" method="post">
  <c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="likeOwner" value ="${user.pageOwner}">
</c:forEach>
  <input type="submit" value="View Liked Pages">
  </form>
  
  <br>

</td>
<td>

<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  
  <br>
</td>
</table>
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