package com.dk.jdbc.controller.deliveryAnalysis;

import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.pojo.Stock;
import com.dk.jdbc.service.GoodsService;
import com.dk.jdbc.service.IssueService;
import com.dk.jdbc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 板凳宽宽
 * 单个商品出库分析
 */
@Controller
public class SingleIssueController {
    @Autowired
    IssueService issueService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    StockService stockService;

    @RequestMapping("/toSingleIssue")
    public String toSingleIssue(QueryGoods queryGoods, Model model){
        //第一个图
        List<Issue> issueRanking = issueService.getIssueRankingNum();
        for (int i=0;i<4;i++){
            Issue issue = issueRanking.get(i);
            if (issue != null){
                model.addAttribute("toSingleName"+i,issue.getGoodsName());
                model.addAttribute("toSingleIssueNum"+i,issue.getNum());
                Goods goods= goodsService.getIssueRankingNum(issue.getGoodsName());
                if (goods != null){
                    model.addAttribute("toSingleGoodsNum"+i,goods.getNum());
                }else {
                    model.addAttribute("toSingleGoodsNum"+i,0);
                }
                List<Stock> byIdAndName = stockService.getStockByName(issue.getGoodsName());
                if (byIdAndName != null){
                    model.addAttribute("toSingleStockNum"+i,byIdAndName.get(0).getNum());
                }else {
                    model.addAttribute("toSingleStockNum"+i,0);
                }
            }else {
                model.addAttribute("toSingleStockNum"+i,0);
                model.addAttribute("toSingleName"+i,"无");
                model.addAttribute("toSingleIssueNum"+i,0);
                model.addAttribute("toSingleGoodsNum"+i,0);
                model.addAttribute("toSingleStockNum"+i,0);

            }

        }

        //第二个(商品销售趋势图)
        graphAreaLine(queryGoods, model);
        comparisonOfHotGoods(model);

        return "deliveryAnalysis/singleIssue";
    }
    //第二个(商品销售趋势图)
    public void graphAreaLine(QueryGoods queryGoods,Model model){
        //设置查询范围为10天
        long tep = 86400000;
        // 返回系统当前时间
        java.util.Date date2 = new Date();


        //回显
        model.addAttribute("graphAreaLineNameText",queryGoods.getGoodsName());
        if (queryGoods.getSpecificDate() == null || queryGoods.getSpecificDate().equals("")){
            queryGoods.setSpecificDate("day");
        }
        if (queryGoods.getSpecificDate().equals("day")){
            model.addAttribute("graphAreaLineDateText", true);
        }else {
            model.addAttribute("graphAreaLineDateText1", true);
        }
        List<Issue> issueList;
        if (queryGoods.getGoodsName() == null || queryGoods.getGoodsName() == ""){
            //为空查询所有
            if (queryGoods.getSpecificDate().equals("day")){
                //一天一天查询所有
                issueList = issueService.graphAreaLineA();
            }else {
            //按月查询所有
                issueList = issueService.graphAreaLineB();
            }
        }
        else {
            String goodsName = queryGoods.getGoodsName();
            //不为空按名字查询
            if (queryGoods.getSpecificDate().equals("day")){
                //按名字一天一天查
                issueList = issueService.graphAreaLineC(goodsName);
            }else {
                //按名字一个月一个月查
                issueList = issueService.graphAreaLineD(goodsName);
            }
        }
        //拿到数据封装给前端
        HashMap<String, Issue> hashMap = new HashMap<>();
        if (issueList != null){
            for (Issue issue:issueList){
                if (queryGoods.getSpecificDate().equals("day")) {
                    hashMap.put(issue.getIssueDate().toString(), issue);
                }else {
                    hashMap.put(issue.getId().toString(), issue);
                }
            }
        }
        if (queryGoods.getSpecificDate().equals("day")){
            //如果是一天一天
            for (int i=0;i<10;i++){
                java.sql.Date date = new java.sql.Date(date2.getTime() - i * tep);
                Issue issue = hashMap.get(date.toString());
                if (issue!=null){
                    //按天封装
                    model.addAttribute("graphAreaLineDate"+i, date.toString());
                    model.addAttribute("graphAreaLineNum"+i, issue.getNum());
                }else {
                    model.addAttribute("graphAreaLineDate"+i, date.toString());
                    model.addAttribute("graphAreaLineNum"+i, 0);
                }
            }
        }else {
            //获取当前月份
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            for (int i=0;i<10;i++){
                String a = String.valueOf(month);
                Issue issue = hashMap.get(a);
                if (issue!=null) {
                    model.addAttribute("graphAreaLineDate"+i, month + "月");
                    model.addAttribute("graphAreaLineNum"+i, issue.getNum());
                }else {
                    model.addAttribute("graphAreaLineDate"+i, month + "月");
                    model.addAttribute("graphAreaLineNum"+i, 0);
                }
                if (month == 1){
                    month = 12;
                }else {
                    month--;
                }
            }
        }
    }

    //第三个 (本月热销商品与上月热销商品对比)
    public void comparisonOfHotGoods(Model model){
        //本月
        List<Issue> list1 = issueService.comparisonOfHotGoodsA();


        //上月
        List<Issue> list2 = issueService.comparisonOfHotGoodsB();

        for (int i=0;i<5;i++){
            if (i < list1.size()){
                model.addAttribute("comparisonOfHotGoodsNumA"+i,list1.get(i).getNum());
                model.addAttribute("comparisonOfHotGoodsNameA"+i,list1.get(i).getGoodsName());
            }else {
                model.addAttribute("comparisonOfHotGoodsNumA"+i,0);
                model.addAttribute("comparisonOfHotGoodsNameA"+i,"无");
            }
            if (i < list2.size()){
                model.addAttribute("comparisonOfHotGoodsNumB"+i,list2.get(i).getNum());
                model.addAttribute("comparisonOfHotGoodsNameB"+i,list2.get(i).getGoodsName());
            }else {
                model.addAttribute("comparisonOfHotGoodsNumB"+i,0);
                model.addAttribute("comparisonOfHotGoodsNameB"+i,"无");
            }
        }
    }

}
