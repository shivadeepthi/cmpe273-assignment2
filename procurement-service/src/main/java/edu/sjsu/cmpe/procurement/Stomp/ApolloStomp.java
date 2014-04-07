package edu.sjsu.cmpe.procurement.Stomp;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.procurement.domain.BookRequest;
import edu.sjsu.cmpe.procurement.domain.ShippedBook;

public class ApolloStomp {
	
       //private ProcurementServiceConfiguration configuration;
       static String apolloUser;
   	   static String apolloPassword;
   	   static String apolloHost;
   	   static int apolloPort;
       static String stompQueueName;
       static String stompTopicPrefix;
       static private String order_book_isbn;
       private BookRequest bookRequest=new BookRequest();
    
       
       public ApolloStomp(){
    	   
       }
       public ApolloStomp(String apolloUser,String apolloPassword,String apolloHost,int apolloPort,String queueName,String topicName){
    	  ApolloStomp.apolloUser=apolloUser;
    	   ApolloStomp.apolloPassword=apolloPassword;
    	   ApolloStomp.apolloHost=apolloHost;
    	   ApolloStomp.apolloPort=apolloPort;
    	   stompQueueName=queueName;
    	   stompTopicPrefix=topicName;
       }
        
       /*receive message from queue*/
       public BookRequest receiveMessageFromQueue()throws JMSException{
    	   StompJmsConnectionFactory factory=new StompJmsConnectionFactory();
    	   factory.setBrokerURI("tcp://"+apolloHost+":"+apolloPort);
    	   
    	   Connection connection=factory.createConnection("admin","password");
    	   connection.start();
    	   
    	   Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	   Destination dest=new StompJmsDestination(stompQueueName);
    	   MessageConsumer consumer=session.createConsumer(dest);
    	   long waitUntil = 5000; // wait for 5 sec
    	   while(true) {
    	       Message msg = consumer.receive(waitUntil);
    	       if( msg instanceof  TextMessage ) {
    	              String body = ((TextMessage) msg).getText();
    	              System.out.println("Received message = " + body);
    	              order_book_isbn=body.substring(10);
    	              bookRequest.getOrder_book_isbns().add(order_book_isbn);
    	       } else if (msg == null) {
    	             System.out.println("No new messages. Exiting due to timeout - " + waitUntil / 1000 + " sec");
    	             break;
    	       } else {
    	            System.out.println("Unexpected message type: " + msg.getClass());
    	       }
    	   } // end while loop
    	   
    	   System.out.println("Done");
    	   connection.close();
    	   return bookRequest;  
       }
 
       //receive GET request from from publisher
       
       public void publishTopicMessage(int isbn,String title,String coverimage,String category)throws JMSException{
    	   StompJmsConnectionFactory factory=new StompJmsConnectionFactory();
    	   factory.setBrokerURI("tcp://"+apolloHost+":"+apolloPort);
    	   Connection connection=factory.createConnection(apolloUser,apolloPassword);
    	   connection.start();
    	   Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	   //for(int i=0;i<shippedBook.getShipped_book().size();i++){
    		   Destination dest=new StompJmsDestination(stompTopicPrefix+category);
    		   MessageProducer producer=session.createProducer(dest);
    		   producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    		   System.out.println(dest);
    		   TextMessage msg= session.createTextMessage(isbn+":"+title+":"+category+":"+coverimage);
    		   System.out.println(msg.getText());
    		   msg.setText(isbn+":"+title+":"+category+":"+coverimage);
    		   producer.send(msg);
    	       connection.close();  
       }
       
}
