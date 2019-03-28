package com.email.email.service.impl;

import com.email.email.mapper.EmailMapper;
import com.email.email.pojo.Email;
import com.email.email.pojo.User;
import com.email.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tk.mybatis.mapper.entity.Example;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    EmailMapper emailMapper ;
@Autowired
    SpringTemplateEngine springTemplateEngine ;
@Value("${spring.mail.username}")
String sender;
@Autowired
    JavaMailSender mailSender ;
    @Override
    public List<Email> list() {
        List<Email> list = emailMapper.selectAll();

        return list;
    }

    @Override
    public List<Email> listByUid(int uid) {
        Example example = new Example(Email.class);
        example.createCriteria().andEqualTo("uid",uid);
        List<Email> list = emailMapper.selectByExample(example);

        return list;
    }

    @Override
    public Email get(int id) {
        Email email = new Email() ;
        email.setId(id);
        Email result = emailMapper.selectByPrimaryKey(email);
        return result;
    }

    @Override
    public void delete(int id) {
        emailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Email email) {
        emailMapper.insert(email);
    }

    @Override
    public void update(Email email) {
        emailMapper.updateByPrimaryKeySelective(email);
    }

    @Override
    public List<Email> listBySendTime(Date time) {
        Example  example  = new Example(Email.class);
        example.createCriteria().andEqualTo("sendTime",time);
        List<Email> list = emailMapper.selectByExample(example);
        return list;
    }

    public boolean sendEmail(Email email , JavaMailSender mailSender ){
        try {

            String[] receives = email.getReceiveemail().split(",");
            for (String receive : receives) {
                MimeMessage mm = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mm, true);
                helper.setFrom(sender);
                helper.setTo(receive);
                helper.setSubject(email.getSubject());
                //html 转换为 thymeleaf
                Context context = new Context();
                context.setVariable("email", email);
                String text = springTemplateEngine.process(email.getTemplate(), context);
                helper.setText(text, true);
                mailSender.send(mm);
                email.setReceiveemail(receive);
                insert(email);
                email.setId(null);
            }
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean sendEmail(Email email )  {
           return  sendEmail(email,mailSender);
    }
/*
spring.mail.host=smtp.163.com
spring.mail.username=q975144677@163.com
#授权码g，在邮箱客户端生成 修改成自己的  设置-账户-开启服务-获取授权码
spring.mail.password=163sqm
#spring.mail.properties.mail.smtp.auth.password=163sqm
#spring.mail.port=25
#spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
 */
    public JavaMailSender createMailSender(User user){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl() ;
        javaMailSender .setPassword(user.getPostPassword());
        String s = user.getPostbox();
        javaMailSender .setUsername(s);
        javaMailSender.setDefaultEncoding("UTF-8");
        String yu = s.split("@")[1].split("\\.")[0];
        javaMailSender .setHost("smtp."+yu+".com");
        Properties properties = new Properties() ;
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("spring.mail.properties.mail.smtp.starttls.required","true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender ;
    }

    @Override
    public boolean sendEmailByUser(Email email, User user) {
        JavaMailSender mailSender = createMailSender(user) ;
     return sendEmail(email , mailSender) ;

    }
}
