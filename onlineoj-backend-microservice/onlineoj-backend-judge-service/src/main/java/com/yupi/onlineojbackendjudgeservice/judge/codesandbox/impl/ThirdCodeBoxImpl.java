package com.yupi.onlineojbackendjudgeservice.judge.codesandbox.impl;


import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeReponse;
import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.yupi.onlineojbackendjudgeservice.judge.codesandbox.CodeSandBox;

/***
 * 第三放代码沙箱
 */
public class ThirdCodeBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
