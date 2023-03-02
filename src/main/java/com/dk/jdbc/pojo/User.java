package com.dk.jdbc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 板凳宽宽
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@ExcelTarget("users")
public class User{
    @Excel(name = "编号")
    private Long id;
    @Excel(name = "账号",width = 30)
    private String username;
    @Excel(name = "密码",width = 25)
    private String password;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "性别")
    private String gender;
    @Excel(name = "邮箱",width = 30)
    private String email;


}
