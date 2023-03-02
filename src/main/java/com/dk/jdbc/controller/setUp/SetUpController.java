package com.dk.jdbc.controller.setUp;

import com.dk.jdbc.pojo.User;
import com.dk.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 板凳宽宽
 */
@Controller
public class SetUpController {
    @Autowired
    UserService userService;
    @RequestMapping("/toRevisePass")
    public String toRevisePass(){
        return "setUp/revisePass";
    }
    @PostMapping("/revisePass")
    public String revisePass(User user, HttpSession session){
        //重新登录
        User loginUser = (User) session.getAttribute("loginUser");
        loginUser.setPassword(user.getPassword());
        userService.updateUser(loginUser);
        return "redirect:/loginOut";
    }
}
