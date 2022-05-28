package com.usst.background.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "article_tag")
public class ArticleTag {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long articleId;
    private Long tagId;
}
