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
import java.util.Vector;

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
import com.FCI.SWE.Models.UserPost;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;


@Path("/userTimeLine")
@Produces("text/html")

public class UserTimeLineController {
	
	
	@POST
	@Path("/ResponseLoadTimeLine")
	@Produces("text/html")
	public Response responseLoadTimeLine(@FormParam("visitingLocation") String visitingLocation,
			@FormParam("visitorMail") String visitorMail
			) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/retrieveUserPosts";
		String serviceUrl = "http://localhost:8888/rest/retrieveUserPosts";
		
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
			ArrayList<UserPost> retPosts = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retPosts.add(UserPost.parsePostInfo(object.toJSONString()));
				
			}
			// Dummy Post to save TimeLine viewer mail & Visiting Location---> Eslam Osama's Idea
			ArrayList<UserPost> userMail = new ArrayList<>();
		    UserPost retMail = new UserPost();
		    retMail.setPostOwner(visitorMail);
		    retMail.setPostLocation(visitingLocation);
		    userMail.add(retMail);
		    map.put("posts",retPosts);
		    map.put("mail", userMail);
		  //  System.out.println(map.get("mails"));
			if (map.isEmpty())
			return Response.ok(new Viewable("/jsp/UserTimeLine",map)).build();
		
			
	
			return Response.ok(new Viewable("/jsp/UserTimeLine",map)).build();
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
	@Path("/ResponseSavePosts")
	@Produces("text/html")
	public void ResponseSavePosts(@FormParam("myEmail")String postOwner, @FormParam("postLocation") String postLocation , @FormParam("content")String content , 
			@FormParam("feelings")String feelings , @FormParam("privacy")String privacy) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/savePost";
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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}  

		

	}


}
