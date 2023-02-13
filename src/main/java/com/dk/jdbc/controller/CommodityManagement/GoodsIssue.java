package com.dk.jdbc.controller.CommodityManagement;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.Stock;
import com.dk.jdbc.service.IssueService;
import com.dk.jdbc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 板凳宽宽
 * 商品出库管理
 */

@Controller
public class GoodsIssue {
    @Autowired
    IssueService issueService;
    @Autowired
    StockService stockService;

    @RequestMapping("/ToGoodsIssue")
    public String ToGoodsIssue(Model model){
        model.addAttribute("IssueA", " ");
        return "Commodity/GoodsIssue";
    }

    @RequestMapping("/Issue")
    public String Issue(Issue issue, Model model){
        boolean b = issueTool(issue);
        if (b){
            model.addAttribute("addIssue", "出库成功");
        }else {
            model.addAttribute("addIssue", "库存不足，请查看库存或商品编号名称是否正确");
        }
        return "Commodity/GoodsIssue";
    }

    public boolean issueTool(Issue issue){

        issue.setCost(issue.getIssuePrice()*issue.getNum());
        //修改库存记录
        Stock stock = new Stock(issue.getGoodsId(), issue.getGoodsName(), issue.getCategory(), issue.getNum(), issue.getUnit(), null, issue.getIssueDate());
        Stock byIdAndName = stockService.getByIdAndName(stock);
        if ( byIdAndName == null){
            return false;
        }

        if (byIdAndName.getNum() < stock.getNum()){
            return false;
        }
        stock.setLastPurchaseDate(byIdAndName.getLastPurchaseDate());  //入库时间不变
        stock.setNum(byIdAndName.getNum() - stock.getNum());  //新的库存等于用来的减这次出库的
        if (byIdAndName.getLastIssueDate() != null){
            //如果上一次时间不为空  再判断是不是需要使用上次的时间
            if (stock.getLastIssueDate().compareTo(byIdAndName.getLastIssueDate()) < 0) {
                stock.setLastIssueDate(byIdAndName.getLastIssueDate());
            }
        }

        boolean b1 = stockService.updateStock(stock); //修改  后期做事务，防止没修改却出库等情况
        //增加出库记录
        boolean b = issueService.addIssue(issue);
        if (b && b1){
            return true;
        }
         return false;
    }





}
