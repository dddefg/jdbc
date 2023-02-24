package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.BasicInformation;

import java.util.List;

/**
 * 板凳宽宽
 */
public interface GoodsBasicInformationService extends IService<BasicInformation> {
    boolean addBasicInformation(BasicInformation basicInformation);

    List<BasicInformation> getListBasicInformations(String goodsName);

    boolean deleteBasicInformation(BasicInformation basicInformation);
}
