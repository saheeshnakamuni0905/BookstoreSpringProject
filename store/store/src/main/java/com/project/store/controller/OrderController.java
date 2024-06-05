package com.project.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.store.DAO.CartDAO;
import com.project.store.DAO.OrderDAO;
import com.project.store.POJO.Cart;
import com.project.store.POJO.User;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private CartDAO cartDAO;
    
    @PostMapping("/placeOrder")
    public ModelAndView placeOrder(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/login");
        }

        Cart cart = cartDAO.getCartByUserId(user.getuserid());
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return new ModelAndView("redirect:/cart").addObject("error", "Your cart is empty.");
        }

        System.out.println("Cart items passed to DAO: " + cart.getCartItems().size());
        System.out.println("hi in order controller");
        orderDAO.placeOrder(user, cart.getCartItems());

        return new ModelAndView("order_placed");
    }

}
