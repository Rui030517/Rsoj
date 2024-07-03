package com.yupi.onlineoj.judge.codesandbox.impl;


import com.yupi.onlineoj.judge.codesandbox.CodeSandBox;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;

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
