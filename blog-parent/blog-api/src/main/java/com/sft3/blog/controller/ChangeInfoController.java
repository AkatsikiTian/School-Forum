package com.sft3.blog.controller;


import com.sft3.blog.service.SysUserService;
import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;

@RestController
@RequestMapping(value = "changeUserInfo")
public class ChangeInfoController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping
    private Result saveUserInfo(@RequestHeader("Authorization") String token, @RequestBody UserInfoVo userInfoVo){
      return  sysUserService.saveUserInfo(token,userInfoVo);
    }
    @PostMapping("savePicture")
    private Result savePicture(@RequestHeader("Authorization") String token, @RequestParam MultipartFile file) throws IOException {
        return sysUserService.saveUserAvatar(token,file);

    }



}
