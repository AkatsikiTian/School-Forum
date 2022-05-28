package com.usst.background.service.Impl;


import com.alibaba.fastjson.JSON;
import com.usst.background.entity.Admin;
import com.usst.background.service.AdminService;
import com.usst.background.service.LoginService;
import com.usst.background.util.JWTUtils;
import com.usst.background.vo.ErrorCode;
import com.usst.background.vo.Result;
import com.usst.background.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String slat = "hdglxt!@#";

    @Autowired
    private AdminService adminService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Result login(LoginParam loginParam) {
        String account = loginParam.getUsername();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        Admin admin = adminService.findUser(account,password);
        if (admin == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(admin.getId());
        redisTemplate.opsForValue().set("AdminTOKEN_"+token, JSON.toJSONString(admin),1, TimeUnit.DAYS);
        return Result.succ(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("AdminTOKEN_"+token);
        return Result.succ(null);
    }


}
