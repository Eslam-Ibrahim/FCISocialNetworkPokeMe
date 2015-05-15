package com.FCI.SWE.Models;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;

public class NotificationsEntity {
	private String senderMail;
	private String receiverMail; 
	private String content;
	private String date;
	private String type;
	

	@Override
	public String toString() {
		return "NotificationsEntity [senderMail=" + senderMail
				+ ", recieverMail=" + receiverMail + ", content=" + content
				+ ", date=" + date + ", type=" + type + "]";
	}

	public String getSenderMail() {
		return senderMail;
	}

	public String getRecieverMail() {
		return receiverMail;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		return date;
	}

	public String getType() {
		return type;
	}

	public NotificationsEntity(String senderMail, String recieverMail,String content, String date , String type) {
		super();
		this.senderMail = senderMail;
		this.receiverMail = recieverMail;
		this.content = content;
		this.date = date;
		this.type = type;
	}

	// Save Notification
	public static void saveNotification(String senderMail , String receiverMail , String content , String type) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Determine Notification Date
        String notificationDate = MessageEntity.returnNowDate();
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
		tempRecord.setProperty("content",content);
		tempRecord.setProperty("date",notificationDate);
		tempRecord.setProperty("type",type);
		datastore.put(tempRecord);
        
	}
	
	// Retrieve Notifications
	public static ArrayList <NotificationsEntity> retrieveNotifications (String receiverMail) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList<NotificationsEntity> retNotifications = new ArrayList<>();
		// Determine Notification Date
		String notificationDate = MessageEntity.returnNowDate();
        
     Filter mailFilter = new FilterPredicate("receiver", FilterOperator.EQUAL,receiverMail);	
	     Filter dateFilter = new FilterPredicate("date", FilterOperator.EQUAL,notificationDate);
		//Use CompositeFilter to combine multiple filters
		Filter CompositeFilter = CompositeFilterOperator.and(mailFilter, dateFilter);
		// Use class Query to assemble a query
		Query gaeQuery = new Query("Notifications").setFilter(CompositeFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			NotificationsEntity tempNot = new NotificationsEntity(entity.getProperty("sender").toString()
					,entity.getProperty("receiver").toString(), 
					entity.getProperty("content").toString(), entity.getProperty("date").toString(),
					entity.getProperty("type").toString());
			retNotifications.add(tempNot);
		}
		
		for (int i = 0; i < retNotifications.size(); i++) {
			
			System.out.println("runOutput: "+retNotifications.get(i).toString());
		}
        return retNotifications;
	
}
	public static NotificationsEntity parseNotificationInfo(String jsonString) {
	     
		JSONParser parser = new JSONParser();
		try {
			JSONObject object  = (JSONObject) parser.parse(jsonString);
			NotificationsEntity notification = new NotificationsEntity(
					object.get("sender").toString(), 
		            object.get("receiver").toString(), 
		            object.get("content").toString(), 
		            object.get("date").toString(),
		            object.get("type").toString());
			return notification;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}