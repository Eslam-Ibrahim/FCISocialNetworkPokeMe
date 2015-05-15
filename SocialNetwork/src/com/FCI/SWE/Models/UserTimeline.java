package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserTimeline {
	
	UserPost myPosts = new UserPost();
	// Load Posts 
	public  ArrayList<UserPost>loadPosts(String userMail)	{
		return myPosts.loadPosts(userMail);
	}
	
	// save post
	public  void savePost(String postOwner, String postLocation ,String content , 
	String feelings , String privacy) {
		
		myPosts.savePost(postOwner, postLocation, content, feelings, privacy);
	}
}
