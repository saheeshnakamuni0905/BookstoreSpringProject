package com.project.store.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.store.DAO.CartDAO;
import com.project.store.POJO.Cart;
import com.project.store.POJO.CartItem;
import com.project.store.POJO.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	
	@Autowired
    private CartDAO cartDAO;

	@PostMapping("/addToCart")
	public String addToCart(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/login"; 
	    }

	    Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	    Integer quantity = Integer.parseInt(request.getParameter("quantity"));

	    Cart cart = (Cart) session.getAttribute("cart");
	    if (cart == null) {
	        cart = cartDAO.getCartByUserId(user.getuserid()); 
	        if (cart == null) {
	            cart = new Cart(); 
	            cart.setUser(user); 
	            session.setAttribute("cart", cart); 
	        }
	    }
	    cartDAO.addToCart(user.getuserid(), bookId, quantity);
	    
	    cart = cartDAO.getCartByUserId(user.getuserid()); 
	    session.setAttribute("cart", cart); 

	    return "cart_added"; 
	}

	
	@GetMapping("/viewCart")
	public ModelAndView viewCart(HttpServletRequest request) {
	    ModelAndView modelAndView = new ModelAndView();
	    User user = (User) request.getSession().getAttribute("user");
	    if (user == null) {
	        modelAndView.setViewName("login");
	        return modelAndView;
	    }
	    
	    System.out.println("view cart controller:" + user.getuserid());
	    
	    Cart cart = cartDAO.getCartByUserId(user.getuserid());
	    
	    if (cart == null) {
	        modelAndView.setViewName("view_cart");
	        return modelAndView;
	    }
	    
	    List<CartItem> cartItems = cart.getCartItems();
	    
	    modelAndView.addObject("cartItems", cartItems);
	    modelAndView.setViewName("view_cart");
	    
	    return modelAndView;
	}
	
	@GetMapping("/removeCartItem")
    public ModelAndView removeCartItem(@RequestParam("cartItemId") int cartItemId) {
        ModelAndView modelAndView = new ModelAndView();
        cartDAO.deleteCartItem(cartItemId);
        modelAndView.setViewName("redirect:/viewCart");
        return modelAndView;
    }

}
