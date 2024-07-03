package com.yupi.onlineoj.judge.codesandbox.model;

import lombok.Data;

/**
 * 题目提交信息
 * */
@Data
public class JudgeInfo {

    //程序执行信息
    private String message;
    //消耗空间 kb
    private Long memory;
    //消耗时间 ms
    private Long time;
}
