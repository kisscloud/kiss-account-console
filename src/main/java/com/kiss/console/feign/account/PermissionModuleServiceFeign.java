package com.kiss.console.feign.account;

import com.kiss.account.client.PermissionModuleClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("kiss-account")
public interface PermissionModuleServiceFeign extends PermissionModuleClient {
}
