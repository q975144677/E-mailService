package com.email.email.service;

import com.email.email.pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();
    User get(int id);
    User get(String username);
    void insert(User user);
    void delete(int id);
    void update(User user);

}
