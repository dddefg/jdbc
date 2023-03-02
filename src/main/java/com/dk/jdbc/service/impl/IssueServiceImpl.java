package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.GoodsMapper;
import com.dk.jdbc.mapper.IssueMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
@Service
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue> implements IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Override
    public boolean addIssue(Issue issue) {
        return issueMapper.addIssue(issue);
    }

    @Override
    public double getMoney(Date date) {
        return issueMapper.getMoney(date);
    }

    @Override
    public List<Issue> get7Day() {
        return issueMapper.get7Day();
    }

    @Override
    public List<Issue> get30Day() {
        return issueMapper.get30Day();
    }

    @Override
    public List<Issue> getIssueCurve() {
        return issueMapper.getIssueCurve();
    }

    @Override
    public List<Issue> getIssueRanking() {
        return issueMapper.getIssueRanking();
    }

    @Override
    public List<Issue> getListIssue(QueryGoods query) {
        return issueMapper.getListIssue(query);
    }

    @Override
    public double sum30NumA(QueryGoods queryGoods) {
        return issueMapper.sum30NumA(queryGoods);
    }

    @Override
    public List<Issue> donutData(String issueDate) {
        return issueMapper.donutData(issueDate);
    }

    @Override
    public List<Issue> getlineData(String category) {
        return issueMapper.getlineData(category);
    }

    @Override
    public List<Issue> getIssueRankingNum() {
        return issueMapper.getIssueRankingNum();
    }

    @Override
    public List<Issue> graphAreaLineA() {
        return issueMapper.graphAreaLineA();
    }

    @Override
    public List<Issue> graphAreaLineC(String goodsName) {
        return issueMapper.graphAreaLineC(goodsName);
    }

    @Override
    public List<Issue> graphAreaLineB() {
        return issueMapper.graphAreaLineB();
    }

    @Override
    public List<Issue> graphAreaLineD(String goodsName) {
        return issueMapper.graphAreaLineD(goodsName);
    }

    @Override
    public List<Issue> comparisonOfHotGoodsA() {
        return issueMapper.comparisonOfHotGoodsA();
    }

    @Override
    public List<Issue> comparisonOfHotGoodsB() {
        return issueMapper.comparisonOfHotGoodsB();
    }

    @Override
    public List<Issue> sortDay() {
        return issueMapper.sortDay();
    }

    @Override
    public List<Issue> sortMonth() {
        return issueMapper.sortMonth();
    }

    @Override
    public List<Issue> sortYear() {
        return issueMapper.sortYear();
    }

}
