
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
textarea 
{
  color: black;
  opacity: 0.7; 
  filter:alpha(opacity=70); 
    
}
</style>

<body>
<script language="JavaScript">

function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Posted Successfully";
  document.forms[formTag].submit();
}
</script>

<h1>Welcome To <c:forEach items = "${it.mail}" var="user"> <c:out value = "${user.privacy}"> </c:out> </c:forEach>Page</h1>
<br> 
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
    Number of Seen :  <c:out value = "${post.numberOfSeens}"></c:out>
     <br>
    <c:forEach items = "${it.mail}" var="user">
     <form action="/social/pageTimeLineController/ResponseSharePost" method="post">
<input type="hidden" name="originalPostContent" value ="${post.content}">
<input type="hidden" name="OriginalPostOwner" value ="${post.postOwner}">
<input type="hidden" name="shareOwner" value ="${user.postOwner}">
<input type="hidden" name="pageOwner" value ="${user.content}">
<input type="hidden" name="pageName" value ="${user.privacy}">
<input type="hidden" name="pageID" value ="${user.pageID}">
<input type="submit" value="Share Post">
</form>
</c:forEach>
   </td>
  </tr>
    </c:forEach>
    
  
</table>

<br>

<br>
<br>




<table>
  <tr>
    <th> Create Post</th>
  </tr>
  <tr>
    <td>
    
    <form action="/social/pageTimeLineController/ResponseCreatePagePost" method="post">
 
 <textarea rows="4" cols="50" name="content">
What's on your mind ?!</textarea>
 <br>
 <br>
Privacy :
<br>
<input type="radio" name="privacy" value="public">public<br>
<input type="radio" name="privacy" value="private">private<br>
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="postOwner" value ="${user.postOwner}">
<input type="hidden" name="pageID" value ="${user.pageID}">
<input type="hidden" name="pageOwner" value ="${user.content}">
<input type="hidden" name="pageName" value ="${user.privacy}">
</c:forEach>
<input type="submit" value="Post" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>
</form>
     
    </td>
    
  </tr>
  
</table>

<br>
<form action="/social/pageTimeLineController/ResponseLoadPageTimeLine" method="post">
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