package com.kiss.console.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    /**
     * 获取用户管理页面参数
     *
     * 1. 查询所有的部门
     * 2. 查询前50个用户
     * 3. 查询用户列表，要支持分页   ?page=1&size=100
     *
     */
    @GetMapping("/page/permission/users")
    public void GetPagePermissionUsersParams() {

//        return {
//            "groups": List<Group>,
//            "users": List<User>,
//        }
    }

    /**
     * 获取权限管理页面参数
     *
     * 1. 获取所有权限模块，按seq排序
     * 2. 获取所有的权限
     */
    @GetMapping("/page/permission/permissions")
    public void GetPagePermissionPermissionsParams() {

//        return {
//            "groups": List<Group>,
//            "users": List<User>,
//        }
    }
}
