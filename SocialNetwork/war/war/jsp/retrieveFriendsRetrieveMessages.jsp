
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Retrieve Messages</title>
</head>

<body>


<p> Friends List</p>
 ${it.mails}





<br>
<form action="/social/ResponseRetriveMessageHistory" method="post">

Friend Mail : <input type="text" name="senderMail" /> <br>
<input type="hidden" name="receiverMail" value ="${it.myEmail}">
<input type="submit" value="Retrieve Messages">

</form>
<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>
</body>
</html>