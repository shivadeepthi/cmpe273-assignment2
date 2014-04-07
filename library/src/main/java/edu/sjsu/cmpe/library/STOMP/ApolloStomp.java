/**
 * 
 */
package edu.sjsu.cmpe.library.STOMP;

import java.net.MalformedURLException;




import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;




import edu.sjsu.cmpe.library.repository.BookRepository;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

/**
 * @author shivadeepthi
 *
 */
public class ApolloStomp extends Listener {
	
	String apolloUser;
	String apolloPassword;
	String apolloHost;
	int apolloPort;
    String queueName;
    String topicName;
    String libraryName;
    BookRepositoryInterface bookRepository;
    MessageListener listener=new Listener();
    
    public ApolloStomp(String apolloUser, String apolloPassword, String apolloHost, int apolloPort,String  queueName, String topicName, String libraryName, BookRepositoryInterface bookRepository){
    	
    	this.apolloUser=apolloUser;
    	this.apolloHost=apolloHost;
    	this.apolloPassword=apolloPassword;
    	this.apolloPort=apolloPort;
    	this.queueName=queueName;
    	this.topicName=topicName;
    	this.libraryName=libraryName;
    	this.bookRepository=bookRepository;
    }
    
    
    /*send message to the queue
     * */
public void sendMessageToQueue(long ISBN) throws JMSException,InterruptedException{
	StompJmsConnectionFactory factory=new StompJmsConnectionFactory();
	System.out.println(apolloHost);
	System.out.println(apolloPassword);
	System.out.println(apolloPort);
	factory.setBrokerURI("tcp://"+apolloHost+":"+apolloPort);
	
	Connection connection=factory.createConnection(apolloUser, apolloPassword);
	connection.start();
	Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Destination dest=new StompJmsDestination(queueName);
	MessageProducer producer=session.createProducer(dest);
	TextMessage msg=session.createTextMessage(libraryName+":"+ISBN);
	producer.send(msg);
	connection.close();
}

//receive topics
public void subscribeTopic()throws JMSException,MalformedURLException{
	StompJmsConnectionFactory factory=new StompJmsConnectionFactory();
	factory.setBrokerURI("tcp://"+apolloHost+":"+apolloPort);
	Connection connection=factory.createConnection(apolloUser,apolloPassword);
	connection.start();
	Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	Destination dest=new StompJmsDestination(topicName);
	MessageConsumer consumer=session.createConsumer(dest);
	System.out.println(dest+"in subscribe topic method");
	
	while(listener!=null){
	consumer.setMessageListener(listener);
	
	}
	connection.close();
	
	}

}


	//consumer.receive();
		//System.out.println("received from topic"+((TextMessage)msg).getText());
	/*	String[] message=((TextMessage)msg).getText().split(":");
		long isbn=Long.valueOf(message[0]);
		Status status=Status.available;
		Book book=bookRepositoryInterface.getBookByISBN(isbn);
		if(book!=null&&book.getStatus()==Status.lost){
			book.setStatus(status);
			System.out.println("changed the status of lost book to available:"+book.getIsbn());
		}
		
		else if(book==null){
			String title=String.valueOf(message[1]);
		    String category=String.valueOf(message[2]);
		    URL coverimage=new URL(message[3]);
		    book=new Book();
		    book.setIsbn(isbn);
		    book.setTitle(title);
		    book.setCategory(category);
		    book.setCoverimage(coverimage);
		    book.setStatus(status);
		    bookRepositoryInterface.saveBook(book);
			
		}
		else{
			System.out.println("book already available in the library");
		}
		System.out.println("sub done");*/
	


