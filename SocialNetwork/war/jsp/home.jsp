
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>
<style>
#background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url('http://wallpaperscraft.com/image/93563/1600x900.jpg');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: 100%;
    opacity: 0.9;
    filter:alpha(opacity=90);
}

.head{
    width: 150px;
    height: 160px;
}
body {
    font-family: tahoma, helvetica, arial, sans-serif;
    font-size: 12px;
    text-align: center;
    background: #000;
    color: #ddd4d4;
    padding-top: 12px;
    line-height: 2;
}
td, th {
    font-size: 12px;
    text-align: left;
    line-height: 2;
}
#wrapper {
    margin: auto;
    text-align: left;
    width: 832px;
    position: relative;
    padding-top: 27px;
}
#body {
         width: 832px;
}
#bodyi {
    
     width: 832px;
}
#bodyj {
    
     padding: 1px 0;
     }
#body2 {
    
     width: 832px;
}
#bodyi2 {
    
     width: 832px;
}
#bodyj2 {
    
     padding: 1px 0;
}
h1, h2, h3, #nav, #nav li {
    margin: 0; padding: 0;
}
#nav {
    font-size: 10px;
    position: absolute;
    right: 0;
    top: 12px;
    line-height: 1.2;
    padding-left: 120px;
}
#nav li {
    float: left;
    width: 108px;
    list-style: none;
    margin-left: 2px;
    border-bottom: 1px solid black;
}
#nav a {
    background: #738d09;
    color: #2e3901;
    font-weight: bold;
    text-decoration: none;
    display: block;
    text-align: center;
    padding: 1px 0;
}
#sidebar {
    float: left;
    width: 250px;
    padding-left: 4px;
}
#sidebar .content {
    padding-left: 24px;
}
#sidebar .content img {
    float: left;
    clear: left;
    margin: 5px 5px 5px 0;
}
#sidebar .divider {
    
    height: 5px;
    width: 169px;
}
#content {
    float: right;
    width: 462px;
}
#content1 {
    float: left;
    width: 800px;
}
#content1 .content {
    margin: 7px 35px 7px 20px;
    padding-left: 20px;
}
#content .content {
    margin: 7px 55px 7px 17px;
}
#content .content table {
    width: 310px;
    margin-right: 0px;
}
#content .content table td,
#content .content table th {
    padding-right: 10px;
}
#content .content table td.download {
    text-align: right;
    padding-right: 0px;
}
#content .divider {
    
    height: 5px;
}
h1 {
    position: absolute;
    left: 0;
    top: 0;
}
h2 {
    font-size: 10px;
    color: #cf9118;
    margin: 1em 0;
}
h3 {
    font-size: 10px;
    margin: 1em 0;
}
h5 {
    font-size: 20px;
    color: #cf9118;
    margin: 1em 0;
    text-align: center;
}
h6 {
    font-size: 18px;
    margin: 1em 0;
}
p {
    margin: 1em 0;
}
img {
    border: 0;
}
img.left  { float: left; margin-right: 14px; }
img.right { float: right; margin-left: 14px; }
.readmore {
    text-align: right;
}
.hidden {
    visibility: hidden;
}
.clear {
    clear: both;
}
a {
    color: #f0b33c;
    font-weight: bold;
    text-decoration: none;
}
a:visited {
    color: #cf9118;
}
a:hover {
    text-decoration: underline;
}
#power {color:#fff;text-align:center;}
#power a {color:#fff;}
</style>
</head>
<body>
    
    <div id="background">
        <h5> Welcome ${it.email}  <ul><li><a href="/social/backtoEntryPoint"> Sign Out</a></li></ul></h5>   
             <table>
  <tr>
 <td>
<!-- search friend  -->
<form action="/social/ResponseSendFriendRequest" method="post">

  <h2>Email : </h2> <input type="text" name="friendEmail" />
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Send Friend Request">
</form>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
  </td>
  
<td>
    
        
        
<!-- Retrieve friend Request  -->
<form action="/social/ResponseRetrieveFriendRequests" method="post">
 
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Retrieve friend Requests">
  </form>
    </td>
<td>
<!-- Retrieve Notifications  -->
<form action="/social/ResponseRetrieveNotifications" method="post">
 
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Retrieve Notifications">
  </form>

</td>

<td>
     <!-- Send Message  -->
 <form action="/social/ResponseRetrieveFriendsSendMessage" method="post">
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Send Message">

  </form>

</td>
     
<td>
     <!-- Retrieve Message History  -->
 <form action="/social/ResponseRetrieveMessage" method="post">
<input type="hidden" name="myEmail" value ="${it.email}">
  <input type="submit" value="Retrieve Messages">

  </form>

</td>
    </tr> 
     

</table>


    </div>

       



</body>
</html>