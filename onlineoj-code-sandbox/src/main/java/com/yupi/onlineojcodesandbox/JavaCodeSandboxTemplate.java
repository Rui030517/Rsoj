package com.yupi.onlineojcodesandbox;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yupi.onlineojcodesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineojcodesandbox.model.ExecuteCodeRequest;
import com.yupi.onlineojcodesandbox.model.ExecuteMessage;
import com.yupi.onlineojcodesandbox.model.JudgeInfo;
import com.yupi.onlineojcodesandbox.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class JavaCodeSandboxTemplate implements CodeSandBox{

    private static final String GLOBAL_CODE_DIR_NAME =  "temCode";
    private static final  String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    private static final Long TIME_OUT = 5000L;  //程序最长运行时间 5秒

    private static final List<String> blackList = Arrays.asList("Files","exec");

    @Override
    public ExecuteCodeReponse ExecuteCode(ExecuteCodeRequest executeCodeRequest) {

        String code = executeCodeRequest.getCode();
        List<String> input = executeCodeRequest.getInput();
        String language = executeCodeRequest.getLanguage();

        //1.把用户的代码保存为文件
        File userfCodeFile = saveCodeTofile(code);

        //2.编译代码，得到.class文件
        ExecuteMessage executeMessage = compileFile(userfCodeFile);
        System.out.println(executeMessage);

        //3.执行代码，得到输出结果
        List<ExecuteMessage> executeMessageList = execCodeFile(userfCodeFile, input);

        //4.收集整理输出结果
        ExecuteCodeReponse executeCodeReponse = getOutputResponse(executeMessageList);

        //5.文件清理
        boolean b = clearFile(userfCodeFile);
        if(!b){
            log.info("删除文件失败,userCodeFilePath = {}",userfCodeFile.getAbsolutePath());
        }

        return executeCodeReponse;
    }



    /**
     * 把用户代码保存为文件
     * @param code
     * @return
     */
    public  File saveCodeTofile(String code){

        String userDir = System.getProperty("user.dir");
        String gobleCodePath = userDir + File.separator + GLOBAL_CODE_DIR_NAME;


        if (!FileUtil.exist(gobleCodePath)) {
            FileUtil.mkdir(gobleCodePath);
        }
        //把用户的代码隔离存放
        String userCodePath = gobleCodePath + File.separator + UUID.randomUUID();
        File userCodeFile = FileUtil.writeString(code, userCodePath + File.separator + GLOBAL_JAVA_CLASS_NAME, StandardCharsets.UTF_8);
        return userCodeFile;
    }

    /**
     * 编译代码
     * @param userCodeFile
     * @return
     */
    public ExecuteMessage compileFile(File userCodeFile){
        //编译代码得到.class文件
        String compileCmd = String.format("javac -encoding UTF-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);//想在命令行执行的语句都可以在这行代码中被执行
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            if(executeMessage.getExitValue()!=0){
                throw new RuntimeException("编译错误");
            }
            return executeMessage;
        } catch (IOException e) {
            throw new RuntimeException("程序编译异常",e);
        }
    }

    /**
     * 执行程序文件
     * @param userCodeFile
     * @param input
     * @return
     */
    public List<ExecuteMessage> execCodeFile(File userCodeFile,List<String> input){
        String userCodePath = userCodeFile.getParentFile().getAbsolutePath();

        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : input) {
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodePath, inputArgs);


            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                //程序运行超时控制
                new Thread(()->{
                    try {
                        Thread.sleep(TIME_OUT);
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

//                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                ExecuteMessage executeMessage = ProcessUtils.runInterProcessAndGetMessage(runProcess, "运行",inputArgs);
                System.out.println(executeMessage);
                executeMessageList.add(executeMessage);
            } catch (IOException e) {
                throw new RuntimeException("程序执行异常",e);
            }
        }
        return executeMessageList;
    }

    /**
     * 整理执行文件输出结果，返回结果响应
     * @param executeMessageList
     * @return
     */
    public ExecuteCodeReponse getOutputResponse(List<ExecuteMessage> executeMessageList){
        ExecuteCodeReponse executeCodeReponse = new ExecuteCodeReponse();

        List<String> outputList = new ArrayList<>();
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            if(StrUtil.isNotBlank(executeMessage.getErrorMessage())){
                executeCodeReponse.setMessage(executeMessage.getErrorMessage());
                executeCodeReponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            if(maxTime<executeMessage.getTime()){
                maxTime = executeMessage.getTime();
            }
        }

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);

        executeCodeReponse.setJudgeInfo(judgeInfo);
        executeCodeReponse.setOutput(outputList);
        if(executeMessageList.size() == outputList.size()) {
            executeCodeReponse.setStatus(1);
        }

        return executeCodeReponse;
    }

    /**
     * 文件清理
     * @param userCodeFile
     */
    public boolean clearFile(File userCodeFile){
        if(userCodeFile.getParentFile() != null){
            String userCodePath = userCodeFile.getParentFile().getAbsolutePath();
            boolean del = FileUtil.del(userCodePath);
            System.out.println("删除" + ((del)?"成功":"失败"));
            return del;
        }
        else{
            return true;
        }

    }

    /**
     * 获取错误响应
     * @param e
     * @return
     */
    private ExecuteCodeReponse getErrorResponse(Throwable e){
        ExecuteCodeReponse executeCodeReponse = new ExecuteCodeReponse();
        executeCodeReponse.setStatus(2);
        executeCodeReponse.setMessage(e.getMessage());
        executeCodeReponse.setOutput(new ArrayList<>());
        executeCodeReponse.setJudgeInfo(new JudgeInfo());
        return executeCodeReponse;
    }

}
