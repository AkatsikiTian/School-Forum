package com.usst.background.controller;

import com.usst.background.service.ArticleService;
import com.usst.background.vo.ArticleVo;
import com.usst.background.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/page")
    public Result page(@RequestParam Integer currentPage, @RequestParam Integer pageSize){
        return Result.succ(articleService.getPageVo(currentPage,pageSize));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(articleService.findbyinput(currentPage,pageSize,select,input));
    }

    @GetMapping("/findcontent/{id}")
    public Result findbyContent(@PathVariable String id){
        return Result.succ(articleService.findbyContent(id));
    }

    @DeleteMapping("/delArticle/{id}")
    public Result delArticle(@PathVariable String id){return Result.succ(articleService.delArticle(id));}

    @PostMapping("/updateArticle/{id}")
    public Result updateArticle(@PathVariable String id){return Result.succ(articleService.updateArticle(id));}

    @PostMapping("/updateArticle1/{id}")
    public Result updateArticle1(@PathVariable String id){return Result.succ(articleService.updateArticle1(id));}
}

