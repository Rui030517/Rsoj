package com.yupi.onlineojcodesandbox;

import com.yupi.onlineojcodesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineojcodesandbox.model.ExecuteCodeRequest;

public interface CodeSandBox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest);


}
