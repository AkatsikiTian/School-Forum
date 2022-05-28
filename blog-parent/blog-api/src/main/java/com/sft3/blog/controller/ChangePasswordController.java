package com.sft3.blog.controller;


import com.sft3.blog.service.impl.SysUserServiceImpl;
import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.params.PasswordParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("changePassword")
public class ChangePasswordController {
    @Autowired
    SysUserServiceImpl sysUserService;

    @PostMapping
    Result changePassword(@RequestHeader("Authorization") String token, @RequestBody PasswordParam passwordParam){
            return sysUserService.changePassword(token,passwordParam) ;
    }
}
