package com.sft3.blog.service;

import com.sft3.blog.vo.Result;
import com.sft3.blog.vo.params.CommentDeleteParam;
import com.sft3.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);

    Result deletemycommentbyid(CommentDeleteParam commentDeleteParam);
}
