package com.dk.jdbc.controller.CommodityManagement;

import com.dk.jdbc.service.GoodsBasicInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 板凳宽宽
 */
@Controller
public class BasicInformation {
    @Autowired
    GoodsBasicInformationService goodsBasicInformationService;


    @GetMapping("/ToBasicInformation")
    public String ToBasicInformation(){
        return  "Commodity/BasicInformation";
    }

    @PostMapping("/addBasicInformation")
    public String addBasicInformation(Model model, com.dk.jdbc.pojo.BasicInformation basicInformation){
        if (basicInformation.getGoodsId() == null || basicInformation.getGoodsId() == " "){
            model.addAttribute("addBasicInformationA","添加失败，商品编号不能为空");
            return  "Commodity/BasicInformation";
        }
        if (basicInformation.getGoodsName() == null){
            model.addAttribute("addBasicInformationA","添加失败，商品名称不能为空");
            return  "Commodity/BasicInformation";
        }
        if (basicInformation.getCategory() == null){
            model.addAttribute("addBasicInformationA","添加失败，商品类别不能为空");
            return  "Commodity/BasicInformation";
        }

         goodsBasicInformationService.save(basicInformation);

        return "";
    }
}
