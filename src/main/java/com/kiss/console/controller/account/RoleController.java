package com.kiss.console.controller.account;

import com.kiss.console.feign.account.RoleServiceFeign;
import com.kiss.console.utils.ResultOutputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import output.ResultOutput;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Api(tags = "AccountRole", description = "账户服务角色模块")
public class RoleController {

    @Autowired
    private RoleServiceFeign roleServiceFeign;

    @GetMapping("/role/accountIds/permissionIds")
    @ApiOperation(value = "获取角色绑定的账户ID列表和权限ID列表")
    public ResultOutput getRoleAccountIdsAndPermissionIds(Integer id) {

        ResultOutput accounts = roleServiceFeign.getRoleAccountIds(id);
        ResultOutput permissions = roleServiceFeign.getRolePermissionIds(id);
        Map<String, Object> result = new HashMap<>();
        result.put("accounts", accounts.getData());
        result.put("permissions", permissions.getData());

        return ResultOutputUtil.success(result);
    }
}
