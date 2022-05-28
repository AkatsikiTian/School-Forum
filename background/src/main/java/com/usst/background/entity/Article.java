package com.usst.background.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Integer commentCounts;
    private Long createDate;
    private String summary;
    private String title;
    private Integer viewCounts;
    private Integer weight;
    private Long authorId;
    private Long bodyId;
    private Integer categoryId;
}
