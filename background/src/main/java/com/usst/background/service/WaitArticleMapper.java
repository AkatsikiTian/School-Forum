package com.usst.background.service;

import com.usst.background.entity.Article;
import com.usst.background.entity.ArticleBody;
import com.usst.background.entity.ArticleTag;
import com.usst.background.vo.ArticleVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface WaitArticleMapper {

    @Select("select blog.wait_article.id,blog.wait_article.title," +
            "       blog.wait_article.summary,blog.wait_article.weight,blog.sys_user.account " +
            "from blog.wait_article,blog.sys_user " +
            "where blog.wait_article.author_id = blog.sys_user.id " +
            "limit ${currentPage},${pageSize}")
    List<ArticleVo> getPageVo(Integer currentPage, Integer pageSize);


    @Select("select blog.wait_article.id,blog.wait_article.title," +
            "       blog.wait_article.summary,blog.wait_article.weight,blog.sys_user.account " +
            "from blog.wait_article,blog.sys_user " +
            "where blog.wait_article.author_id = blog.sys_user.id " +
            "and ${select} like concat('%',${input},'%') " +
            "limit ${currentPage},${pageSize}")
    List<ArticleVo> findbyInput(Integer currentPage, Integer pageSize, String select, String input);


    @Select("select count(*) from blog.wait_article,blog.sys_user " +
            "where blog.wait_article.author_id = blog.sys_user.id ")
    Integer  selectTotal();


    @Select("select count(*) from blog.wait_article,blog.sys_user " +
            "where blog.wait_article.author_id = blog.sys_user.id " +
            "and ${select} like concat('%',${input},'%')")
    Integer  selectinputTotal(String select, String input);


    @Select("select blog.wait_article_body.content_html " +
            "from blog.wait_article_body " +
            "where article_id = #{findId}")
    String findbyContent(Long findId);


    @Delete("delete from blog.wait_article " +
            "where blog.wait_article.id =#{delId}")
    Integer delArticle(Long delId);


    @Delete("delete from blog.wait_article_body " +
            "where blog.wait_article_body.article_id = #{delId}")
    Integer delArticleBody(Long delId);


    @Delete("delete from blog.wait_article_tag " +
            "where blog.wait_article_tag.article_id = #{delId}")
    Integer delArticleTag(Long delId);


    @Update("update  blog.wait_article " +
            "set weight = 1 " +
            "where id = #{updateId}")
    Integer updateArticle(Long updateId);


    @Update("update  blog.wait_article " +
            "set weight = 0 " +
            "where id = #{updateId}")
    Integer updateArticle1(Long updateId);


    @Select("select * from blog.wait_article " +
            "where blog.wait_article.id = #{addId}")
    Article getArticle(Long addId);


    @Select("select * from blog.wait_article_body " +
            "where blog.wait_article_body.article_id = #{addId}")
    ArticleBody getArticleBody(Long addId);


    @Select("select * from blog.wait_article_tag " +
            "where blog.wait_article_tag.article_id = #{addId}")
    List<ArticleTag> getArticleTag(Long addId);

    @Insert("insert into blog.article " +
            " (id, comment_counts, create_date, summary, title," +
            " view_counts, weight, author_id, body_id, category_id) " +
            "values( #{id},#{commentCounts},#{createDate},#{summary},#{title}," +
            "#{viewCounts},#{weight},#{authorId},#{bodyId},#{categoryId} )")
    int  saveArticle (Article article);


    @Insert("insert into blog.article_body(id,content, content_html, article_id) " +
            "values(#{id},#{content},#{contentHtml},#{articleId})")
    int  saveArticleBody (ArticleBody articleBody);


    @Insert("insert into blog.article_tag(id,article_id, tag_id) " +
            "values(#{id},#{articleId},#{tagId})")
    int  saveArticleTag (ArticleTag articleTag);
}
