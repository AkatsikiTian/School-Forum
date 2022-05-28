package com.usst.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private int code;//200表示正常，其他都是不正常
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return new Result(200,"操作成功",data);
    }

    public static Result fail(int code,String msg){
        return new Result(code,msg,null);
    }
}
