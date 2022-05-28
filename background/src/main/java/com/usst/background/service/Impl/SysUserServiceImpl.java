package com.usst.background.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.background.entity.SysUser;
import com.usst.background.mapper.SysUserMapper;
import com.usst.background.service.SysUserService;
import com.usst.background.vo.SysUserVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public int update(SysUser user) {
        return sysUserMapper.updateById(user);
    }


    @Override
    public int delete(List<Long> ids) {
        return sysUserMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return sysUserMapper.deleteById(id);
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<SysUser> userPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        userPage = sysUserMapper.selectPage(userPage, null);
        List<SysUser> list = userPage.getRecords();
        List<SysUserVo> sysUserVos = copyList(list);
        map.put("total",sysUserMapper.selectCount(null));
        map.put("data",sysUserVos);
        return map;
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<SysUser> userPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(select,input);
        userPage = sysUserMapper.selectPage(userPage, queryWrapper);
        List<SysUser> list = userPage.getRecords();
        List<SysUserVo> sysUserVos = copyList(list);
        map.put("total",sysUserMapper.selectCount(queryWrapper));
        map.put("data",sysUserVos);
        return map;
    }

    private SysUserVo copy(SysUser sysUser){
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setId(String.valueOf(sysUser.getId()));
        BeanUtils.copyProperties(sysUser,sysUserVo);
        sysUserVo.setCreateDate(new DateTime(sysUser.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        sysUserVo.setLastLogin(new DateTime(sysUser.getLastLogin()).toString("yyyy-MM-dd HH:mm"));
        return  sysUserVo;
    }

    private List<SysUserVo> copyList(List<SysUser> sysUsers){
        ArrayList<SysUserVo> sysUserVoArrayList = new ArrayList<>();
        for (SysUser sysUser : sysUsers) {
            sysUserVoArrayList.add(copy(sysUser));
        }
        return  sysUserVoArrayList;
    }
}
