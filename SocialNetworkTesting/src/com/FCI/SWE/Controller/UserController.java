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
import com.FCI.SWE.Models.UserPost;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;

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
public class UserController {
	
	
	public static String userMail;
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return entry point page (Home page of this application)
	 */
	@GET
	@Path("/Entry")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}
	@GET
	@Path("/backtoEntryPoint")
	public Response signOut() {
		UserController.userMail = "";
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}
	
	@GET
	@Path("/backHome")
	public Response backHome() {
		// Update here by Eslam Osama - current User Mail was not transfered to home page again. (loosing Data) 
		 Map<String, String> map = new HashMap<String, String>();
		    map.put("email",UserController.userMail);
		return Response.ok(new Viewable("/jsp/home",map)).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	@GET
	@Path("/searchFriend")
	public Response searchFriend() {
		return Response.ok(new Viewable("/jsp/searchFriend")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/RegistrationService";
		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&email=" + email
					+ "&password=" + pass;
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
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			System.out.println(retJson);
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
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
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	// coming from searchfriend.jsp
	@POST
	@Path("/ResponseSendFriendRequest")
	@Produces("text/html")
	public Response SendFriendRequest(@FormParam("myEmail") String myEmail , @FormParam("friendEmail") String friendEmail) {
		// service to search friend
		System.out.println("I am here");
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/SendFriendRequest";
		String serviceUrl = "http://localhost:8888/rest/SendFriendRequest";
		try {
			System.out.println("I am here in try");
			URL url = new URL(serviceUrl);
			String urlParameters = "myEmail=" + myEmail + "&friendEmail=" + friendEmail
					;
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
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
			return Response.ok(new Viewable("/jsp/searchUserFailed")).build();
			
			return Response.ok(new Viewable("/jsp/SendFriendRequest")).build();
			
			
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

		return null;

	}
	
	
	
	
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/LoginService";
		String serviceUrl = "http://localhost:8888/rest/LoginService";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&password=" + pass;
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
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			UserController.userMail = user.getEmail();
			System.out.println(UserController.userMail+"login");
			return Response.ok(new Viewable("/jsp/home", map)).build();
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
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}

	


	@POST
	@Path("/ResponseRetrieveFriendRequests")
	@Produces("text/html")
	public Response retrieveFriendRequests(@FormParam("myEmail") String myEmail) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/retrieveFriendRequests";
		String serviceUrl = "http://localhost:8888/rest/retrieveFriendRequests";
		
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
			Map<String, ArrayList<UserEntity>> map = new HashMap<String, ArrayList<UserEntity>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			ArrayList<UserEntity> retNames = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retNames.add(UserEntity.parseUserInfo(object.toJSONString()));
			}
			// Dummy User to save current user mail ---> Eslam Osama's Idea
			ArrayList<UserEntity> userMail = new ArrayList<>();
			UserEntity retMail = new UserEntity("", UserController.userMail, "", "");
		    userMail.add(retMail);
		    map.put("requests",retNames);
		    map.put("mail", userMail);

			if (map.isEmpty())
			return Response.ok(new Viewable("/jsp/AcceptFriendRequest")).build();
		
			
	
			return Response.ok(new Viewable("/jsp/AcceptFriendRequest",map)).build();
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
	@Path("/ResponseAcceptFriendRequest")
	@Produces("text/html")
	public void acceptFriendRequest(@FormParam("myEmail") String myEmail , @FormParam("friendEmail") String friendEmail) {
		//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/AcceptFriendRequest";
		String serviceUrl = "http://localhost:8888/rest/AcceptFriendRequest";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "myEmail=" + myEmail + "&friendEmail=" + friendEmail
					;
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
		@Path("/ResponseRetrieveFriends")
		@Produces("text/html")
		public Response retrieveFriends(@FormParam("myEmail") String myEmail) {
	    	//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/retrieveFriends";
			String serviceUrl = "http://localhost:8888/rest/retrieveFriends";
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
				Map<String, ArrayList<UserEntity>> map = new HashMap<String, ArrayList<UserEntity>>();
				while ((line = reader.readLine()) != null) {
					retjson+=line;
				}
				writer.close();
				reader.close();
				JSONParser parser = new JSONParser();
				JSONArray retArr = (JSONArray) parser.parse(retjson);
				ArrayList<UserEntity> retNames = new ArrayList<>();
				for (int i = 0; i < retArr.size(); ++i) {
					JSONObject object  = (JSONObject) retArr.get(i);
					retNames.add(UserEntity.parseUserInfo(object.toJSONString()));
				}
				// Dummy User to save current user mail ---> Eslam Osama's Idea
				ArrayList<UserEntity> userMail = new ArrayList<>();
				UserEntity retMail = new UserEntity("", UserController.userMail, "", "");
			    userMail.add(retMail);
			    map.put("mails",retNames);
			    map.put("mail", userMail);

			 	

			    if (map.isEmpty()) 
				{return Response.ok(new Viewable("/jsp/retrieveFriendsRetrieveMessages")).build();}

				return Response.ok(new Viewable("/jsp/retrieveFriendsRetrieveMessages",map)).build();
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
	    
	    
	    //retrieve friends to access their timeLine
	    @POST
		@Path("/ResponseViewFriends")
		@Produces("text/html")
		public Response ResponseViewFriends(@FormParam("myEmail") String myEmail) {
	    	//String serviceUrl = "http://pokemesocailnetwork.appspot.com/rest/retrieveFriends";
			String serviceUrl = "http://localhost:8888/rest/retrieveFriends";
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
				Map<String, ArrayList<UserEntity>> map = new HashMap<String, ArrayList<UserEntity>>();
				while ((line = reader.readLine()) != null) {
					retjson+=line;
				}
				writer.close();
				reader.close();
				JSONParser parser = new JSONParser();
				JSONArray retArr = (JSONArray) parser.parse(retjson);
				ArrayList<UserEntity> retNames = new ArrayList<>();
				for (int i = 0; i < retArr.size(); ++i) {
					JSONObject object  = (JSONObject) retArr.get(i);
					retNames.add(UserEntity.parseUserInfo(object.toJSONString()));
				}
				// Dummy User to save current user mail ---> Eslam Osama's Idea
				ArrayList<UserEntity> userMail = new ArrayList<>();
				UserEntity retMail = new UserEntity("", UserController.userMail, "", "");
			    userMail.add(retMail);
			    map.put("mails",retNames);
			    map.put("mail", userMail);

			 	

			    if (map.isEmpty()) 
				{return Response.ok(new Viewable("/jsp/Friends")).build();}

				return Response.ok(new Viewable("/jsp/Friends",map)).build();
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