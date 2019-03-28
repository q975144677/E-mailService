package com.email.email.controller;

import com.email.email.pojo.User;
import com.email.email.service.EmailService;
import com.email.email.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class MyPageController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;

    @GetMapping("/my/{username}")
    public ModelAndView my(
            @PathVariable(name = "username")
                    String username, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("my");
        User user = (User) session.getAttribute("user");
        if (username.isEmpty() || user == null || (!user.getUsername().equals(username))) {
            modelAndView.setViewName("redirect:/account/login");
        }

        return modelAndView;
    }

    @PutMapping("/my/{username}")
    public ModelAndView post(@PathVariable(name = "username") String username, User user, HttpSession session, String postPassword) {
        ModelAndView modelAndView = new ModelAndView("redirect:/my/" + username);
        User old = null;

        if (session.getAttribute("user") == null) {
            old = userService.get(username);
        } else {
            old = (User) session.getAttribute("user");
        }
        old.setPostbox(user.getPostbox());
        old.setPostPassword(user.getPostPassword());
        userService.update(old);
        return modelAndView;
    }

    @PostMapping("my/{username}")
    public ModelAndView changePassword(User user, @PathVariable(name = "username") String username) {

        ModelAndView modelAndView = new ModelAndView("redirect:/my/" + username);
        User old = userService.get(username);
        old.setPassword(user.getPassword());
        userService.update(old);
        return modelAndView;
    }


}
