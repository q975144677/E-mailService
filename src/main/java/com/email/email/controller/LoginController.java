package com.email.email.controller;

import com.email.email.pojo.User;
import com.email.email.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ModelAndView login(String msg) {
        ModelAndView modelAndView = new ModelAndView("login");
        System.out.println("_____________------"+msg);
        modelAndView.addObject("msg",msg);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login_check(User user , HttpSession  session ) {
        ModelAndView modelAndView = new ModelAndView();
        User result = userService.get(user.getUsername());
        if (result != null) {
            if (result.getPassword().equals(user.getPassword())) {
                if (session.getAttribute("user") != null) {
                 session.removeAttribute("user");
                }
                session.setAttribute("user", result);
                modelAndView.setViewName("redirect:/homePage");
                return modelAndView;
            }
        }
        modelAndView.addObject("msg","error");
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

    @GetMapping("register")
    public ModelAndView register(@RequestParam(value = "message", defaultValue = "regular") String message) {
        ModelAndView modelAndView = new ModelAndView("register");
        return modelAndView;
    }

    @PostMapping("register")
    public ModelAndView signUp(User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage");
        if (userService.get(user.getUsername()) != null) {
            modelAndView.setViewName("redirect:register?message=fail");
            return modelAndView;
        }
        userService.insert(user);
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        session.setAttribute("user", user);
        return modelAndView;
    }
    @PostMapping("vali")
    public String validate(String username){
        if(userService.get(username) == null){
            return "ok";
        }

        return "fail";
    }
}
