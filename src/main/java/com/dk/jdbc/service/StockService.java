package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.pojo.Stock;

import java.util.List;

/**
 * 板凳宽宽
 */
public interface StockService extends IService<Stock> {
    Stock getByIdAndName(Stock stock);

    boolean addStock(Stock stock);

    boolean updateStock(Stock stock);

    double sumStock(QueryGoods queryGoods);

    List<Stock> getStockByName(String goodsName);
}
