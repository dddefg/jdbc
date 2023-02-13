package com.dk.jdbc.controller.CommodityManagement;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.dk.jdbc.pojo.Loss;
import com.dk.jdbc.pojo.Return;
import com.dk.jdbc.pojo.Stock;
import com.dk.jdbc.service.LossService;
import com.dk.jdbc.service.StockService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
 */

@Controller
public class GoodsLoss {

    @Autowired
    StockService stockService;
    @Autowired
    LossService lossService;
    @RequestMapping("/ToGoodsLoss")
    public String toGoodsLoss(Model model){
        model.addAttribute("addLoss", " ");
        return "Commodity/GoodsLoss";
    }

    @PostMapping("/Loss")
    public String loss(Loss loss,Model model){
        boolean b = lossTool(loss);
        if (b){
            model.addAttribute("addLoss", "报备成功");
        }else {
            model.addAttribute("addLoss", "库存不足，请查看库存或商品编号名称是否正确");
        }
        return "Commodity/GoodsLoss";
    }

    public boolean lossTool(Loss loss){
        //修改库存
        Stock stock = new Stock(loss.getGoodsId(), loss.getGoodsName(), null, loss.getNum(), null, null, null);
        Stock byIdAndName = stockService.getByIdAndName(stock);
        if (byIdAndName == null){
            return false;
        }
        if (byIdAndName.getNum() < loss.getNum()){
            return false;
        }
        //减少库存
        byIdAndName.setNum(byIdAndName.getNum() - loss.getNum());
        boolean b1 = stockService.updateStock(byIdAndName);

        //增加报备记录
        boolean b = lossService.addLoss(loss);

        if (b && b1){  //两个都成功为成功
            return true;
        }
        return false;
    }





}

