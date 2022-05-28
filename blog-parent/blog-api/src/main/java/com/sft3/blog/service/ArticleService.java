package com.sft3.blog.service;

import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.params.ArticleParam;
import com.sft3.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);

    Result deleteAriticleById(Long articleId);

    Result deleteArticleBody(Long articleId);

    Result deleteArticleTag(Long articleId);

    Result deleteArticleComment(Long articleId);

    Result findmyArticle(Long userId);

    Result searchArticle(String search);
}
