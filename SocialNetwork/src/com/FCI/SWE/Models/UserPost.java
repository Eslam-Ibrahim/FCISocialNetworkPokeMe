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
import com.FCI.SWE.Models.PagePost.Builder;
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
public class UserPost {
	private String postOwner;
	private String postLocation;
	private String content;
	private String feelings;
	private String date;
	private String privacy;
	private long numberOflikes;
	private long postID;
	////////////
	public static class Builder {
		private String postOwner;
		private String postLocation;
		private String content;
		private String feelings;
		private String date;
		private String privacy;
		private long numberOflikes;
		private long postID;
		
		 public Builder()
		 {
			 
		 }
		 
		 public Builder postOwner(String posOwner)  
	       { this.postOwner = postOwner;       return this; }
		
		 public Builder postLocation(String postLocation)  
	       { this.postLocation = postLocation;       return this; }
		
		 public Builder content(String content)  
	       { this.content = content;       return this; }
		
		 public Builder feelings(String feelings)  
	       { this.feelings = feelings;       return this; }
		
		 public Builder date(String date)  
	       { this.date = date;       return this; }
		
		 public Builder privacy(String privacy)  
	       { this.privacy = privacy;       return this; }
		
		 public Builder numberOflikes(long numberOflikes)  
	       { this.numberOflikes = numberOflikes;       return this; }
		
		 public Builder postID(long postID)  
	       { this.postID = postID;       return this; }
		
		 public UserPost build() 
		 {  
		       return new UserPost(this);
	     }
	}
	
	private UserPost(Builder builder) {
		builder.postID=postID;
		builder.feelings=feelings;
		builder.content=content;
		builder.date=date;
		builder.numberOflikes=numberOflikes;
		builder.postLocation=postLocation;
		builder.postOwner=postOwner;
		builder.privacy=privacy;
}

	
	
	
	////////////
	
	public UserPost()
	{
		postOwner="";
		postLocation="";
		content="";
		feelings="";
		date="";
		privacy="";
		numberOflikes=0;
	}
	
	public String getPostOwner() {
		return postOwner;
	}

	public void setPostOwner(String postOwner) {
		this.postOwner = postOwner;
	}

	public String getPostLocation() {
		return postLocation;
	}

	public void setPostLocation(String postLocation) {
		this.postLocation = postLocation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFeelings() {
		return feelings;
	}

	public void setFeelings(String feelings) {
		this.feelings = feelings;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public long getnumberOflikes() {
		return numberOflikes;
	}

	public void setnumberOflikes(long numberOflikes) {
		this.numberOflikes = numberOflikes;
	}

	public long getPostID() {
		return postID;
	}

	public void setPostID(long postID) {
		this.postID = postID;
	}

	// Load Posts 
	public ArrayList <UserPost> loadPosts(String userMail)	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList <UserPost> retPosts = new ArrayList <>();
		
		System.out.println("Owner&loaction: "+userMail);
         // You Are the owner of the post (Creator)
          Filter ownerMailFilter = new FilterPredicate("postOwner", FilterOperator.EQUAL,userMail);	
          // Someone posts at your timeLine 
	     Filter locationMailFilter = new FilterPredicate("postLocation", FilterOperator.EQUAL,userMail);
	  // You Are not  the owner of the post (Creator)
         Filter notOwnerMailFilter = new FilterPredicate("postOwner", FilterOperator.NOT_EQUAL,userMail);	
		//Condition #2 : Someone posts at your timeLine And You Are Not The Owner 
		Filter CompositeFilter1 = CompositeFilterOperator.and(notOwnerMailFilter, locationMailFilter);
		// combine (1) OR (2)
		Filter FinalCompositeFilter = CompositeFilterOperator.or (ownerMailFilter,CompositeFilter1);
		// Use class Query to assemble a query
		Query gaeQuery = new Query("UserPosts").setFilter(FinalCompositeFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			{
				UserPost postsRecord = new UserPost();
			    postsRecord.setPostOwner(entity.getProperty("postOwner").toString());
			    postsRecord.setContent(entity.getProperty("content").toString());
			    postsRecord.setFeelings(entity.getProperty("feelings").toString());
                postsRecord.setnumberOflikes(Long.parseLong(entity.getProperty("numberOflikes").toString()));
			    postsRecord.setDate(entity.getProperty("date").toString());
			    postsRecord.setPostID(Long.parseLong(entity.getProperty("postID").toString()));
			    retPosts.add(postsRecord);
		    }
		}
     	return retPosts;
	}
	
	// Save Post
	public  void savePost(String postOwner, String postLocation ,String content , 
	String feelings ,  String privacy) {
	
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Determine post Date
		String postDate = MessageEntity.returnNowDate();
        Query gaeQuery = new Query("UserPosts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		long tempID;
        if (list.isEmpty())
        {
        	tempID = 0;
        }
        else
        {tempID = (long)list.size();}
        
		Entity tempRecord = new Entity("UserPosts", tempID+ 1);
		long numberOflikes = 0;
		tempRecord.setProperty("postID",tempID+ 1);
		tempRecord.setProperty("postOwner",postOwner);
		tempRecord.setProperty("postLocation",postLocation);
		tempRecord.setProperty("content",content);
		tempRecord.setProperty("feelings",feelings);
		tempRecord.setProperty("numberOflikes",numberOflikes);
		tempRecord.setProperty("date",postDate);
		tempRecord.setProperty("privacy",privacy);
		datastore.put(tempRecord);
		// Add notification
		String userName = postOwner;
		if (userName.equals(postLocation))
		{
			userName = "You";
		}
	    String Notificationcontent = userName +" Has just posted at your timeline";
	    NotificationsEntity.saveNotification(postOwner, postLocation, Notificationcontent,"Post");
	}

	public static UserPost parsePostInfo(String jsonString) {
	     
		JSONParser parser = new JSONParser();
	     
		try {
			JSONObject object  = (JSONObject) parser.parse(jsonString);
			UserPost post = new UserPost();
			post.setPostOwner(object.get("postOwner").toString());
			post.setContent(object.get("content").toString());
			post.setFeelings(object.get("feelings").toString());
			post.setnumberOflikes(Long.parseLong(( object.get("numberOflikes").toString())));
			post.setDate(object.get("date").toString());
			post.setPostID(Long.parseLong( object.get("postID").toString()));
			return post;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "UserPost [postOwner=" + postOwner + ", postLocation="
				+ postLocation + ", content=" + content + ", feelings="
				+ feelings + ", date=" + date + ", privacy=" + privacy
				+ ", numberOflikes=" + numberOflikes + ", postID=" + postID
				+ "]";
	}
}