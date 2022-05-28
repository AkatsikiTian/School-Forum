package com.sft3.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sft3.blog.dao.pojo.ArticleTag;
import org.apache.ibatis.annotations.Insert;

public interface ArticleTagMapper  extends BaseMapper<ArticleTag> {
    @Insert("Insert into wait_article_tag(tag_id,article_id) VALUES(#{tagId},#{articleId});")
    Integer inserttag(ArticleTag articleTag);
}
