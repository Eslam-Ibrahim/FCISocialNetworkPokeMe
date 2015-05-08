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

import com.FCI.SWE.Models.PagePost;
import com.FCI.SWE.Models.pageTimeLine;

public class PageTimeLineServicesTest {

  @DataProvider (name = "DP")
  public Object[][] dp() {
    
	  ArrayList<pageTimeLine> excpectedResult = new ArrayList<>();
	  //excpectedResult.add(new NotificationsEntity("Aya@yahoo.com", "Aya@yahoo.com", "You Have Joind PokeME!", "13/4/2015","Join"));

	  return new Object[][] {
		      new Object[] {excpectedResult , 
		    		  1}
		     
		    };
	  
  }

  @Test(dataProvider = "DP")
  public void loadPagePosts(ArrayList<pageTimeLine>expectedResult,long pageID) {
	  String serviceUrl = "http://localhost:8888/rest/loadPagePosts";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters ="pageID=" + pageID;
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
			Map<String, ArrayList<PagePost>> map = new HashMap<String, ArrayList<PagePost>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			ArrayList<PagePost> retPosts = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retPosts.add(PagePost.parsePagePostInfo((object.toJSONString())));
			}
		
			
			Assert.assertEquals(expectedResult, retPosts);
			
		}catch(Exception e)
		{
			
		}
	  
	 // throw new RuntimeException("Test not implemented");
  }

  
  
  @DataProvider (name = "DP2")
  public Object[][] dp2() {
	  ArrayList<pageTimeLine> excpectedResult = new ArrayList<>();
	  //excpectedResult.add(new NotificationsEntity("Aya@yahoo.com", "Aya@yahoo.com", "You Have Joind PokeME!", "13/4/2015","Join"));

	  return new Object[][] {
		      new Object[] {excpectedResult , 
		    		  "hassan@yahoo.com","PlayStation4",3}
		     
		    };
  }

  
  
  
  @Test(dataProvider = "DP2")
  public void loadPagePostsForUser(ArrayList<pageTimeLine>expectedResult,String pageOwner, String pageName,long pageID) {
	  
	  String serviceUrl = "http://localhost:8888/rest/loadPagePostsForUser";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters ="pageID=" + pageID;
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
			Map<String, ArrayList<PagePost>> map = new HashMap<String, ArrayList<PagePost>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			ArrayList<PagePost> retPosts = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retPosts.add(PagePost.parsePagePostInfo((object.toJSONString())));
			}
		
			
			Assert.assertEquals(expectedResult, retPosts);
			
		}catch(Exception e )
		{
			
		}
	  
    
  }
  
	 @DataProvider (name = "dp3")
public Object[][] dp3() {
	//  String excpectedResult = "";
	  //excpectedResult.add(new NotificationsEntity("Aya@yahoo.com", "Aya@yahoo.com", "You Have Joind PokeME!", "13/4/2015","Join"));

	  return new Object[][] {
		      new Object[] { 
		    		  "OK"}
		     
		    };
}
	
  
  @Test(dataProvider = "dp3")
  public void savePagePost(String expectedResult,String pageOwner,String pageName,long pageID
		  ,String postOwner,String content,String privacy
		  ){
	  
	  String serviceUrl = "http://localhost:8888/rest/savePagePost";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "pageOwner=" + pageOwner + "&pageName=" + pageName +
					"&pageID="+pageID+"&postOwner="+postOwner+"&content="+content+"&privacy="+privacy;
					
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
	  
	
	  throw new RuntimeException("Test not implemented");
  }
}
