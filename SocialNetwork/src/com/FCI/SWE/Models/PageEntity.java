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
public class PageEntity {
	
	private String pageOwner;
	private String pageName;
	private String type;
	private String category;
	private long numberOfLikes;
	private long pageID;
	private long numberOfActiveUsers;
	///////
	public static class Builder {
		private String pageOwner;
		private String pageName;
		private String type;
		private String category;
		private long numberOfLikes;
		private long pageID;
		private long numberOfActiveUsers;
			
		 public Builder()
		 {
			 
		 }
		 
		 public Builder pageOwner(String pageOwner)  
	       { this.pageOwner = pageOwner;       return this; }
		 
		 public Builder pageName(String pageName)  
	       { this.pageName = pageName;       return this; }
		 
		 public Builder type(String type)  
	       { this.type = type;       return this; }
		 
		 public Builder category(String category)  
	       { this.category = category;       return this; }
		 
		 public Builder numberOfLikes(long numberOfLikes)  
	       { this.numberOfLikes = numberOfLikes;       return this; }
		 
		 public Builder pageID(long pageID)  
	       { this.pageID = pageID; 		return this; }
		 
		 public Builder numberOfActiveUsers(long numberOfActiveUsers)  
	       { this.numberOfActiveUsers = numberOfActiveUsers;       return this; }
		 
		 public PageEntity build() 
		 {  
		       return new PageEntity(this);
	     }
		 
	 }
	//
	
	//
	private PageEntity(Builder builder) {
		builder.pageID=pageID;
		builder.pageOwner=pageOwner;
		builder.pageName=pageName;
		builder.type=type;
		builder.numberOfLikes=numberOfLikes;
		builder.category=category;
		builder.numberOfActiveUsers=numberOfActiveUsers;
}

	/*public PageEntity(String pageOwner, String pageName, String type,
			String category, long numberOfLikes, long pageID,
			long numberOfActiveUsers) {
		super();
		this.pageOwner = pageOwner;
		this.pageName = pageName;
		this.type = type;
		this.category = category;
		this.numberOfLikes = numberOfLikes;
		this.pageID = pageID;
		this.numberOfActiveUsers = numberOfActiveUsers;
	}*/
		public PageEntity() {
			// TODO Auto-generated constructor stub
			pageOwner = "";
			pageName = "";
			type = "";
			category ="";
			numberOfLikes = 0;
			pageID = 0;
			numberOfActiveUsers = 0;
		}
	@Override
	public String toString() {
		return "PageEntity [pageOwner=" + pageOwner + ", pageName=" + pageName
				+ ", type=" + type + ", category=" + category
				+ ", numberOfLikes=" + numberOfLikes + ", pageID=" + pageID
				+ ", numberOfActiveUsers=" + numberOfActiveUsers + "]";
	}
		public String getPageOwner() {
		return pageOwner;
	}
	public String getPageName() {
		return pageName;
	}
	public String getType() {
		return type;
	}
	public String getCategory() {
		return category;
	}
	public long getNumberOfLikes() {
		return numberOfLikes;
	}
	public long getPageID() {
		return pageID;
	}
	public long getNumberOfActiveUsers() {
		return numberOfActiveUsers;
	}
	public void setPageOwner(String userMail) {
		this.pageOwner = userMail;
	}
		// Create Page 
		public  void createPage (String pageOwner, String pageName, String type,String category) 
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
	        Query gaeQuery = new Query("Pages");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			long tempID;
	        if (list.isEmpty())
	        {
	        	tempID = 0;
	        }
	        else
	        {tempID = list.size();}
	        
			Entity tempRecord = new Entity("Pages", tempID+ 1);
			tempRecord.setProperty("pageID",tempID+ 1);
			tempRecord.setProperty("pageOwner",pageOwner);
			tempRecord.setProperty("pageName",pageName);
			tempRecord.setProperty("type",type);
			tempRecord.setProperty("category",category);
			tempRecord.setProperty("numberOflikes",0);
			tempRecord.setProperty("numberOfActiveUsers",0);
			datastore.put(tempRecord);
			// Add notification
		    String Notificationcontent = " You Has just Created a page named ' "+pageName+" '";
		    NotificationsEntity.saveNotification(pageOwner, pageOwner, Notificationcontent,"PageCreation");
		}
		// List Pages for owner 
		public ArrayList<PageEntity>listPagesForOwner(String pageOwner)
		{

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			ArrayList<PageEntity> retPages = new ArrayList<>();
			System.out.println("Owner: "+pageOwner);
	         // You Are the owner of the post (Creator)
	          Filter ownerMailFilter = new FilterPredicate("pageOwner", FilterOperator.EQUAL,pageOwner);	
			Query gaeQuery = new Query("Pages").setFilter(ownerMailFilter);
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				{
					//System.out.println("I am Here in retMessage condition");
					/*PageEntity pageRecord = new PageEntity(entity.getProperty("pageOwner").toString(), entity.getProperty("pageName").toString(),
							entity.getProperty("type").toString(), entity.getProperty("category").toString(), 
							Long.parseLong(entity.getProperty("numberOflikes").toString()), 
							Long.parseLong(entity.getProperty("pageID").toString()), 
							Long.parseLong(entity.getProperty("numberOfActiveUsers").toString()));*/
					
					PageEntity pageRecord = new PageEntity.Builder().
							pageID(Long.parseLong(entity.getProperty("pageID").toString()))
							.pageOwner(entity.getProperty("pageOwner").toString())
							.pageName(entity.getProperty("pageName").toString())
							.type(entity.getProperty("type").toString())
							.category(entity.getProperty("category").toString())
							.numberOfLikes(Long.parseLong(entity.getProperty("numberOflikes").toString()))
							.numberOfActiveUsers(Long.parseLong(entity.getProperty("numberOfActiveUsers").toString()))
							.build();
		
					
					retPages.add(pageRecord);
				    }
				}
			return retPages;
			}

		// Search For page by type - category 
		public ArrayList<PageEntity>searchForPagebyTypeAndCategory(String userMail , String type , String category)
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			ArrayList<PageEntity> retPages = new ArrayList<>();
	         // You Are not  the owner of the page (Creator)
	          Filter notOwnerMailFilter = new FilterPredicate("pageOwner", FilterOperator.NOT_EQUAL,userMail);	
	          Filter typeFilter = new FilterPredicate("type", FilterOperator.EQUAL,type);	
	          Filter categoryFilter = new FilterPredicate("category", FilterOperator.EQUAL,category);
	          Filter CompositeFilter1 = CompositeFilterOperator.and(notOwnerMailFilter, typeFilter,categoryFilter);
			Query gaeQuery = new Query("Pages").setFilter(CompositeFilter1);
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				{
					PageEntity pageRecord = new PageEntity.Builder().
							pageID(Long.parseLong(entity.getProperty("pageID").toString()))
							.pageOwner(entity.getProperty("pageOwner").toString())
							.pageName(entity.getProperty("pageName").toString())
							.type(entity.getProperty("type").toString())
							.category(entity.getProperty("category").toString())
							.numberOfLikes(Long.parseLong(entity.getProperty("numberOflikes").toString()))
							.numberOfActiveUsers(Long.parseLong(entity.getProperty("numberOfActiveUsers").toString()))
							.build();
		
					
					retPages.add(pageRecord);
				    }
				}
			return retPages;
			
		}
	
		public static PageEntity parsePageInfo(String jsonString) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject object  = (JSONObject) parser.parse(jsonString);
				
				PageEntity pageRecord = new PageEntity.Builder()
						.pageOwner(object.get("pageOwner").toString())
						.pageName(object.get("pageName").toString())
						.type(object.get("type").toString())
						.category(object.get("category").toString())
						.numberOfLikes(Long.parseLong(object.get("numberOfLikes").toString()))
						.pageID(Long.parseLong(object.get("pageID").toString()))
						.numberOfActiveUsers(Long.parseLong(object.get("numberOfActiveUsers").toString()))
						.build();
				/*
				PageEntity page = new PageEntity(
						object.get("pageOwner").toString(), 
			            object.get("pageName").toString(),
			            object.get("type").toString(),
			            object.get("category").toString(), 
			            Long.parseLong(object.get("numberOfLikes").toString()) ,
			            Long.parseLong (object.get("pageID").toString()),
			            Long.parseLong(object.get("numberOfActiveUsers").toString())
						);*/
				return pageRecord;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}
		
		public static void likePageAddRecord(long pageID , String likeOwner , String pageName , String pageOwner)
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Query gaeQuery = new Query("PagesLike");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			long tempID;
	        if (list.isEmpty())
	        	tempID = 0;
	        else
	        	tempID = list.size();
	        
			Entity tempRecord = new Entity("PagesLike", tempID+ 1);
			tempRecord.setProperty("pageID",pageID);
			tempRecord.setProperty("pageName",pageName);
			tempRecord.setProperty("likeOwner",likeOwner);
			tempRecord.setProperty("pageOwner",pageOwner);
			datastore.put(tempRecord);	
		}
		
		public static void likePageIncreaseLikeCounter(long pageID , String likeOwner , String pageName , String pageOwner)
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			
			Filter pageIDFilter = new FilterPredicate("pageID", FilterOperator.EQUAL,pageID);
		    Query gaeQuery2 = new Query("Pages").setFilter(pageIDFilter);
			PreparedQuery pq2 = datastore.prepare(gaeQuery2);
			Entity result = pq2.asSingleEntity();		
			
			PageEntity pageRecord = new PageEntity.Builder().
					pageID(Long.parseLong(result.getProperty("pageID").toString()))
					.pageOwner(result.getProperty("pageOwner").toString())
					.pageName(result.getProperty("pageName").toString())
					.type(result.getProperty("type").toString())
					.category(result.getProperty("category").toString())
					.numberOfLikes(Long.parseLong(result.getProperty("numberOflikes").toString()))
					.numberOfActiveUsers(Long.parseLong(result.getProperty("numberOfActiveUsers").toString()))
					.build();
			
			/*PageEntity pageRecord = new PageEntity(result.getProperty("pageOwner").toString(), result.getProperty("pageName").toString(),
					result.getProperty("type").toString(), result.getProperty("category").toString(), 
					Long.parseLong(result.getProperty("numberOflikes").toString())+1, 
					Long.parseLong(result.getProperty("pageID").toString()), 
					Long.parseLong(result.getProperty("numberOfActiveUsers").toString()));*/
			
			
			result.setProperty("pageID",pageRecord.getPageID());
			result.setProperty("pageOwner",pageRecord.getPageOwner());
			result.setProperty("pageName",pageRecord.getPageName());
			result.setProperty("type",pageRecord.getType());
			result.setProperty("category",pageRecord.getCategory());
			result.setProperty("numberOflikes",pageRecord.getNumberOfLikes());
			result.setProperty("numberOfActiveUsers",pageRecord.getNumberOfActiveUsers());
			datastore.put(result);

		}
		
		// User Like page
		public static void likePage(long pageID , String likeOwner , String pageName , String pageOwner)
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			  // Handling Multiple Likes ---> Abd El-Rahman Mohamed'S Idea
			///////////////////////////////////////////////////////////////////////////////////////
			// Step #0 : check if the user already likes this page
	          Filter likeOwnerFilter = new FilterPredicate("likeOwner", FilterOperator.EQUAL,likeOwner);	
			Query gaeQuery0 = new Query("PagesLike").setFilter(likeOwnerFilter);
			PreparedQuery pq0 = datastore.prepare(gaeQuery0);
			for (Entity entity : pq0.asIterable()) {
	
				    if (Long.parseLong(entity.getProperty("pageID").toString()) == pageID  )
				    {
				    	// Already liked this page before
				    	return;
				    }
				}

			// Step #1 : Add New Record in PagesLike Table
	        PageEntity.likePageAddRecord(pageID, likeOwner, pageName, pageOwner);
			
	        // Step #2: Increase Like Counter in the liked page
		    PageEntity.likePageIncreaseLikeCounter(pageID, likeOwner, pageName, pageOwner);
	        
	        // Step #3 : Notify Page Owner
		    String Notificationcontent = likeOwner+" Has just liked your page ' "+pageName+" '";
		    NotificationsEntity.saveNotification(likeOwner, pageOwner, Notificationcontent,"PageLike");
		}
		
		// View Liked pages list
		public static ArrayList<PageEntity> getLikedPages (String likeOwner)
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			ArrayList<PageEntity> retPages = new ArrayList<>();
			System.out.println("likeOwner: "+likeOwner);
	        Filter likeOwnerFilter = new FilterPredicate("likeOwner", FilterOperator.EQUAL,likeOwner);	
			Query gaeQuery = new Query("PagesLike").setFilter(likeOwnerFilter);
			PreparedQuery pq = datastore.prepare(gaeQuery);

			for (Entity entity : pq.asIterable()) {
				{
					PageEntity pageRecord = new PageEntity.Builder().
							pageID(Long.parseLong(entity.getProperty("pageID").toString()))
							.pageOwner(entity.getProperty("pageOwner").toString())
							.pageName(entity.getProperty("pageName").toString())
							.type(entity.getProperty("likeOwner").toString())
							.category("")
							.numberOfLikes(0)
							.numberOfActiveUsers(0)
							.build();
		
					/*PageEntity pageRecord = new PageEntity(entity.getProperty("pageOwner").toString(), 
							entity.getProperty("pageName").toString(),
							entity.getProperty("likeOwner").toString(), "", 0, 
							Long.parseLong(entity.getProperty("pageID").toString()), 0);*/
					
					retPages.add(pageRecord);
				    }
				}
				return retPages;
	}

}