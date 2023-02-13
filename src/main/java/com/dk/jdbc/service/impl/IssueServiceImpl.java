package com.dk.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dk.jdbc.mapper.GoodsMapper;
import com.dk.jdbc.mapper.IssueMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
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

}
