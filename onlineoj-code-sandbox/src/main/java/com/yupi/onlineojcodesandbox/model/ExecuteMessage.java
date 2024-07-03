package com.yupi.onlineojcodesandbox.model;

import lombok.Data;

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {
    private Integer exitValue; //相应码
    private String message; //返回的信息
    private String errorMessage;  //返回错误信息
    private Long time;   //程序执行花费时间
    private Long memory;  //程序执行花费空间
}
