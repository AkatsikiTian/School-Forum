package com.usst.background.controller;
import com.usst.background.service.LoginService;
import com.usst.background.vo.Result;
import com.usst.background.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam, HttpServletResponse response){
        //登录 验证用户
        Result login = loginService.login(loginParam);
        System.out.println(login.getData());
        return login;
    }
}
