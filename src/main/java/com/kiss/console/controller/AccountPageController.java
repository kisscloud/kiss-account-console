package com.kiss.console.controller;

import com.kiss.account.output.RoleOutput;
import com.kiss.console.feign.account.*;
import com.kiss.console.utils.ResultOutputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import output.ResultOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/page/account")
@Api(tags = "AccountPage", description = "权限管理相关页面接口")
public class AccountPageController {

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


    @GetMapping("/dashboard")
    @ApiOperation(value = "获取主面板页面参数")
    public ResultOutput getPageDashboardParams() {

        ResultOutput accountGtroupsCount = accountServiceFeign.getAccountsCount();

        Map<String, Object> result = new HashMap<>();
        result.put("accountGroups", accountGtroupsCount.getData());

        return ResultOutputUtil.success(result);
    }

    @GetMapping("/accounts")
    @ApiOperation(value = "获取成员管理页面参数")
    public ResultOutput GetPagePermissionUsersParams() {

        ResultOutput accountsOutput = accountServiceFeign.getAccounts("1", "0");
        ResultOutput groupsOutput = accountGroupServiceFeign.getGroups();

        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accountsOutput.getData());
        result.put("groups", groupsOutput.getData());

        return ResultOutputUtil.success(result);
    }

    @GetMapping("/roles")
    @ApiOperation(value = "获取角色管理页面参数")
    public ResultOutput GetPagePermissionRolesParams() {

        ResultOutput roles = roleServiceFeign.getRoles();
        ResultOutput modules = permissionModuleServiceFeign.getBindPermissionModules();
        ResultOutput permissions = permissionServiceFeign.getPermissions();
        ResultOutput accounts = accountServiceFeign.getAccounts("1", "0");

        List<Object> roleOutputs = (List<Object>) roles.getData();

        ResultOutput firstRoleAccounts = new ResultOutput();
        ResultOutput firstRolePermissions = new ResultOutput();

        if (!roleOutputs.isEmpty()) {
            RoleOutput firstRole = new RoleOutput();
            BeanUtils.copyProperties(roleOutputs.get(0), firstRole);
            firstRolePermissions = roleServiceFeign.getRolePermissions(firstRole.getId());
            firstRoleAccounts = roleServiceFeign.getRoleAccountIds(firstRole.getId());
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

    @GetMapping("/permissions")
    @ApiOperation(value = "获取权限管理页面参数")
    public ResultOutput GetPagePermissionPermissionsParams() {

        ResultOutput permissions = permissionServiceFeign.getPermissions();
        ResultOutput modules = permissionModuleServiceFeign.getPermissionModules();

        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions.getData());
        result.put("modules", modules.getData());

        return ResultOutputUtil.success(result);
    }
}
