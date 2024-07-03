package com.yupi.onlineoj.judge.codesandbox.impl;

import com.yupi.onlineoj.judge.codesandbox.CodeSandBox;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.onlineoj.judge.codesandbox.model.JudgeInfo;
import com.yupi.onlineoj.model.enums.QuestionSubmitEnum;
import com.yupi.onlineoj.model.enums.QuestionSubmitJudgeStatusEnum;

import java.util.List;

/**
 *  实例代码沙箱
 *  @param
 *  @return
 */

public class ExampleCodeBoxImpl implements CodeSandBox {

    @Override
    public ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest) {
//        System.out.println("实例代码沙箱");

        List<String> input = executeCodeRequest.getInput();

        ExecuteCodeReponse executeCodeReponse = new ExecuteCodeReponse();
        executeCodeReponse.setOutput(input);
        executeCodeReponse.setMessage("测试执行成功！");
        executeCodeReponse.setStatus(QuestionSubmitEnum.SUCCESS.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        judgeInfo.setMessage(QuestionSubmitJudgeStatusEnum.ACCEPTED.getValue());

        executeCodeReponse.setJudgeInfo(judgeInfo);
        return executeCodeReponse;
    }
}
