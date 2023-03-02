package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板凳宽宽
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUser(String username);

    void addUser(User user);

    boolean updateUser(User loginUser);
}
