package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.ReturnMapper;
import com.dk.jdbc.pojo.Return;
import com.dk.jdbc.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 板凳宽宽
 */
@Service
public class ReturnServiceImpl extends ServiceImpl<ReturnMapper, Return> implements ReturnService {
    @Autowired
    ReturnMapper returnMapper;
    @Override
    public boolean addReturn(Return r) {
        return returnMapper.addReturn(r);
    }

    @Override
    public List<Return> listA() {
        return returnMapper.listA();
    }
}
