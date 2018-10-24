package com.kiss.console.feign.account;

import com.kiss.account.client.AccountClient;
import com.kiss.console.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "kiss-account",configuration = FeignConfig.class)
public interface AccountServiceFeign extends AccountClient {
}
