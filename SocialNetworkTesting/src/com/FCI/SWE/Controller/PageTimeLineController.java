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
import com.FCI.SWE.Models.PagePost;
import com.FCI.SWE.Models.UserEntity;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;


@Path("/pageTimeLineController")
@Produces("text/html")
public class PageTimeLineController {
	
	
	
	@POST
	@Path("/ResponseLoadPageTimeLine")
	public Response responseLoadPageTimeLine(@FormParam("pageOwner")String pageOwner, @FormParam("pageName") String pageName,
			@FormParam("pageID") long pageID) {

		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/loadPagePosts";
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
					// Dummy PagePost to save current user mail ---> Eslam Osama's Idea - pageOwner = content - pageName = privacy
					ArrayList<PagePost> retUserMail = new ArrayList<>();
					PagePost retMail = new PagePost( pageID, 0, UserController.userMail, pageOwner, pageName, "", 0, 0);
				    retUserMail.add(retMail);
			        map.put("pagePosts",retPosts);
			        map.put("mail", retUserMail);
				  //  System.out.println(map.get("mails"));
					if (map.isEmpty())
						return Response.ok(new Viewable("/jsp/pageTimeLine",map)).build();				
					
			
					return Response.ok(new Viewable("/jsp/pageTimeLine",map)).build();				
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
	@Path("/ResponseCreatePagePost")
	@Produces("text/html")
	public void responseCreatePagePost(@FormParam("pageOwner")String pageOwner, @FormParam("pageName") String pageName , 
			@FormParam("pageID")long pageID , 
			@FormParam("postOwner")String postOwner,@FormParam("content")String content,@FormParam("privacy")String privacy ) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/savePagePost";
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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}  

		

	}
	
	@POST
	@Path("/ResponseLoadPageTimeLineForUser")
	public Response responseLoadPageTimeLineForUser(@FormParam("pageOwner")String pageOwner, @FormParam("pageName") String pageName,
			@FormParam("pageID") long pageID) {

		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/loadPagePostsForUser";
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
					// Dummy PagePost to save current user mail ---> Eslam Osama's Idea - pageOwner = content - pageName = privacy
					ArrayList<PagePost> retUserMail = new ArrayList<>();
					PagePost retMail = new PagePost( pageID, 0, UserController.userMail, pageOwner, pageName, "", 0, 0);
				    retUserMail.add(retMail);
			        map.put("pagePosts",retPosts);
			        map.put("mail", retUserMail);
				  //  System.out.println(map.get("mails"));
					if (map.isEmpty())
						return Response.ok(new Viewable("/jsp/pageTimeLineForUser",map)).build();				
					
			
					return Response.ok(new Viewable("/jsp/pageTimeLineForUser",map)).build();				
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
	@Path("/ResponseSharePost")
	@Produces("text/html")
	public Response responseSharePost(@FormParam("originalPostContent") String originalPostContent,
			@FormParam("OriginalPostOwner") String OriginalPostOwner,
			@FormParam("shareOwner") String shareOwner,
			@FormParam("pageOwner")String pageOwner, @FormParam("pageName") String pageName,
			@FormParam("pageID") long pageID) 
	{
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("originalPostContent", originalPostContent);
		map.put("OriginalPostOwner", OriginalPostOwner);
		map.put("shareOwner", shareOwner);
		map.put("pageOwner", pageOwner);
		map.put("pageName", pageName);
		map.put("pageID",  String.valueOf(pageID));
		return Response.ok(new Viewable("/jsp/sharePagePost",map)).build();
		
			
	}

	



}
