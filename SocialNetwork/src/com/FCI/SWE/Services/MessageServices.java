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
import com.FCI.SWE.Models.MessageEntity;
import com.FCI.SWE.Models.NotificationsEntity;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;


@Path("/")
@Produces("text/html")
public class MessageServices {
	
	
	@POST
	@Path("/sendMessage")
	public String sendMessage(@FormParam("senderMail") String senderMail , @FormParam("recieverMail") String receiverMail 
	,@FormParam("messageContents") String content) {
		JSONObject object = new JSONObject();
		MessageEntity.saveMessage(senderMail, receiverMail, content);
		object.put("Status", "OK");
		return object.toString();
		
}
	
	
	@POST
	@Path("/RetriveMessageHistory")
	public String RetriveMessageHistory(@FormParam("receiverMail") String receiverMail , @FormParam("senderMail") String senderMail) {
		ArrayList<MessageEntity> retMessages = new ArrayList<>();
		JSONArray retArry = new JSONArray();
		retMessages = MessageEntity.retrieveMessages(receiverMail, senderMail);
		for (MessageEntity message : retMessages)
		{
			JSONObject object = new JSONObject();
			object.put("sender", message.getSenderMail());
			object.put("receiver", message.getReceiverMail());
			object.put("content", message.getContent());
			object.put("date", message.getDate());
		    retArry.add(object);
		}
	
		return retArry.toJSONString();
}	
	

}
