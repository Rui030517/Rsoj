package com.yupi.onlineoj.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.yupi.onlineoj.judge.strategy.JudgeContext;
import com.yupi.onlineoj.judge.strategy.JudgeStrategy;
import com.yupi.onlineoj.model.dto.question.JudgeCase;
import com.yupi.onlineoj.model.dto.question.JudgeConfig;
import com.yupi.onlineoj.judge.codesandbox.model.JudgeInfo;
import com.yupi.onlineoj.model.entity.Question;
import com.yupi.onlineoj.model.enums.QuestionSubmitJudgeStatusEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class JavaJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        System.out.println("执行java判题策略");
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> outputList = judgeContext.getOutput();
        List<String> inputList = judgeContext.getInput();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        Long memory = judgeInfo.getMemory();
        if(memory == null) memory = 0L;
        Long time = judgeInfo.getTime();
        if(time == null) time = 0L;
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMessage(judgeInfo.getMessage());


        if(inputList.size() != outputList.size()){
            judgeInfoResponse.setMessage(QuestionSubmitJudgeStatusEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }
        for(int i=0;i<outputList.size();i++)
        {
            JudgeCase judgeCase1 = judgeCaseList.get(i);
            if(! judgeCase1.getOutput().equals(outputList.get(i))){
                judgeInfoResponse.setMessage(QuestionSubmitJudgeStatusEnum.WRONG_ANSWER.getValue());
                return judgeInfoResponse;
            }
        }

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimelimit = judgeConfig.getTimelimit();
        Long needMemorylimit = judgeConfig.getMemorylimit();
        if(needTimelimit == null) needTimelimit = 1000L;
        if(needMemorylimit == null) needMemorylimit = 64L;

        // todo 这里直接假设java程序多消耗10s时间，后期更改
//        time = time - 10000;
        if(needTimelimit < time){
            judgeInfoResponse.setMessage(QuestionSubmitJudgeStatusEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        if(needMemorylimit < memory){
            judgeInfoResponse.setMessage(QuestionSubmitJudgeStatusEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }


        judgeInfoResponse.setMessage(QuestionSubmitJudgeStatusEnum.ACCEPTED.getValue());
        return judgeInfoResponse;
    }
}
