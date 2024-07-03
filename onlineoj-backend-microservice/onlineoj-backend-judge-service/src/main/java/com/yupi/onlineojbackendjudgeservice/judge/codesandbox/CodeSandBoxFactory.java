package com.yupi.onlineojbackendjudgeservice.judge.codesandbox;


import com.yupi.onlineojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeBoxImpl;
import com.yupi.onlineojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeBoxImpl;
import com.yupi.onlineojbackendjudgeservice.judge.codesandbox.impl.ThirdCodeBoxImpl;

/**
 *  代码沙箱工厂（根据传入的字符串自动创建对应的代码沙箱实例）
 */

public class CodeSandBoxFactory {

    /**
     * 创建代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSandBox newInstance(String type){
        switch (type){
            case "example" :
                return new ExampleCodeBoxImpl();
            case "remote" :
                return new RemoteCodeBoxImpl();
            case "third" :
                return new ThirdCodeBoxImpl();
            default:
                return new ExampleCodeBoxImpl();
        }
    }

}
