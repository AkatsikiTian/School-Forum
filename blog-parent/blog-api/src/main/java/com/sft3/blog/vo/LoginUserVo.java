package com.sft3.blog.vo;

import lombok.Data;

@Data
public class LoginUserVo {

    private String id;

    private String account;

    private String nickname;

    private String avatar;

    private Integer admin;
}
