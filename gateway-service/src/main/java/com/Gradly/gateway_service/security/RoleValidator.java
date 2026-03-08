package com.Gradly.gateway_service.security;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleValidator {

    public static final List<RoleRule> rules = List.of(

            // POSTS
            new RoleRule("POST", "/posts", List.of("student", "alumni", "admin")),
            new RoleRule("GET", "/posts", List.of("student", "alumni", "admin")),

            // JOBS
            new RoleRule("POST", "/jobs", List.of("alumni", "admin")),
            new RoleRule("GET", "/jobs", List.of("student", "alumni", "admin")),

            //Events
            new RoleRule("POST", "/events", List.of("alumni", "admin")),
            new RoleRule("GET", "/events", List.of("student", "alumni", "admin")),

            // NOTIFICATIONS
            new RoleRule("GET", "/notifications", List.of("student", "alumni", "admin")),



            // APPLY JOB
            new RoleRule("POST", "/jobs/*/apply", List.of("student", "alumni")),



            // USERS
            new RoleRule("GET", "/users", List.of("student", "alumni", "admin")),
            new RoleRule("PUT", "/users", List.of("student", "alumni", "admin"))
    );
}