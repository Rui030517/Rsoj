package com.yupi.onlineojcodesandbox.security;

import java.io.FileDescriptor;
import java.security.Permission;

/**
 * 默认安全管理器
 */
public class DefaultSecurityManage extends SecurityManager{

    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何权限限制");

//        super.checkPermission(perm);
    }
}
