package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.User;

/**
 * 板凳宽宽
 */
public interface UserService extends IService<User> {
    User getUser(String username);

    void addUser(User user);

    boolean updateUser(User loginUser);
}
