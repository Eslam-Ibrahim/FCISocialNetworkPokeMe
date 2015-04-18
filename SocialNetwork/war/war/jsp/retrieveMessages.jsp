
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Retrieve Messages History</title>
<style>
table, td, th {
    border: 1px solid green;
}

th {
    background-color: green;
    color: white;
}
</style>
</head>

<body>
<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Message Sent";
  document.forms[formTag].submit();
}

</script>





<table>
  <tr>
    <th>Message Flow between ${it.email} And ${it.friendMail}</th>
  </tr>
  <tr>
    <td> ${it.Messages}</td>
    
  </tr>
  
</table>

<br>

<br>
<br>

<form action="/social/ResponseSendMessage" method="post">
 
Message : <input type="text" name="messageContents" /> <br>
<input type="hidden" name="recieverMail" value ="${it.friendMail}">
<input type="hidden" name="senderMail" value ="${it.email}">
<input type="submit" value="Send Message" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>

</form>
<br>
<form action="/social/ResponseRetriveMessageHistory" method="post">
<input type="hidden" name="senderMail" value ="${it.friendMail}">
<input type="hidden" name="receiverMail" value ="${it.email}">
<input type="submit" value="Refresh">
</form>
<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>
</body>
</html>