package com.dk.jdbc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 板凳宽宽
 * 商品基础信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInformation {
    private String goodsId; //商品Id
    private String goodsName; //商品名
    private String category; //商品类别

}
