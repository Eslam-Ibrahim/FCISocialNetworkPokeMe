
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>

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
              
                <h1> Welcome ${it.email} </h1>
                            
            
            </div>

        </div> <!--end of id=header1 -->
        
       <!--  <div id="PokeMeImage">
        <img src="logo.png" height="141" width="532" alt="" />
        </div> -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			<table>
  <tr>
  <td>
<!-- Retrieve friend Request  -->
<form action="/social/ResponseRetrieveFriendRequests" method="post">
 
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Retrieve friend Requests">
  </form>
    </td>
<td>
<!-- Retrieve Notifications  -->
<form action="/social/notificationController/ResponseRetrieveNotifications" method="post">
 
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Retrieve Notifications">
  </form>

</td>


<td>
     <!-- Retrieve Message History  -->
 <form action="/social/ResponseRetrieveMessage" method="post">
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Messages">

  </form>

</td>

<td>
     <!-- view TimeLine -->
 <form action="/social/userTimeLine/ResponseLoadTimeLine" method="post">
<input type="hidden" name="visitingLocation" value = "${it.email}">
<input type="hidden" name="visitorMail" value = "${it.email}">
  <input type="submit" value="TimeLine">
  </form>

</td>

<td>
     <!-- Pages -->
 <form action="/social/pageController/ResponsePages" method="post">
 <input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Pages">

  </form>

</td>
<!-- Friends -->
<td>

<form action="/social/ResponseViewFriends" method="post">
 <input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Friends">

  </form>

</td>

    </tr> 

</table>
<br>
<br>

 <td>
<!-- search friend  -->
<form action="/social/ResponseSendFriendRequest" method="post">

  <h2>
  <font color="#ef4e01">Email :  </font> </b>
  </h2> <input type="text" name="friendEmail" placeholder="Email" />
<input type="hidden" name="myEmail" value ="${it.email}">
<br><br>
  <input type="submit" value="Send Friend Request">
</form>
    <br>
    <br>
  </td>

<a href="/social/backtoEntryPoint"> <h2> <b> <font color="#ef4e01">Sign Out </font> </b> </h2></a>

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