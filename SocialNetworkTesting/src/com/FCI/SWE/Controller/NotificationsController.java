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
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;



@Path("/notificationController")
@Produces("text/html")
public class NotificationsController {

	
	
	
	

	@POST
	@Path("/ResponseRetrieveNotifications")
	@Produces("text/html")
	public Response retrieveNotifications(@FormParam("myEmail") String myEmail) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/retrieveNotifications";
		String serviceUrl = "http://localhost:8888/rest/retrieveNotifications";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters ="myEmail=" + myEmail;
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
			// Dummy Notification to save current user mail ---> Eslam Osama's Idea
			ArrayList<NotificationsEntity> userMail = new ArrayList<>();
		    NotificationsEntity retMail = new NotificationsEntity(UserController.userMail, "", "", "", "");
			userMail.add(retMail);
	        map.put("notifications",retNotifications);
	        map.put("mail", userMail);
			if (map.isEmpty())
				return Response.ok(new Viewable("/jsp/retrieveNotifications")).build();
			
			return Response.ok(new Viewable("/jsp/retrieveNotifications",map)).build();
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

}
