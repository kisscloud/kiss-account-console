package com.kiss.console.controller;

import com.kiss.account.input.*;
import com.kiss.console.feign.account.AccountServiceFeign;
import com.kiss.console.feign.account.PermissionServiceFeign;
import com.kiss.console.feign.account.RoleServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import output.ResultOutput;

@RestController
@RequestMapping("/service/account")
@Api(tags = "AccountAPI", description = "账户服务相关API")
public class AccountServiceController {

    @Autowired
    private AccountServiceFeign accountServiceFeign;

    @Autowired
    private RoleServiceFeign roleServiceFeign;

    @Autowired
    private PermissionServiceFeign permissionServiceFeign;

    @PostMapping("/")
    @ApiOperation(value = "添加账户")
    public ResultOutput createAccount(@RequestBody CreateAccountInput createAccountInput) {
        return accountServiceFeign.createAccount(createAccountInput);
    }

    @PutMapping("/")
    @ApiOperation(value = "更新账户信息")
    public ResultOutput updateAccount(@RequestBody UpdateAccountInput updateAccountInput) {
        return accountServiceFeign.updateAccount(updateAccountInput);
    }

    @PutMapping("/status")
    @ApiOperation(value = "用户离职")
    public ResultOutput updateAccountStatus(@RequestBody UpdateAccountStatusInput updateAccountStatusInput) {
        return accountServiceFeign.updateAccountStatus(updateAccountStatusInput);
    }

    @PutMapping("/password")
    @ApiOperation(value = "重置账户密码")
    public ResultOutput updateAccountPassword(Integer id) {
        return accountServiceFeign.updateAccountPassword(id);
    }

    @PostMapping("/group")
    @ApiOperation(value = "添加部门")
    public ResultOutput createAccountGroup(@RequestBody CreateAccountGroupInput createAccountGroupInput) {
        return accountServiceFeign.createAccountGroup(createAccountGroupInput);
    }

    @PutMapping("/group")
    @ApiOperation(value = "更新部门")
    public ResultOutput updateAccountGroup(@RequestBody UpdateAccountGroupInput updateAccountGroupInput) {
        return accountServiceFeign.updateAccountGroup(updateAccountGroupInput);
    }

    @PostMapping("/role")
    @ApiOperation(value = "添加角色")
    public ResultOutput createRole(@RequestBody CreateRoleInput createRoleInput) {
        return roleServiceFeign.createRole(createRoleInput);
    }

    @PostMapping("/role")
    @ApiOperation(value = "更新角色")
    public ResultOutput updateRole(@RequestBody UpdateRoleInput updateRoleInput) {
        return roleServiceFeign.updateRole(updateRoleInput);
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
        return permissionServiceFeign.createPermissionModule(createPermissionModuleInput);
    }

    @PutMapping("/permission/module")
    @ApiOperation(value = "更新权限模块")
    public ResultOutput updatePermissionModule(@RequestBody UpdatePermissionModuleInput updatePermissionInput) {
        return permissionServiceFeign.updatePermissionModule(updatePermissionInput);
    }

    @DeleteMapping("/permission/module")
    @ApiOperation(value = "删除权限模块")
    public ResultOutput deletePermissionModule(Integer id) {
        return permissionServiceFeign.deletePermissionModule(id);
    }
}
