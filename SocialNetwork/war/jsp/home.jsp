
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>
</head>
<body>
 Welcome ${it.name}                                                                <a href="/social/backtoEntryPoint"> Sign Out</a> <br>

<!-- search friend  -->
<form action="/social/ResponseSendFriendRequest" method="post">
 
  Email : <input type="text" name="friendEmail" /> <br>
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Send Friend Request">
  </form>
  <br>
  
  
  <!-- Retrieve friend Request  -->
<form action="/social/ResponseRetrieveFriendRequests" method="post">
 
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Retrieve friend Requests">
  </form>
  <br>
  
  
 


</body>
</html>