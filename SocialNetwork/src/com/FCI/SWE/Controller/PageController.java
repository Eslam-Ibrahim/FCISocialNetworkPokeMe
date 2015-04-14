package com.FCI.SWE.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.UserEntity;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;






@Path("/pageController")
@Produces("text/html")

public class PageController {
	
	
		
	@POST
	@Path("/ResponsePages")
	public Response ResponsePages() {

		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/listPagesForOwner";
				String serviceUrl = "http://localhost:8888/rest/listPagesForOwner";
				
				try {
					URL url = new URL(serviceUrl);
					String urlParameters ="myEmail=" + UserController.userMail;
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
					Map<String, String> map = new HashMap<String, String>();
					while ((line = reader.readLine()) != null) {
						retjson+=line;
					}
					writer.close();
					reader.close();
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(retjson);
				    JSONObject object = (JSONObject) obj;
				    JSONArray list= (JSONArray) object.get("pageNames");
				    String retNames = "";
				    for(Integer i=0;i<list.size();i++){
							
				    	 retNames+=list.get(i).toString() + "\n";
				    }
				    map.put("pageNames",retNames);
				    map.put("myEmail", UserController.userMail);
				  //  System.out.println(map.get("mails"));
					if (map.isEmpty())
						return Response.ok(new Viewable("/jsp/Pages",map)).build();				
					
			
					return Response.ok(new Viewable("/jsp/Pages",map)).build();				
					} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}  catch (ParseException e){
				e.printStackTrace();
			}
				return null;
	}	
	
	
	@POST
	@Path("/ResponseSearchPages")
	public Response ResponseSearchPages(@FormParam("myEmail") String userMail , @FormParam("type") String type
			,@FormParam("category") String category , @FormParam("pageNames") String createdPages)
	{

		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/searchPage";
				String serviceUrl = "http://localhost:8888/rest/searchPage";
				
				try {
					URL url = new URL(serviceUrl);
					String urlParameters ="myEmail=" + userMail +"&type="+type+"&category="+category;
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
					Map<String, String> map = new HashMap<String, String>();
					while ((line = reader.readLine()) != null) {
						retjson+=line;
					}
					writer.close();
					reader.close();
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(retjson);
				    JSONObject object = (JSONObject) obj;
				    JSONArray list= (JSONArray) object.get("SearchPageNames");
				    String retNames = "";
				    for(Integer i=0;i<list.size();i++){
							
				    	 retNames+=list.get(i).toString() + "\n";
				    }
				    map.put("SearchPageNames",retNames);
				    map.put("myEmail", UserController.userMail);
				    map.put("pageNames", createdPages);
				  //  System.out.println(map.get("mails"));
					if (map.isEmpty())
						return Response.ok(new Viewable("/jsp/Pages",map)).build();				
					
			
					return Response.ok(new Viewable("/jsp/Pages",map)).build();				
					} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}  catch (ParseException e){
				e.printStackTrace();
			}
				return null;
	}	
	
	@POST
	@Path("/ResponseCreatePage")
	@Produces("text/html")
	public void ResponseCreatePage(@FormParam("myEmail")String pageOwner, @FormParam("pageName") String pageName , @FormParam("type")String type , 
			@FormParam("category")String category ) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/createPage";
		String serviceUrl = "http://localhost:8888/rest/createPage";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myEmail=" + pageOwner + "&pageName=" + pageName +
					"&type="+type+"&category="+category;
					
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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}  

		

	}

	

}
