package com.dk.jdbc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 板凳宽宽
 * 损耗报备
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("损耗报备记录")
public class Loss {
    private Long id;
    @Excel(name = "商品编号",width = 30)
    private String goodsId;
    @Excel(name = "商品名称",width = 30)
    private String goodsName;
    @Excel(name = "商品类别",width = 30)
    private String category;
    @Excel(name = "报备数量",width = 30)
    private double num;
    @Excel(name = "数量单位",width = 30)
    private String unit;
    @Excel(name = "报备人员",width = 30)
    private String filedBy;//报备人
    @Excel(name = "报备日期",width = 30,format = "yyyy-MM-dd")
    private Date date;
}
