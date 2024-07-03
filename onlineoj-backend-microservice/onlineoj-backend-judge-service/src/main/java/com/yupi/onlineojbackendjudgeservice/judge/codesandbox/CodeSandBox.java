package com.yupi.onlineojbackendjudgeservice.judge.codesandbox;


import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeReponse;
import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeRequest;

public interface CodeSandBox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest);

}
