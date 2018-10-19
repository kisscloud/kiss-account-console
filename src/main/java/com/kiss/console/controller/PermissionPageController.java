package com.kiss.console.controller;


import com.kiss.account.output.RoleOutput;
import com.kiss.console.feign.account.*;
import com.kiss.console.utils.ResultOutputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import output.ResultOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/page/permission")
@Api(tags = "PermissionPage", description = "权限管理相关页面接口")
public class PermissionPageController {

    @Autowired
    private AccountServiceFeign accountServiceFeign;

    @Autowired
    private AccountGroupServiceFeign accountGroupServiceFeign;

    @Autowired
    private PermissionServiceFeign permissionServiceFeign;

    @Autowired
    private PermissionModuleServiceFeign permissionModuleServiceFeign;

    @Autowired
    private RoleServiceFeign roleServiceFeign;


    /**
     * 获取用户管理页面参数
     * <p>
     * 1. 查询所有的部门
     * 2. 查询前50个
     */
    @GetMapping("/accounts")
    @ApiOperation(value = "获取成员管理页面参数")
    public ResultOutput GetPagePermissionUsersParams() {

        ResultOutput accountsOutput = accountServiceFeign.getAccounts("1", "10");
        ResultOutput groupsOutput = accountGroupServiceFeign.getGroups();

        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accountsOutput.getData());
        result.put("groups", groupsOutput.getData());

        return ResultOutputUtil.success(result);
    }

    /**
     * 获取角色页面参数
     */
    @GetMapping("/roles")
    @ApiOperation(value = "获取角色管理页面参数")
    public ResultOutput GetPagePermissionRolesParams() {

        ResultOutput roles = roleServiceFeign.getRoles();
        ResultOutput modules = permissionModuleServiceFeign.getBindPermissionModules();
        ResultOutput permissions = permissionServiceFeign.getPermissions();
        ResultOutput accounts = accountServiceFeign.getAccounts("1", "0");

        List<RoleOutput> roleOutputs = (List<RoleOutput>) roles.getData();
        ResultOutput firstRolePermissions = new ResultOutput();
        ResultOutput firstRoleAccounts = new ResultOutput();
        if (!roleOutputs.isEmpty()) {
            firstRolePermissions = roleServiceFeign.getRolePermissionIds(roleOutputs.get(0).getId());
            firstRoleAccounts = roleServiceFeign.getRoleAccountIds(roleOutputs.get(0).getId());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("modules", modules.getData());
        result.put("roles", roles.getData());
        result.put("accounts", accounts.getData());
        result.put("permissions", permissions.getData());
        result.put("firstRolePermissions", firstRolePermissions.getData());
        result.put("firstRoleAccounts", firstRoleAccounts.getData());

        return ResultOutputUtil.success(result);
    }

    /**
     * 获取权限页面参数
     */
    @GetMapping("/permissions")
    @ApiOperation(value = "获取权限管理页面参数")
    public ResultOutput GetPagePermissionPermissionsParams() {

        ResultOutput permissions = permissionServiceFeign.getPermissions();
        ResultOutput modules = permissionModuleServiceFeign.getBindPermissionModules();

        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions.getData());
        result.put("modules", modules.getData());

        return ResultOutputUtil.success(result);
    }
}
