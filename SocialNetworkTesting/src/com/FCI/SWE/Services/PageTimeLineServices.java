package com.FCI.SWE.Services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.NotificationsEntity;
import com.FCI.SWE.Models.PageEntity;
import com.FCI.SWE.Models.PagePost;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserTimeline;
import com.FCI.SWE.Models.pageTimeLine;





@Path("/")
@Produces("text/html")
public class PageTimeLineServices {

	
	@POST
	@Path("/savePagePost")
	public String savePagePost(@FormParam("pageOwner")String pageOwner, @FormParam("pageName") String pageName , 
			@FormParam("pageID")long pageID , 
			@FormParam("postOwner")String postOwner,@FormParam("content")String content,@FormParam("privacy")String privacy)
			{
		         JSONObject object = new JSONObject();	
	             pageTimeLine.savePostToPage(pageOwner, pageName, pageID, postOwner, content, privacy);
	             object.put("Status", "OK");
	     		return object.toString();
		
            }

	@POST
	@Path("/loadPagePosts")
	public String loadPagePosts(@FormParam("pageID") long pageID) {
		ArrayList<PagePost> retPosts = new ArrayList<>();
		JSONArray retArry = new JSONArray();
		retPosts = pageTimeLine.loadPageTimeLine(pageID);
		for (PagePost post : retPosts)
		{
			JSONObject object = new JSONObject();
			object.put("pageID", post.getPageID());
			object.put("postID", post.getPostID());
			object.put("postOwner", post.getPostOwner());
			object.put("content", post.getContent());
			object.put("privacy", post.getPrivacy());
			object.put("date", post.getDate());
			object.put("numberOfLikes", post.getNumberOfLikes());		
			object.put("numberOfSeens", post.getNumberOfSeens());
		    retArry.add(object);
		}
	
		return retArry.toJSONString();	

}
	@POST
	@Path("/loadPagePostsForUser")
	public String loadPagePostsForUser(@FormParam("pageID") long pageID) {
		ArrayList<PagePost> retPosts = new ArrayList<>();
		JSONArray retArry = new JSONArray();
		retPosts = pageTimeLine.loadPageTimeLineForUser(pageID);
		for (PagePost post : retPosts)
		{
			JSONObject object = new JSONObject();
			object.put("pageID", post.getPageID());
			object.put("postID", post.getPostID());
			object.put("postOwner", post.getPostOwner());
			object.put("content", post.getContent());
			object.put("privacy", post.getPrivacy());
			object.put("date", post.getDate());
			object.put("numberOfLikes", post.getNumberOfLikes());		
			object.put("numberOfSeens", post.getNumberOfSeens());
		    retArry.add(object);
		}
	
		return retArry.toJSONString();	

}
	
}
