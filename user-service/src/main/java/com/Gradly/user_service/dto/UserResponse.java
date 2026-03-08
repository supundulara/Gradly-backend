package com.Gradly.user_service.dto;

public class UserResponse {

    private String id;
    private String name;

    public UserResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}