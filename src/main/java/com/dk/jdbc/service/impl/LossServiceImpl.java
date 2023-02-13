package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.LossMapper;
import com.dk.jdbc.mapper.ReturnMapper;
import com.dk.jdbc.pojo.Loss;
import com.dk.jdbc.pojo.Return;
import com.dk.jdbc.service.LossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 板凳宽宽
 */
@Service
public class LossServiceImpl extends ServiceImpl<LossMapper, Loss> implements LossService {
    @Autowired
    LossMapper lossMapper;
    @Override
    public boolean addLoss(Loss loss) {
        return lossMapper.addLoss(loss);
    }
}
