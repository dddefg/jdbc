package com.dk.jdbc.controller.deliveryAnalysis;

import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.pojo.Return;
import com.dk.jdbc.service.GoodsService;
import com.dk.jdbc.service.IssueService;
import com.dk.jdbc.service.StockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 板凳宽宽
 * 出库分析查询
 */
@Controller
public class DeliveryAnalysisController {
    @Autowired
    IssueService issueService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    StockService stockService;

    QueryGoods query=new QueryGoods();



    private String dateA() {
        //获取本月第一天 为查询条件
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        Calendar cale = Calendar.getInstance();
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }
    @RequestMapping("/toDeliveryAnalysis")
    public String toDeliveryAnalysis(QueryGoods queryGoods,Model model){
        //第一个圆饼图数据
       pieData(queryGoods,model);
       //甜甜圈饼图数据
        donutData(model);
        //第三个柱形图
        barChartData(model);
        //第四个趋势图
        lineData(model);
        return "deliveryAnalysis/DeliveryAnalysisA";
    }
    @RequestMapping("/toOutboundGoods")
    public String toOutboundGoods(Model model){
        return "deliveryAnalysis/outboundGoods";
    }

    //查询所有出库信息
    @RequestMapping("/outboundList")
    public String outboundList(QueryGoods queryGoods,
                               @RequestParam(value = "pn",defaultValue = "1")Integer pn,//用于表示第几页
                               Model model
    ){
        /**
         * 按条件查询
         * 保存储存条件
         */
        if (queryGoods.getIssueId()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setIssueId(queryGoods.getIssueId());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getGoodsName()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setGoodsName(queryGoods.getGoodsName());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getDistributorName()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setDistributorName(queryGoods.getDistributorName());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getCategory()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setCategory(queryGoods.getCategory());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getIssueDate()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setIssueDate(queryGoods.getIssueDate());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }

        //把数据渲染回页面
        model.addAttribute("issueIdText",query.getIssueId());
        model.addAttribute("issueDistributorNameText",query.getDistributorName());
        model.addAttribute("issueGoodsNameText",query.getGoodsName());
        model.addAttribute("issueCategoryText",query.getCategory());
        model.addAttribute("issueDateText",query.getIssueDate());




        /**
         * 设置分页查询
         */
        //设置当前页面  ，每页显示多少条
        PageHelper.startPage(pn, 15 );
        //按条件查询
        List<Issue> list = issueService.getListIssue(query);
        //把查询的数据加入，设置分页菜单栏
        PageInfo<Issue> vegetablesAll = new PageInfo<>(list, 7);
        model.addAttribute("issueAll",vegetablesAll);

        //跳转到入库查询页面
        return "deliveryAnalysis/nest/outboundList";
    }

    //第一个饼图
public void pieData(QueryGoods queryGoods,Model model){
    if (queryGoods.getGoodsName()!=null && !(queryGoods.getGoodsName().equals(""))){
        //查询名字回显
        model.addAttribute("DeliveryAnalysisText", queryGoods.getGoodsName());
    }

    //加入作为查询条件
    queryGoods.setDate(dateA());
    double numGoods=0;
    double numIssue=0;
    double numStock=0;
    //本月入库量
    try {
        numGoods= goodsService.sum30Num(queryGoods);
    }catch (Exception e){
        System.out.println(e);
    }
    //本月出库量
    try {
        numIssue = issueService.sum30NumA(queryGoods);
    }catch (Exception e){
        System.out.println(e);
    }
    //库存量
    try {
        numStock= stockService.sumStock(queryGoods);
    }catch (Exception e){
        System.out.println(e);
    }
    model.addAttribute("numGoods",numGoods);
    model.addAttribute("numIssue",numIssue);
    model.addAttribute("numStock",numStock);
}
//第二个饼图
public void donutData(Model model){


    String issueDate = dateA();
        List<Issue> list = issueService.donutData(issueDate);
    model.addAttribute("donutDataAll" , list);
        if (list!=null){
            model.addAttribute("donutDataSize",list.size());
            for (int i=0;i<15;i++){
                if (i<list.size()) {
                    model.addAttribute("donutDataNumCategory"+i,list.get(i).getCategory());
                    model.addAttribute("donutDataNum" + i, list.get(i).getNum());
                }else {
                    model.addAttribute("donutDataNum" + i, 0);
                }
            }
        }else {
            model.addAttribute("donutDataSize",0);
        }
}
//第三个柱形图
    public void barChartData(Model model){
        String issueDate = dateA();
        List<Issue> list = issueService.donutData(issueDate);
        if (list!=null){
            //            冒泡排序进行排序
            Issue issue=null;
            boolean flag=false;
            for (int a=0;a<list.size()-1;a++){
                for (int b=0;b<list.size()-1-a;b++){
                    //如果前面的数比后面的小，则交换
                    if (list.get(b).getNum()<list.get(b+1).getNum()){
                        issue =list.get(b+1);
                        list.set(b+1, list.get(b));
                        list.set(b, issue);
                    }
                }
            }


            //把数据等比例缩小

            for (int i=0;i<7;i++) {
            if (i < list.size()){
                model.addAttribute("barChartDataName"+i, list.get(i).getCategory());
             int j= (int) list.get(i).getCost();
                int jj = (int) list.get(i).getNum();

                model.addAttribute("barChartDataCost"+i, j);
                model.addAttribute("barChartDataNum"+i, jj);
            }else {
                model.addAttribute("barChartDataName"+i, "无");
                model.addAttribute("barChartDataCost"+i, 0);
                model.addAttribute("barChartDataNum"+i, 0);
            }
            }
        }else {
            for (int i=0;i<7;i++) {
                model.addAttribute("barChartDataName"+i, "无");
                model.addAttribute("barChartDataCost"+i, 0);
                model.addAttribute("barChartDataNum"+i, 0);
            }
        }
    }

    //第四个趋势图
    public void lineData(Model model){
        //设置查询范围为365天
        long tep = 86400000;
        // 返回系统当前时间
        Date date2 = new Date();

        for (int i=0;i<3;i++) {
            //循环获取销量类被前三的名字
            String category = (String) model.getAttribute("barChartDataName" + i);
            model.addAttribute("lineDataName"+i, category);
            List<Issue> issues = issueService.getlineData(category);
            //加入到Map方便查找
            HashMap<String, Issue> issueHashMap = new HashMap<>();
            for (Issue issue:issues){
                String s = issue.getIssueDate().toString();
                issueHashMap.put(s, issue);
            }
            int temp = 0;
            //计算过去一周的时间
            for (int j=0;j<7;j++) {
                java.sql.Date date = new java.sql.Date(date2.getTime() - temp * tep);
                String s = date.toString();
                Issue issue = issueHashMap.get(s);  //对照时间一个一个找
                if (issue!=null){
                    int a = (int) issue.getNum();
                    model.addAttribute("lineDataNum"+j+i,a);
                    model.addAttribute("lineData"+j,date);
                }else {
                    model.addAttribute("lineDataNum"+j+i, 0);
                    model.addAttribute("lineData"+j,date);
                }
                temp++;
            }
        }
    }


    @RequestMapping ("toOutboundRankingList")
    public String toOutboundRankingList(QueryGoods queryGoods,Model model,
                                         @RequestParam(value = "pn",defaultValue = "1")Integer pn,//用于表示第几页
                                         HttpSession session){
        //如果时间为空则给一个默认时间(月)
        if (queryGoods == null || queryGoods.getSpecificDate() == null || queryGoods.getSpecificDate() == ""){
           String b = (String) session.getAttribute("condition");
           if (b != null) {  //不是第一次就根据上一次的访问赋值
               queryGoods.setSpecificDate(b);
           }else { //第一次访问赋值月
               queryGoods.setSpecificDate("month");
           }
        }


        /**
         * 设置分页查询
         */
        //设置当前页面  ，每页显示多少条
        PageHelper.startPage(pn, 15 );
        List<Issue> issueList = null;

        //回显时间 根据不同要求获取数据
        if (queryGoods.getSpecificDate().equals("day")){
            session.setAttribute("condition", "day");
            issueList = issueService.sortDay();  //根据天获取
            model.addAttribute("toOutboundRankingListText0", true);
        }else if (queryGoods.getSpecificDate().equals("month")){
            session.setAttribute("condition", "month");
            issueList = issueService.sortMonth();
            model.addAttribute("toOutboundRankingListText1", true);
        }else {
            session.setAttribute("condition", "year");
            issueList = issueService.sortYear();
            model.addAttribute("toOutboundRankingListText2", true);
        }
        PageInfo<Issue> vegetablesAll = new PageInfo<>(issueList, 7);

        int j=0;
        while (j<15){
            for (Issue issue:issueList){
                model.addAttribute("OutboundRankingListName"+j, issue.getGoodsName());
                model.addAttribute("OutboundRankingListNum"+j, issue.getNum());
                j++;
            }
            while (j<15){
                model.addAttribute("OutboundRankingListName"+j, "暂无");
                model.addAttribute("OutboundRankingListNum"+j, 0);
                j++;
            }
        }
        model.addAttribute("OutboundRankingListAll", vegetablesAll);
    return "deliveryAnalysis/outboundRankingList";
    }


}
