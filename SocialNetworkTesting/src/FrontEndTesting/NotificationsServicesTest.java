package FrontEndTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.MessageEntity;
import com.FCI.SWE.Models.NotificationsEntity;

public class NotificationsServicesTest {

	 @DataProvider (name = "DP")
  public Object[][] dp() {
	  ArrayList<NotificationsEntity> excpectedResult = new ArrayList<>();
	  //excpectedResult.add(new NotificationsEntity("Aya@yahoo.com", "Aya@yahoo.com", "You Have Joind PokeME!", "13/4/2015","Join"));

	  return new Object[][] {
		      new Object[] {excpectedResult , 
		    		  "Aya@yahoo.com"}
		     
		    };
  }

	  @Test(dataProvider = "DP")
  public void retrieveNotifications(ArrayList<NotificationsEntity>expectedResult,String receiverMail) {
		 
		 String serviceUrl = "http://localhost:8888/rest/retrieveNotifications";
			
			try {
				URL url = new URL(serviceUrl);
				String urlParameters ="myEmail=" + receiverMail;
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				String line ,retjson= "";
				Map<String, ArrayList<NotificationsEntity>> map = new HashMap<String, ArrayList<NotificationsEntity>>();
				while ((line = reader.readLine()) != null) {
					retjson+=line;
				}
				writer.close();
				reader.close();
				JSONParser parser = new JSONParser();
				JSONArray retArr = (JSONArray) parser.parse(retjson);
				ArrayList<NotificationsEntity> retNotifications = new ArrayList<>();
				for (int i = 0; i < retArr.size(); ++i) {
					JSONObject object  = (JSONObject) retArr.get(i);
					retNotifications.add(NotificationsEntity.parseNotificationInfo(object.toJSONString()));
					
				}
				 System.out.println("EXP->"+expectedResult);
				 System.out.println("Act->"+retNotifications);
				if(expectedResult.equals(retNotifications))
				{System.out.println("True");}

					Assert.assertEquals(expectedResult, retNotifications);
				
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}  catch (ParseException e){
			e.printStackTrace();
		}




}


		 
    
 }
