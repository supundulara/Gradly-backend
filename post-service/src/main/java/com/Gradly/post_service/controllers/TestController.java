package com.Gradly.post_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class TestController {
    @GetMapping("/test")
    public String getPosts() {
        return "Post service running";
    }
}
