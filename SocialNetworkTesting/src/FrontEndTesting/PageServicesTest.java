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
import org.testng.annotations.DataProvider;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.PageEntity;

public class PageServicesTest {

  @Test
  public void createPage() {
	  String serviceUrl = "http://localhost:8888/rest/createPage";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myEmail=" + "eom_realmadrid@live.com" + "&pageName=" + "Test" +
					"&type="+"Bar"+"&category="+"Local Business";
					
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

  @Test
  public void listPagesForOwner() {
		String serviceUrl = "http://localhost:8888/rest/listPagesForOwner";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters ="myEmail=" + "hassan@yahoo.com";
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
			Map<String, ArrayList<PageEntity>> map = new HashMap<String, ArrayList<PageEntity>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			ArrayList<PageEntity> rePages = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				rePages.add(PageEntity.parsePageInfo(object.toJSONString()));
				
			}
		// expected list
			ArrayList<PageEntity> exptResult = new ArrayList<>();
			exptResult.add(new PageEntity("hassan@yahoo.com", "PlayStation4", 
					"Brand/Product", "Games/Toys", 1, 3, 0));
			for (int i = 0; i < rePages.size(); i++) {
				Assert.assertEquals(rePages.get(i), exptResult.get(i));
			}
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
