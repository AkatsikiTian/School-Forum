package com.sft3.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sft3.blog.dao.mapper.SysUserMapper;
import com.sft3.blog.dao.pojo.SysUser;
import com.sft3.blog.service.LoginService;
import com.sft3.blog.service.SysUserService;
import com.sft3.blog.vo.*;
import com.sft3.blog.vo.params.PasswordParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private LoginService loginService;

    private static final String slat = "mszlu!@#";


    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/user/user_1.png");
            sysUser.setNickname("测试号");
        }
        UserVo userVo  = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("该作者可能已被删除");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1. token合法性校验
         *    是否为空，解析是否成功 redis是否存在
         * 2. 如果校验失败 返回错误
         * 3. 如果成功，返回对应的结果 LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAdmin(sysUser.getAdmin());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        //保存用户这 id会自动生成
        //这个地方 默认生成的id是 分布式id 雪花算法
        //mybatis-plus
        this.sysUserMapper.insert(sysUser);
    }

    @Override
    public Result saveUserInfo(String token, UserInfoVo userInfoVo) {
        SysUser sysUser = loginService.checkToken(token);
        System.out.println(userInfoVo.getNickname());
        Long id = sysUser.getId();
        sysUser.setEmail(userInfoVo.getEmail());
        sysUser.setMobilePhoneNumber(userInfoVo.getMobilePhoneNumber());
        sysUser.setAvatar(userInfoVo.getAvatar());
        sysUser.setNickname(userInfoVo.getNickname());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper();
        wrapper.eq("id",id+"");
        this.sysUserMapper.update(sysUser,wrapper);
        return Result.success("成功");
    }

    @Override
    public Result saveUserAvatar(String token, MultipartFile file) throws IOException {
        SysUser sysUser = loginService.checkToken(token);
        String originalFilenamePath = file.getOriginalFilename();
        String sysPath = ResourceUtils.getURL("classpath:").getPath();
        int index = sysPath.indexOf("blog-api");
        sysPath = sysPath.substring(0,index)+"blog-app/" +"static/img";
        sysPath = sysPath.replace('/', '\\').substring(1,sysPath.length());
        String realPath = "/static/img/"+originalFilenamePath;
        Long id = sysUser.getId();
        sysUser.setAvatar(realPath);
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        File dest = new File(sysPath,originalFilenamePath);
        file.transferTo(dest);
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper();
        wrapper.eq("id",id+"");
        this.sysUserMapper.update(sysUser,wrapper);
        return Result.success(realPath);
    }

    @Override
    public Result changePassword(String token, PasswordParam passwordParam){
        SysUser sysUser = loginService.checkToken(token);
        redisTemplate.delete("TOKEN_"+token);
        String oldPassword = passwordParam.getOldPassword();
        oldPassword = DigestUtils.md5Hex(oldPassword+slat);
        String confirmPassword = sysUser.getPassword();
        if(!oldPassword.equals(confirmPassword)) {
            return Result.fail(ErrorCode.PASSWORD_ERROR.getCode(), ErrorCode.PASSWORD_ERROR.getMsg());
        }
        String newPassword = DigestUtils.md5Hex(passwordParam.getNewPassword() + slat);
        sysUser.setPassword(newPassword);
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper();
        wrapper.eq("id",sysUser.getId()+"");
        this.sysUserMapper.update(sysUser,wrapper);
        return Result.success(token);
    }

    @Override
    public Result findUserInfoByToken(String token) {
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        };
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(sysUser,userInfoVo);
        return Result.success(userInfoVo);
    }
}
