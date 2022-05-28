package com.usst.background.service.Impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.usst.background.entity.Admin;
import com.usst.background.mapper.AdminMapper;
import com.usst.background.service.AdminService;
import com.usst.background.service.LoginService;
import com.usst.background.util.JWTUtils;
import com.usst.background.vo.ErrorCode;
import com.usst.background.vo.LoginUserVo;
import com.usst.background.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Lazy
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public Admin findUser(String account, String password) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,account);
        queryWrapper.eq(Admin::getPassword,password);
        queryWrapper.last("limit 1");
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        Admin admin = this.checkToken(token);
        if (admin == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(admin.getId()));
        loginUserVo.setAccount(admin.getUsername());
        return Result.succ(loginUserVo);
    }

    @Override
    public Admin checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
//        从redis中获取token
        String userJson = redisTemplate.opsForValue().get("AdminTOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        Admin admin = JSON.parseObject(userJson, Admin.class);
        return admin;
    }
}
