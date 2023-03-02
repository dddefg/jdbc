package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.QueryGoods;

import java.sql.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
public interface IssueService extends IService<Issue> {
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

    List<Issue> sortDay();

    List<Issue> sortMonth();

    List<Issue> sortYear();
}
