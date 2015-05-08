
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Message Page</title>

<style>
     <%@ include file="style.css"%>
</style>

</head>

<body  bgcolor="#181819">
<script language="JavaScript">
function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Message Sent";
  document.forms[formTag].submit();
}

</script>

    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
                <h1>Friends List</h1>
            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			


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