
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeLine Page</title>

<style>
     <%@ include file="style.css"%>
</style>

</head>

<body  bgcolor="#181819">

<script language="JavaScript">

function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Posted Successfully";
  document.forms[formTag].submit();
}
</script>


    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
                <h1>Welcome To <c:forEach items = "${it.mail}" var="user"> <c:out value = "${user.postLocation}"></c:out> </c:forEach> TimeLine</h1>
            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			<table border="5">
  <tr>
        <th><b> <font color="#ef4e01"><h2>Posts</h2></font> </b> </th>
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
     <form action="/social/userTimeLine/ResponseSharePost" method="post">
<input type="hidden" name="originalPostContent" value ="${post.content}">
<input type="hidden" name="OriginalPostOwner" value ="${post.postOwner}">
<input type="hidden" name="shareOwner" value ="${user.postOwner}">
<input type="hidden" name="visitingLocation" value = "${user.postLocation}">
<input type="submit" value="Share Post">

</form>
<br>
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
        <th><b> <font color="#ef4e01"><h2>Create Post</h2></font> </b> </th>
  </tr>
  <tr>
    <td>
    
    <form action="/social/userTimeLine/ResponseSavePosts" method="post">
 
 <textarea rows="4" cols="50" name="content" placeholder="What's on your mind?"></textarea>
 <br>
 <b> <font color="#ef4e01">Feelings</font> </b>
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
 <br><br>
<b> <font color="#ef4e01">Privacy</font> </b>
<br>
<input type="radio" name="privacy" value="public">public<br>
<input type="radio" name="privacy" value="private">private<br><br>
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.postOwner}">
<input type="hidden" name="postLocation" value ="${user.postLocation}">
</c:forEach>
<input type="submit" value="Post" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>

</form>
     
    </td>
    
  </tr>
  
</table>

<br>
<form action="/social/userTimeLine/ResponseLoadTimeLine" method="post">
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="visitingLocation" value = "${user.postLocation}">
<input type="hidden" name="visitorMail" value = "${user.postOwner}">
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
    <br />
    <br />
    <br />
    <br />

</body>
</html>