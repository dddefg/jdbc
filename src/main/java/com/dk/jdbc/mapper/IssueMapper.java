package com.dk.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
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
}
