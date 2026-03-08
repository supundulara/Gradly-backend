package com.Gradly.gateway_service.security;

import java.util.List;

public class RoleRule {

    public String method;
    public String path;
    public List<String> roles;

    public RoleRule(String method, String path, List<String> roles) {
        this.method = method;
        this.path = path;
        this.roles = roles;
    }
}