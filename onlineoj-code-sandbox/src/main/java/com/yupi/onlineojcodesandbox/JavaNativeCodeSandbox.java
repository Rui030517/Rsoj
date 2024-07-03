package com.yupi.onlineojcodesandbox;

import com.yupi.onlineojcodesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineojcodesandbox.model.ExecuteCodeRequest;
import org.springframework.stereotype.Component;


/**
 * java原生代码沙箱
 */

@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate{

    @Override
    public ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest) {
        return super.ExecuteCode(executeCodeRequest);
    }
}
