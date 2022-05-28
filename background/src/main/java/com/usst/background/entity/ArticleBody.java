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
@TableName(value = "article_body")
public class ArticleBody {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
