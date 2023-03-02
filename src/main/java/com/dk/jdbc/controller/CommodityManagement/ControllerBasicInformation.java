package com.dk.jdbc.controller.CommodityManagement;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.dk.jdbc.pojo.BasicInformation;
import com.dk.jdbc.pojo.Goods;
import com.dk.jdbc.service.GoodsBasicInformationService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 板凳宽宽
 */
@Controller
public class ControllerBasicInformation {
    @Autowired
    GoodsBasicInformationService goodsBasicInformationService;


    @GetMapping("/ToBasicInformation")
    public String ToBasicInformation(){
        return  "Commodity/BasicInformation";
    }

    //基础信息添加
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
        boolean b=false;
        try {
             b = goodsBasicInformationService.addBasicInformation(basicInformation);
        }catch (Exception e){
            model.addAttribute("addBasicInformationA","添加失败,请检查商品信息是否重复");
        }
        if (b){
            model.addAttribute("addBasicInformationA","添加成功");
        }
        return "Commodity/BasicInformation";
    }


    @RequestMapping("/InformationTable")
    public String informationTable(com.dk.jdbc.pojo.BasicInformation basicInformation,
                                   @RequestParam(value = "pn",defaultValue = "1")Integer pn,//用于表示第几页
                                   Model model
    ){



        /**
         * 设置分页查询
         */
        //设置当前页面  ，每页显示多少条
        PageHelper.startPage(pn, 8 );
        List<com.dk.jdbc.pojo.BasicInformation> list;
        //按条件查询
        if (basicInformation.getGoodsName() != null && basicInformation.getGoodsName() != "") {
            String s = basicInformation.getGoodsName();
            s = "%"+s+"%";
            list = goodsBasicInformationService.getListBasicInformations(s);
        }else {
             list = goodsBasicInformationService.list();
        }
        //把查询的数据加入，设置分页菜单栏
        PageInfo<com.dk.jdbc.pojo.BasicInformation> All = new PageInfo<>(list, 7);
        model.addAttribute("BasicInformationAll",All);

        //跳转到入库查询页面
        return "Commodity/GoodsInStock/InformationTable";
    }

    //删除基础信息
    @RequestMapping("/deleteGoodsBasicInformation")
    public String deleteBasicInformation(@RequestParam(value = "goodsName")String goodsName){
        BasicInformation basicInformation = new BasicInformation();
        basicInformation.setGoodsName(goodsName);
        try {
            boolean b = goodsBasicInformationService.deleteBasicInformation(basicInformation);
        }catch (Exception e){
            System.out.println(e);
        }

        return "redirect:/InformationTable";
    }

    //下载基础数据
    @RequestMapping("/downloadBasicInformation")
    ResponseEntity<byte[]> download() throws IOException {
        //生成当前时间，把当前时间作为文件名
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String format = dateFormat.format(date);

        String src="商品入库表"+format+".xls";
        //获取数据
        List<BasicInformation> list = goodsBasicInformationService.list();
        //生成Excel数据
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("商品基础信息", "基础信息表"), BasicInformation.class, list);
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
}
