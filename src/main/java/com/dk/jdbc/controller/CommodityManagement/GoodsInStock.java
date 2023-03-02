package com.dk.jdbc.controller.CommodityManagement;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.dk.jdbc.mapper.GoodsMapper;
import com.dk.jdbc.mapper.StockMapper;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.pojo.QueryGoods;
import com.dk.jdbc.pojo.Stock;
import com.dk.jdbc.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 板凳宽宽
 * 入库查询模块
 */
@Controller
public class GoodsInStock {
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    StockMapper stockMapper;
    QueryGoods query=new QueryGoods();

    @GetMapping("/ToGoodsInStock")
    public String toGoodsInStock(){
        //跳转到入库查询页面
        return "Commodity/GoodsInStock";
    }


    /**
     * 分页查询显示
     * @param queryGoods 查询条件，默认无
     * @param pn   页码
     * @param model
     * @return
     */
    @RequestMapping("/Vegetables")
    public String vegetables(QueryGoods queryGoods,
                             @RequestParam(value = "pn",defaultValue = "1")Integer pn,//用于表示第几页
                              Model model
                             ){
        /**
         * 按条件查询
         * 保存储存条件
         */
        if (queryGoods.getOrderId()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setOrderId(queryGoods.getOrderId());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getGoodsName()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setGoodsName(queryGoods.getGoodsName());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getCategory()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setCategory(queryGoods.getCategory());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getSupplierName()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setSupplierName(queryGoods.getSupplierName());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }
        if (queryGoods.getDate()!=null){   //如果不点分类提交，传入的参数就为null，就说明不用改变分类
            query.setDate(queryGoods.getDate());  //如果提交的为”“或其他的参数，就说明要改变查询查询条件
        }

        //把数据渲染回页面
        model.addAttribute("OrderIdText",query.getOrderId());
        model.addAttribute("GoodsNameText",query.getGoodsName());
        model.addAttribute("CategoryText",query.getCategory());
        model.addAttribute("SupplierNameText",query.getSupplierName());
        model.addAttribute("DateText",query.getDate());




        /**
         * 设置分页查询
         */
        //设置当前页面  ，每页显示多少条
        PageHelper.startPage(pn, 15 );
        //按条件查询
        List<Goods> vegetables = goodsService.getListGoods(query);
        //把查询的数据加入，设置分页菜单栏
        PageInfo<Goods> vegetablesAll = new PageInfo<>(vegetables, 7);
        model.addAttribute("Vegetables",vegetablesAll);

        //跳转到入库查询页面
        return "Commodity/GoodsInStock/Vegetables";
    }



    //下载数据
    @RequestMapping("/download")
    ResponseEntity<byte[]> download() throws IOException {
        //生成当前时间，把当前时间作为文件名
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String format = dateFormat.format(date);

        String src="商品入库表"+format+".xls";
        //获取数据
        List<Goods> list = goodsService.getListGoods(query);

        //生成Excel数据
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("入库信息", "入库表"), Goods.class, list);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        }finally {
            bos.close();
        }
        byte[] bytes1 = bos.toByteArray();
         //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename="+src);
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes1, headers,statusCode);
        return responseEntity;
    }


    //删除入库信息
    @RequestMapping("deleteGoods")
    public String deleteGoods(@RequestParam(value = "deleteGoods",defaultValue = "0")Integer id){
        //减少库存
        Goods byId = goodsService.getById(id);
        Stock stock = new Stock(byId.getGoodsId(), byId.getGoodsName(), byId.getCategory(), byId.getNum(), null, null, null);
        Stock byIdAndName = stockMapper.getByIdAndName(stock);
        byIdAndName.setNum(byIdAndName.getNum() - stock.getNum());
        stockMapper.updateStock(byIdAndName);
        //删除记录
        goodsMapper.deleteById(id);
        return "redirect:/Vegetables";
    }
}

