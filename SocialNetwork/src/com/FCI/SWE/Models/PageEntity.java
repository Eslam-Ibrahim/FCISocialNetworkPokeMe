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

		public PageEntity(String pageOwner, String pageName, String type,
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
	}




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
			// Use class Query to assemble a query
			Query gaeQuery = new Query("Pages").setFilter(ownerMailFilter);
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				//System.out.println("I am Here in retMessage");
				//if((entity.getProperty("sender").equals(senderMail)&&entity.getProperty("receiver").equals(receiverMail))||
			      //  (entity.getProperty("sender").equals(receiverMail)&&entity.getProperty("receiver").equals(senderMail)))
				{
					//System.out.println("I am Here in retMessage condition");
					PageEntity pageRecord = new PageEntity(entity.getProperty("pageOwner").toString(), entity.getProperty("pageName").toString(),
							entity.getProperty("type").toString(), entity.getProperty("category").toString(), 
							Long.parseLong(entity.getProperty("numberOflikes").toString()), 
							Long.parseLong(entity.getProperty("pageID").toString()), 
							Long.parseLong(entity.getProperty("numberOfActiveUsers").toString()));
					retPages.add(pageRecord);
				    }
				}

			     for (int i = 0; i < retPages.size(); ++i) {
					System.out.println(retPages.get(i).toString());
				}
				return retPages;
			}

		
		// Search For page by type - category 
		public ArrayList<PageEntity>searchForPagebyTypeAndCategory(String userMail , String type , String category)
		{

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			ArrayList<PageEntity> retPages = new ArrayList<>();
			System.out.println("pageName: "+userMail);
	         // You Are not  the owner of the page (Creator)
	          Filter notOwnerMailFilter = new FilterPredicate("pageOwner", FilterOperator.NOT_EQUAL,userMail);	
	          Filter typeFilter = new FilterPredicate("type", FilterOperator.EQUAL,type);	
	          Filter categoryFilter = new FilterPredicate("category", FilterOperator.EQUAL,category);
	          Filter CompositeFilter1 = CompositeFilterOperator.and(notOwnerMailFilter, typeFilter,categoryFilter);
			// Use class Query to assemble a query
			Query gaeQuery = new Query("Pages").setFilter(CompositeFilter1);
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				//System.out.println("I am Here in retMessage");
				//if((entity.getProperty("sender").equals(senderMail)&&entity.getProperty("receiver").equals(receiverMail))||
			      //  (entity.getProperty("sender").equals(receiverMail)&&entity.getProperty("receiver").equals(senderMail)))
				{
					//System.out.println("I am Here in retMessage condition");
					PageEntity pageRecord = new PageEntity(entity.getProperty("pageOwner").toString(), entity.getProperty("pageName").toString(),
							entity.getProperty("type").toString(), entity.getProperty("category").toString(), 
							Long.parseLong(entity.getProperty("numberOflikes").toString()), 
							Long.parseLong(entity.getProperty("pageID").toString()), 
							Long.parseLong(entity.getProperty("numberOfActiveUsers").toString()));
					retPages.add(pageRecord);
				    }
				}

			     for (int i = 0; i < retPages.size(); ++i) {
					System.out.println(retPages.get(i).toString());
				}

		
			return retPages;
			
		}
	
		public static PageEntity parsePageInfo(String jsonString) {
			JSONParser parser = new JSONParser();
		     
			try {
				JSONObject object  = (JSONObject) parser.parse(jsonString);
				PageEntity page = new PageEntity(
						object.get("pageOwner").toString(), 
			            object.get("pageName").toString(),
			            object.get("type").toString(),
			            object.get("category").toString(), 
			            Long.parseLong(object.get("numberOfLikes").toString()) ,
			            Long.parseLong (object.get("pageID").toString()),
			            Long.parseLong(object.get("numberOfActiveUsers").toString())
						);
				return page;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}




		public void setPageOwner(String userMail) {
			this.pageOwner = userMail;
			
		}

}
