package com.dk.jdbc.controller.CommodityManagement;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.dk.jdbc.pojo.*;
import com.dk.jdbc.service.ReturnService;
import com.dk.jdbc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;


/**
 * 板凳宽宽
 */
@Controller
public class GoodsReturn {
    @Autowired
    StockService stockService;
    @Autowired
    ReturnService returnService;

    @RequestMapping("/ToGoodsReturn")
    public String toGoodsReturn(Model model){
        model.addAttribute("addReturn", " ");
        return "Commodity/ReturnGoods";
    }

    @PostMapping("/GoodsReturn")
    public String goodsReturn(Return r,Model model){
        boolean b = returnTool(r);
        if (b){
            model.addAttribute("addReturn", "退货成功");
        }else {
            model.addAttribute("addReturn", "库存不足，进查看库存或商品编号名称是否正确");
        }
        return "Commodity/ReturnGoods";
    }


    public boolean returnTool(Return r){
        Stock stock = new Stock(r.getGoodsId(), r.getGoodsName(), r.getCategory(), r.getNum(), r.getUnit(), null, null);
        Stock byIdAndName = stockService.getByIdAndName(stock);
        if (byIdAndName == null){
            //没有这个库存
            return false;
        }
        if (byIdAndName.getNum()<stock.getNum()){
            //库存量不足
            return false;
        }

        /**
         * 缺少事务管理
         */
        //减少库存
        byIdAndName.setNum(byIdAndName.getNum() - stock.getNum());
        boolean b = stockService.updateStock(stock);
        //添加退货记录
        boolean b1=returnService.addReturn(r);

        if (b && b1){
            return true;
        }
        return false;
    }
}
