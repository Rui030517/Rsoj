package com.yupi.onlineoj.judge.strategy;

import com.yupi.onlineoj.judge.strategy.impl.DefaultJudgeStrategy;
import com.yupi.onlineoj.judge.strategy.impl.JavaJudgeStrategy;
import com.yupi.onlineoj.judge.codesandbox.model.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 *  管理题目调用
 */
@Service
public class JudgeManage {

    public JudgeInfo doJudge(JudgeContext judgeContext){
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(judgeContext.getLanguage())){
            judgeStrategy = new JavaJudgeStrategy();
        }

        return judgeStrategy.doJudge(judgeContext);
    }

}
