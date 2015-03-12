
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Accept Friend Request Page</title>
</head>
<body>


 
    
<p> Friend Request List</p>
${it.mails}










<form action="/social/ResponseAcceptFriendRequest" method="post">
 
  Input Email : <input type="text" name="friendEmail"/> <br>
<input type="hidden" name="myEmail" value ="${it.mail}">
  <input type="submit" value="Accept Friend Request">
  </form>




</body>
</html>