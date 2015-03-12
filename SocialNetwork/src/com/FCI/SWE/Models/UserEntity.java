package com.FCI.SWE.Models;

import java.util.ArrayList;
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
			// Eslam Osama Idea to Swap friendMail with uesrMail made to fix the naming mistake made in google datastore friend table
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
		}
}
