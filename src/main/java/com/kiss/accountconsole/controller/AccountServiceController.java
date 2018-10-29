package com.kiss.accountconsole.controller;

import com.kiss.account.input.*;
import com.kiss.accountconsole.feign.account.*;
import com.kiss.accountconsole.utils.ResultOutputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import output.ResultOutput;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service/account")
@Api(tags = "AccountAPI", description = "账户服务相关API")
public class AccountServiceController {

    @Autowired
    private AuthServiceFeign authServiceFeign;

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

    @PostMapping("/login")
    @ApiOperation(value = "账户登录")
    public ResultOutput login(@RequestBody LoginInput loginInput) {
        return authServiceFeign.loginWithUsernameAndPassword(loginInput);
    }

    @PostMapping
    @ApiOperation(value = "添加账户")
    public ResultOutput createAccount(@RequestBody CreateAccountInput createAccountInput) {
        return accountServiceFeign.createAccount(createAccountInput);
    }

    @PutMapping
    @ApiOperation(value = "更新账户信息")
    public ResultOutput updateAccount(@RequestBody UpdateAccountInput updateAccountInput) {
        return accountServiceFeign.updateAccount(updateAccountInput);
    }

    @PutMapping("/dimission")
    @ApiOperation(value = "用户离职")
    public ResultOutput updateAccountStatusDimission(@RequestBody UpdateAccountStatusInput updateAccountStatusInput) {
        updateAccountStatusInput.setStatus(2);
        return accountServiceFeign.updateAccountStatus(updateAccountStatusInput);
    }

    @PutMapping("/resume")
    @ApiOperation(value = "用户复职")
    public ResultOutput updateAccountStatusResume(@RequestBody UpdateAccountStatusInput updateAccountStatusInput) {
        updateAccountStatusInput.setStatus(1);
        return accountServiceFeign.updateAccountStatus(updateAccountStatusInput);
    }

    @PutMapping("/reset/password")
    @ApiOperation(value = "重置账户密码")
    public ResultOutput updateAccountPassword(Integer id) {
        return accountServiceFeign.updateAccountPassword(id);
    }

    @PostMapping("/group")
    @ApiOperation(value = "添加部门")
    public ResultOutput createAccountGroup(@RequestBody CreateAccountGroupInput createAccountGroupInput) {
        return accountGroupServiceFeign.createAccountGroup(createAccountGroupInput);
    }

    @PutMapping("/group")
    @ApiOperation(value = "更新部门")
    public ResultOutput updateAccountGroup(@RequestBody UpdateAccountGroupInput updateAccountGroupInput) {
        return accountGroupServiceFeign.updateAccountGroup(updateAccountGroupInput);
    }

    @DeleteMapping("/group")
    @ApiOperation(value = "删除部门")
    public ResultOutput deleteAccountGroup(Integer id) {
        return accountGroupServiceFeign.deleteGroup(id);
    }

    @PostMapping("/role")
    @ApiOperation(value = "添加角色")
    public ResultOutput createRole(@RequestBody CreateRoleInput createRoleInput) {
        return roleServiceFeign.createRole(createRoleInput);
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新角色")
    public ResultOutput updateRole(@RequestBody UpdateRoleInput updateRoleInput) {
        return roleServiceFeign.updateRole(updateRoleInput);
    }

    @PostMapping("/role/permissions")
    @ApiOperation(value = "绑定角色权限")
    public ResultOutput bindRolePermissions(@RequestBody BindPermissionToRoleInput bindPermissionToRoleInput) {
        return roleServiceFeign.bindRolePermissions(bindPermissionToRoleInput);
    }

    @PostMapping("/role/accounts")
    @ApiOperation(value = "绑定角色账户")
    public ResultOutput bindRoleAccounts(@RequestBody BindAccountsToRoleInput bindAccountsToRoleInput) {
        return roleServiceFeign.bindRoleAccounts(bindAccountsToRoleInput);
    }

    @GetMapping("/role/accountIds/permissionIds")
    @ApiOperation(value = "获取角色的账户权限列表")
    public ResultOutput getRoleAccountIdsAndPermissionIds(Integer id) {

        ResultOutput accounts = roleServiceFeign.getRoleAccountIds(id);
        ResultOutput permissions = roleServiceFeign.getRolePermissions(id);

        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accounts.getData());
        result.put("permissions", permissions.getData());

        return ResultOutputUtil.success(result);
    }

    @DeleteMapping("/role")
    @ApiOperation(value = "删除角色")
    public ResultOutput updateRole(Integer id) {
        return roleServiceFeign.deleteRole(id);
    }

    @PostMapping("/permission")
    @ApiOperation(value = "添加权限")
    public ResultOutput createPermission(@RequestBody CreatePermissionInput createPermissionInput) {
        return permissionServiceFeign.createPermission(createPermissionInput);
    }

    @PutMapping("/permission")
    @ApiOperation(value = "更新权限")
    public ResultOutput updatePermission(@RequestBody UpdatePermissionInput updatePermissionInput) {
        return permissionServiceFeign.updatePermission(updatePermissionInput);
    }

    @DeleteMapping("/permission")
    @ApiOperation(value = "删除权限")
    public ResultOutput deletePermission(Integer id) {
        return permissionServiceFeign.deletePermission(id);
    }

    @PostMapping("/permission/module")
    @ApiOperation(value = "添加权限模块")
    public ResultOutput createPermissionModule(@RequestBody CreatePermissionModuleInput createPermissionModuleInput) {
        return permissionModuleServiceFeign.createPermissionModule(createPermissionModuleInput);
    }

    @PutMapping("/permission/module")
    @ApiOperation(value = "更新权限模块")
    public ResultOutput updatePermissionModule(@RequestBody UpdatePermissionModuleInput updatePermissionInput) {
        return permissionModuleServiceFeign.updatePermissionModule(updatePermissionInput);
    }

    @DeleteMapping("/permission/module")
    @ApiOperation(value = "删除权限模块")
    public ResultOutput deletePermissionModule(Integer id) {
        return permissionModuleServiceFeign.deletePermissionModule(id);
    }

    @PostMapping("/client")
    @ApiOperation(value = "添加授权客户端")
    public ResultOutput postClient(@RequestBody CreateClientInput createClientInput) {
        return clientServiceFeign.createClient(createClientInput);
    }

    @PostMapping("/client/secret")
    @ApiOperation(value = "获取客户端秘钥")
    public ResultOutput getClientSecret(@RequestBody GetClientSecretInput getClientSecretInput) {
        return clientServiceFeign.getClientSecret(getClientSecretInput);
    }

    @PutMapping("/client")
    @ApiOperation(value = "更新授权客户端")
    public ResultOutput putClient(@RequestBody UpdateClientInput updateClientInput) {
        return clientServiceFeign.updateClient(updateClientInput);
    }

    @DeleteMapping("/client")
    @ApiOperation(value = "删除授权客户端")
    public ResultOutput deleteClient(@RequestParam("id") Integer id) {
        return clientServiceFeign.deleteClient(id);
    }

    @PutMapping("/client/modules")
    @ApiOperation(value = "绑定客户端的权限模块")
    public ResultOutput updateClientModules(@RequestBody UpdateClientModulesInput updateClientModulesInput) {
        return clientModuleServiceFeign.updateClientModules(updateClientModulesInput);
    }

    @GetMapping("/client/modules")
    @ApiOperation(value = "获取客户端的权限模块")
    public ResultOutput getClientModules(@RequestParam("id") Integer id) {
        return clientModuleServiceFeign.getClientModules(id);
    }


    @GetMapping("/operation/logs")
    @ApiOperation(value = "获取操作日志")
    public ResultOutput getOperationLogs(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return operationLogServiceFeign.getOperationLogs(page, size);
    }
}
