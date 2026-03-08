package com.Gradly.user_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class TestController {

    @GetMapping("/test")
    public String getUsers(){
        return "User service running";
    }

}
