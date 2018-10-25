package com.kiss.console.controller;

import com.kiss.account.output.ClientOutput;
import com.kiss.account.output.RoleOutput;
import com.kiss.console.feign.account.*;
import com.kiss.console.utils.ResultOutputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import output.ResultOutput;

import java.util.*;


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

    @Autowired
    private ClientServiceFeign clientServiceFeign;

    @Autowired
    private ClientModuleServiceFeign clientModuleServiceFeign;

    @Autowired
    private OperationLogServiceFeign operationLogServiceFeign;


    @GetMapping("/dashboard")
    @ApiOperation(value = "获取主面板页面参数")
    public ResultOutput getPageDashboardParams() {

        ResultOutput accountGroupsCount = accountGroupServiceFeign.getAccountGroupsCount();
        ResultOutput rolesCount = roleServiceFeign.getValidRolesCount();
        ResultOutput permissionsCount = permissionServiceFeign.getValidPermissionsCount();
        ResultOutput accountsCount = accountServiceFeign.getValidAccountsCount();
        ResultOutput recentOptionLogs = operationLogServiceFeign.getOperationLogs(1, 10);

        Map<String, Object> result = new HashMap<>();
        result.put("accountsCount", accountsCount.getData());
        result.put("rolesCount", rolesCount.getData());
        result.put("accountGroupsCount", accountGroupsCount.getData());
        result.put("permissionsCount", permissionsCount.getData());
        result.put("recentOptionLogs", recentOptionLogs.getData());


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

        List<Map<String, Object>> roleOutputs = (List<Map<String, Object>>) roles.getData();
        ResultOutput firstRoleAccounts = new ResultOutput();
        ResultOutput firstRolePermissions = new ResultOutput();

        if (!roleOutputs.isEmpty()) {
            Integer firRoleId = (Integer) roleOutputs.get(0).get("id");
            firstRolePermissions = roleServiceFeign.getRolePermissions(firRoleId);
            firstRoleAccounts = roleServiceFeign.getRoleAccountIds(firRoleId);
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

    @GetMapping("/clients")
    @ApiOperation(value = "获取客户端管理页面参数")
    public ResultOutput getPagePermissionClientsParams() {

        ResultOutput allModules = permissionModuleServiceFeign.getBindPermissionModules();
        ResultOutput clients = clientServiceFeign.getClients();
        ResultOutput modules = new ResultOutput();

        List<Map<String, Object>> clientObjects = (List<Map<String, Object>>) clients.getData();

        if (!clientObjects.isEmpty()) {
            modules = clientModuleServiceFeign.getClientModules((Integer) clientObjects.get(0).get("id"));
        }


        Map<String, Object> result = new HashMap<>();
        result.put("clients", clients.getData());
        result.put("allModules", allModules.getData());
        result.put("firstClientModules", modules.getData());


        return ResultOutputUtil.success(result);
    }
}
