package com.usst.background.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.background.entity.Article;
import com.usst.background.vo.ArticleVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select blog.article.id,blog.article.title,blog.article.summary," +
            "       blog.article.weight,blog.sys_user.account " +
            "from blog.article,blog.sys_user " +
            "where blog.article.author_id = blog.sys_user.id " +
            "order by article.weight desc " +
            "limit ${currentPage},${pageSize}")
    List<ArticleVo> getPageVo(Integer currentPage,Integer pageSize);

    @Select("select blog.article.id,blog.article.title,blog.article.summary," +
            "       blog.article.weight,blog.sys_user.account " +
            "from blog.article,blog.sys_user " +
            "where blog.article.author_id = blog.sys_user.id " +
            "and ${select} like concat('%',${input},'%') " +
            "order by article.weight desc " +
            "limit ${currentPage},${pageSize}")
    List<ArticleVo> findbyInput(Integer currentPage, Integer pageSize, String select, String input);

    @Select("select count(*) from blog.article,blog.sys_user " +
            "where blog.article.author_id = blog.sys_user.id ")
    Integer  selectTotal();

    @Select("select count(*) from blog.article,blog.sys_user " +
            "where blog.article.author_id = blog.sys_user.id " +
            "and ${select} like concat('%',${input},'%')")
    Integer  selectinputTotal(String select, String input);

    @Select("select blog.article_body.content_html " +
            "from blog.article_body " +
            "where article_id = #{findId}")
    String findbyContent(Long findId);

    @Delete("delete from blog.article " +
            "where blog.article.id =#{delId}")
    Integer delArticle(Long delId);

    @Delete("delete from blog.article_body " +
            "where blog.article_body.article_id =#{delId}")
    Integer delArticleBody(Long delId);

    @Delete("delete from blog.article_tag " +
            "where blog.article_tag.article_id =#{delId}")
    Integer delArticleTag(Long delId);

    @Update("update  blog.article " +
            "set weight = 1 " +
            "where id = #{updateId}")
    Integer updateArticle(Long updateId);

    @Update("update  blog.article " +
            "set weight = 0 " +
            "where id = #{updateId}")
    Integer updateArticle1(Long updateId);
}
