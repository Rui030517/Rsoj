package com.yupi.onlineoj.judge.codesandbox;

import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理模式用来增强被实现类或一些接口的功能的代理（即可以实现原本被代理接口的原始功能上给它一些额外的能力）
 * 这里我避免在原接口的方法实现上重复写一些日志输出就使用代理模式来完成这些日志的输出
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{

    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox){
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息："+ executeCodeRequest.toString());
        ExecuteCodeReponse executeCodeReponse = codeSandBox.ExecuteCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeReponse.toString());
        return executeCodeReponse;
    }
}
