package com.kiss.accountconsole.controller;

import com.kiss.account.input.*;
import com.kiss.account.output.RolePermissionOutput;
import com.kiss.accountconsole.feign.account.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import java.util.HashMap;
import java.util.List;
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
    public Object login(@RequestBody LoginInput loginInput) {
        return authServiceFeign.loginWithUsernameAndPassword(loginInput);
    }

    @PostMapping
    @ApiOperation(value = "添加账户")
    public Object createAccount(@RequestBody CreateAccountInput createAccountInput) throws InvalidNameException {
        return accountServiceFeign.createAccount(createAccountInput);
    }

    @PutMapping
    @ApiOperation(value = "更新账户信息")
    public Object updateAccount(@RequestBody UpdateAccountInput updateAccountInput) throws InvalidNameException {
        return accountServiceFeign.updateAccount(updateAccountInput);
    }

    @PutMapping("/dimission")
    @ApiOperation(value = "用户离职")
    public Object updateAccountStatusDimission(@RequestBody UpdateAccountStatusInput updateAccountStatusInput) throws InvalidNameException {
        updateAccountStatusInput.setStatus(2);
        return accountServiceFeign.updateAccountStatus(updateAccountStatusInput);
    }

    @PutMapping("/resume")
    @ApiOperation(value = "用户复职")
    public Object updateAccountStatusResume(@RequestBody UpdateAccountStatusInput updateAccountStatusInput) throws InvalidNameException {
        updateAccountStatusInput.setStatus(1);
        return accountServiceFeign.updateAccountStatus(updateAccountStatusInput);
    }

    @PutMapping("/reset/password")
    @ApiOperation(value = "重置账户密码")
    public void resetAccountPassword(Integer id) throws InvalidNameException {
        accountServiceFeign.resetAccountPassword(id);
    }

    @PostMapping("/group")
    @ApiOperation(value = "添加部门")
    public Object createAccountGroup(@RequestBody CreateAccountGroupInput createAccountGroupInput) {
        return accountGroupServiceFeign.createAccountGroup(createAccountGroupInput);
    }

    @PutMapping("/group")
    @ApiOperation(value = "更新部门")
    public Object updateAccountGroup(@RequestBody UpdateAccountGroupInput updateAccountGroupInput) {
        return accountGroupServiceFeign.updateAccountGroup(updateAccountGroupInput);
    }

    @DeleteMapping("/group")
    @ApiOperation(value = "删除部门")
    public void deleteAccountGroup(Integer id) {
        accountGroupServiceFeign.deleteGroup(id);
    }

    @PostMapping("/role")
    @ApiOperation(value = "添加角色")
    public Object createRole(@RequestBody CreateRoleInput createRoleInput) {
        return roleServiceFeign.createRole(createRoleInput);
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新角色")
    public Object updateRole(@RequestBody UpdateRoleInput updateRoleInput) {
        return roleServiceFeign.updateRole(updateRoleInput);
    }

    @PostMapping("/role/permissions")
    @ApiOperation(value = "绑定角色权限")
    public Object bindRolePermissions(@RequestBody BindPermissionToRoleInput bindPermissionToRoleInput) {
        return roleServiceFeign.bindRolePermissions(bindPermissionToRoleInput);
    }

    @PostMapping("/role/accounts")
    @ApiOperation(value = "绑定角色账户")
    public Object bindRoleAccounts(@RequestBody BindAccountsToRoleInput bindAccountsToRoleInput) {
        return roleServiceFeign.bindRoleAccounts(bindAccountsToRoleInput);
    }

    @GetMapping("/role/accountIds/permissionIds")
    @ApiOperation(value = "获取角色的账户权限列表")
    public Object getRoleAccountIdsAndPermissionIds(Integer id) {

        List<Integer> accounts = roleServiceFeign.getRoleAccountIds(id);
        List<RolePermissionOutput> permissions = roleServiceFeign.getRolePermissions(id);

        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accounts);
        result.put("permissions", permissions);

        return result;
    }

    @DeleteMapping("/role")
    @ApiOperation(value = "删除角色")
    public void updateRole(Integer id) {
        roleServiceFeign.deleteRole(id);
    }

    @PostMapping("/permission")
    @ApiOperation(value = "添加权限")
    public Object createPermission(@RequestBody CreatePermissionInput createPermissionInput) throws NoSuchFieldException, IllegalAccessException {
        return permissionServiceFeign.createPermission(createPermissionInput);
    }

    @PutMapping("/permission")
    @ApiOperation(value = "更新权限")
    public Object updatePermission(@RequestBody UpdatePermissionInput updatePermissionInput) {
        return permissionServiceFeign.updatePermission(updatePermissionInput);
    }

    @DeleteMapping("/permission")
    @ApiOperation(value = "删除权限")
    public void deletePermission(Integer id) {
        permissionServiceFeign.deletePermission(id);
    }

    @PostMapping("/permission/module")
    @ApiOperation(value = "添加权限模块")
    public Object createPermissionModule(@RequestBody CreatePermissionModuleInput createPermissionModuleInput) {
        return permissionModuleServiceFeign.createPermissionModule(createPermissionModuleInput);
    }

    @PutMapping("/permission/module")
    @ApiOperation(value = "更新权限模块")
    public Object updatePermissionModule(@RequestBody UpdatePermissionModuleInput updatePermissionInput) {
        return permissionModuleServiceFeign.updatePermissionModule(updatePermissionInput);
    }

    @DeleteMapping("/permission/module")
    @ApiOperation(value = "删除权限模块")
    public void deletePermissionModule(Integer id) {
        permissionModuleServiceFeign.deletePermissionModule(id);
    }

    @PostMapping("/client")
    @ApiOperation(value = "添加授权客户端")
    public Object postClient(@RequestBody CreateClientInput createClientInput) {
        return clientServiceFeign.createClient(createClientInput);
    }

    @PostMapping("/client/secret")
    @ApiOperation(value = "获取客户端秘钥")
    public Object getClientSecret(@RequestBody GetClientSecretInput getClientSecretInput) {
        return clientServiceFeign.getClientSecret(getClientSecretInput);
    }

    @PutMapping("/client")
    @ApiOperation(value = "更新授权客户端")
    public Object putClient(@RequestBody UpdateClientInput updateClientInput) {
        return clientServiceFeign.updateClient(updateClientInput);
    }

    @DeleteMapping("/client")
    @ApiOperation(value = "删除授权客户端")
    public void deleteClient(@RequestParam("id") Integer id) {
        clientServiceFeign.deleteClient(id);
    }

    @PutMapping("/client/modules")
    @ApiOperation(value = "绑定客户端的权限模块")
    public Object updateClientModules(@RequestBody UpdateClientModulesInput updateClientModulesInput) {
        return clientModuleServiceFeign.updateClientModules(updateClientModulesInput);
    }

    @GetMapping("/client/modules")
    @ApiOperation(value = "获取客户端的权限模块")
    public Object getClientModules(@RequestParam("id") Integer id) {
        return clientModuleServiceFeign.getClientModules(id);
    }

    @GetMapping("/operation/logs")
    @ApiOperation(value = "获取操作日志")
    public Object getOperationLogs(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return operationLogServiceFeign.getOperationLogs(page, size);
    }

    @GetMapping("/root/check")
    @ApiOperation(value = "检查超级管理员")
    public Object checkRoot() {
        return accountServiceFeign.checkRoot();
    }

    @PostMapping("/root")
    @ApiOperation(value = "创建超级管理员")
    public Object createRoot(@RequestBody CreateRootAccountInput createRootAccountInput) {
        return accountServiceFeign.createRoot(createRootAccountInput);
    }
}
