package com.dk.jdbc.controller.otherRecords;

import com.dk.jdbc.pojo.Issue;
import com.dk.jdbc.pojo.Loss;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.pojo.Return;
import com.dk.jdbc.service.LossService;
import com.dk.jdbc.service.ReturnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 板凳宽宽
 */
@Controller
public class otherRecordsController {
    QueryGoods query=new QueryGoods();
    @Autowired
    ReturnService returnService;
    @Autowired
    LossService lossService;
    @RequestMapping("toReturnGoods")
    public String toReturnGoods(){
        return "otherRecords/returnGoods";
    }
    @RequestMapping("toLossGoods")
    public String toLossGoods(){
        return "otherRecords/lossGoods";
    }

    //查询所有退货信息
    @RequestMapping("/returnList")
    public String outboundList(@RequestParam(value = "pn",defaultValue = "1")Integer pn,//用于表示第几页
                               Model model
    ){

        /**
         * 设置分页查询
         */
        //设置当前页面  ，每页显示多少条
        PageHelper.startPage(pn, 15 );
        //按条件查询
        List<Return> list = returnService.listA();

        //把查询的数据加入，设置分页菜单栏
        PageInfo<Return> vegetablesAll = new PageInfo<>(list, 7);
        model.addAttribute("returnAll",vegetablesAll);

        //跳转到入库查询页面
        return "otherRecords/nest/returnList";
    }

    //查询所有出库信息
    @RequestMapping("/lossList")
    public String outboundLossList(@RequestParam(value = "pn",defaultValue = "1")Integer pn,//用于表示第几页
                               Model model
    ){

        /**
         * 设置分页查询
         */
        //设置当前页面  ，每页显示多少条
        PageHelper.startPage(pn, 15 );
        //按条件查询
        List<Loss> list = lossService.list();
        //把查询的数据加入，设置分页菜单栏
        PageInfo<Loss> vegetablesAll = new PageInfo<>(list, 7);
        model.addAttribute("lossAll",vegetablesAll);

        //跳转到入库查询页面
        return "otherRecords/nest/lossList";
    }
}
