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
public class PagePost {
	private long pageID ;	
	private long PostID;
	private String postOwner;
	private String content;
	private String privacy;
	private String date;
	private long numberOfLikes;
	private long numberOfSeens;
	///////
	
	
	public static class Builder {
		 private long pageID ;
		 private long PostID;
		 private String postOwner;
		private String content;
		private String privacy;
		private String date;
		private long numberOfLikes;
		private long numberOfSeens;
		
		 public Builder()
		 {
			 
		 }
	 	
		 public Builder pageID(long pageID)  
	       { this.pageID = pageID;       return this; }
		 
		 public Builder postID(long postID)  
	       { PostID = postID;       return this; }
		 
		 public Builder postOwner(String postOwner)  
	       { this.postOwner = postOwner;       return this; }
		 
		 public Builder content(String content)  
	       { this.content = content;       return this; }
		
		 public Builder privacy(String privacy)  
	       { this.privacy = privacy;       return this; }
		
		 public Builder date(String date)  
	       { this.date = date;       return this; }
		
		 public Builder numberOfLikes(long numberOfLikes)  
	       { this.numberOfLikes = numberOfLikes;       return this; }
		
		 public Builder numberOfSeens(long numberOfSeens)  
	       { this.numberOfSeens = numberOfSeens;       return this; }
		
		 public PagePost build() 
		 {  
		       return new PagePost(this);
	     }
		 
	 }
	
	private PagePost(Builder builder) {
		builder.pageID=pageID;
		builder.PostID=PostID;
		builder.content=content;
		builder.date=date;
		builder.numberOfLikes=numberOfLikes;
		builder.numberOfSeens=numberOfSeens;
		builder.postOwner=postOwner;
		builder.privacy=privacy;
}
	
	
	///////
	public long getPageID() {
		return pageID;
	}
	public long getPostID() {
		return PostID;
	}
	public String getPostOwner() {
		return postOwner;
	}
	public String getContent() {
		return content;
	}
	public String getPrivacy() {
		return privacy;
	}
	public String getDate() {
		return date;
	}
	public long getNumberOfLikes() {
		return numberOfLikes;
	}
	public long getNumberOfSeens() {
		return numberOfSeens;
	}
	
	/*public PagePost(long pageID, long postID, String postOwner, String content,
			String privacy, String date, long numberOfLikes, long numberOfSeens) {
		super();
		this.pageID = pageID;
		PostID = postID;
		this.postOwner = postOwner;
		this.content = content;
		this.privacy = privacy;
		this.date = date;
		this.numberOfLikes = numberOfLikes;
		this.numberOfSeens = numberOfSeens;
	}*/
	
	@Override
	public String toString() {
		return "PagePost [pageID=" + pageID + ", PostID=" + PostID
				+ ", postOwner=" + postOwner + ", content=" + content
				+ ", privacy=" + privacy + ", date=" + date
				+ ", numberOfLikes=" + numberOfLikes + ", numberOfSeens="
				+ numberOfSeens + "]";
	}
	
	public static PagePost parsePagePostInfo(String jsonString)
	{
		JSONParser parser = new JSONParser();
	     
		try {
			JSONObject object  = (JSONObject) parser.parse(jsonString);

			PagePost pagepost = new PagePost.Builder().pageID(Long.parseLong(object.get("pageID").toString()))
					.postID(Long.parseLong(object.get("postID").toString()))
					.postOwner(object.get("postOwner").toString())
					.content(object.get("content").toString())
					.privacy(object.get("privacy").toString())
					.date(object.get("date").toString())
					.numberOfLikes(Long.parseLong(object.get("numberOfLikes").toString()))
					.numberOfSeens(Long.parseLong(object.get("numberOfSeens").toString()))
					.build();
			
			
		/*	PagePost post = new PagePost( 
					Long.parseLong(object.get("pageID").toString()), Long.parseLong(object.get("postID").toString()), 
					object.get("postOwner").toString(), object.get("content").toString(), object.get("privacy").toString(), 
					object.get("date").toString(),
					Long.parseLong(object.get("numberOfLikes").toString()), 
					Long.parseLong(object.get("numberOfSeens").toString()));*/
			return pagepost;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	// Save Post
	//
	public static void savePagePost(String pageOwner, String pageName,
			long pageID, String postOwner, String content, String privacy) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Determine post Date
        String postDate = MessageEntity.returnNowDate();
        Query gaeQuery = new Query("pagePosts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		long tempID;
        if (list.isEmpty())
        {
        	tempID = 0;
        }
        else
        {tempID = (long)list.size();}
        // Check if the page posts to itself ---> Abd El-Rahman Mohamed'S Idea
        ///////////////////////////////////////////////////////////////////////
        if (pageOwner.equals(postOwner))
        {
        	postOwner = pageName;
        }
        //////////////////////////////////////////////////////////////////////
		Entity tempRecord = new Entity("pagePosts", tempID+ 1);
		long numberOflikes = 0;
		tempRecord.setProperty("pageID",pageID);
		tempRecord.setProperty("postID",tempID+ 1);
		tempRecord.setProperty("postOwner",postOwner);
		tempRecord.setProperty("content",content);
		tempRecord.setProperty("privacy",privacy);
		tempRecord.setProperty("date",postDate);
		tempRecord.setProperty("numberOflikes",numberOflikes);
		tempRecord.setProperty("numberOfSeens", numberOflikes);
		datastore.put(tempRecord);
		// Add notification
	    String Notificationcontent = postOwner +" Has just posted at your Page Called ' "+pageName+" '";
	    NotificationsEntity.saveNotification(postOwner, pageOwner, Notificationcontent,"PagePost");
		
	}
	// Load Page Post ---> page Owner + Like User
	public static ArrayList<PagePost> loadPosts(long pageID) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList <PagePost> retPosts = new ArrayList <>();
		
		System.out.println("pageID : " +pageID);
         // Page ID filter
          Filter pageIDFilter = new FilterPredicate("pageID", FilterOperator.EQUAL,pageID);	
		// Use class Query to assemble a query
		Query gaeQuery = new Query("pagePosts").setFilter(pageIDFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			
			{
				
				PagePost pagepost = new PagePost.Builder().
						pageID(Long.parseLong(entity.getProperty("pageID").toString()))
						.postID(Long.parseLong(entity.getProperty("postID").toString()))
						.postOwner(entity.getProperty("postOwner").toString())
						.content(entity.getProperty("content").toString())
						.privacy(entity.getProperty("privacy").toString())
						.date(entity.getProperty("date").toString())
						.numberOfLikes(Long.parseLong(entity.getProperty("numberOflikes").toString()))
						.numberOfSeens(Long.parseLong(entity.getProperty("numberOfSeens").toString()))
						.build();
				
				
				/*PagePost postsRecord = new PagePost( Long.parseLong(entity.getProperty("pageID").toString())   
						, Long.parseLong(entity.getProperty("postID").toString()), 
						entity.getProperty("postOwner").toString(), entity.getProperty("content").toString(),
						entity.getProperty("privacy").toString(), entity.getProperty("date").toString(), 
						Long.parseLong(entity.getProperty("numberOflikes").toString()),
						Long.parseLong(entity.getProperty("numberOfSeens").toString()));
*/
			    retPosts.add(pagepost);
		    }
		}
         for (int i = 0; i < retPosts.size(); i++) {
			
        	 System.out.println(retPosts.get(i).toString());
		}
		return retPosts;
	}
	// Load posts --> Regular User View - (public Posts only)
	public static ArrayList<PagePost> loadPostsForUser(long pageID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList <PagePost> retPosts = new ArrayList <>();
		
		System.out.println("pageID : " +pageID);
         // Page ID filter
          Filter pageIDFilter = new FilterPredicate("pageID", FilterOperator.EQUAL,pageID);	
       // Post privacy filter
          Filter postPrivacyFilter = new FilterPredicate("privacy", FilterOperator.EQUAL,"public");	
          Filter CompositeFilter1 = CompositeFilterOperator.and(pageIDFilter, postPrivacyFilter);
		// Use class Query to assemble a query
		Query gaeQuery = new Query("pagePosts").setFilter(CompositeFilter1);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			{
	
				PagePost pagepost = new PagePost.Builder().
						pageID(Long.parseLong(entity.getProperty("pageID").toString()))
						.postID(Long.parseLong(entity.getProperty("postID").toString()))
						.postOwner(entity.getProperty("postOwner").toString())
						.content(entity.getProperty("content").toString())
						.privacy(entity.getProperty("privacy").toString())
						.date(entity.getProperty("date").toString())
						.numberOfLikes(Long.parseLong(entity.getProperty("numberOflikes").toString()))
						.numberOfSeens(Long.parseLong(entity.getProperty("numberOfSeens").toString()))
						.build();
				
				/*PagePost postsRecord = new PagePost( Long.parseLong(entity.getProperty("pageID").toString())   
						, Long.parseLong(entity.getProperty("postID").toString()), 
						entity.getProperty("postOwner").toString(), entity.getProperty("content").toString(),
						entity.getProperty("privacy").toString(), entity.getProperty("date").toString(), 
						Long.parseLong(entity.getProperty("numberOflikes").toString()),
						Long.parseLong(entity.getProperty("numberOfSeens").toString()));
*/
			    retPosts.add(pagepost);
		    }
		}
		return retPosts;
	}
}
