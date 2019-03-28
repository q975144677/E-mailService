package com.email.email.controller;

import com.email.email.pojo.Email;
import com.email.email.pojo.User;
import com.email.email.service.EmailService;
import com.email.email.service.UserService;
import com.email.email.util.PostBoxVail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;
@Autowired
    UserService userService ;
    @GetMapping("post/{username}")
    public ModelAndView postList(@PathVariable(name = "username") String username, HttpSession session,
                                 @RequestParam(value = "page" , defaultValue = "1")Integer page) {
        ModelAndView modelAndView = new ModelAndView("post");
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUsername().equals(username)) {
            modelAndView.setViewName("redirect:/account/login");
            return modelAndView;
        }
        int id = user.getId();
        PageHelper.startPage(page,7,"id desc");
        List<Email> emails = emailService.listByUid(id);
        modelAndView.addObject("emails", emails);
      PageInfo<Email> pages = new PageInfo<>(emails);
     // pages.getPageNum();dangqianyemian
     //wy   pages.getPages();
        modelAndView.addObject("pageinfo", pages);
        if(emails.isEmpty()){
            modelAndView.addObject("msg","空空如也");
        }
        int[] p = new int[5];
        if(pages.getPages()>5) {
            if (page < 5) {
                for (int i = 1; i <= 5; i++) {
                    p[i - 1] = i;
                }

            } else if (page > pages.getPages() - 5) {
                for (int i = pages.getPages() - 4; i <= pages.getPages(); i++) {
                    p[i - pages.getPages() + 4] = i;
                }
            }       else{
                for (int i = pages.getPageNum()-2 ; i <= pages.getPages()+2 ; i ++){
                    p[i-pages.getPageNum()+2] = i ;
                }
            }
        }else{
            p = new int[pages.getPages()];
            for(int i = 1; i <= p.length ; i ++){
                p[i-1] = i ;
            }
        }
        modelAndView.addObject("pages",p);
        return modelAndView;
    }
    @GetMapping("detail/{emailId}")
    public ModelAndView detail(@PathVariable(name = "emailId") Integer id){
    ModelAndView modelAndView = new ModelAndView("detail") ;
    Email email = emailService.get(id);
    modelAndView.addObject("email",email );
    return modelAndView ;
    }


    @GetMapping("send")
    public ModelAndView send(@RequestParam(value = "message", defaultValue = "success") String message,HttpServletRequest request ) {
        ModelAndView modelAndView = new ModelAndView("send");
        modelAndView.addObject("msg", message);

        return modelAndView;
    }

    @PostMapping("send")
    public ModelAndView sendEmail(Email email, HttpSession session) {
        email.setTemplate("emailTemplate");
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage");
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        email.setUid(user.getId());
        try {
            boolean flag = true;
            if(email.getReceiveemail() == null) {
                throw  new Exception("no_postbox");
            }
            String[] valiPost = email.getReceiveemail().split(",");
            for(int i = 0 ; i < valiPost.length - 1 ; i ++ ){
                for(int j = i + 1 ; j < valiPost.length ; j ++){
                    if(valiPost[i].equals(valiPost[j])){
                        throw new Exception("same_postbox");
                    }
                }
            }
            for (String s : valiPost) {
                flag = flag && Boolean.parseBoolean(PostBoxVail.valid(s).get("success").toString());
            }
            if(email.getReceiveemail()==null||email.getReceiveemail().isEmpty()){
                flag = false;
            }
            if (!flag || !emailService.sendEmailByUser(email, user)) {
                throw new Exception("error_postbox");
            }
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/email/send?message=" + e.getMessage());
        }


    }
    @GetMapping("all/{username}")
    public ModelAndView all(@PathVariable(name = "username")String username){
        if(username!=""){
            User user = userService.get(username);
            ModelAndView modelAndView = new ModelAndView();

            return modelAndView;
        }else{
            return new ModelAndView("redirect:/account/login");
        }

    }


}
