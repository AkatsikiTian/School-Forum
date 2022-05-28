package com.sft3.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sft3.blog.dao.dos.Archives;
import com.sft3.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {


    List<Archives> listArchives();


    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);

    @Delete("Delete from article where id = #{id}")
    Integer deleteById(Long id);
    @Delete("Delete from article_body where article_id = #{id}")
    Integer deleteBody(Long id);
    @Delete("Delete from article_tag where article_id = #{id}")
    Integer deleteTag(Long id);
    @Delete("Delete from comment where article_id = #{id}")
    Integer deleteComment(Long articleId);
    @Insert("Insert into wait_article(author_id,weight,view_counts,title,summary,comment_counts,creat_date,category_id) VALUES (#{authorId}, #{weight}, #{viewCounts}, #{title}, #{summary}, #{commentCounts}, #{createDate}, #{categoryId});")
    Integer insertart(Article article);
    @Update("Update wait_article set author_id=#{authorId}, weight=#{weight}, view_counts=#{viewCounts}, title=#{title}, summary=#{summary}, comment_counts=#{commentCounts}, creat_date=#{createDate}, category_id=#{categoryId}, body_id=#{bodyId} where id=#{id}")
    Integer updateart(Article article);
    @Select("Select * from wait_article where author_id=#{authorId} and creat_date=#{createDate};")
    Article findid(Article article);
}
