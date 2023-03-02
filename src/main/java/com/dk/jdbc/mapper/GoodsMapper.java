package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.QueryGoods;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {




    boolean addPurchase(Goods goods);


    List<String> getCategory();


    List<Goods> getListGoods(QueryGoods queryGoods);

    double getMoney(Date date);

    List<Goods> getGoodsCurve();

    List<Goods> WarehousingAnalysisByName(String goodsName);

    List<Goods> monthWarehousingAnalysisByName(String goodsName,Date date);

    List<Goods> getNum365(Date date);

    double sum30Num(QueryGoods queryGoods);

    Goods getIssueRankingNum(String goodsName);
    List<Goods> sortDayB();

    List<Goods> sortMonthB();

    List<Goods> sortYearB();
}
