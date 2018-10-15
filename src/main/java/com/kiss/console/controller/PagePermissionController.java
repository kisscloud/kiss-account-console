package com.kiss.console.controller;


import com.kiss.console.feign.account.AccountServiceFeign;
import com.kiss.console.feign.account.PermissionServiceFeign;
import com.kiss.console.feign.account.RoleServiceFeign;
import com.kiss.console.utils.ResultOutputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import output.ResultOutput;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/page/permission")
public class PagePermissionController {

    @Autowired
    private AccountServiceFeign accountServiceFeign;

    @Autowired
    private PermissionServiceFeign permissionServiceFeign;

    @Autowired
    private RoleServiceFeign roleServiceFeign;

    /**
     * 获取用户管理页面参数
     * <p>
     * 1. 查询所有的部门
     * 2. 查询前50个
     */
    @GetMapping("/accounts")
    public ResultOutput GetPagePermissionUsersParams() {

        ResultOutput accountsOutput = accountServiceFeign.getAccounts("1", "10");
        ResultOutput groupsOutput = accountServiceFeign.getGroups();

        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accountsOutput.getData());
        result.put("groups", groupsOutput.getData());

        return ResultOutputUtil.success(result);
    }

    /**
     * 获取角色页面参数
     */
    @GetMapping("/roles")
    public ResultOutput GetPagePermissionRolesParams() {

        ResultOutput roles = roleServiceFeign.getRoles();
        ResultOutput modules = permissionServiceFeign.getBindPermissionsModules();
        ResultOutput permissions = permissionServiceFeign.getPermissions();

        Map<String, Object> result = new HashMap<>();
        result.put("roles", roles.getData());
        result.put("modules", modules.getData());
        result.put("permissions", permissions.getData());

        return ResultOutputUtil.success(result);
    }

    /**
     * 获取权限页面参数
     */
    @GetMapping("/permissions")
    public ResultOutput GetPagePermissionPermissionsParams() {

        ResultOutput permissions = permissionServiceFeign.getPermissions();
        ResultOutput modules = permissionServiceFeign.getBindPermissionsModules();

        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions.getData());
        result.put("modules", modules.getData());

        return ResultOutputUtil.success(result);
    }
}
