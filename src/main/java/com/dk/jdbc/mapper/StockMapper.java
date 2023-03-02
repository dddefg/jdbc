package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 板凳宽宽
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    Stock getByIdAndName(Stock stock);

    boolean addStock(Stock stock);

    boolean updateStock(Stock stock);

    double sumStock(QueryGoods queryGoods);

    List<Stock> getStockByName(String goodsName);
}
