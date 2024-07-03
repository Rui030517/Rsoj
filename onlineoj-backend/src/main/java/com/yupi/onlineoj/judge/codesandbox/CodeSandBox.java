package com.yupi.onlineoj.judge.codesandbox;

import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;

public interface CodeSandBox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest);

}
