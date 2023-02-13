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
 * 出库记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("出库记录表")
public class Issue implements Serializable {
    private Long id;  //主键ID
    @Excel(name = "出货编号",width = 30)
    private String issueId;  //出货编号
    @Excel(name = "经销商或客户",width = 30)
    private String distributorName; //经销商或者客户
    @Excel(name = "商品编号",width = 30)
    private String goodsId; //商品编号
    @Excel(name = "货品名称",width = 30)
    private String goodsName; //商品编号
    @Excel(name = "类别",width = 30)
    private String category;  //类别
    @Excel(name = "数量",width = 30)
    private double num;   //数量
    @Excel(name = "数量单位",width = 30)
    private String unit;  //数量单位
    @Excel(name = "出库价格",width = 30)
    private double issuePrice; //出库价格
    @Excel(name = "总价格",width = 30)
    private double cost;  //总价格
    @Excel(name = "出货日期",width = 30,format = "yyyy-MM-dd")
    private Date issueDate; //出货时间
}
