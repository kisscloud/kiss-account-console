package com.kiss.accountconsole.feign.account;

import com.kiss.account.client.ClientClient;
import com.kiss.accountconsole.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "kiss-account",configuration = FeignConfig.class)
public interface ClientServiceFeign extends ClientClient {
}
