
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Page TimeLine</title>
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
function formSubmitterLike(formTag, messageTag){
	  document.getElementById(messageTag).innerHTML = "Liked Successfully";
	  document.forms[formTag].submit();
	}

</script>

<h1>Welcome To <c:forEach items = "${it.mail}" var="user"> <c:out value = "${user.privacy}"> </c:out> </c:forEach>Page</h1>
<br>
 <form action="/social/pageController/ResponseLikePageForUser" method="post">
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="pageID" value ="${user.pageID}">
<input type="hidden" name="pageOwner" value ="${user.content}">
<input type="hidden" name="pageName" value ="${user.privacy}">
<input type="hidden" name="likeOwner" value ="${user.postOwner}">
</c:forEach>
<input type="submit" value="Like Page" onclick="formSubmitterLike('sampleform', 'message')"><div id='message'></div>
</form>
 
<br>
<table>
  <tr>
    <th> Posts</th>
  </tr>
  
    <c:forEach items = "${it.pagePosts}" var="post">
    <tr>
    <td> 
     <c:out value = "${post.postOwner}"></c:out>
     <br>
     <c:out value = "${post.content}"></c:out>
     <br>
     Likes : <c:out value = "${post.numberOfLikes}"></c:out>
     <br>
    Date :  <c:out value = "${post.date}"></c:out>
     <br>
   
     
    <c:forEach items = "${it.mail}" var="user">
     <form action="/social/pageTimeLineController/ResponseLikePost" method="post">
<input type="hidden" name="postID" value ="${post.postID}">
<input type="hidden" name="likeOwner" value ="${user.postOwner}">
<input type="submit" value="Like" onclick="formSubmitterLike('sampleform', 'message')"><div id='message'></div>
</form>
</c:forEach>
   </td>
  </tr>
    </c:forEach>
    
  
</table>

<br>
<br>
<form action="/social/pageTimeLineController/ResponseLoadPageTimeLineForUser" method="post">
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="pageID" value ="${user.pageID}">
<input type="hidden" name="pageOwner" value ="${user.content}">
<input type="hidden" name="pageName" value ="${user.privacy}">
</c:forEach>
<input type="submit" value="Refresh">
</form>
<br>
<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  
  <br>

</body>
</html>