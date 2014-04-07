package edu.sjsu.cmpe.procurement.jobs;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.procurement.Stomp.ApolloStomp;
import edu.sjsu.cmpe.procurement.domain.Book;
import edu.sjsu.cmpe.procurement.domain.BookRequest;
import edu.sjsu.cmpe.procurement.domain.ShippedBook;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This job will run at every 5 second.
 */
//TODO--change it to 5s 
@Every("15s")
public class ProcurementSchedulerJob extends Job {
    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doJob() {
	/*String strResponse = ProcurementService.jersyClient.resource(
		"http://ip.jsontest.com/").get(String.class);
	log.debug("Response from jsontest.com: {}", strResponse);*/
	BookRequest bookRequest=new BookRequest();
	
	System.out.println("pull all message from the queue and do HTTP post to the client");
	ApolloStomp apolloStomp=new ApolloStomp();
	
	try{
    bookRequest=apolloStomp.receiveMessageFromQueue();
    System.out.println(bookRequest);
	}catch(Exception e){
		e.printStackTrace();
	}
   if( bookRequest.getOrder_book_isbns().size()!=0){
	   System.out.println("posting to publisher");
	   Client client=Client.create();
	   String url="http://54.193.56.218:9000/orders";
	   WebResource webResource=client.resource(url);
	   ClientResponse clientResponse=webResource.accept("application/json").type("application/json").post(ClientResponse.class,bookRequest);
	   System.out.println(clientResponse.getEntity(String.class));
   }
   
   System.out.println("receive Get request from publisher");
   try{
	  Client client=Client.create();
	  String url="http://54.193.56.218:9000/orders/86351";
	  WebResource webResource=client.resource(url);
	  ClientResponse  response=webResource.accept("application/json").type("application/json").get(ClientResponse.class);
	  System.out.println("getting from publisher");
	  String shipped_book=response.getEntity(String.class);
	  System.out.println(shipped_book);
	  JSONObject jObject = new JSONObject(shipped_book);
	  JSONArray jArray = jObject.getJSONArray("shipped_books");
	  for(int i=0;i<jArray.length();i++){
		  String category=jArray.getJSONObject(i).getString("category");
		  String coverimage=jArray.getJSONObject(i).getString("coverimage");
		  int isbn=jArray.getJSONObject(i).getInt("isbn");
		  String title=jArray.getJSONObject(i).getString("title");  
		  apolloStomp.publishTopicMessage(isbn, title, coverimage, category);
	  }
	 
	 
   }catch(Exception e){
	   e.printStackTrace();
   }
   }
     
}
