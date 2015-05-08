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




public class MessageEntity {

	private String senderMail;
	private String receiverMail;
	private String content;
	private String date;
	public MessageEntity(String senderMail, String receiverMail,
			String content, String date) {
		super();
		this.senderMail = senderMail;
		this.receiverMail = receiverMail;
		this.content = content;
		this.date = date;
	}
	public String getSenderMail() {
		return senderMail;
	}
	public String getReceiverMail() {
		return receiverMail;
	}
	public String getContent() {
		return content;
	}
	public String getDate() {
		return date;
	}
	@Override
	public String toString() {
		return "MessageEntity [senderMail=" + senderMail + ", receiverMail="
				+ receiverMail + ", content=" + content + ", date=" + date
				+ "]";
	}
	
	

	// Save Message
	public static boolean saveMessage(String senderMail , String receiverMail , String content) {
		
		if (senderMail.equals("")||receiverMail.equals("")||content.equals(""))
		{
			return false;
		}
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
	    NotificationsEntity.saveNotification(senderMail, receiverMail, Notificationcontent,"Message");
		return true;
	}
	
	
	// Retrieve Messages
	public static ArrayList <MessageEntity> retrieveMessages (String receiverMail , String senderMail) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		System.out.println("sender: "+senderMail);
		System.out.println("receiver: "+receiverMail);
		ArrayList<MessageEntity> retMessages = new ArrayList<>();
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
			{
				//System.out.println("I am Here in retMessage condition");
				MessageEntity messageRecord = new MessageEntity(entity.getProperty("sender").toString(), 
						 entity.getProperty("receiver").toString(), entity.getProperty("content").toString(), 
						 entity.getProperty("date").toString());
			retMessages.add(messageRecord);
		    }
		}
		for (int i = 0; i < retMessages.size(); i++) {
			System.out.println(retMessages.get(i));
		}
        return retMessages;
	
     }
	
	public static MessageEntity parseMessageInfo(String jsonString )
	{
		  
		JSONParser parser = new JSONParser();
	     
		try {
			JSONObject object  = (JSONObject) parser.parse(jsonString);
			MessageEntity ms = new MessageEntity(object.get("sender").toString(), 
					object.get("receiver").toString(), 
					object.get("content").toString(), 
					object.get("date").toString());
			return ms;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
}
