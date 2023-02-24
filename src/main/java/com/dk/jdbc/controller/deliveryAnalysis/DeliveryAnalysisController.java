package com.dk.jdbc.controller.deliveryAnalysis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 板凳宽宽
 * 出库分析查询
 */
@Controller
public class DeliveryAnalysisController {
    @RequestMapping("/toDeliveryAnalysis")
    public String toDeliveryAnalysis(Model model){
        return "deliveryAnalysis/DeliveryAnalysisA";
    }
    @RequestMapping("/toOutboundGoods")
    public String toOutboundGoods(Model model){
        return "deliveryAnalysis/outboundGoods";
    }
}
