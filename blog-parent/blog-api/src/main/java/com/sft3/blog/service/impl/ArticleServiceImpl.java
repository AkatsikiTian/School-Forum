package com.sft3.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sft3.blog.dao.dos.Archives;
import com.sft3.blog.dao.mapper.ArticleBodyMapper;
import com.sft3.blog.dao.mapper.ArticleMapper;
import com.sft3.blog.dao.mapper.ArticleTagMapper;
import com.sft3.blog.dao.pojo.Article;
import com.sft3.blog.dao.pojo.ArticleBody;
import com.sft3.blog.dao.pojo.ArticleTag;
import com.sft3.blog.dao.pojo.SysUser;
import com.sft3.blog.service.*;
import com.sft3.blog.utils.UserThreadLocal;
import com.sft3.blog.vo.ArticleBodyVo;
import com.sft3.blog.vo.ArticleVo;
import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.TagVo;
import com.sft3.blog.vo.params.ArticleParam;
import com.sft3.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by create_date desc desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. ??????id?????? ????????????
         * 2. ??????bodyId???categoryid ??????????????????
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);
        //???????????????????????????
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    @Override
    public Result deleteAriticleById(Long articleId) {

        return Result.success(this.articleMapper.deleteById(articleId));
    }

    @Override
    public Result deleteArticleBody(Long articleId) {

        return Result.success(this.articleMapper.deleteBody(articleId));
    }

    @Override
    public Result deleteArticleTag(Long articleId) {

        return Result.success(this.articleMapper.deleteTag(articleId));
    }


    @Override
    public Result publish(ArticleParam articleParam) {
        //????????? ??????????????????????????????
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. ???????????? ?????? ??????Article??????
         * 2. ??????id  ?????????????????????
         * 3. ??????  ????????????????????? ??????????????????
         * 4. body ???????????? article bodyId
         */
        Article article;
        boolean isEdit = false;
        if(articleParam.getId() != null){
            article = new Article();
            article.setAuthorId(sysUser.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setWeight(Article.Article_Common);
            article.setCreateDate(System.currentTimeMillis());
            article.setCommentCounts(0);
            article.setViewCounts(0);
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            articleMapper.insertart(article);
            article = this.articleMapper.findid(article);
            isEdit = true;
        }else {
            article = new Article();
            article.setAuthorId(sysUser.getId());
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            //???????????? ?????????????????????id
            this.articleMapper.insertart(article);
            article = this.articleMapper.findid(article);
            //tag
        }
        List<TagVo> tags = articleParam.getTags();
        if (tags != null && !isEdit){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                this.articleTagMapper.inserttag(articleTag);
            }
        }

        if(tags != null && isEdit){
            Long articleId = article.getId();
            //this.articleMapper.deleteTag(articleId);
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.inserttag(articleTag);
            }
        }
        //body
        if(isEdit) {
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParam.getBody().getContent());
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            articleBodyMapper.insertbody(articleBody);
            articleBody = this.articleBodyMapper.findbodyid(articleBody);
            article.setBodyId(articleBody.getId());
            articleMapper.updateart(article);
            Map<String, String> map = new HashMap<>();
            map.put("id", article.getId().toString());
            articleMapper.deleteById(articleParam.getId());
            articleMapper.deleteBody(articleParam.getId());
            articleMapper.deleteTag(articleParam.getId());
            articleMapper.deleteComment(articleParam.getId());
            Set<String> keys = redisTemplate.keys("listArticle*");
            keys.forEach(s->{
                redisTemplate.delete(s);
            });
            return Result.success(map);
        }else{
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParam.getBody().getContent());
            articleBody.setContentHtml(articleParam.getBody().getContentHtml());
            articleBodyMapper.insertbody(articleBody);
            articleBody = this.articleBodyMapper.findbodyid(articleBody);
            article.setBodyId(articleBody.getId());
            articleMapper.updateart(article);
            Map<String, String> map = new HashMap<>();
            map.put("id", article.getId().toString());
            return Result.success(map);
        }
    }


    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;


    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setAuthor_id(String.valueOf(article.getAuthorId()));
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //???????????????????????? ??????????????? ???????????????
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
            articleVo.setAuthor_avatar(sysUserService.findUserById(authorId).getAvatar());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public Result deleteArticleComment(Long articleId) {

        return Result.success(this.articleMapper.deleteComment(articleId));
    }

    @Override
    public Result findmyArticle(Long userId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.eq(Article::getAuthorId,userId);
        queryWrapper.last("limit "+5);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result searchArticle(String search){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //LambdaQueryWrapper<SysUser> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        //queryWrapper1.select(SysUser::getId)
        queryWrapper.select(Article::getId,Article::getTitle,Article::getAuthorId);
        queryWrapper.like(Article::getTitle,search);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,true));
    }
}
