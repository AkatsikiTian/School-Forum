package com.usst.background.service;


import com.usst.background.entity.Admin;
import com.usst.background.vo.Result;
import com.usst.background.vo.params.LoginParam;

public interface LoginService {
    Result login(LoginParam loginParam);

    Result logout(String token);

}
