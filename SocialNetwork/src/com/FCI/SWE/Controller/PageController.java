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

import com.FCI.SWE.Models.NotificationsEntity;
import com.FCI.SWE.Models.PageEntity;
import com.FCI.SWE.Models.UserEntity;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;






@Path("/pageController")
@Produces("text/html")

public class PageController {
	
	
	public ArrayList<PageEntity> getCreatedPagesByUser(String userMail)
	{
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/listPagesForOwner";
		String serviceUrl = "http://localhost:8888/rest/listPagesForOwner";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters ="myEmail=" + userMail;
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
			return rePages;
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
	@Path("/ResponsePages")
	public Response ResponsePages(@FormParam("myEmail") String userMail) {

		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/listPagesForOwner";
				String serviceUrl = "http://localhost:8888/rest/listPagesForOwner";
				
				try {
					URL url = new URL(serviceUrl);
					String urlParameters ="myEmail=" + userMail;
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
					// Dummy Page to save current user mail ---> Eslam Osama's Idea
					ArrayList<PageEntity> retUserMail = new ArrayList<>();
				    PageEntity retMail = new PageEntity();
				    retMail.setPageOwner(UserController.userMail);
				    retUserMail.add(retMail);
			        map.put("pagesForOwner",rePages);
			        map.put("mail", retUserMail);
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
			,@FormParam("category") String category /* ,@FormParam("pagesForOwner") ArrayList<PageEntity> createdPages*/)
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
					// Get Created Pages Again to keep it available when building JSP Again
					ArrayList<PageEntity> createdPages = getCreatedPagesByUser(userMail);
			        map.put("searchPages",rePages);
			       map.put("pagesForOwner", createdPages);
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
