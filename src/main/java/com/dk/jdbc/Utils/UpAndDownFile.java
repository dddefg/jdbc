package com.dk.jdbc.Utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 板凳宽宽
 * SpringMVC上传下载文件工具类
 */
public class UpAndDownFile {
    /**
     * @param session  获取ServletContext对象
     * @param realPathName  要下载文件的所在路径
     * @return  ResponseEntity<byte[]> 要下载文件的byte[]数组
     */
    public  ResponseEntity<byte[]> downUtils(HttpSession session,String realPathName) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
//        String realPath = servletContext.getRealPath(realPathName);
        String realPath="target/classes/temp/11.jpg";
        System.out.println("文件的真实路径"+realPath);
        //创建输入流
        InputStream is = new FileInputStream(realPathName);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //根据下载路径分割出文件名字
        String[] split = realPathName.split("/");
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename="+split[split.length-1]);
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }

    /**
     * MultipartFile需要在springmvc.xml中配置文件上传解析器
     *<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
     * 并且添加对应依赖
     *   <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
     *    <dependency>
     *   <groupId>commons-fileupload</groupId>
     *   <artifactId>commons-fileupload</artifactId>
     *   <version>1.3.1</version>
     *   </dependency>
     * @param fileName  表单提交的文件名
     */
    public String UpFile(MultipartFile fileName, HttpSession session) throws Exception{
        //获取上传文件的文件名
        String filename = fileName.getOriginalFilename();
        //防止文件重名覆盖，处理文件给它加一个UUID
        //获取后缀
        String substring = filename.substring(filename.lastIndexOf("."));
        //获取UUID
        String uuid = UUID.randomUUID().toString();
        //拼接新的文件名字
        filename = uuid +substring;
        //工具文件类型生成存放路径  后期使用根据不同文件类型调整
        String storePath ="defaultfile";
        System.out.println("文件类型"+substring);
        if (substring.equals(".jpg")||substring.equals(".png")||substring.equals(".gif")){
            storePath = "img";
        }else if (substring.equals(".mp3")||substring.equals(".wma")){
            storePath = "music";
        }
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取当前工程的真实路径
        String photoPath = servletContext.getRealPath(storePath);
        //创建photoPath对应的File对象
        File file = new File(photoPath);
        if (!file.exists()){
            //判断是不是存在，如果不存在创建
            file.mkdir();
        }
        //最终存放路径
        String finalPath =photoPath+File.separator + filename;
        //上传文件
        fileName.transferTo(new File(finalPath));
        return "hello";
    }
}
