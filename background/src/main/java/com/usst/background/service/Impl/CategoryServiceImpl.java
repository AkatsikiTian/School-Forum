package com.usst.background.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.background.entity.Category;
import com.usst.background.entity.Tag;
import com.usst.background.mapper.CategoryMapper;
import com.usst.background.service.CategoryService;
import com.usst.background.vo.CategoryVo;
import com.usst.background.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public Map<String, Object> findbypage(Integer currentPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Category> categoryPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        categoryPage = categoryMapper.selectPage(categoryPage, null);
        List<Category> list = categoryPage.getRecords();
        List<CategoryVo> categoryVos = copyList(list);
        map.put("total",categoryMapper.selectCount(null));
        map.put("data",categoryVos);
        return map;
    }

    @Override
    public Integer save(Category category) {
        categoryMapper.updateautoid();
        return categoryMapper.insert(category);
    }

    @Override
    public Integer update(Category category) {
        return categoryMapper.updateById(category);
    }

    @Override
    public Integer delete(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public Integer count(Long id) {
        return categoryMapper.countid(id);
    }

    @Override
    public Map<String, Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input) {
        Map<String, Object> map = new HashMap<>();
        IPage<Category> categoryPage = new Page<>(currentPage, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (select.equals("categoryName")) select="category_name";
        queryWrapper.eq(select,input);
        categoryPage = categoryMapper.selectPage(categoryPage, queryWrapper);
        List<Category> list = categoryPage.getRecords();
        List<CategoryVo> categoryVos = copyList(list);
        map.put("total",categoryMapper.selectCount(queryWrapper));
        map.put("data",categoryVos);
        return map;
    }

    private CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setId(String.valueOf(category.getId()));
        BeanUtils.copyProperties(category,categoryVo);
        return  categoryVo;
    }

    private List<CategoryVo> copyList(List<Category> categories){
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            categoryVos.add(copy(category));
        }
        return  categoryVos;
    }
}
