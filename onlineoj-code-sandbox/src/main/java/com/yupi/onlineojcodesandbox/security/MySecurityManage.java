package com.yupi.onlineojcodesandbox.security;

import java.io.FileDescriptor;

public class MySecurityManage extends SecurityManager{
    @Override
    public boolean getInCheck() {
        return super.getInCheck();
    }

    //检查程序是可执行
    @Override
    public void checkExec(String cmd) {
        super.checkExec(cmd);
    }

    //检查程序是否可读文件
    @Override
    public void checkRead(String file) {
        super.checkRead(file);
    }

    //检测程序是否可写文件
    @Override
    public void checkWrite(FileDescriptor fd) {
        super.checkWrite(fd);
    }

    //检测程序是否可删文件
    @Override
    public void checkDelete(String file) {
        super.checkDelete(file);
    }

    //检测程序是否可连接网络
    @Override
    public void checkConnect(String host, int port) {
        super.checkConnect(host, port);
    }
}
