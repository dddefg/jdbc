package com.dk.jdbc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * 板凳宽宽
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("入库记录表")
public class Goods implements Serializable {
    private Long id;//主键

    @Excel(name = "订单编号",width = 30)
    private String orderId;//订单编号

    @Excel(name = "供货商名称",width = 20)
    private String supplierName;//供货商名称

    @Excel(name = "商品编号",width = 20)
    private String goodsId; //商品编号
    @Excel(name = "商品名",width = 20)
    private String goodsName; //商品名
    @Excel(name = "商品类别",width = 20)
    private String category; //商品类别
    @Excel(name = "单价")
    private double price;  //单价
    @Excel(name = "数量")
    private double num;  //数量

    @Excel(name = "价格单位")
    private String unit;  //价格单位
    @Excel(name = "总计",width = 50)
    private double cost;  //总计
    @Excel(name = "进货日期",width = 50,format = "yyyy-MM-dd")
    private Date date;    //进货日期
}
