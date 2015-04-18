
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Share Page Post Page</title>

<style>
     <%@ include file="style.css"%>
</style>

</head>

<body  bgcolor="#181819">

<script language="JavaScript">

function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Shared Successfully";
  document.forms[formTag].submit();
}
</script>


    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
                
				<h1>Share Page Post </h1>

            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
				<table border="5">
  <tr>
        <th><b> <font color="#ef4e01"><h2>Share Post</h2></font> </b> </th>
  </tr>
  <tr>
    <td>
    
    <form action="/social/userTimeLine/ResponseSavePosts" method="post">
 <textarea rows="4" cols="50" name="content">
${it.OriginalPostOwner} :
${it.originalPostContent} - Shared
</textarea>
 <br>
 <b> <font color="#ef4e01">Feelings</font> </b>
 <br>
 <select name="feelings">
  <option value="Happy">Happy</option>
  <option value="Alive">Alive</option>
  <option value="Positive">Positive</option>
  <option value="Depressed">Depressed</option>
  <option value="Hurt">Hurt</option>
  <option value="Sad">Sad</option>
  <option value="Nervous">Nervous</option>
  <option value="worried">Worried</option>
  <option value="neutral">Neutral</option>
  <option value="Lonely">Lonely</option>
  <option value="Angry">Angry</option>
</select>
 <br><br>
<b> <font color="#ef4e01">Privacy</font> </b>
<br>
<input type="radio" name="privacy" value="public">public<br>
<input type="radio" name="privacy" value="private">private<br>
<input type="hidden" name="myEmail" value ="${it.shareOwner}">
<input type="hidden" name="postLocation" value ="${it.shareOwner}">
<br>
<input type="submit" value="Share Post Now!" onclick="formSubmitter('sampleform', 'message')"><div id='message'></div>

</form>
     
    </td>
    
  </tr>
  
</table>

<br>
<form action="/social/pageTimeLineController/ResponseLoadPageTimeLine" method="post">
<input type="hidden" name="pageOwner" value ="${it.pageOwner}">
<input type="hidden" name="pageID" value ="${it.pageID}">
<input type="hidden" name="pageName" value ="${it.pageName}">
<input type="submit" value="Back To Previous Page TimeLine">
</form>
<br>
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