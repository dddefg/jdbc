package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.Loss;

/**
 * 板凳宽宽
 */
public interface LossService extends IService<Loss> {
    boolean addLoss(Loss loss);
}
