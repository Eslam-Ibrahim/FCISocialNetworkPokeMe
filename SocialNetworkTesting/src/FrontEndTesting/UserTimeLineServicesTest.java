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

import javax.ws.rs.FormParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.FCI.SWE.Models.UserPost;
import com.FCI.SWE.Models.UserTimeline;

public class UserTimeLineServicesTest {

  @DataProvider (name = "DP")
  public Object[][] dp() {
    
	  ArrayList<UserTimeline> excpectedResult = new ArrayList<>();
	  //excpectedResult.add(new NotificationsEntity("Aya@yahoo.com", "Aya@yahoo.com", "You Have Joind PokeME!", "13/4/2015","Join"));

	  return new Object[][] {
		      new Object[] {excpectedResult , 
		    		  "my first post"}
		     
		    };
	  
  }

  @Test(dataProvider = "DP")
  public void retrieveUserPosts(ArrayList<UserTimeline>expectedResult,String visitingLocation) {
	  String serviceUrl = "http://localhost:8888/rest/retrieveUserPosts";
	  ArrayList<UserPost> retPosts = new ArrayList<>();
		try {
			URL url = new URL(serviceUrl);
			String urlParameters ="visitingLocation=" + visitingLocation;
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
			Map<String, ArrayList<UserPost>> map = new HashMap<String, ArrayList<UserPost>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retPosts.add(UserPost.parsePostInfo(object.toJSONString()));
				
			}
		
		Assert.assertEquals(expectedResult, retPosts);
		
		}
		catch(Exception e)
		{
			
		}
	  
	  
    
  }

	 @DataProvider (name = "dp2")
public Object[][] dp3() {
	  String excpectedResult = "";
	  //excpectedResult.add(new NotificationsEntity("Aya@yahoo.com", "Aya@yahoo.com", "You Have Joind PokeME!", "13/4/2015","Join"));

	  return new Object[][] {
		      new Object[] {excpectedResult , 
		    		  "OK"}
		     
		    };
}
  
  
  
	 @Test(dataProvider = "dp2")
  public void savePost(String expectedResult,String postOwner, String postLocation ,String content , 
			String feelings , String privacy)  {
	  
	  
	  String serviceUrl = "http://localhost:8888/rest/savePost";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myEmail=" + postOwner + "&postLocation=" + postLocation +
					"&content="+content+"&feelings="+feelings+"&privacy="+privacy;
					
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
			writer.close();
			reader.close();
			
			String line, retJson = "";
			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			  
				  Assert.assertEquals(expectedResult, object.get("Status"));
			  
			
			
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
