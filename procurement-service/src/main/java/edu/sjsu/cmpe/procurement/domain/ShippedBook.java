package edu.sjsu.cmpe.procurement.domain;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippedBook {
	
	@JsonProperty
	@JsonIgnoreProperties
	List<Book> shipped_books=new ArrayList<Book>();
public ShippedBook(){
	
}
	/**
	 * @return the shipped_book
	 */
	public List<Book> getShipped_book() {
		return shipped_books;
	}

	/**
	 * @param shipped_book the shipped_book to set
	 */
	public void setShipped_book(List<Book> shipped_book) {
		this.shipped_books = shipped_book;
	}
	

}
