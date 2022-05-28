package com.sft3.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Category {
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
