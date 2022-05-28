package com.sft3.blog.controller;

import com.sft3.blog.common.aop.LogAnnotation;
import com.sft3.blog.common.cache.Cache;
import com.sft3.blog.service.ArticleService;
import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.params.ArticleParam;
import com.sft3.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    //加上此注解 代表要对此接口记录日志
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页 最热文章
     *
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     *
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000, name = "news_article")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     *
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 首页 查文章详情
     *
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }
    //接口url：/articles/publish
    //
    //请求方式：POST

    /**
     * 发表
     *
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    /**
     * 编辑
     *
     * @return
     */
    @PostMapping("{id}")
    public Result editbyid(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 删除文章
     *
     * @return
     */
    @PostMapping("delete/{id}")
    public Result deletebyid(@PathVariable("id") Long articleId) {
        return articleService.deleteAriticleById(articleId);
    }

    @PostMapping("delete/body/{id}")
    public Result deleteBody(@PathVariable("id") Long articleId) {
        return articleService.deleteArticleBody(articleId);
    }

    @PostMapping("delete/Tag/{id}")
    public Result deleteTag(@PathVariable("id") Long articleId) {
        return articleService.deleteArticleTag(articleId);
    }

    @PostMapping("delete/Comment/{id}")
    public Result deleteComment(@PathVariable("id") Long articleId) {
        return articleService.deleteArticleComment(articleId);
    }

    /**
     * 查看我自己新发布的文章
     *
     * @return
     */
    @PostMapping("viewme/{id}")
    public Result findmyarticleById(@PathVariable("id") Long userId) {
        return articleService.findmyArticle(userId);
    }

    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam) {
        String search = articleParam.getSearch();
        return articleService.searchArticle(search);
    }

}
