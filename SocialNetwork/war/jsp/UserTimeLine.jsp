
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeLine Page</title>
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
function formSubmitterLike(formTag, messageTag){
	  document.getElementById(messageTag).innerHTML = "Liked Successfully";
	  document.forms[formTag].submit();
	}

</script>

<h1>Welcome To Your TimeLine</h1>
<br> 
<br>
<table>
  <tr>
    <th> Posts</th>
  </tr>
  
    <c:forEach items = "${it.posts}" var="post">
    <tr>
    <td> 
     <c:out value = "${post.postOwner}"></c:out>
     <br>
     <c:out value = "${post.content}"></c:out>
     <br>
     Feelings- <c:out value = "${post.feelings}"></c:out>
     <br>
     Likes : <c:out value = "${post.numberOflikes}"></c:out>
     <br>
    Date :  <c:out value = "${post.date}"></c:out>
     <br>
    <c:forEach items = "${it.mail}" var="user">
     <form action="/social/userTimeLine/ResponseLikePost" method="post">
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
<br>




<table>
  <tr>
    <th> Create Post</th>
  </tr>
  <tr>
    <td>
    
    <form action="/social/userTimeLine/ResponseSavePosts" method="post">
 
 <textarea rows="4" cols="50" name="content">
What's on your mind ?!</textarea>
 <br>
 Feelings:
 <br>
 <select name="feelings">
  <option value="Happy">Happy</option>
  <option value="Alive">Alive</option>
  <option value="Positive">Positive</option>
  <option value="Depressed">Depressed</option>
  <option value="Hurt">Hurt</option>
  <option value="Sad">Sad</option>
  <option value="Nervous">Nervous</option>
  <option value="worried">Worried</option>
  <option value="neutral">Neutral</option>
  <option value="Lonely">Lonely</option>
  <option value="Angry">Angry</option>
</select>
 <br>
Privacy :
<br>
<input type="radio" name="privacy" value="public">public<br>
<input type="radio" name="privacy" value="private">private<br>
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.postOwner}">
<input type="hidden" name="postLocation" value ="${user.postOwner}">
</c:forEach>
<input type="submit" value="Post" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>

</form>
     
    </td>
    
  </tr>
  
</table>

<br>
<form action="/social/userTimeLine/ResponseLoadTimeLine" method="post">
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.postOwner}">
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