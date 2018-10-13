package com.kiss.console.feign.account;

import com.kiss.account.client.AccountClient;
import com.kiss.account.client.PermissionClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("kiss-account")
public interface PermissionServiceFeign extends PermissionClient {
}
