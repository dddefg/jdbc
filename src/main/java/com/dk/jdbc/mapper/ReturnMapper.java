package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.Return;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 板凳宽宽
 */
@Mapper
public interface ReturnMapper extends BaseMapper<Return> {
    boolean addReturn(Return r);

    List<Return> listA();
}
