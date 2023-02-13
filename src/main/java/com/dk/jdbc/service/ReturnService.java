package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.Return;
import com.dk.jdbc.pojo.Stock;

import java.util.List;

/**
 * 板凳宽宽
 */
public interface ReturnService extends IService<Return> {
    boolean addReturn(Return r);
    List<Return> listA();
}
