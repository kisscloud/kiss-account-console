package com.kiss.console.feign.account;

import com.kiss.account.client.ClientClient;
import com.kiss.account.client.ClientModuleClient;
import com.kiss.console.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "kiss-account",configuration = FeignConfig.class)
public interface ClientModuleServiceFeign extends ClientModuleClient {
}
