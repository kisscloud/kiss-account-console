package com.kiss.console.feign.account;

import com.kiss.account.client.AuthClient;
import com.kiss.account.client.RoleClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("kiss-account")
public interface AuthServiceFeign extends AuthClient {
}
