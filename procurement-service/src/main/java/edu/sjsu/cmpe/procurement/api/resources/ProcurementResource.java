/**
 * 
 */
package edu.sjsu.cmpe.procurement.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.client.JerseyClientConfiguration;

import edu.sjsu.cmpe.procurement.Stomp.ApolloStomp;
import edu.sjsu.cmpe.procurement.domain.BookRequest;

/**
 * @author shivadeepthi
 *
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProcurementResource {
	private Client client;
	private ApolloStomp apolloStomp;
	
	public ProcurementResource(Client client,ApolloStomp apolloStomp){
		this.client=client;
		this.apolloStomp=apolloStomp;
		
	}
	
	   @GET
	    public Response getProcurementResponse() {
		   System.out.println("i am here in p resource");
		return Response.ok("Ok").build();
	    }
	}



