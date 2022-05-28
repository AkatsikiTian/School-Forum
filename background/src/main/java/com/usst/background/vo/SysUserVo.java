package com.usst.background.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SysUserVo {

    private String id;

    private String account;

    private Integer admin;

    private String avatar;

    private String createDate;

    private Boolean deleted;

    private String email;

    private String lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String status;
}
