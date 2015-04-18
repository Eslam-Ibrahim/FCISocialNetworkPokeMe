
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Accept Friend Request Page</title>
</head>
<body>

<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "You Are Now Friends";
  document.forms[formTag].submit();
}

</script>

 
    
<p> Friend Request List</p>
${it.mails}










<form action="/social/ResponseAcceptFriendRequest" method="post">
 
  Input Email : <input type="text" name="friendEmail"/> <br>
<input type="hidden" name="myEmail" value ="${it.mail}">
  <input type="submit" value="Accept Friend Request" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>
  </form>
<br>

<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>




</body>
</html>