package com.project.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.store.DAO.BookDAO;
import com.project.store.POJO.Book;

@Controller
public class BookController {
	
	 @Autowired
	 private BookDAO bookDAO;

	@GetMapping("/user_books")
    public String listBooks(Model model) {
        List<Book> books = bookDAO.findAllBooks();
        model.addAttribute("books", books);
        return "books_list"; 
    }
	
}
