package com.dk.jdbc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 板凳宽宽
 * 库存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String goodsId;
    private String goodsName;
    private String category;
    private double num;
    private String unit;
    private Date lastPurchaseDate;
    private Date lastIssueDate;
}
