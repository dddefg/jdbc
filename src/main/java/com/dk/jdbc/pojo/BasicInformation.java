package com.dk.jdbc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("商品基础信息表")
public class BasicInformation {
    @Excel(name = "商品Id",width = 30)
    private String goodsId; //商品Id
    @Excel(name = "商品名称",width = 50)
    private String goodsName; //商品名
    @Excel(name = "商品类别",width = 50)
    private String category; //商品类别

}
