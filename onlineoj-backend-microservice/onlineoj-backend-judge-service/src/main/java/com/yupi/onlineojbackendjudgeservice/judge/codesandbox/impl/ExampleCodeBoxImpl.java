package com.yupi.onlineojbackendjudgeservice.judge.codesandbox.impl;


import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeReponse;
import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.yupi.onlineojbackendcommon.model.codesandbox.JudgeInfo;
import com.yupi.onlineojbackendcommon.model.enums.QuestionSubmitEnum;
import com.yupi.onlineojbackendcommon.model.enums.QuestionSubmitJudgeStatusEnum;
import com.yupi.onlineojbackendjudgeservice.judge.codesandbox.CodeSandBox;

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
