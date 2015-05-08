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

import com.FCI.SWE.Models.MessageEntity;
import com.FCI.SWE.Models.NotificationsEntity;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;



@Path("/MessageController")
@Produces("text/html")
public class MessageController {
	
	
	
	
	
    @POST
		@Path("/ResponseSendMessage")
		@Produces("text/html")
		public void responseSendMessage(@FormParam("senderMail") String senderMail , @FormParam("recieverMail") String receiverMail 
				,@FormParam("messageContents") String content) {
	    //	String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/sendMessage";
			String serviceUrl = "http://localhost:8888/rest/sendMessage";
			try {
				URL url = new URL(serviceUrl);
				String urlParameters = "senderMail=" + senderMail + "&recieverMail=" + receiverMail
						+ "&messageContents=" + content;
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
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

			

		}
    
    
    

    @POST
	@Path("/ResponseRetriveMessageHistory")
	@Produces("text/html")
	public Response ResponseRetriveMessageHistory(@FormParam("receiverMail") String receiverMail , @FormParam("senderMail") String senderMail) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/RetriveMessageHistory";
		String serviceUrl = "http://localhost:8888/rest/RetriveMessageHistory";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "receiverMail=" + receiverMail+"&senderMail=" + senderMail ;
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
			Map<String, ArrayList<MessageEntity>> map = new HashMap<String, ArrayList<MessageEntity>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			ArrayList<MessageEntity> retMessages = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retMessages.add(MessageEntity.parseMessageInfo(object.toJSONString()));
				
			}
			// Dummy Message To Save the sender and receiver ---> Eslam Osama's Idea
			ArrayList<MessageEntity> userMail = new ArrayList<>();
			MessageEntity retMails = new MessageEntity(senderMail, UserController.userMail, "", "");
			userMail.add(retMails);
	        map.put("messages",retMessages);
	        map.put("mails", userMail);
			if (map.isEmpty())
				return Response.ok(new Viewable("/jsp/retrieveMessages")).build();
		
			
	
			return Response.ok(new Viewable("/jsp/retrieveMessages",map)).build();
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
