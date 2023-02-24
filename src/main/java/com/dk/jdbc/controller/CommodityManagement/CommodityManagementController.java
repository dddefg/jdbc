package com.dk.jdbc.controller.CommodityManagement;

import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Stock;
import com.dk.jdbc.service.GoodsService;
import com.dk.jdbc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * 板凳宽宽
 * 商品入库模块
 */
@Controller
public class CommodityManagementController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    StockService stockService;

    @GetMapping("/ToCommodityManagement")
    public String ToCommodityManagement(Model model){
        model.addAttribute("addGoods", " ");
        return "Commodity/CommodityManagement";
    }



    @PostMapping ("/Purchase")
    public String Purchase(Goods goods, Model model){
        //校验
        if (goods.getOrderId()=="" || goods.getOrderId()== " "){
            model.addAttribute("addGoods", "添加失败，订单编号不能为空");
            return "Commodity/CommodityManagement";
        }
        if (goods.getGoodsId()=="" || goods.getGoodsId() == " "){
            model.addAttribute("addGoods", "添加失败，商品编号不能为空");
            return "Commodity/CommodityManagement";
        }
        if (goods.getGoodsName()=="" || goods.getGoodsName() == " "){
            model.addAttribute("addGoods", "添加失败，商品名称不能为空");
            return "Commodity/CommodityManagement";
        }
        if (goods.getPrice() < 0){
            model.addAttribute("addGoods", "添加失败，价格不能为负");
            return "Commodity/CommodityManagement";
        }
        if (goods.getNum() < 0){
            model.addAttribute("addGoods", "添加失败，数量不能为负");
            return "Commodity/CommodityManagement";
        }

        boolean b = toolGoods(goods);
        if (b) {
            model.addAttribute("addGoods", "添加成功");
        }else {
            model.addAttribute("addGoods", "失败");
        }
        return "Commodity/CommodityManagement";
    }

    public boolean toolGoods(Goods goods){
        //计算总价并加入
        goods.setCost(goods.getPrice()*goods.getNum());
        //增加到入库记录
        boolean b1 = goodsService.addPurchase(goods);
        boolean b;

        //赋值给库存类
        Stock stock = new Stock(goods.getGoodsId(), goods.getGoodsName(), goods.getCategory(), goods.getNum(), goods.getUnit(), goods.getDate(), null);

        //查看是否有这个库存
        Stock stock1 = stockService.getByIdAndName(stock);

        if (stock1 == null){  //没有这个商品说明第一次增加 直接加入
            b = stockService.addStock(stock);
        }else {  //系统中有库存记录，在原来记录上修改  用新地修改，就可以顺便更新其他数值，
            //库存相加
            if (stock1.getLastPurchaseDate().compareTo(stock.getLastPurchaseDate())>0){
                //判断新加的库存时间是不是最新的，如果不是最新的可能为补录，时间不用更新
                stock.setLastPurchaseDate(stock1.getLastPurchaseDate());
            }
            stock.setNum(stock.getNum()+stock1.getNum());
            stock.setLastIssueDate(stock1.getLastIssueDate());
            b = stockService.updateStock(stock);
        }
        if (b && b1){  //都添加成功再返回对
            return true;
        }
        return false;
    }
}
