package com.usst.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo implements Serializable {
    private String id;
    private String tagName;
    private String avatar;
}
