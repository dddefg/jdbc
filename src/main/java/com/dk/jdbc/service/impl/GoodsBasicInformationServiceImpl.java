package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.GoodsBasicInformationMapper;
import com.dk.jdbc.pojo.BasicInformation;
import com.dk.jdbc.service.GoodsBasicInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 板凳宽宽
 */
@Service
public class GoodsBasicInformationServiceImpl extends ServiceImpl<GoodsBasicInformationMapper, BasicInformation> implements GoodsBasicInformationService {
    @Autowired
    GoodsBasicInformationMapper goodsBasicInformationMapper;
    @Override
    public boolean addBasicInformation(BasicInformation basicInformation) {
       return goodsBasicInformationMapper.addBasicInformation(basicInformation);

    }

    @Override
    public List<BasicInformation> getListBasicInformations(String goodsName) {
        return goodsBasicInformationMapper.getListBasicInformations(goodsName);
    }

    @Override
    public boolean deleteBasicInformation(BasicInformation basicInformation) {
        return goodsBasicInformationMapper.deleteBasicInformation(basicInformation);
    }
}
