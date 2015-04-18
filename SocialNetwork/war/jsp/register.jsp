<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>SignUp Page</title>
<style>
     <%@ include file="style.css"%>
</style>

</head>

<body  bgcolor="#181819">
    <div id="container">
        <br />
        
        <div id="header1">
            <div id="textinsideheader1">
              
                <h1>Register in PokeMe Social Network</h1>
            
            
            </div>

        </div> <!--end of id=header1 -->
        
        <br /><br />

        <div id="header2">  

            <div id="text_inside_header2">
			
			
		  <form action="/social/response" method="post">
		  <table>
  <tr>
  <td>
  Name :
  <br><br>
  </td>
   <td>
   <input type="text" name="uname" placeholder="UserName"/> 
  <br><br>
   </td>
   
  </tr>
  
  
  <tr>
<td>
  Email : 
  <br><br>
  </td>
  <td>
  <input type="text" name="email" placeholder="Email"/> <br>
  <br><br>
  </td>
  </tr>
  
  
  <tr>
  <td>
  Password :
  <br><br>
  </td>
   <td>
   <input type="password" name="password" placeholder="Password"/> <br>
   <br><br>
  </td>
  </tr>
  
  <tr>
  <td>
  <br><br>
  <input type="submit" value="Register">
  </td>
  </tr>
  
  </table>
  </form>
  
  

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
