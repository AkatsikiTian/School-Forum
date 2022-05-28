package com.usst.background.service;

import com.usst.background.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    int update(SysUser user);


    int delete(List<Long> ids);

    int delete(Long id);

    SysUser getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);
}
