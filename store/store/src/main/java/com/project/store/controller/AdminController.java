package com.project.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.store.POJO.Author;
import com.project.store.POJO.Book;
import com.project.store.POJO.User;
import com.project.store.DAO.AuthorDAO;
import com.project.store.DAO.BookDAO;
import com.project.store.DAO.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AdminController {

    @Autowired
    private BookDAO bookDAO;
    
    @Autowired
    private AuthorDAO authorDAO;
    
    @Autowired
    private UserDAO userDAO;

    @PostMapping("/book_operations")
    public ModelAndView showBookOperations(@ModelAttribute("book") Book book) {
        return new ModelAndView("book_operations");
    }

    @PostMapping("/author_operations")
    public ModelAndView showAuthorOperations(@ModelAttribute("author") Author author) {
        return new ModelAndView("author_operations");
    }
    
    @PostMapping("/delete_user")
    public ModelAndView deleteUserPage(@ModelAttribute("user") User user) {
        return new ModelAndView("user_operations");
    }
    
    @PostMapping("/addBook")
    public ModelAndView addBook(@ModelAttribute Book book, @RequestParam(value = "authorId", required = false) Integer authorId) {
      	ModelAndView mav = new ModelAndView();
    	System.out.println("Received authorId: " + authorId);

    	if (authorId == null) {
            mav.setViewName("error");
            return mav;
        }

        Author author = authorDAO.getAuthorById(authorId);
        if (author == null) {
          
            mav.setViewName("error");
            return mav;
        }
        
        book.setAuthor(author);

        bookDAO.saveBook(book);
        mav.setViewName("book_Added");
        return mav;
    }
    
    @PostMapping("/addAuthor")
    public ModelAndView addAuthor(@ModelAttribute("author") Author author) {
        authorDAO.saveAuthor(author);
        return new ModelAndView("author_Added");
    }
    
    @PostMapping("/updateAuthor")
    public ModelAndView updateAuthor(@ModelAttribute("author") Author author) {
        authorDAO.updateAuthor(author);
        return new ModelAndView("author_updated");
    }
    
    @PostMapping("/deleteAuthor")
    public ModelAndView deleteAuthor(@RequestParam("authorName") String authorName) {
        authorDAO.deleteAuthor(authorName);
        return new ModelAndView("author_deleted");
    }
    
    @PostMapping("/deleteUser")
    public ModelAndView deleteUser(@RequestParam("username") String username) {
        userDAO.deleteUser(username);
        return new ModelAndView("user_deleted");
    }
    
}
