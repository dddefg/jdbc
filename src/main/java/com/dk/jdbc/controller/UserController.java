package com.dk.jdbc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dk.jdbc.pojo.*;
import com.dk.jdbc.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
@Controller
public class UserController {
    //获取当前时间
    Calendar calendar = Calendar.getInstance();
    Date time = calendar.getTime();
    java.sql.Date date = new java.sql.Date(time.getTime());

    @Autowired
    LossService lossService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    IssueService issueService;
    @Autowired
    StockService stockService;
    @Autowired
    ReturnService returnService;
    @Autowired
    UserService userService;



    int nums=5;  //每页条数

    @GetMapping("/all")  //设置分页，默认为1
    public String getAll(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        List<User> list = userService.list();
        model.addAttribute("users", list);
        //分页查询
        Page<User> userPage = new Page<>(pn, nums); //当前页码每页条数
        //分页查询结果
        Page<User> page = userService.page(userPage);
        model.addAttribute("page", page);
        return "userAll";
    }
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id")Integer id, //删除的ID
                             @RequestParam(value = "pn",defaultValue = "1")Integer pn,  //当前页码
                             RedirectAttributes r){  //重定向参数
            userService.removeById(id);
            //重定向参数
        r.addAttribute("pn",pn);

        //重定向
        return "redirect:/all";
    }

    @GetMapping("/increase")
    public String increaseUser(){
        return "increaseUser";
    }

    @PostMapping("/registeredUser")
    public String increaseUser(User user,Model model){
        User user1 = userService.getUser(user.getUsername());
        if (user1 != null){
            model.addAttribute("increaseUser","账号已存在请重新输入");
            return "/user/registered";
        }
        userService.addUser(user);
        model.addAttribute("msg", "注册成功请登录");
        return "login";
    }


    @RequestMapping({"/login","/"})  // 处理/处理 /login请求  登录界面的
    public String login(User user, HttpSession session,Model model,RedirectAttributes redirectAttributes){
        User loginUser = userService.getUser(user.getUsername());
        if (user.getUsername() == null){
            return "/login";
        }
        if (loginUser!=null){
            if (user.getUsername().equals(loginUser.getUsername())
                    && user.getPassword().equals(loginUser.getPassword())){
                //登录改成把用户保存起来
                session.setAttribute("loginUser", user);
                redirectAttributes.addAttribute("username", user.getUsername());
                //登录成功重定向到index.html
                return "redirect:/index";
            }else {
                model.addAttribute("msg", "密码错误");
                //回到登录界面
                return "login";
            }
        }else {
            model.addAttribute("msg", "账号不存在");
            //回到登录界面
            return "login";
        }
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "login";
    }

    @GetMapping("/cs")
    public String cs(HttpSession session){
        return "vue/cs";
    }



    @GetMapping({"/index","index.html"})
    public String index(Model model){
        double goodsMoney=0;
        double issueMoney=0;

        //以当前日期为条件获取今日入库金额
        try {
           goodsMoney = goodsService.getMoney(date);
        }catch (Exception e){
        }
        //以当前日期为条件获取今日出库金额
        try {
            issueMoney = issueService.getMoney(date);
        }catch (Exception e){
        }



        /**
         * 本周热销
         */
        model.addAttribute("day", getDay());
        /**
         * 本月热销
         */
        model.addAttribute("day30", getDay30());


        /**
         * 滞销数量
         */
        model.addAttribute("unsalableNum",unsalableNum());
        /**
         * 库存不足数量
         */
        model.addAttribute("stockLess",stockLess());

        /**
         * 增加出库入库金额
         */
        model.addAttribute("goodsMoney",goodsMoney);
        model.addAttribute("issueMoney",issueMoney);

        /**
         * 出库入库损耗退货饼图数据
         */
        List<Long> longs = pieChart();
        for (int i=0;i<longs.size()-1;i++){
            long i1=longs.get(i);
            double ii1=(int) i1;

            long i2=longs.get(4);
            double ii2=(int) i2;

            double j=ii1/ii2;
            BigDecimal bigDecimal = new BigDecimal(j);
            double v = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            int j1 = (int) (v*100);

            model.addAttribute("num"+i, j1);
           model.addAttribute("proportion"+i, j1+"%");
        }

        //入库曲线图
        getGoodsCurve(model);
        //出库曲线图
        getIssueCurve(model);
        //30天内销量排行
getIssueRanking(model);

        //跳转首页index.html
        return "/index";
    }

    @GetMapping("/registered")
    public String registered(){
        //跳转注册registered.html
        return "/user/registered";
    }

    //出库入库退货损耗报备饼图比例
    public List<Long> pieChart(){
        /**
         * 30天入出库数量
         */
        long goodsNum = 0;
        List<Goods> list = goodsService.list();
        for (Goods goods:list){
            Long i = (date.getTime() - goods.getDate().getTime())/(1000 * 60 * 60 * 24);
            if (i <= 30){
                goodsNum +=goods.getNum();
            }
        }

        //30天内出库数量
        long issueNum =0;
        List<Issue> list1 = issueService.list();
        for (Issue issue:list1){
            Long i = (date.getTime() - issue.getIssueDate().getTime())/(1000 * 60 * 60 * 24);
            if (i <=30){
                issueNum +=issue.getNum();
            }
        }
        //30天内退货数量
        long returnNum = 0;
        List<Return> list2 = returnService.listA();
        for (Return r:list2){
            Long i = (date.getTime() - r.getDate().getTime())/(1000 * 60 * 60 * 24);
            if (i <=30){
                returnNum +=r.getNum();
            }
        }
        //30天内损耗数量
        long lossNum = 0;
        List<Loss> list3 = lossService.list();
        for (Loss loss:list3){
            Long i = (date.getTime() - loss.getDate().getTime())/(1000 * 60 * 60 * 24);
            if (i <=30){
                lossNum +=loss.getNum();
            }
        }
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(goodsNum);
        longs.add(issueNum);
        longs.add(returnNum);
        longs.add(lossNum);
        longs.add(goodsNum+issueNum+returnNum+lossNum);
        return longs;
    }

    //滞销数量
    public int unsalableNum(){
        int num=0;
        List<Stock> list = stockService.list();
        for (Stock stock:list){
            //如果出库日期为空
            if (stock.getLastIssueDate() == null){
                //如果一个商品从未出库，且入库时间已经超过7天，并且库存大于100 判断为滞销商品
                Long i = (date.getTime() - stock.getLastPurchaseDate().getTime())/(1000 * 60 * 60 * 24);
                if (i > 7 && stock.getNum() > 100 ){
                    num++;
                }
                //如果一个商品从未出库，且入库时间已经超过30天 判断为滞销商品
                else if (i > 30 ){
                    num++;
                }
            }else {
                Long i = (date.getTime()-stock.getLastIssueDate().getTime())/(1000 * 60 * 60 * 24);
                if (i > 30 && stock.getNum() > 100){
                    //如果一个商品30天未售出且库存大于100
                    num++;
                }
                else if (i > 60 && stock.getNum() > 0){
                    //如果超过60天未售出且库存大于0
                    num++;
                }
            }
        }
        return num;
    }

    //库存不足商品数量
    public int stockLess(){
        int num = 0;
        List<Stock> list = stockService.list();
        for (Stock stock:list){
            if (stock.getNum() < 30 ){
                num ++;
            }
        }
        return num;
    }

    //本周热卖商品
     public String getDay(){
        String name="无";
        double goodsNum=0.0;
         List<Issue> day = issueService.get7Day();
         for (Issue issue:day){
             if (issue.getNum() > goodsNum){
                 goodsNum = issue.getNum();
                 name = issue.getGoodsName();
             }
         }
         return name;
     }

    //本月热卖
    public String getDay30(){
        String name="无";
        double goodsNum=0.0;
        List<Issue> day = issueService.get30Day();
        for (Issue issue:day){
            if (issue.getNum() > goodsNum){
                goodsNum = issue.getNum();
                name = issue.getGoodsName();
            }
        }
        return name;
    }

    @RequestMapping("/index11")
    public String index11(){
        return "/index11";
    }


    //入库趋势图
    public void getGoodsCurve(Model model){


        List<Goods> goodsCurve = goodsService.getGoodsCurve();
        //一天的时间戳
        long tep = 86400000;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int time = 10;  //计算前面10-1天的日期
        int Pointer = 0;    //List<goods> 指针
        for (int i = 0; i < 11; i++) {  //遍历获取11天的值

            //计算时间
            String format = simpleDateFormat.format(new Date((date.getTime() - tep * time)));

            model.addAttribute("dateCurve", simpleDateFormat.format(new Date(date.getTime()-tep*10)));
            model.addAttribute("dateCurveTo", simpleDateFormat.format(new Date(date.getTime())));


            if (Pointer == goodsCurve.size()){  //如果指针等于集合的长度，说明越界了赋值为0
                model.addAttribute("goodsCurveNum" + i, 0);
            }else {   //否则就可以继续取值
                Goods goods = goodsCurve.get(Pointer);
                //如果计算的时间能对上数据库的时间  渲染给页面
                if (format.equals(simpleDateFormat.format(goods.getDate()))) {
                    model.addAttribute("goodsCurveNum" + i, goods.getNum());
                    //如果渲染成功指针+1
                    Pointer++;
                } else {
                    //时间对不上赋值为0
                    model.addAttribute("goodsCurveNum" + i, 0);
                }
            }
            //时间推移
            time--;
            //如果时间小于今天直接break
            if (time < 0) {
                break;
            }
        }
        }
//出库趋势图
        public void getIssueCurve(Model model){
       List<Issue> issues = issueService.getIssueCurve();
            //一天的时间戳
            long tep = 86400000;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            int time = 10;  //计算前面10-1天的日期
            int Pointer = 0;    //List<goods> 指针
            for (int i = 0; i < 11; i++) {  //遍历获取11天的值

                //计算时间
                String format = simpleDateFormat.format(new Date((date.getTime() - tep * time)));

                if (Pointer == issues.size()){  //如果指针等于集合的长度，说明越界了赋值为0
                    model.addAttribute("issueCurveNum" + i, 0);
                }else {   //否则就可以继续取值
                    Issue issue = issues.get(Pointer);
                    //如果计算的时间能对上数据库的时间  渲染给页面
                    if (format.equals(simpleDateFormat.format(issue.getIssueDate()))) {
                        model.addAttribute("issueCurveNum" + i, issue.getNum());
                        //如果渲染成功指针+1
                        Pointer++;
                    } else {
                        //时间对不上赋值为0
                        model.addAttribute("issueCurveNum" + i, 0);
                    }
                }
                //时间推移
                time--;
                //如果时间小于今天直接break
                if (time < 0) {
                    break;
                }
            }
        }
        //本月销量前五
    public void getIssueRanking(Model mode) {
        //获取30天内各商品销售量
        List<Issue> issuesList = issueService.getIssueRanking();
        double sum = 0;
        //统计销售量
        for (int i=0;i<issuesList.size();i++){
            sum +=issuesList.get(i).getCost();
        }

        //取前五展示
        for (int i=0;i < 5;i++){
            if (i<issuesList.size()) {
                double d = issuesList.get(i).getCost();
                double width = d/sum*100;

                BigDecimal bigDecimal = new BigDecimal(width);
                //只保留两位小数
                double f1 = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                System.out.println("宽"+width);
                mode.addAttribute("RankingName"+i,issuesList.get(i).getGoodsName());
                mode.addAttribute("RankingCost"+i,issuesList.get(i).getCost());
                mode.addAttribute("RankingNum"+i,f1);
            }else {
                mode.addAttribute("RankingName"+i,"暂无");
                mode.addAttribute("RankingCost"+i,"0");
                mode.addAttribute("RankingNum"+i,0);

            }
        }

    }
}
