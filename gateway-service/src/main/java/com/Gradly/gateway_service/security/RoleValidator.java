package com.Gradly.gateway_service.security;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RoleValidator {

    public static final Map<String, List<String>> rolePermissions = Map.of(

            "/posts", List.of("student", "alumni", "admin"),

            "/jobs", List.of("alumni", "admin"),

            "/events", List.of("admin"),

            "/users", List.of("student", "alumni", "admin")
    );

}