package com.dk.jdbc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

/**
 * 板凳宽宽
 * 用来储存查询条件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryGoods {
    private String orderId;  //订单编号
    private String supplierName;//供货商名称
    private String goodsName; //商品名
    private String category; //商品类别
    private String date;     //时间类别

    //后加
    private String specificDate;  //年月日

    //出库条件
    private String issueId;  //出库编号
    private String distributorName; //经销商名
    private String issueDate; //出库时间
    private String issuePrice; //出库价格
}
