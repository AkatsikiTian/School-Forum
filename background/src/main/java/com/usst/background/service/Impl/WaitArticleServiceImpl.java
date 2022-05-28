package com.usst.background.service.Impl;


import com.usst.background.entity.Article;
import com.usst.background.entity.ArticleBody;
import com.usst.background.entity.ArticleTag;
import com.usst.background.mapper.WaitArticleMapper;
import com.usst.background.service.WaitArticleService;
import com.usst.background.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class WaitArticleServiceImpl implements WaitArticleService {
    @Autowired
    WaitArticleMapper waitArticleMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public Map<String,Object> getPageVo(Integer currentPage, Integer pageSize) {
        currentPage = (currentPage-1)*pageSize;
        List<ArticleVo> data = waitArticleMapper.getPageVo(currentPage,pageSize);
        addState(data);
        Integer total = waitArticleMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    @Override
    public Map<String,Object> findbyinput(Integer currentPage,Integer pageSize,String select,String input){
        currentPage = (currentPage-1)*pageSize;
        List<ArticleVo> data = waitArticleMapper.findbyInput(currentPage,pageSize,select,"'"+input+"'");
        addState(data);
        Integer total = waitArticleMapper.selectinputTotal(select,"'"+input+"'");
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    @Override
    public String findbyContent(String id) {
        Long findId = Long.parseLong(id);
        String contentHtml = waitArticleMapper.findbyContent(findId);
        return contentHtml;
    }

    @Override
    public Integer delArticle(String id) {
        Long delId = Long.parseLong(id);
        waitArticleMapper.delArticle(delId);
        waitArticleMapper.delArticleBody(delId);
        waitArticleMapper.delArticleTag(delId);
        return waitArticleMapper.delArticle(delId);
    }

    public void addState(List<ArticleVo> data){
        for (ArticleVo article : data) {
            if (article.getWeight()==1) {
                article.setState(true);
            }else if (article.getWeight()==0) {
                article.setState(false);
            }
        }
    }

    @Override
    public Integer updateArticle(String id) {
        Long updateId = Long.parseLong(id);
        return waitArticleMapper.updateArticle(updateId);
    }

    @Override
    public Integer updateArticle1(String id) {
        Long updateId = Long.parseLong(id);
        return waitArticleMapper.updateArticle1(updateId);
    }

    @Override
    public Integer addArticle(String id) {
        Long addId = Long.parseLong(id);
        Article article = waitArticleMapper.getArticle(addId);
        ArticleBody articleBody = waitArticleMapper.getArticleBody(addId);
        List<ArticleTag> articleTag = waitArticleMapper.getArticleTag(addId);
        article.setCreateDate(System.currentTimeMillis());
        int i = waitArticleMapper.saveArticle(article);
        waitArticleMapper.saveArticleBody(articleBody);
        for (ArticleTag articleTag1 : articleTag) {
            waitArticleMapper.saveArticleTag(articleTag1);
        }
        delArticle(id);
        Set<String> keys = redisTemplate.keys("listArticle*");
        keys.forEach(s->{
            redisTemplate.delete(s);
        });
        return i;
    }
}
