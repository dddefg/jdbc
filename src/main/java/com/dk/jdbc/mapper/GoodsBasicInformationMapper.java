package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.BasicInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 板凳宽宽
 */
@Mapper
public interface GoodsBasicInformationMapper extends BaseMapper<BasicInformation> {
    boolean addBasicInformation(BasicInformation basicInformation);

    List<BasicInformation> getListBasicInformations(String goodsName);

    boolean deleteBasicInformation(BasicInformation basicInformation);
}
