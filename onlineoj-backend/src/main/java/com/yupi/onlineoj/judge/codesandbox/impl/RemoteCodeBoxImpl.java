package com.yupi.onlineoj.judge.codesandbox.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.onlineoj.common.ErrorCode;
import com.yupi.onlineoj.exception.BusinessException;
import com.yupi.onlineoj.judge.codesandbox.CodeSandBox;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;
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
