package com.usst.background.service;

import com.usst.background.entity.Tag;

import java.util.Map;

public interface TagService {
    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Integer save(Tag tag);

    Integer update(Tag tag);

    Integer delete(Long id);

    Integer count(Long id);

    Map<String,Object> findbyinput(Integer currentPage, Integer pageSize, String select, String input);
}
