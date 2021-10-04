package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

    //private final UserRepository userRepository;

/*    @Autowired
    UserRepository userRepository;*/

    @Autowired
    MyUserDetailsService myUserDetailsService;



/*    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @GetMapping(path = "/registration")
    public String getRegistrationForm(HttpServletRequest httpServletRequest, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user", new User());
            return "registration";
        } else if (httpServletRequest.isUserInRole("ADMIN")) {
            return "redirect:/ProcessPictures";
        } else {
            return "redirect:/";
        }

    }

    @PostMapping(path = "/registration")
    public String register(@ModelAttribute User user, Model model, HttpServletRequest httpServletRequest){

        model.addAttribute("user", user);

        myUserDetailsService.registerUser(user);

            try {
                httpServletRequest.login(user.getUserName(),user.getPassword());
            } catch(ServletException ex) {
                return "registrationError";
            }



        return "redirect:/";

    }



}
