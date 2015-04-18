
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Message Page</title>
</head>

<body>
<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Message Sent";
  document.forms[formTag].submit();
}

</script>

<p> Friends List</p>
 ${it.mails}





<br>
<form action="/social/ResponseSendMessage" method="post">

Receiver Mail : <input type="text" name="recieverMail" /> <br>
Message : <input type="text" name="messageContents" /> <br>
<input type="hidden" name="senderMail" value ="${it.email}">
<input type="submit" value="Send Message" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>

</form>
<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>
</body>
</html>