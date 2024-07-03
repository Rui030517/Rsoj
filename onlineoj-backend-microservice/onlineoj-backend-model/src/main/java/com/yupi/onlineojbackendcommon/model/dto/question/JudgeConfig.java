package com.yupi.onlineojbackendcommon.model.dto.question;

import lombok.Data;
/**
 * 题目配置
 * */
@Data
public class JudgeConfig {

    //时间限制
    private Long timelimit;
    //空间限制
    private Long memorylimit;
}
