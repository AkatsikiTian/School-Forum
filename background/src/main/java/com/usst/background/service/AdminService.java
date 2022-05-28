package com.usst.background.service;


import com.usst.background.entity.Admin;
import com.usst.background.vo.Result;

public interface AdminService {
    Admin findUser(String account, String password);

    Result findUserByToken(String token);

    Admin checkToken(String token);
}
