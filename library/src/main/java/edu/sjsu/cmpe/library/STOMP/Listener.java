/**
 * 
 */
package edu.sjsu.cmpe.library.STOMP;

import java.net.URL;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Book.Status;
import edu.sjsu.cmpe.library.repository.*;




/**
 * @author shivadeepthi
 *
 */
public class Listener implements MessageListener {
	
	private BookRepository bookRepository=new BookRepository();
	private static String title;
	private static  String category;
	private  static URL coverimage=null;
	private Book book=null;

	public Listener(){
		System.out.println("i am here i listener");
	}
	
	@Override
	public void onMessage(Message msg) {
		
		
		
		try{
			if(msg instanceof TextMessage){
				System.out.println("topis mess"+msg);
				 
				 String topicmess=((TextMessage)msg).getText();
	                System.out.println("Received from topic: " + topicmess);
	                String[] tmessage=topicmess.split(":");
	        		Long isbn=Long.valueOf(tmessage[0]);
	        		System.out.println(isbn+"  isbn");
	        		Status status=Status.available;
	        		//System.out.println(status);
	        		book=bookRepository.getBookByISBN(isbn);
	        		if(book!=null && book.getStatus().equals(Status.lost) ){
	        			book.setStatus(status);
	        			System.out.println("changed the status of lost book to available:"+book.getIsbn());
	        		}else{
	        			System.out.println("i am creating new book");
	        			if(book==null){
		        			title=String.valueOf(tmessage[1]);
		        		    category=String.valueOf(tmessage[2]);
		        		     coverimage=new URL("http:"+tmessage[3]);
		        		    Book book1=new Book();
		        		    book1.setIsbn(isbn.longValue());
		        		    book1.setTitle(title);
		        		    book1.setCategory(category);
		        		    book1.setCoverimage(coverimage);
		        		    book1.setStatus(status);
		        		    System.out.println("storing new book");
		        		    bookRepository.saveBook(book1);
		        			
		        		}else{
	        			System.out.println("book already available in library");
	        		}
	        		
	        		
	        		
	        		System.out.println("sub done");
			}
	
}
	}catch(Exception e){
		e.printStackTrace();
	}
}}
	        		
	        		
	        	

