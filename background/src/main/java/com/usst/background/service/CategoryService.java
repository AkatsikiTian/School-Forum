package com.usst.background.service;


import com.usst.background.entity.Category;

import java.util.Map;

public interface CategoryService {
    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Integer save(Category category);

    Integer update(Category category);

    Integer delete(Long id);

    Integer count(Long id);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);
}
