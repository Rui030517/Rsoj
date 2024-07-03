package com.yupi.onlineojbackendjudgeservice.judge.strategy;


import com.yupi.onlineojbackendcommon.model.codesandbox.JudgeInfo;

public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
