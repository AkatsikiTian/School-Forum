package com.sft3.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sft3.blog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Delete;

public interface CommentMapper extends BaseMapper<Comment> {
    @Delete("Delete from comment where id = #{id} or parent_id = #{id}")
    Integer deletecommentById(Long Id);
}
