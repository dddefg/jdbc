package com.dk.jdbc;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.dk.jdbc.controller.UserController;
import com.dk.jdbc.mapper.GoodsMapper;
import com.dk.jdbc.mapper.IssueMapper;
import com.dk.jdbc.mapper.StockMapper;
import com.dk.jdbc.mapper.UserMapper;
import com.dk.jdbc.pojo.*;
import com.dk.jdbc.service.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SpringBootTest
class JdbcApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsMapper goodsMapper;

    //获取当前时间
    Calendar calendar = Calendar.getInstance();
    Date time = calendar.getTime();
    java.sql.Date date = new java.sql.Date(time.getTime());

    @Test
    void contextLoads() {

       long l = date.getTime()- 30*1000 * 60 * 60 * 24;
        System.out.println(l);
        Date date1 = new Date(l);
        System.out.println(date1);

    }



    @Test
    void testExcel() throws IOException {
        //生成当前时间，把当前时间作为文件名
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String format = dateFormat.format(date);

        String src="E:\\"+"商品入库表"+format+".xls";
        //获取数据
        List<Goods> list = goodsService.list();

        //生成Excel数据
        Workbook workbook
                = ExcelExportUtil.exportExcel(new ExportParams("入库信息", "入库表"), Goods.class, list);
        //指定位置
        FileOutputStream fileOutputStream =new FileOutputStream(src);
        //数据导出
        workbook.write(fileOutputStream);
        //关流
        fileOutputStream.close();
        workbook.close();
    }

//    @Test
//    void testA(){
//        QueryGoods queryGoods = new QueryGoods("121", "", "", "");
//        List<Goods> listGoods = goodsMapper.getListGoods(queryGoods);
//        listGoods.forEach(System.out :: println);
//    }

    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws
            IOException {
//获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
//获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath("/static/images/11.jpg");
//创建输入流
        InputStream is = new FileInputStream(realPath);
//创建字节数组
        byte[] bytes = new byte[is.available()];
//将流读到字节数组中
        is.read(bytes);
//创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
//设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
//设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
//创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers,
                statusCode);
//关闭输入流
        is.close();
        return responseEntity;
    }

    @Test
    public void test(){
       returnService.list().forEach(System.out::println);

    }
    @Autowired
    StockMapper stockMapper;
    @Autowired
    StockService stockService;
@Autowired
    ReturnService returnService;
@Autowired
LossService lossService;
    @Autowired
    IssueService issueService;
    @Test
    public void test1(){
    }

    @Autowired
    IssueMapper issueMapper;

    @Autowired
    UserController userController;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    void test2(){
        List<Issue> issueRanking = issueService.getIssueRanking();
        System.out.println(issueRanking);

    }
    @Test
    void aa() {
        List<Goods> goodsCurve = goodsService.getGoodsCurve();
        //一天的时间戳
        long tep = 86400000;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int time = 10;  //计算前面10-1天的日期
        int Pointer = 0;    //List<goods> 指针
        for (int i = 0; i < 11; i++) {  //遍历获取11天的值

            //计算时间
            String format = simpleDateFormat.format(new Date((date.getTime() - tep * time)));
            System.out.println("计算时间"+format);


            if (Pointer == goodsCurve.size()){  //如果指针等于集合的长度，说明越界了赋值为0
                System.out.println("0");
            }else {   //否则就可以继续取值
                Goods goods = goodsCurve.get(Pointer);
                //如果计算的时间能对上数据库的时间  渲染给页面
                if (format.equals(simpleDateFormat.format(goods.getDate()))) {
                    System.out.println("1");
                    //如果渲染成功指针+1
                    Pointer++;
                } else {
                    //时间对不上赋值为0
                    System.out.println("0");
                }
            }
            //时间推移
            time--;
            //如果时间小于今天直接break
            if (time < 0) {
                System.out.println("break");
                break;
            }
        }
    }
    @Autowired
    GoodsBasicInformationService goodsBasicInformationService;

    @Test
    public void aaa(){
        Long l= Long.valueOf(1111);
        Loss loss = new Loss(l, "l", "aa", "aa", 11, "a", "aa", date);
//        boolean b = lossService.addLoss(loss);
//        System.out.println(b);
        boolean save = lossService.save(loss);



    }
}
