package org.example.client;

import org.example.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://user-service:8081/api/v1/user", configuration = FeignConfig.class)
public interface UserClient {
    static final String TOKEN = "Authorization";

    @GetMapping("/id")
    UUID getUserByToken(@RequestHeader(TOKEN) String token);

}
