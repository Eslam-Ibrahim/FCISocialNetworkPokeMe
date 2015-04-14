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

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.PageEntity;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserTimeline;
import com.google.appengine.labs.repackaged.org.json.JSONArray;





@Path("/")
@Produces("text/html")

public class PageServices {
	
	
	
	private PageEntity myPage = new PageEntity();
	
	
	@POST
	@Path("/createPage")
	public String createPage(@FormParam("myEmail")String pageOwner, @FormParam("pageName") String pageName , @FormParam("type")String type , 
			@FormParam("category")String category)
			{
		         JSONObject object = new JSONObject();	
	             myPage.createPage(pageOwner, pageName, type, category);
	             object.put("Status", "OK");
	     		return object.toString();
		
            }



	@POST
	@Path("/listPagesForOwner")
	public String listPagesForOwner(@FormParam("myEmail") String pageOwner) {
		JSONObject object = new JSONObject();
	
		object.put("pageNames", myPage.listPagesForOwner(pageOwner));
		System.out.println(object.toString());
		
		return object.toString();
	

}

	@POST
	@Path("/searchPage")
	public String searchForPagebyTypeAndCategory(@FormParam("myEmail") String userMail , @FormParam("type") String type
			,@FormParam("category") String category) {
		JSONObject object = new JSONObject();
	
		object.put("SearchPageNames", myPage.searchForPagebyTypeAndCategory(userMail, type, category));
		System.out.println(object.toString());
		return object.toString();
}
	
	
}
