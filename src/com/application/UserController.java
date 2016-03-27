package com.application;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/")
public class UserController {

	@Context
	HttpServletRequest request;

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public Response loginPage() {
		return Response.ok(new Viewable("/Login.jsp")).build();
	}

	@GET
	@Path("/signUp")
	@Produces(MediaType.TEXT_HTML)
	public Response signUpPage() {
		return Response.ok(new Viewable("/Signup.jsp")).build();
	}
	
	@GET
	@Path("/showLocation")
	@Produces(MediaType.TEXT_HTML)
	public Response showLocationPage(){
		return Response.ok(new Viewable("/ShowLocation.jsp")).build();
	}
	//_______________________________________________________________________________________//
	@GET
	@Path("/follow")
	@Produces(MediaType.TEXT_HTML)
	public Response followpage() {
		return Response.ok(new Viewable("/follow.jsp")).build();
	}
	@GET
	@Path("/unfollow")
	@Produces(MediaType.TEXT_HTML)
	public Response unfollowPage() {
		return Response.ok(new Viewable("/unfollow.jsp")).build();
	}
	@GET
	@Path("/getfollowerslist")
	@Produces(MediaType.TEXT_HTML)
	public Response followerslistPage() {
		return Response.ok(new Viewable("/getfollowerslist.jsp")).build();
	}
	
	@GET
	@Path("/getlocation")
	@Produces(MediaType.TEXT_HTML)
	public Response getlocationPage() {
		return Response.ok(new Viewable("/getlocation.jsp")).build();
	}
//_______________________________________________________________________________________//
	@POST
	@Path("/updateMyLocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateLocation(@FormParam("lat") String lat, @FormParam("long") String lon){
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/updatePosition";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/login";

		String urlParameters = "id=" + id + "&lat=" + lat + "&long="+ lon;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try {
			obj = (JSONObject)parser.parse(retJson);
			Long status = (Long) obj.get("status");
			if(status == 1)
				return "Your location is updated";
			else
				return "A problem occured";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "A problem occured";
		
	}
	
	@POST
	@Path("/doLogin")
	@Produces(MediaType.TEXT_HTML)
	public Response showHomePage(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/login";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/login";

		String urlParameters = "email=" + email + "&pass=" + pass;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			session.setAttribute("email", obj.get("email"));
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("long", obj.get("long"));
			session.setAttribute("pass", obj.get("pass"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));

			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Path("/doSignUp")
	@Produces(MediaType.TEXT_HTML)
	public Response showHomePage(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/signup";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/signup";

		String urlParameters = "name=" + name + "&email=" + email + "&pass="
				+ pass;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			session.setAttribute("email", obj.get("email"));
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("long", obj.get("long"));
			session.setAttribute("pass", obj.get("pass"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));
				
			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}




//------------------------------------------------------------------------------------//
@POST
@Path("/getMyLocation")
@Produces(MediaType.TEXT_PLAIN)
public Response getLocation(@FormParam("id") int id){
	HttpSession session = request.getSession();
	Long Id = (Long) session.getAttribute("id");
	String serviceUrl = "firstapp-socialnetworkapp.rhcloud.com/FCISquare/rest/getUserPosition";
	//String serviceUrl = "http://localhost:8080/FCISquare/rest/getUserPosition";

	String urlParameters = "id=" + id ;
	// System.out.println(urlParameters);
	String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");
	
	JSONParser parser = new JSONParser();
	JSONObject obj;
	try {
		obj = (JSONObject) parser.parse(retJson);
		
		session.setAttribute("position", obj.get("position"));
		//session.setAttribute("long", obj.get("long"));
		
		Map<String,String> map = new HashMap<String,String>();

		map.put("position", (String) obj.get("position"));
			
		return Response.ok(new Viewable("/getPosition.jsp", map)).build();

	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	
}

//------------------------------------------------------------------------------------//
@POST
@Path("/follow")
@Produces(MediaType.TEXT_PLAIN)
public String follow(@FormParam("email1") String followeremail,
		@FormParam("email2") String followedemail, @FormParam("name") String name){
	HttpSession session = request.getSession();
	Long Id = (Long) session.getAttribute("id");
	String serviceUrl = "firstapp-socialnetworkapp.rhcloud.com/FCISquare/rest/follow";
	//String serviceUrl = "http://localhost:8080/FCISquare/rest/getUserPosition";

	String urlParameters = "followerEmail=" + followeremail + "followedemail=" + followedemail + "name=" + name ;
	// System.out.println(urlParameters);
	String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");
	
	JSONParser parser = new JSONParser();
	JSONObject obj;
	try {
		obj = (JSONObject)parser.parse(retJson);
		Long status = (Long) obj.get("status");
		if(status == 1)
			return "DONE";
		else
			return "A problem occured";
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "A problem occured";
	
}

//------------------------------------------------------------------------------------//
@POST
@Path("/unfollow")
@Produces(MediaType.TEXT_PLAIN)
public String follow(@FormParam("email1") String followeremail,
		@FormParam("email2") String followedemail){
	HttpSession session = request.getSession();
	//Long Id = (Long) session.getAttribute("id");
	String serviceUrl = "firstapp-socialnetworkapp.rhcloud.com/FCISquare/rest/unfollow";
	//String serviceUrl = "http://localhost:8080/FCISquare/rest/getUserPosition";

	String urlParameters = "followerEmail=" + followeremail + "followedemail=" + followedemail  ;
	// System.out.println(urlParameters);
	String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");
	
	JSONParser parser = new JSONParser();
	JSONObject obj;
	try {
		obj = (JSONObject)parser.parse(retJson);
		Long status = (Long) obj.get("status");
		if(status == 1)
			return "DONE";
		else
			return "A problem occured";
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "A problem occured";
	
}

//------------------------------------------------------------------------------------//
@POST
@Path("/getfollowerslist")
@Produces(MediaType.TEXT_PLAIN)
public String getfollowerslist(@FormParam("email") String followeremail){
	HttpSession session = request.getSession();
	//Long Id = (Long) session.getAttribute("id");
	String serviceUrl = "firstapp-socialnetworkapp.rhcloud.com/FCISquare/rest/getfollowerslist";
	//String serviceUrl = "http://localhost:8080/FCISquare/rest/getUserPosition";

	String urlParameters = "followerEmail=" + followeremail  ;
	// System.out.println(urlParameters);
	String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");
	
	JSONParser parser = new JSONParser();
	JSONObject obj;
	try {
		obj = (JSONObject)parser.parse(retJson);
		while(!obj.isEmpty())
		{
		String status = (String) obj.get("status");
		
			return status;		
			
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "A problem occured";
	
}
}
