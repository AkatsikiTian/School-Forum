package com.usst.background.controller;

import cn.hutool.crypto.SecureUtil;
import com.usst.background.entity.SysUser;
import com.usst.background.service.SysUserService;
import com.usst.background.vo.Result;
import com.usst.background.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;
    @PostMapping
    public Result save(@RequestBody SysUserVo user){
        int result;
        SysUser user1 = new SysUser();
        user1.setId(Long.valueOf(user.getId()));
        user1.setDeleted(user.getDeleted());
        user1.setAdmin(user.getAdmin());
        System.out.println(user1);
        if (user1.getId()!=null){
            result =sysUserService.update(user1);
        }else{
           return Result.fail(404,"找不到改用户");
        }
        return Result.succ(result);
    }
    @PostMapping("/deletemore")
    public Result delete(@RequestBody List<Long> ids){
        return Result.succ(sysUserService.delete(ids));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        return Result.succ(sysUserService.delete(id));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Long id){
        System.out.println("user getbyid"+id);
        return Result.succ(sysUserService.getById(id));
    }

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage,Integer pageSize){
        return Result.succ(sysUserService.findbypage(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(sysUserService.findbyinput(currentPage,pageSize,select,input));
    }
}
