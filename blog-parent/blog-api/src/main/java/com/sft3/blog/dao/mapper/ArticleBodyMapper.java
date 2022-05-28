package com.sft3.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sft3.blog.dao.pojo.ArticleBody;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
    @Insert("Insert into wait_article_body (article_id,content,content_html) VALUES (#{articleId},#{content},#{contentHtml});")
    Integer insertbody(ArticleBody articleBody);
    @Select("Select * from wait_article_body where article_id=#{articleId};")
    ArticleBody findbodyid(ArticleBody articleBody);
}
