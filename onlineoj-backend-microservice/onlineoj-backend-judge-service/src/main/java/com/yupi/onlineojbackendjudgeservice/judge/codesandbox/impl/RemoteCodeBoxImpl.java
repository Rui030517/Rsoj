package com.yupi.onlineojbackendjudgeservice.judge.codesandbox.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.onlineojbackendcommon.common.ErrorCode;
import com.yupi.onlineojbackendcommon.exception.BusinessException;
import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeReponse;
import com.yupi.onlineojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.yupi.onlineojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import org.springframework.stereotype.Component;

/**
 * 远程代码沙箱
 */
@Component
public class RemoteCodeBoxImpl implements CodeSandBox {
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_KEY = "secretkey";

    @Override
    public ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8088/executeCode";
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);

        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_KEY)
                .body(jsonStr)
                .execute()
                .body();

        if(!StrUtil.isNotBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteSandbox error, message=" + responseStr);
        }
        System.out.println("远程代码沙箱执行响应" + responseStr.toString());

        return JSONUtil.toBean(responseStr,ExecuteCodeReponse.class);
    }
}
