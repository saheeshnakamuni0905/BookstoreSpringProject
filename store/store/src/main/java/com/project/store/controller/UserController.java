package com.project.store.controller;

import com.project.store.DAO.BookDAO;
import com.project.store.DAO.RoleDAO;
import com.project.store.DAO.UserDAO;
import com.project.store.POJO.Role;
import com.project.store.POJO.User;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private RoleDAO roleDAO;
    
    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/landingpage")
    public String LandingPage() {
        return "landingpage";  
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registrationloading")
    public ModelAndView handleRegistration(@ModelAttribute("user") User newUser) {
        Role userRole = roleDAO.findOrCreateRole("user");
        newUser.setRole(userRole);
        newUser.setRegistrationDate(new Date()); 
        userDAO.saveUser(newUser);

        return new ModelAndView("registration_success");
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new User());
        return "login";
    }

    @PostMapping("/loginloading")
    @Transactional
    public ModelAndView handleLogin(@ModelAttribute("login") User loginUser, HttpServletRequest request) {
        User user = userDAO.checkLogin(loginUser.getUsername(), loginUser.getPassword());
        ModelAndView mav = new ModelAndView();
        if (user != null && user.getRole().getRoleName().equals("admin")) {
            request.getSession().setAttribute("user", user);
            mav.setViewName("adminhomepage");
        } else if (user != null) {
            request.getSession().setAttribute("user", user);
            mav.addObject("books", bookDAO.findAllBooks());
            mav.setViewName("userhomepage");
        } else {
            mav.setViewName("login"); 
        }
        return mav;
    }
    
    @GetMapping("/updatePassword")
    public String showUpdatePasswordForm() {
        return "update_password"; 
    }
    
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userDAO.updateUser(user);
                return "password_updated";
            } else {
                return "error"; 
            }
        } else {
            return "login"; 
        }
    }
    
}
