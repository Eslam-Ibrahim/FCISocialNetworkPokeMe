package com.FCI.SWE.Services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.NotificationsEntity;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;


@Path("/")
@Produces("text/html")
public class NotificationsServices {
	
	@POST
	@Path("/retrieveNotifications")
	public String retrieveNotifications(@FormParam("myEmail") String receiverMail) {
		ArrayList<NotificationsEntity> retNotifications = new ArrayList<>();
		JSONArray retArry = new JSONArray();
		retNotifications =  NotificationsEntity.retrieveNotifications(receiverMail);
		for (NotificationsEntity notification : retNotifications)
		{
			JSONObject object = new JSONObject();
			object.put("sender", notification.getSenderMail());
			object.put("receiver", notification.getRecieverMail());
			object.put("content", notification.getContent());
			object.put("date", notification.getDate());
			object.put("type", notification.getType());
		    retArry.add(object);
		}
	
		return retArry.toJSONString();
   }

}
