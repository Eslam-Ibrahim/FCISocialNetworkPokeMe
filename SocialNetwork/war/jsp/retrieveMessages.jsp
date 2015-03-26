
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





<table>
  <tr>
    <th>Message Flow between ${it.email} And ${it.friendMail}</th>
  </tr>
  <tr>
    <td> ${it.Messages}</td>
    
  </tr>
  
</table>

<br>
<!-- Back Home  -->
<form action="/social/backHome" method="get">
 
  <input type="submit" value="Back To  HomePage">
  </form>
  <br>
</body>
</html>