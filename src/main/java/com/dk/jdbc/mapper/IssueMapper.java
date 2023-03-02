package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.QueryGoods;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
@Mapper
public interface IssueMapper extends BaseMapper<Issue> {
    boolean addIssue(Issue issue);

    double getMoney(Date date);

    List<Issue> get7Day();

    List<Issue> get30Day();

    List<Issue> getIssueCurve();

    List<Issue> getIssueRanking();

    List<Issue> getListIssue(QueryGoods query);

    double sum30NumA(QueryGoods queryGoods);

    List<Issue> donutData(String issueDate);

    List<Issue> getlineData(String category);

    List<Issue> getIssueRankingNum();

    List<Issue> graphAreaLineA();

    List<Issue> graphAreaLineC(String goodsName);

    List<Issue> graphAreaLineB();

    List<Issue> graphAreaLineD(String goodsName);

    List<Issue> comparisonOfHotGoodsA();

    List<Issue> comparisonOfHotGoodsB();

    List<Issue> sortYear();

    List<Issue> sortMonth();

    List<Issue> sortDay();
}
