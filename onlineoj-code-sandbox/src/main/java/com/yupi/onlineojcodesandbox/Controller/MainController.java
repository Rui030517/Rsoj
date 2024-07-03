package com.yupi.onlineojcodesandbox.Controller;

import com.yupi.onlineojcodesandbox.JavaDockerCodeSandbox;
import com.yupi.onlineojcodesandbox.JavaNativeCodeSandbox;
import com.yupi.onlineojcodesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineojcodesandbox.model.ExecuteCodeRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("/")
public class MainController {

    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_KEY = "secretkey";

    @Resource
    private JavaNativeCodeSandbox javaNativeCodeSandbox;
    @Resource
    private JavaDockerCodeSandbox javaDockerCodeSandbox;

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/executeCode")
    public ExecuteCodeReponse ExecuteCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(executeCodeRequest == null){
            throw new RuntimeException("请求参数为空");
        }

        String header = httpServletRequest.getHeader(AUTH_REQUEST_HEADER);
        if(!header.equals(AUTH_REQUEST_KEY)){
            httpServletResponse.setStatus(403);
            throw new RuntimeException("请求拒绝");
        }

        ExecuteCodeReponse executeCodeReponse = javaNativeCodeSandbox.ExecuteCode(executeCodeRequest);
        return executeCodeReponse;
    }
}
