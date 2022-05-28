package com.sft3.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUser {


@TableId(value ="id", type = IdType.AUTO)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Boolean deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
