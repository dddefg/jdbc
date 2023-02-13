package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.Loss;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板凳宽宽
 */
@Mapper
public interface LossMapper extends BaseMapper<Loss> {
    boolean addLoss(Loss loss);
}
