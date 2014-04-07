package edu.sjsu.cmpe.procurement.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookRequest {
	@JsonProperty
	@NotNull
	private  String id="86351";
	
	@JsonProperty
	@NotNull
	private List<String> order_book_isbns=new ArrayList<String>();
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the order_book_isbns
	 */
	public List<String> getOrder_book_isbns() {
		return order_book_isbns;
	}
	/**
	 * @param order_book_isbns the order_book_isbns to set
	 */
	public void setOrder_book_isbns(List<String> order_book_isbns) {
		this.order_book_isbns = order_book_isbns;
	}
	

}
