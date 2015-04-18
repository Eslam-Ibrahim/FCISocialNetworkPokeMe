<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Retrieve Notifications Page</title>

<style>
     <%@ include file="style.css"%>
</style>

</head>

<body  bgcolor="#181819">

<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "You Are Now Friends";
  document.forms[formTag].submit();
}

</script>

    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
       			<h1> Notifications Center</h1>
            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			<br>
<table border="5">
  <tr>
        <th><b> <font color="#ef4e01"><h2>Notification</h2></font> </b> </th>
        <th><b> <font color="#ef4e01"><h2>Action</h2></font> </b> </th>
  </tr>
  
    <c:forEach items = "${it.notifications}" var="notificationObject">
    <tr>
    <td> 
     <c:out value = "${notificationObject.content}"></c:out>
     <br>Date : <c:out value = "${notificationObject.date}"></c:out>
     </td>
  <td>
	<c:choose>
	    <c:when test="${notificationObject.type == 'Join'}">
	       Welcome :)
	    </c:when>
	    <c:when test="${notificationObject.type == 'Request'}">
		      <form action="/social/ResponseAcceptFriendRequest" method="post">
	            <input type="hidden" name="friendEmail" value = "${notificationObject.senderMail}"> 
				<input type="hidden" name="myEmail" value ="${notificationObject.recieverMail}">
	  			<input type="submit" value="Accept Friend Request" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>
	  		  </form>
	    </c:when>
	    <c:when test="${notificationObject.type == 'Accept'}">
	       Congratulations !
	    </c:when>
	    <c:when test="${notificationObject.type == 'Message'}">
	       <form action="/social/ResponseRetriveMessageHistory" method="post"> 
	           <input type="hidden" name="senderMail" value = "${notificationObject.senderMail}">
	           <input type="hidden" name="receiverMail" value ="${notificationObject.recieverMail}">
	           <input type="submit" value="Chat Now !">
            </form>
	    </c:when>
	    <c:when test="${notificationObject.type == 'Post'}">
	           <!-- view TimeLine -->
 			<form action="/social/userTimeLine/ResponseLoadTimeLine" method="post">
				<input type="hidden" name="visitingLocation" value = "${notificationObject.recieverMail}">
				<input type="hidden" name="visitorMail" value = "${notificationObject.recieverMail}">
  				<input type="submit" value="Show TimeLine">
  			</form>
	    </c:when>
	    
	    <c:when test="${notificationObject.type == 'PagePost'}">
	           <!-- view Pages -->
 			<form action="/social/pageController/ResponsePages" method="post">
				<input type="hidden" name="myEmail" value ="${notificationObject.recieverMail}">
  				<input type="submit" value="Show Pages">
  			</form>
	    </c:when>
	    
	    <c:when test="${notificationObject.type == 'PageLike'}">
	           <!-- view Pages -->
 			<form action="/social/pageController/ResponsePages" method="post">
				<input type="hidden" name="myEmail" value ="${notificationObject.recieverMail}">
  				<input type="submit" value="Show Pages">
  			</form>
	    </c:when>
	    <c:otherwise>
	        No Action Needed !
	    </c:otherwise>
	</c:choose>
   </td>
  </tr>
    </c:forEach>
    
</table>
<br>
<form action="/social/notificationController/ResponseRetrieveNotifications" method="post">
<c:forEach items = "${it.mail}" var="user">
<input type="hidden" name="myEmail" value ="${user.senderMail}">
</c:forEach>
<input type="submit" value="Refresh">
</form>

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