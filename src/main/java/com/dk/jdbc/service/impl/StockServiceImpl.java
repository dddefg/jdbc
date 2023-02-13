package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.GoodsMapper;
import com.dk.jdbc.mapper.StockMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Stock;
import com.dk.jdbc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 板凳宽宽
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {
    @Autowired
    StockMapper stockMapper;
    @Override
    public Stock getByIdAndName(Stock stock) {
        return stockMapper.getByIdAndName(stock);
    }

    @Override
    public boolean addStock(Stock stock) {
        return stockMapper.addStock(stock);
    }

    @Override
    public boolean updateStock(Stock stock) {
        return stockMapper.updateStock(stock);
    }
}
