package com.sft3.blog.service;

import com.sft3.blog.vo.CategoryVo;
import com.sft3.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
