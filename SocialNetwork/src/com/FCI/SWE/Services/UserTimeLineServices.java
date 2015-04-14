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
import java.util.Vector;

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
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;
import com.FCI.SWE.Models.UserTimeline;
//import com.google.appengine.labs.repackaged.org.json.JSONArray;





@Path("/")
@Produces("text/html")


public class UserTimeLineServices {
	
	UserTimeline myTimeLine = new UserTimeline();
	
	@POST
	@Path("/retrieveUserPosts")
	public String retrieveFriendRequests(@FormParam("myEmail") String myEmail) {
		
		ArrayList<UserPost> retPosts = new ArrayList<>();
		JSONArray retArry = new JSONArray();
		retPosts =  myTimeLine.loadPosts(myEmail);
		for (UserPost post : retPosts)
		{
			JSONObject object = new JSONObject();
			object.put("postOwner", post.getPostOwner());
			object.put("content", post.getContent());
			object.put("feelings", post.getFeelings());
			object.put("numberOflikes", post.getnumberOflikes());
			object.put("date", post.getDate());
			object.put("postID", post.getPostID());
		    retArry.add(object);
		}
	
		return retArry.toJSONString();
}
	
	@POST
	@Path("/savePost")
	public String savePost(@FormParam("myEmail")String postOwner, @FormParam("postLocation") String postLocation , @FormParam("content")String content , 
			@FormParam("feelings")String feelings , @FormParam("privacy")String privacy)
			{
		         JSONObject object = new JSONObject();	
	             myTimeLine.savePost(postOwner, postLocation, content, feelings, privacy);
	             object.put("Status", "OK");
	     		return object.toString();
		
            }
	
	

}
