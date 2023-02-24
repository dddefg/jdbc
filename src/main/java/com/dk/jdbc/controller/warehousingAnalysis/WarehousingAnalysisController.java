package com.dk.jdbc.controller.warehousingAnalysis;

import com.dk.jdbc.controller.UserController;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 板凳宽宽
 */
@Controller
public class WarehousingAnalysisController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserController userController;
    //获取当前时间
    Calendar calendar = Calendar.getInstance();
    Date time = calendar.getTime();
    java.sql.Date date = new java.sql.Date(time.getTime());

    /**
     *
     * @param queryGoods  按商品名查看入库情况，如果为空则默认查看本月销量最高的
     * @param model    返回数据
     * @return
     */
    @RequestMapping("/ToWarehousingAnalysis")
    public String toWarehousingAnalysis(QueryGoods queryGoods,Model model){
        String day30 = userController.getDay30();  //名字条件
        String day = "day";   //按时间查询
        //如果名字为空，则更新名字作为条件
        if (queryGoods == null || queryGoods.getGoodsName() == null || queryGoods.getGoodsName() == ""){
            queryGoods.setGoodsName(day30);
        }
        //如果时间为空则给一个默认时间
        if (queryGoods == null || queryGoods.getSpecificDate() == null || queryGoods.getSpecificDate() == ""){
            queryGoods.setSpecificDate("day");
        }
        //查询名字回显示
        model.addAttribute("flotGoodsNameText", queryGoods.getGoodsName());
        //查询时间回显
        if (queryGoods.getSpecificDate().equals("day")){
            model.addAttribute("flotGoodsDateText", true);
        }else {
            model.addAttribute("flotGoodsDateText1", true);
        }

        toWarehousingAnalysisByName(queryGoods.getGoodsName(),queryGoods.getSpecificDate(),model);
        day365(model);
        return "/warehousingAnalysis/flot";
    }

//出库量及价格趋势图
    public void toWarehousingAnalysisByName(String goodsName,String date1,Model model) {
        model.addAttribute("sellText", "销  量");
        model.addAttribute("priceText", "价  格");
        //如果按天统计
        if (date1.equals("day")){
        List<Goods> goodsList = goodsService.WarehousingAnalysisByName(goodsName);
            System.out.println(goodsList);
        //一天的时间戳
        long tep = 86400000;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int time = 12;  //计算前面11-1天的日期
        int pointer = 0;    //List<goods> 指针
        for (int i = 0; i < 12; i++) {  //遍历获取11天的值
//计算时间
            String format = simpleDateFormat.format(new Date((date.getTime() - tep * time)));
            model.addAttribute("WarehousingAnalysisDate" + i, format);
            //时间对应的销量  如果不为空
            if (goodsList != null){
                //如果集合的长等于坐标了，停止取值
                if (goodsList.size() == pointer){
                    System.out.println("停止1");
                    model.addAttribute("WarehousingAnalysisPrice"+i,0);
                    model.addAttribute("WarehousingAnalysisName"+i,0);
                }else {
                    Goods goods = goodsList.get(pointer);
                    //如果时间对的上赋值
                    if(format.equals(simpleDateFormat.format(goods.getDate()))){
                        System.out.println("加入");
                        //当日总价除以数量得出单价
                        model.addAttribute("WarehousingAnalysisPrice"+i,goods.getCost()/ goods.getNum());
                        //当日入库数量
                        model.addAttribute("WarehousingAnalysisName"+i,goods.getNum());
                        //如果渲染成功指针+1
                        pointer++;
                    }else {
                        //时间对不上赋值为0
                        System.out.println("停止2");
                        model.addAttribute("WarehousingAnalysisName" + i, 0);
                        model.addAttribute("WarehousingAnalysisPrice"+i,0);
                    }
                }
            }else {
                System.out.println("停止3");
                model.addAttribute("WarehousingAnalysisName"+i,0);
                model.addAttribute("WarehousingAnalysisPrice"+i,0);
            }
            //时间推移
            time--;
            //如果时间小于今天直接break
            if (time < 0) {
                break;
            }


        }
        }

        if (date1.equals("month")){
            //设置查询范围为365天
            long tep = 86400000;
            // 返回系统当前时间
            Date date2 = new Date();
            // 将当前时间转化为sql时间
            java.sql.Date date = new java.sql.Date(date2.getTime() - 365*tep);


            List<Goods> goodsList = goodsService.monthWarehousingAnalysisByName(goodsName,date);

            //储存数据
            HashMap<Integer, Goods> goodsHashMap = new HashMap<>();
            for (Goods goods:goodsList){
                goodsHashMap.put((int) goods.getPrice(), goods);
            }

            //获取当前月份
            int month = calendar.get(Calendar.MONTH) + 1;
            int tem = 11;

            //给12个月份赋值
            for (int i=0;i<12;i++){
                Goods goods = goodsHashMap.get(month);
                if (goods != null){
                    //如果goods不为0，加入
                    model.addAttribute("WarehousingAnalysisName"+tem,goods.getNum());
                    model.addAttribute("WarehousingAnalysisPrice"+tem, goods.getCost()/ goods.getNum());
                }else { //如果为null直接给0
                    model.addAttribute("WarehousingAnalysisName"+tem,0);
                    model.addAttribute("WarehousingAnalysisPrice"+tem, 0);
                }
                if (month==1){  //到一月时直接跳回12月
                    //如果为1月跳回12月
                    month = 12;
                }else {  //月份减1
                    month --;
                }
                //model值坐标减一
                tem--;
            }
            }
        }


    //365天内商品情况
    public void day365(Model model){
        //设置查询范围为365天
        long tep = 86400000;
        // 返回系统当前时间
        Date date2 = new Date();
        // 将当前时间转化为sql时间
        java.sql.Date date = new java.sql.Date(date2.getTime() - 365*tep);
        /**
         * 假如今天是第二周，拿到的数据就是第2周，1周，然后去年的 52周，51，50
         */
        List<Goods> goodsList = goodsService.getNum365(date);
        //把数据放入到Map对象,方便取值
        HashMap<Integer, Goods> map = new HashMap<>();
        //保存最大的值
        int big = 0;
        for (int i=0;i<goodsList.size();i++){
            if (goodsList.get(i).getNum() > big){
                big = (int) goodsList.get(i).getNum();
            }
            map.put((int)goodsList.get(i).getPrice(), goodsList.get(i));
        }
        /**
         * 把数据缩小 如果最大的为999 全部数据就除以10为99 以此类推 冗余代码，有时间再优化
         */
        int tem=1;
        if (big>=10000000){
            tem=1000000;
        }else if (big>=1000000){
            tem=100000;
        }else if (big>=100000){
            tem=10000;
        }else if (big>=10000){
            tem=1000;
        }else if (big>=1000){
            tem=100;
        }else if (big>=100){
            tem=10;
        }

        //获取当前是第几周
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.WEEK_OF_YEAR);
        //循环遍历加入到model里
        for (int j=1;j<=52;j++){
            //从本周开始遍历
            Goods goods = map.get(i);
            if (goods != null) {
                System.out.println("WarehousingAnalysisNum"+j+" "+goods.getNum()/tem);
                model.addAttribute("WarehousingAnalysisNum" + j, goods.getNum()/tem);
            }else {
                System.out.println("WarehousingAnalysisNum"+j+" "+0);
                model.addAttribute("WarehousingAnalysisNum" + j, 0);
            }
            if (i==1){
                //如果来到第一周，说明要跳转到去年的最后一周了
             i=52;
            }else {
                i--;
            }
        }
        }
    }

