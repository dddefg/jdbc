package com.dk.jdbc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

/**
 * 板凳宽宽
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ExcelTarget("退货记录表")
public class Return {
    private Long id;
    @Excel(name = "供货商",width = 30)
    private String supplierName;  //供货商
    @Excel(name = "商品名称",width = 30)
    private String goodsName;   //商品名称
    @Excel(name = "商品编号",width = 30)
    private String goodsId;   //  商品编号
    @Excel(name = "商品类别",width = 30)
    private String category;  //  商品类别
    @Excel(name = "退货数量",width = 30)
    private double num;   //  退货数量
    @Excel(name = "数量单位",width = 30)
    private String unit;  //  数量单位
    @Excel(name = "退货日期",width = 30,format = "yyyy-MM-dd")
    private Date date;   //   退货日期
}
