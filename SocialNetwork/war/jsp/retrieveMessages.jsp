
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Retrieve Messages History</title>


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
              
                <h2>Message Flow between ${it.email} And ${it.friendMail}</h2>
            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			
<table>
  <tr>
    <td> ${it.Messages}</td>
    
  </tr>
  
</table>

<br>


<form action="/social/ResponseSendMessage" method="post">
 

<h3> <b> <font color="#ef4e01">Message</font> </b> </h3>
<textarea rows="4" cols="50" name="messageContents" placeholder="Write a reply"></textarea>
<br>
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