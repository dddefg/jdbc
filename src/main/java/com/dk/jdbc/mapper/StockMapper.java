package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板凳宽宽
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    Stock getByIdAndName(Stock stock);

    boolean addStock(Stock stock);

    boolean updateStock(Stock stock);
}
