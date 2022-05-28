package com.usst.background.service.Impl;

import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
import com.usst.background.mapper.ArticleMapper;
import com.usst.background.service.ArticleService;
import com.usst.background.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public Map<String,Object> getPageVo(Integer currentPage,Integer pageSize) {
        currentPage = (currentPage-1)*pageSize;
        List<ArticleVo> data = articleMapper.getPageVo(currentPage,pageSize);
        addState(data);
        Integer total = articleMapper.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    @Override
    public Map<String,Object> findbyinput(Integer currentPage,Integer pageSize,String select,String input){
        currentPage = (currentPage-1)*pageSize;
        List<ArticleVo> data = articleMapper.findbyInput(currentPage,pageSize,select,"'"+input+"'");
        addState(data);
        Integer total = articleMapper.selectinputTotal(select,"'"+input+"'");
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    @Override
    public String findbyContent(String id) {
        Long findId = Long.parseLong(id);
        String contentHtml = articleMapper.findbyContent(findId);
        return contentHtml;
    }

    @Override
    public Integer delArticle(String id) {
        Long delId = Long.parseLong(id);
        int i = articleMapper.delArticle(delId);
        articleMapper.delArticleBody(delId);
        articleMapper.delArticleTag(delId);
        return i;
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
        Set<String> keys = redisTemplate.keys("listArticle*");
        System.out.println(keys);
        keys.forEach(s -> {
            redisTemplate.delete(s);
        });
        return articleMapper.updateArticle(updateId);
    }

    @Override
    public Integer updateArticle1(String id) {
        Long updateId = Long.parseLong(id);
        Set<String> keys = redisTemplate.keys("listArticle*");
        keys.forEach(s->{
            redisTemplate.delete(s);
        });
        return articleMapper.updateArticle1(updateId);
    }
}
