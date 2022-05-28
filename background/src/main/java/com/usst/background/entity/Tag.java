package com.usst.background.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Tag)实体类
 *
 * @author makejava
 * @since 2022-04-15 22:19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = 477864299768554739L;
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    
    private String avatar;

    private String tagName;
}