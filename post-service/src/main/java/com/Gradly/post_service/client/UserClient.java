package com.Gradly.post_service.client;

import com.Gradly.post_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/internal/users/{id}")
    UserResponse getUser(@PathVariable String id);

}
