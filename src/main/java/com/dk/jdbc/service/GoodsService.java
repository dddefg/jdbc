package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.QueryGoods;

import java.sql.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
public interface GoodsService extends IService<Goods> {
    boolean addPurchase(Goods goods);


    List<Goods> getListGoods(QueryGoods queryGoods);


    double getMoney(Date date);

    //十天内每日入库量
    List<Goods> getGoodsCurve();


    List<Goods> WarehousingAnalysisByName(String goodsName);

    List<Goods> monthWarehousingAnalysisByName(String goodsName,Date date);

    List<Goods> getNum365(Date date);
}
