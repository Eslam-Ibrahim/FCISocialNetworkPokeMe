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
		UserEntity.saveNotification(this.email, this.email, content);
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
	    UserEntity.saveNotification(myEmail, friendEmail, content);
	}
	
	
	// Retrieve Friend Requests
	public static ArrayList <String> retrieveFriendRequests(String UserMail) {
		ArrayList <String> requestNames = new ArrayList<>();
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
		requestNames.add(entity.getProperty("myMail").toString());
		}
		for (int i = 0; i < requestNames.size(); i++) {
			System.out.println(requestNames.get(i));
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
		    UserEntity.saveNotification(UserMail, friendMail, content);
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
		
		// Save Notification
				public static void saveNotification(String senderMail , String receiverMail , String content) {
					
					DatastoreService datastore = DatastoreServiceFactory
							.getDatastoreService();
					// Determine Notification Date
					DateFormat newDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		            Date newDate = new Date();
		            newDateFormat.format(newDate);
		            int date = newDate.getDate();
		            int month = newDate.getMonth() + 1;
		            int year = newDate.getYear() + 1900;
		            String notificationDate = date + "/" + month + "/" + year;
		            Query gaeQuery = new Query("Notifications");
		    		PreparedQuery pq = datastore.prepare(gaeQuery);
		    		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		    		int tempID;
		            if (list.isEmpty())
		            {
		            	tempID = 0;
		            }
		            else
		            {tempID = list.size();}
		            
		    		Entity tempRecord = new Entity("Notifications", tempID+ 1);

		    		tempRecord.setProperty("sender", senderMail);
		    		tempRecord.setProperty("receiver",receiverMail );
		    		tempRecord.setProperty("date",notificationDate);
		    		tempRecord.setProperty("content",content);
		    		datastore.put(tempRecord);
		            
				}
				
				// Retrieve Notifications
				public static ArrayList <String> retrieveNotifications (String receiverMail) {
					
					DatastoreService datastore = DatastoreServiceFactory
							.getDatastoreService();
					ArrayList<String> retNotifications = new ArrayList<>();
					// Determine Notification Date
					DateFormat newDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		            Date newDate = new Date();
		            newDateFormat.format(newDate);
		            int date = newDate.getDate();
		            int month = newDate.getMonth() + 1;
		            int year = newDate.getYear() + 1900;
		            String notificationDate = date + "/" + month + "/" + year;
		            
		         Filter mailFilter = new FilterPredicate("receiver", FilterOperator.EQUAL,receiverMail);	
		   	     Filter dateFilter = new FilterPredicate("date", FilterOperator.EQUAL,notificationDate);
		   		//Use CompositeFilter to combine multiple filters
		   		Filter CompositeFilter = CompositeFilterOperator.and(mailFilter, dateFilter);
		   		// Use class Query to assemble a query
		   		Query gaeQuery = new Query("Notifications").setFilter(CompositeFilter);
		   		PreparedQuery pq = datastore.prepare(gaeQuery);
		   		for (Entity entity : pq.asIterable()) {
		   			String notificationContent="";
		   			notificationContent = entity.getProperty("content").toString() + " --> ";
		   			notificationContent += entity.getProperty("date").toString();
		   			retNotifications.add(notificationContent);
		   		}
		   		
		   		for (int i = 0; i < retNotifications.size(); i++) {
		   			
		   			System.out.println("runOutput: "+retNotifications.get(i));
		   		}
		            return retNotifications;
				
         }
				
		// Retrieve Friends for single chat
				public static ArrayList <String> retrieveFriendsForSingleChat(String UserMail) {
					ArrayList <String> requestNames = new ArrayList<>();
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
					requestNames.add(entity.getProperty("friendMail").toString());
					}
					for (int i = 0; i < requestNames.size(); i++) 
					{
						System.out.println("retrieveChatMail"+requestNames.get(i));
					}
					return requestNames;
				}
				
				// Save Message
				public static void saveMessage(String senderMail , String receiverMail , String content) {
					
					DatastoreService datastore = DatastoreServiceFactory
							.getDatastoreService();
					// Determine Message Date
					DateFormat newDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		            Date newDate = new Date();
		            newDateFormat.format(newDate);
		            int date = newDate.getDate();
		            int month = newDate.getMonth() + 1;
		            int year = newDate.getYear() + 1900;
		            String MessageDate = date + "/" + month + "/" + year;
		            Query gaeQuery = new Query("Messages");
		    		PreparedQuery pq = datastore.prepare(gaeQuery);
		    		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		    		int tempID;
		            if (list.isEmpty())
		            {
		            	tempID = 0;
		            }
		            else
		            {tempID = list.size();}
		            
		    		Entity tempRecord = new Entity("Messages", tempID+ 1);

		    		tempRecord.setProperty("sender", senderMail);
		    		tempRecord.setProperty("receiver",receiverMail );
		    		tempRecord.setProperty("date",MessageDate);
		    		tempRecord.setProperty("content",content);
		    		datastore.put(tempRecord);
		    		// Add notification
		    	    String Notificationcontent = senderMail+" Has just sent to you a Message";
		    	    UserEntity.saveNotification(senderMail, receiverMail, Notificationcontent);
				}
				
				// Retrieve Messages
				public static ArrayList <String> retrieveMessages (String receiverMail , String senderMail) {
					
					DatastoreService datastore = DatastoreServiceFactory
							.getDatastoreService();
					ArrayList<String> retMessages = new ArrayList<>();
		          // Friend --> current user  (1)
		         Filter senderMailFilter = new FilterPredicate("sender", FilterOperator.EQUAL,senderMail);	
		   	     Filter recieverMailFilter = new FilterPredicate("receiver", FilterOperator.EQUAL,receiverMail);
		   		//Use CompositeFilter to combine multiple filters
		   		Filter CompositeFilter1 = CompositeFilterOperator.and(senderMailFilter, recieverMailFilter);
		   		// current user --> Friend   (2)
		   		Filter senderMailFilter2 = new FilterPredicate("sender", FilterOperator.EQUAL,receiverMail);	
		   	     Filter recieverMailFilter2 = new FilterPredicate("receiver", FilterOperator.EQUAL,senderMail);
		   		//Use CompositeFilter to combine multiple filters
		   		Filter CompositeFilter2 = CompositeFilterOperator.and(senderMailFilter2, recieverMailFilter2);
		   		// combine (1) OR (2)
		   		Filter FinalCompositeFilter = CompositeFilterOperator.or (CompositeFilter1,CompositeFilter2);
		   		// Use class Query to assemble a query
		   		Query gaeQuery = new Query("Messages").setFilter(FinalCompositeFilter);
		   		PreparedQuery pq = datastore.prepare(gaeQuery);
		   		//System.out.println("senderMail-> "+senderMail + " ,receiverMail-> "+receiverMail);
		   		for (Entity entity : pq.asIterable()) {
		   			//System.out.println("I am Here in retMessage");
		   			//if((entity.getProperty("sender").equals(senderMail)&&entity.getProperty("receiver").equals(receiverMail))||
		   		      //  (entity.getProperty("sender").equals(receiverMail)&&entity.getProperty("receiver").equals(senderMail)))
		   			{
		   				//System.out.println("I am Here in retMessage condition");
		   			String messageRecord="";
		   			messageRecord = entity.getProperty("sender").toString() + " Said: ";
		   			messageRecord += entity.getProperty("content").toString() + " on: ";
		   			messageRecord += entity.getProperty("date").toString();
		   			retMessages.add(messageRecord);
		   		    }
		   		}
		            return retMessages;
				
         }
				
				
				
				
				
				
				
				
}



