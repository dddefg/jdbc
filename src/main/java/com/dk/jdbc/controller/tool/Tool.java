package com.dk.jdbc.controller.tool;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.dk.jdbc.controller.CommodityManagement.CommodityManagementController;
import com.dk.jdbc.controller.CommodityManagement.GoodsIssue;
import com.dk.jdbc.controller.CommodityManagement.GoodsLoss;
import com.dk.jdbc.controller.CommodityManagement.GoodsReturn;
import com.dk.jdbc.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 板凳宽宽
 * 公共方法抽取
 */
@Controller
public class Tool {


    //批量数据库操作
    @Autowired
    GoodsReturn goodsReturn;
    @Autowired
    GoodsLoss lossAutowired;
    @Autowired
    GoodsIssue issueAutowired;
    @Autowired
    CommodityManagementController commodityManagementController;

    //下载模板文件
    @RequestMapping("/Template")
    ResponseEntity<byte[]> toolTemplate(@RequestHeader Map<String, String> header) throws IOException {
        String name = "";
        //判断是哪里发来的请求，回复对应的模板文件
        int aReturn = header.get("referer").indexOf("Return");
        int Loss = header.get("referer").indexOf("Loss");
        int Issue = header.get("referer").indexOf("Issue");
        int Management = header.get("referer").indexOf("Management");
        //给要下载的文件名赋值
        if (aReturn >= 0) {
            name = "returnTemplate.xlsx";
        } else if (Loss >= 0) {
            name = "lossTemplate.xlsx";
        } else if (Issue >= 0) {
            name = "IssueTemplate.xlsx";
        } else if (Management >= 0) {
            name = "WarehousingTemplate.xlsx";
        }

        //获取ServletContext对象
        //ServletContext servletContext = session.getServletContext();
        //获取服务器中获取导入模板文件
        String realPath = "src/main/resources/Template/" + name;
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=" + name);
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }

    //批量导入数据
    @PostMapping("/Batch")
    public String batch(MultipartFile excel,
                        @RequestHeader Map<String, String> header,
                        RedirectAttributes redirectAttributes) throws Exception {
        //设置excel表信息
        ImportParams importParams = new ImportParams();
        // importParams.setTitleRows(1);  //标题信息
        importParams.setHeadRows(1);   //头部信息
        //看是访问路径地址
        String s = header.get("referer");

        //如果是Return 则调用Return的方法进行批量添加   批量退货
        int aReturn = s.indexOf("Return");
        if (aReturn >= 0) {
            List<Return> returns = ExcelImportUtil.importExcel(excel.getInputStream(), Return.class, importParams);
            //把传入的数据转为List集合  然后遍历加入到数据库

            for (Return r : returns) {
                boolean b = goodsReturn.returnTool(r);
                if (!b){ //如果出错停止  并返回出错位置
                    redirectAttributes.addAttribute("ReturnBatch", "批量添加有误-"+r.toString());
                }
            }
            redirectAttributes.addAttribute("ReturnBatch", "批量添加成功");
            return "redirect:/ToGoodsReturn";
        }

        //批量报备
        int loss = s.indexOf("Loss");
        if (loss >= 0) {
            List<Loss> losses = ExcelImportUtil.importExcel(excel.getInputStream(), Loss.class, importParams);
            //把传入的数据转为List集合  如何遍历加入到数据库
            for (Loss r : losses) {
                boolean b = lossAutowired.lossTool(r);
                if (!b){ //如果出错停止  并返回出错位置
                    redirectAttributes.addAttribute("LossBatch", "批量报备有误-"+r.toString());
                }
            }

            redirectAttributes.addAttribute("LossBatch", "批量报备成功");
            return "redirect:/ToGoodsLoss";
        }

        //出库
        int issueIndex = s.indexOf("Issue");
        if (issueIndex >= 0) {
            List<Issue> issues = ExcelImportUtil.importExcel(excel.getInputStream(), Issue.class, importParams);
            //把传入的数据转为List集合  如何遍历加入到数据库
            for (Issue issue : issues) {
                boolean b = issueAutowired.issueTool(issue);
                if (!b){ //如果出错停止  并返回出错位置
                    redirectAttributes.addAttribute("IssueBatch", "批量出库有误-"+issue.toString());
                }
            }
            redirectAttributes.addAttribute("IssueBatch", "批量添加成功");
            return "redirect:/ToGoodsIssue";
        }

        int managementIndex = s.indexOf("Management");
        if (managementIndex >= 0) {
            List<Goods> goodses = ExcelImportUtil.importExcel(excel.getInputStream(), Goods.class, importParams);
            //把传入的数据转为List集合  如何遍历加入到数据库
            for (Goods goods : goodses) {
                boolean b = commodityManagementController.toolGoods(goods);
                if (!b){
                    redirectAttributes.addAttribute("GoodsBatch", "批量报备有误-"+goods.toString());
                    return "redirect:/ToCommodityManagement";
                }
            }
            //向url地址中加入参数
            redirectAttributes.addAttribute("GoodsBatch", "批量入库成功");
            return "redirect:/ToCommodityManagement";
        }

        redirectAttributes.addAttribute("err", "重新登陆试试");
        return "redirect:/ToGoodsReturn";
    }


}
