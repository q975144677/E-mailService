package com.email.email.controller;

import com.email.email.pojo.Email;
import com.email.email.service.EmailService;
import com.email.email.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class ForeController {

    @Autowired
    EmailService emailService ;
    @Autowired
    UserService userService;

    @GetMapping("homePage")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("homePage");

        return modelAndView;

    }
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session ){
        ModelAndView modelAndView =  new ModelAndView("redirect:/homePage") ;
        if(session.getAttribute("user") != null){
            session.removeAttribute("user");
        }
        return modelAndView ;
    }
    @GetMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }
    @GetMapping("email")
    public ModelAndView modelAndView (){
       ModelAndView modelAndView = new ModelAndView("emailTemplate");
       Email email = new Email() ;
       email.setContent("<b>33</b>");
        modelAndView.addObject("email",email);
        return modelAndView ;
    }
    @PostMapping("url")
    public String url(String url){
String s[] = url.split("/");
        if(s.length >= 3)
        return s[2];
        else {
            return "fail";
        }
    }
    @GetMapping("help")
    public ModelAndView help(){
        ModelAndView modelAndView = new ModelAndView("help") ;

        return modelAndView;

    }
@PostMapping("username_check")
    public String userCheck(String username){
        if(userService.get(username) != null){

            return "fail";
        }

        return "success";
}
}
