package com.therounds;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.util.Header;

import com.therounds.model.SQLiteJDBC;
import com.therounds.model.User;
import com.therounds.model.Users;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as jason response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt() {
		return "Got it!";
	}

	
	
	@GET
	@Path("/users")
	@Produces("application/json;")
	public Users getAllUsers() {
		String str="getAllUsers";
		try {
			debugAttempt(str);
			Users users = SQLiteJDBC.getUsers();
			debugSuccess(str);
			
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	@GET
	@Path("/users/{id}")
	@Produces("application/json;")
	public User getUserById(@PathParam("id") int id) {
		String str="getUserById";
		try {
			debugAttempt(str);
			User user = SQLiteJDBC.getUser(id);
			debugSuccess(str);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	@DELETE
	@Path("/users/{id}")
	@Produces("application/json;")
	public Response delUserById(@PathParam("id") int id) {
		String str="delUserById";
		try {
			debugAttempt(str);
			SQLiteJDBC.deleteUser(id);
			debugSuccess(str);
			return Response.status(201)
					.contentLocation(new URI("/user-management/users/123")).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@POST
	@Path("/users")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user) throws URISyntaxException {
		String str="createUser";
		try {
			debugAttempt(str);
			this.printDebugInfo(user);
			SQLiteJDBC.insertUser(user);
			debugSuccess(str);
			return Response.status(201)
					.contentLocation(new URI("/user-management/users/123")).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	@PUT
	@Path("/users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(@PathParam("id") int id, User user) throws URISyntaxException {
		String str="updateUser";
		try {
			debugAttempt(str);
			this.printDebugInfo(user);
			user.setId(id);
			User u = SQLiteJDBC.updateUser(user);
			debugSuccess(str);
			return u;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	private void debugAttempt(String str) {
		System.out.println("------------------------------------");
		System.out.println("attempting to perform "+str+" operation");
	}
	
	private void debugSuccess(String str) {
		System.out.println("successfuly executed "+str+" operation");
	}
	
	
	private void printDebugInfo(User user) {
		System.out.println("--recieved data--");
		System.out.println("first " + user.getFirstName());
		System.out.println("last " + user.getLastName());
		System.out.println("id " + user.getId());
		System.out.println("pass " + user.getPassword());
		System.out.println("uri " + user.getUri());
		System.out.println("email " + user.getEmail());
		System.out.println("");
	}
}
