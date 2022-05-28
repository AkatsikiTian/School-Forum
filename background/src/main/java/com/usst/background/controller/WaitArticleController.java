package com.usst.background.controller;

import com.usst.background.service.WaitArticleService;
import com.usst.background.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waitarticle")
public class WaitArticleController {
    @Autowired
    WaitArticleService waitArticleService;

    // 获取分页
    @GetMapping("/page")
    public Result page(@RequestParam Integer currentPage, @RequestParam Integer pageSize){
        return Result.succ(waitArticleService.getPageVo(currentPage,pageSize));
    }

    // 模糊查询分页
    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(waitArticleService.findbyinput(currentPage,pageSize,select,input));
    }

    // 获取帖子内容
    @GetMapping("/findcontent/{id}")
    public Result findbyContent(@PathVariable String id){
        return Result.succ(waitArticleService.findbyContent(id));
    }

    // 删除帖子
    @DeleteMapping("/delArticle/{id}")
    public Result delArticle(@PathVariable String id){
        return Result.succ(waitArticleService.delArticle(id));
    }

    // 帖子置顶
    @PostMapping("/updateArticle/{id}")
    public Result updateArticle(@PathVariable String id){
        return Result.succ(waitArticleService.updateArticle(id));
    }

    // 帖子取消置顶
    @PostMapping("/updateArticle1/{id}")
    public Result updateArticle1(@PathVariable String id){
        return Result.succ(waitArticleService.updateArticle1(id));
    }

    // 将审核帖发布
    @PostMapping("/add/{articleId}")
    public Result addArticle(@PathVariable String articleId) {
        return Result.succ(waitArticleService.addArticle(articleId));
    }
}
