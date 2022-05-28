package com.usst.background.service;

import java.util.Map;

public interface WaitArticleService {
    Map<String,Object> getPageVo(Integer currentPage, Integer pageSize);

    Map<String,Object> findbyinput(Integer currentPage,Integer pageSize,String select,String input);

    String findbyContent(String id);

    Integer delArticle(String id);

    Integer updateArticle(String id);

    Integer updateArticle1(String id);

    Integer addArticle(String id);
}
