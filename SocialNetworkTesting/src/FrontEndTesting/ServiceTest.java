package FrontEndTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.UserEntity;

public class ServiceTest {

	 @DataProvider (name = "DPSendRequest")
	  public static Object [][]DPSendRequest() 
	  {
		  return new Object [][] {
				  {"OK","eom_realmadrid@live.com","hassan@yahoo.com"},
				  {"Failed","eom_realmadrid@live.com","23"},
				  {"Failed","eom_realmadrid@live.com",""},
				  {"Failed","",""},
		  }; 
	  }
	  @Test(dataProvider = "DPSendRequest")
  public void SendFriendRequest(String expStatus , String sender, String receiver) {
	  String serviceUrl = "http://localhost:8888/rest/SendFriendRequest";
		try {
	
			URL url = new URL(serviceUrl);
			String urlParameters = "myEmail=" + sender + "&friendEmail=" + receiver
					;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			 Assert.assertEquals(expStatus, object.get("Status"));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



  }



  @DataProvider (name = "DP")
  public static Object [][]DP() 
  {
	  return new Object [][] {
			  {"OK","Eslam","123"},
			  {"Failed","Eslam","23"},
			  {"Failed","Eslam",""},
			  {"Failed","",""},
	  }; 
  }
  @Test(dataProvider = "DP")
  public void loginService(String expStatus , String name, String pass) {
	  String serviceUrl = "http://localhost:8888/rest/LoginService";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + name + "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			// Testing Service
			Assert.assertEquals(expStatus, object.get("Status"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


  }

  @Test
  public void registrationService() {
	String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + "Test" + "&email=" + "test@test.com"
					+ "&password=" + "123";
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			 Assert.assertEquals("OK", object.get("Status"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


  }
}
