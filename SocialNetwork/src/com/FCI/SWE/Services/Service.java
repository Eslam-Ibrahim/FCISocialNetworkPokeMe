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

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author T.A.Mohamed Samir & Eslam Osama Mohamed & Mostafa El-Sayad & Abd El-Rahman Mohamed & Mostafa Nasser
 * @version 1.0 - 1.2
 * @since 2014-02-12 to 2015-03-09
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass , "");
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	
	@POST
	@Path("/SendFriendRequest")
	public String SendFriendRequest(@FormParam("myEmail") String myEmail , @FormParam("friendEmail") String friendEmail) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.searchUser(friendEmail);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			UserEntity.sendFriendRequest( myEmail ,  friendEmail);
			
		}

		return object.toString();

	}
	
	
	@POST
	@Path("/AcceptFriendRequest")
	public String acceptFriendRequest(@FormParam("myEmail") String myEmail , @FormParam("friendEmail") String friendEmail) {	
		JSONObject object = new JSONObject();
		UserEntity.AddFriend(myEmail, friendEmail);
		object.put("Status", "OK");
		return object.toString();
		
	}
	
	
	@POST
	@Path("/retrieveFriendRequests")
	public String retrieveFriendRequests(@FormParam("myEmail") String myEmail) {
		JSONObject object = new JSONObject();
	
		object.put("Email", UserEntity.retrieveFriendRequests(myEmail));
		System.out.println(object.toString());
		return object.toString();
	

}
	
	@POST
	@Path("/retrieveNotifications")
	public String retrieveNotifications(@FormParam("myEmail") String receiverMail) {
		JSONObject object = new JSONObject();
	
		object.put("Notification", UserEntity.retrieveNotifications(receiverMail));
		System.out.println(object.toString());
		return object.toString();
}
	@POST
	@Path("/sendMessage")
	public String sendMessage(@FormParam("senderMail") String senderMail , @FormParam("recieverMail") String receiverMail 
	,@FormParam("messageContents") String content) {
		JSONObject object = new JSONObject();
		UserEntity.saveMessage(senderMail, receiverMail, content);
		object.put("Status", "OK");
		return object.toString();
		
}
	
	@POST
	@Path("/retrieveFriendsForSingleChat")
	public String retrieveFriendsForSingleChat(@FormParam("myEmail") String myEmail) {
		JSONObject object = new JSONObject();
		object.put("Email", UserEntity.retrieveFriendsForSingleChat(myEmail));
		System.out.println(object.toString());
		return object.toString();
	

}
	@POST
	@Path("/retrieveFriendsRetrieveMessages")
	public String retrieveFriendsRetrieveMessages(@FormParam("myEmail") String myEmail) {
		JSONObject object = new JSONObject();
		object.put("Email", UserEntity.retrieveFriendsForSingleChat(myEmail));
		System.out.println(object.toString());
		return object.toString();
	

	}

	
	@POST
	@Path("/RetriveMessageHistory")
	public String RetriveMessageHistory(@FormParam("receiverMail") String receiverMail , @FormParam("senderMail") String senderMail) {
		JSONObject object = new JSONObject();
		object.put("Messages", UserEntity.retrieveMessages(receiverMail, senderMail));
		System.out.println(object.toString());
		return object.toString();
}	
	
}