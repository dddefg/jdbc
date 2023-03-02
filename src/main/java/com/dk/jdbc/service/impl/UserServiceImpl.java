package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.UserMapper;
import com.dk.jdbc.pojo.User;
import com.dk.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * 板凳宽宽
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    public User getUser(String username){
        return userMapper.getUser(username);
    }

    @Override
    public void addUser(User user) {
        System.out.println(user);
        userMapper.addUser(user);
    }

    @Override
    public boolean updateUser(User loginUser) {
        return userMapper.updateUser(loginUser);
    }


}
