package com.sft3.blog.controller;

import com.sft3.blog.service.CommentsService;
import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.params.CommentDeleteParam;
import com.sft3.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }


    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }

    @PostMapping("delete")
    public Result deletemycomment(@RequestBody CommentDeleteParam commentDeleteParam){ return commentsService.deletemycommentbyid(commentDeleteParam);}

}
