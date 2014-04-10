package edu.sjsu.cmpe.library.ui.views;

import java.util.List;

import com.yammer.dropwizard.views.View;

import edu.sjsu.cmpe.library.domain.Book;

public class HomeView extends View {
    private final List<Book> book;

    public HomeView(List<Book> book) {
	super("home.mustache");
	this.book = book;
    }

    public List<Book> getBook() {
	return book;
    }
}
