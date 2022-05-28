package com.sft3.blog.service;

import com.sft3.blog.dao.pojo.SysUser;
import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.UserInfoVo;
import com.sft3.blog.vo.UserVo;
import com.sft3.blog.vo.params.PasswordParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    Result saveUserAvatar(String token, MultipartFile file) throws IOException;

    Result saveUserInfo(String token, UserInfoVo userInfoVo);

    Result changePassword(String token, PasswordParam passwordParam);

    Result findUserInfoByToken(String token);
}
