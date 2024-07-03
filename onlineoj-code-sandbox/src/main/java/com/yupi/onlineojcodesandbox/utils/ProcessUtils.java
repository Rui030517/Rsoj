package com.yupi.onlineojcodesandbox.utils;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.yupi.onlineojcodesandbox.model.ExecuteMessage;
import javafx.scene.paint.Stop;
import org.apache.tomcat.util.buf.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程工具类
 */
public class ProcessUtils {

    /**
     * 执行进程并获取信息
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess,String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitValue = runProcess.waitFor();  //等待程序执行返回错误码
            executeMessage.setExitValue(exitValue);
            if (exitValue == 0) {  //正常退出
                System.out.println(opName + "成功");
                //分批拿到进程正常编译输出流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                //逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, '\n'));
                System.out.println(outputStrList);
            } else {  //异常退出
                System.out.println(opName + "失败，错误码：" + exitValue);
                //分批拿到进程正常编译输出流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                //逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, '\n'));
                System.out.println(outputStrList);

                //分批拿到进程异常编译输出流
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                List<String> errorOutputStrList = new ArrayList<>();
                //逐行读取
                String errorcompileOutputLine;
                while ((errorcompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorOutputStrList.add(errorcompileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(errorOutputStrList, '\n'));
                System.out.println(errorOutputStrList);

                stopWatch.stop();
                long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
                executeMessage.setTime(lastTaskTimeMillis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }

    /**
     * 执行交互式进程并获取信息
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runInterProcessAndGetMessage(Process runProcess,String opName,String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            InputStream inputStream = runProcess.getInputStream();
            OutputStream outputStream = runProcess.getOutputStream();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] splits = args.split(",");
            outputStreamWriter.write(StrUtil.join("\n",splits) + "\n");  //写入数据
            outputStreamWriter.flush();   //相当于写入完成按下回车

            //分批拿到进程正常编译输出流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> outputStrList = new ArrayList<>();
            //逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                outputStrList.add(compileOutputLine);
            }
            executeMessage.setMessage(StringUtils.join(outputStrList, '\n'));
            //记得支援的回收否则会卡死程序
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            runProcess.destroy();

            stopWatch.stop();
            long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
            executeMessage.setTime(lastTaskTimeMillis);

        }catch(Exception e){
            e.printStackTrace();
        }

        return executeMessage;
    }

}
