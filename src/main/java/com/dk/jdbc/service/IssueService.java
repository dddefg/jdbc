package com.dk.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dk.jdbc.pojo.Issue;

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
}
