package com.email.email.service;

import com.email.email.pojo.Email;
import com.email.email.pojo.User;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

public interface EmailService {
    List<Email> list();
    List<Email> listByUid(int uid);
    Email get(int id);
    void delete(int id);
    void insert(Email email );
    void update(Email email );
    List<Email> listBySendTime(Date time);
    boolean sendEmail(Email email ) throws MessagingException;
boolean sendEmailByUser(Email email , User user );
}
