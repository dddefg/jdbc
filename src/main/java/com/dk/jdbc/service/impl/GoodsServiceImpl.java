package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.GoodsMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    //商品入库
    @Override
    public boolean addPurchase(Goods goods) {
        //总价自动校验
        goods.setCost(goods.getPrice()*goods.getNum());
        boolean b = goodsMapper.addPurchase(goods);
        return b;
    }


   //根据条件查询
    @Override
    public List<Goods> getListGoods(QueryGoods queryGoods) {
        return goodsMapper.getListGoods(queryGoods);
    }

    @Override
    public double getMoney(Date date) {
        return goodsMapper.getMoney(date);
    }

    @Override
    public List<Goods> getGoodsCurve() {
        return goodsMapper.getGoodsCurve();
    }

    @Override
    public List<Goods> WarehousingAnalysisByName(String goodsName) {
        return goodsMapper.WarehousingAnalysisByName(goodsName);
    }

    @Override
    public List<Goods> monthWarehousingAnalysisByName(String goodsName,Date date) {
        return goodsMapper.monthWarehousingAnalysisByName(goodsName,date);
    }

    @Override
    public List<Goods> getNum365(Date date) {
        return goodsMapper.getNum365(date);
    }

    @Override
    public double sum30Num(QueryGoods queryGoods) {
        return goodsMapper.sum30Num(queryGoods);
    }

    @Override
    public Goods getIssueRankingNum(String goodsName) {
        return goodsMapper.getIssueRankingNum(goodsName);
    }

    @Override
    public List<Goods> sortDayB() {
        return goodsMapper.sortDayB();
    }

    @Override
    public List<Goods> sortMonthB() {
        return goodsMapper.sortMonthB();
    }

    @Override
    public List<Goods> sortYearB() {
        return goodsMapper.sortYearB();
    }


}
