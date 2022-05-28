package com.usst.background.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.background.entity.SysUser;
import com.usst.background.entity.Tag;
import com.usst.background.mapper.TagMapper;
import com.usst.background.service.TagService;
import com.usst.background.vo.SysUserVo;
import com.usst.background.vo.TagVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class TagServiceImpl implements TagService {
@Autowired
    TagMapper tagMapper;
    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Tag> tagPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        tagPage = tagMapper.selectPage(tagPage, null);
        List<Tag> list = tagPage.getRecords();
        List<TagVo> tagVos = copyList(list);
        map.put("total",tagMapper.selectCount(null));
        map.put("data",tagVos);
        return map;
    }

    @Override
    public Integer save(Tag tag) {
        tagMapper.updateautoid();
        return tagMapper.insert(tag);
    }

    @Override
    public Integer update(Tag tag) {
        return tagMapper.updateById(tag);
    }

    @Override
    public Integer delete(Long id) {
        return tagMapper.deleteById(id);
    }

    @Override
    public Integer count(Long id) {
        return tagMapper.countid(id);
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<Tag> tagPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        if (select.equals("tagName")) select="tag_name";
        queryWrapper.eq(select,input);
        tagPage = tagMapper.selectPage(tagPage, queryWrapper);
        List<Tag> list = tagPage.getRecords();
        List<TagVo> tagVos = copyList(list);
        map.put("total",tagMapper.selectCount(queryWrapper));
        map.put("data",tagVos);
        return map;
    }

    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        tagVo.setId(String.valueOf(tag.getId()));
        BeanUtils.copyProperties(tag,tagVo);
        return  tagVo;
    }

    private List<TagVo> copyList(List<Tag> tags){
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            tagVos.add(copy(tag));
        }
        return  tagVos;
    }
}
