
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Share Page Post Page</title>
</head>
<style>
table, td, th {
    border: 1px solid gray;
}

th {
    background-color: blue;
    color: white;
}
h1
{
text-align:center;
font-family:courier;
color: #cf9118;
}
textarea 
{
  color: black;
  opacity: 0.7; 
  filter:alpha(opacity=70); 
    
}
</style>

<body>
<script language="JavaScript">

function formSubmitter(formTag, messageTag){
  document.getElementById(messageTag).innerHTML = "Shared Successfully";
  document.forms[formTag].submit();
}
</script>

<h1>Share page Post Page</h1>
<br>
<table>
  <tr>
    <th> Share Post</th>
  </tr>
  <tr>
    <td>
    
    <form action="/social/userTimeLine/ResponseSavePosts" method="post">
 <textarea rows="4" cols="50" name="content">
${it.OriginalPostOwner} :
${it.originalPostContent} - Shared
</textarea>
 <br>
 Feelings:
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
 <br>
Privacy :
<br>
<input type="radio" name="privacy" value="public">public<br>
<input type="radio" name="privacy" value="private">private<br>
<input type="hidden" name="myEmail" value ="${it.shareOwner}">
<input type="hidden" name="postLocation" value ="${it.shareOwner}">
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

</body>
</html>