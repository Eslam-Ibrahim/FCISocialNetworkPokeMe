package com.FCI.SWE.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;


/**
 *done
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author T.A.Mohamed Samir & Eslam Osama Mohamed & Mostafa El-Sayad & Abd El-Rahman Mohamed & Mostafa Nasser
 * @version 1.0 - 1.2
 * @since 2014-02-12 to 2015-03-09
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private String ID;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password , String ID) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.ID = ID;

	}
	

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}
	public String getID() {
		return ID;
	}

	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new UserEntity(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString(),"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString(),"");
				return returnedUser;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", email=" + email + ", password="
				+ password + ", ID=" + ID + "]";
	}


	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("users", list.size() + 1);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);
        // Add notification
		String content = "You Have Joind PokeME!";
		NotificationsEntity.saveNotification(this.email, this.email, content , "Join");
		return true;

	}
	// search for user to use it in send friend request

	public static UserEntity searchUser(String email) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("email").toString().equals(email)) {
				
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(),"","");
				return returnedUser;
			}
		}

		return null;
	}
	
	
	
	
	public static void sendFriendRequest(String myEmail , String friendEmail) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Mustafa El-Sayad Idea to create tables using Query :)
		Query gaeQuery = new Query("Friends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int tempID;
        if (list.isEmpty())
        {
        	tempID = 0;
        }
        else
        {tempID = list.size();}
        
		Entity tempRecord = new Entity("Friends", tempID+ 1);

		tempRecord.setProperty("myMail", myEmail);
		tempRecord.setProperty("friendMail",friendEmail );
		tempRecord.setProperty("Status",0);
		datastore.put(tempRecord);
		// Add notification
	    String content = myEmail+" Has just sent to you a Friend Request";
	    NotificationsEntity.saveNotification(myEmail, friendEmail, content , "Request");
	}
	
	
	// Retrieve Friend Requests
	public static ArrayList <UserEntity> retrieveFriendRequests(String UserMail) {
		ArrayList <UserEntity> requestNames = new ArrayList<>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Eslam Osama Idea to Swap friendMail with uesrMail made to fix the naming mistake made in google datastore friend table
		Filter mailFilter = new FilterPredicate("friendMail", FilterOperator.EQUAL,UserMail);
	
	     Filter statusFilter = new FilterPredicate("Status", FilterOperator.LESS_THAN_OR_EQUAL,0);
		//Use CompositeFilter to combine multiple filters
		Filter CompositeFilter = CompositeFilterOperator.and(mailFilter, statusFilter);
		// Use class Query to assemble a query
		Query gaeQuery = new Query("Friends").setFilter(CompositeFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
		UserEntity temp = new UserEntity("", entity.getProperty("myMail").toString(), "", "");
		requestNames.add(temp);
		}
		for (int i = 0; i < requestNames.size(); i++) {
			System.out.println(requestNames.get(i).toString());
		}
		return requestNames;
	}
	
	
	
	// Accept Friend Request
		public static void AddFriend(String UserMail , String friendMail) {
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			// Eslam Osama Idea to Swap friendMail with uesrMail , made to fix the naming mistake made in google datastore friend table
			Filter mailFilter = new FilterPredicate("friendMail", FilterOperator.EQUAL,UserMail);
		
		     Filter friendMailFilter = new FilterPredicate("myMail", FilterOperator.EQUAL,friendMail);
			//Use CompositeFilter to combine multiple filters
			Filter CompositeFilter = CompositeFilterOperator.and(mailFilter, friendMailFilter);
			// Use class Query to assemble a query
			Query gaeQuery = new Query("Friends").setFilter(CompositeFilter);
			PreparedQuery pq = datastore.prepare(gaeQuery);
			Entity result = pq.asSingleEntity();
			result.setProperty("myMail",UserMail);
			result.setProperty("friendMail",friendMail);
			result.setProperty("Status",1);
			datastore.put(result);
			// Add notification
		    String content = UserMail+" Has just Accepted your Friend Request";
		    NotificationsEntity.saveNotification(UserMail, friendMail, content,"Accept");
                 //////////////////////////////////////////////////DUPLICATING ROW - Two Ways FriendShip/////////////////////////
            Query gaeQuery2 = new Query("Friends");
            PreparedQuery pq2 = datastore.prepare(gaeQuery2);
            List<Entity> list = pq2.asList(FetchOptions.Builder.withDefaults());
            int tempID;
            if (list.isEmpty())
             {
              tempID = 0;
             }
             else
             {tempID = list.size();}

            Entity tempRecord = new Entity("Friends", tempID+ 1);

            tempRecord.setProperty("myMail", friendMail);
            tempRecord.setProperty("friendMail",UserMail);
            tempRecord.setProperty("Status",1);
            datastore.put(tempRecord);
}
		

				
		// Retrieve Your Friends List 
				public static ArrayList <UserEntity> retrieveFriendsList(String UserMail) {
					ArrayList <UserEntity> requestNames = new ArrayList<>();
					DatastoreService datastore = DatastoreServiceFactory
							.getDatastoreService();
					// Eslam Osama Idea to Swap friendMail with uesrMail made to fix the naming mistake made in google datastore friend table
					
					Filter mailFilter = new FilterPredicate("myMail", FilterOperator.EQUAL,UserMail);
					
				     Filter statusFilter = new FilterPredicate("Status", FilterOperator.EQUAL,1);
					//Use CompositeFilter to combine multiple filters
					
				     Filter CompositeFilter = CompositeFilterOperator.and(mailFilter, statusFilter);

					// Use class Query to assemble a query
					Query gaeQuery = new Query("Friends").setFilter(CompositeFilter);
					PreparedQuery pq = datastore.prepare(gaeQuery);
					for (Entity entity : pq.asIterable()) {
						
					UserEntity temp = new UserEntity("", entity.getProperty("friendMail").toString(), "", "");
					requestNames.add(temp);
					}
					
					for (int i = 0; i < requestNames.size(); i++) 
					{
						System.out.println("retrieveChatMails"+requestNames.get(i).toString());
					}
					return requestNames;
				}
								
				
				
				public static UserEntity parseUserInfo(String jsonString) {
				     
					JSONParser parser = new JSONParser();
				     
					try {
						JSONObject object  = (JSONObject) parser.parse(jsonString);
						UserEntity user = new UserEntity("", object.get("email").toString(), "", "");
						return user;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return null;
				}
		
				
				
				
				
				
				
}



