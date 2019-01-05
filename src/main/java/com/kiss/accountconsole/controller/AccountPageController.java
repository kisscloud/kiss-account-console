package com.kiss.accountconsole.controller;


import com.kiss.account.output.*;
import com.kiss.accountconsole.feign.account.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> getPageDashboardParams() {

        Integer accountGroupsCount = accountGroupServiceFeign.getAccountGroupsCount();
        Integer rolesCount = roleServiceFeign.getValidRolesCount();
        Integer permissionsCount = permissionServiceFeign.getValidPermissionsCount();
        Integer accountsCount = accountServiceFeign.getValidAccountsCount();
        OperationLogsOutput recentOptionLogs = operationLogServiceFeign.getOperationLogs(1, 10);

        Map<String, Object> result = new HashMap<>();
        result.put("accountsCount", accountsCount);
        result.put("rolesCount", rolesCount);
        result.put("accountGroupsCount", accountGroupsCount);
        result.put("permissionsCount", permissionsCount);
        result.put("recentOptionLogs", recentOptionLogs);


        return result;
    }

    @GetMapping("/accounts")
    @ApiOperation(value = "获取成员管理页面参数")
    public Map<String, Object> GetPagePermissionUsersParams() {

        GetAccountsOutput accountsOutput = accountServiceFeign.getAccounts("1", "0");
        List<AccountGroupOutput> groupsOutput = accountGroupServiceFeign.getGroups();

        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accountsOutput);
        result.put("groups", groupsOutput);

        return result;
    }

    @GetMapping("/roles")
    @ApiOperation(value = "获取角色管理页面参数")
    public Map<String, Object> GetPagePermissionRolesParams() {

        List<RoleOutput> roles = roleServiceFeign.getRoles();
        List<PermissionModuleOutput> modules = permissionModuleServiceFeign.getBindPermissionModules();
        List<PermissionOutput> permissions = permissionServiceFeign.getPermissions();
        GetAccountsOutput accounts = accountServiceFeign.getAccounts("1", "0");
        Map<String, Object> result = new HashMap<>();

        result.put("modules", modules);
        result.put("roles", roles);
        result.put("accounts", accounts);
        result.put("permissions", permissions);

        List<Integer> firstRoleAccounts;
        List<RolePermissionOutput> firstRolePermissions;

        if (!roles.isEmpty()) {
            Integer firRoleId = roles.get(0).getId();
            firstRoleAccounts = roleServiceFeign.getRoleAccountIds(firRoleId);
            firstRolePermissions = roleServiceFeign.getRolePermissions(firRoleId);
            result.put("firstRolePermissions", firstRolePermissions);
            result.put("firstRoleAccounts", firstRoleAccounts);

        }

        return result;
    }

    @GetMapping("/permissions")
    @ApiOperation(value = "获取权限管理页面参数")
    public Map<String, Object> GetPagePermissionPermissionsParams() {

        List<PermissionOutput> permissions = permissionServiceFeign.getPermissions();
        List<PermissionModuleOutput> modules = permissionModuleServiceFeign.getPermissionModules();

        Map<String, Object> result = new HashMap<>();
        result.put("permissions", permissions);
        result.put("modules", modules);

        return result;
    }

    @GetMapping("/clients")
    @ApiOperation(value = "获取客户端管理页面参数")
    public Map<String, Object> getPagePermissionClientsParams() {

        List<PermissionModuleOutput> allModules = permissionModuleServiceFeign.getBindPermissionModules();
        List<ClientOutput> clients = clientServiceFeign.getClients();


        Map<String, Object> result = new HashMap<>();

        result.put("clients", clients);
        result.put("allModules", allModules);

        if (!clients.isEmpty()) {
            List<ClientModuleOutput> modules = clientModuleServiceFeign.getClientModules(clients.get(0).getId());
            result.put("firstClientModules", modules);
        }




        return result;
    }
}
