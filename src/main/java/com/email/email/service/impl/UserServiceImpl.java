package com.email.email.service.impl;

import com.email.email.mapper.UserMapper;
import com.email.email.pojo.User;
import com.email.email.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper ;

    @Override
    public List<User> list() {
        List<User> list = userMapper.selectAll();

        return list;
    }

    @Override
    public User get(int id) {
        User user = new User();
        user.setId(id);
   User user1  = userMapper.selectOne(user);
        return user1;
    }

    @Override
    public User get(String username) {

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
       List<User> user = userMapper.selectByExample(example);
    if(user.isEmpty()){
        return null;
    }
    User result = user.get(0);
        return result;
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }


}
