<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Accept Friend Request Page</title>
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
              
               <h1>Friend Requests</h1>
            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			<table>
  <tr>

  <h3> <b> <font color="#ef4e01">Friend Requests List</font> </b> </h3>
  </tr>
<br><br>  
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
<br><br><br>
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