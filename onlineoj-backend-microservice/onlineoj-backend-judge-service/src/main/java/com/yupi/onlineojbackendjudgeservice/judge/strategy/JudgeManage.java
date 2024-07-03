package com.yupi.onlineojbackendjudgeservice.judge.strategy;


import com.yupi.onlineojbackendcommon.model.codesandbox.JudgeInfo;
import com.yupi.onlineojbackendjudgeservice.judge.strategy.impl.DefaultJudgeStrategy;
import com.yupi.onlineojbackendjudgeservice.judge.strategy.impl.JavaJudgeStrategy;
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
