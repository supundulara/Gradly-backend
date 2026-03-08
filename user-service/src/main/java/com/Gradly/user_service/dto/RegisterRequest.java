package com.Gradly.user_service.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String role;
    private String department;
    private Integer graduationYear;
    private String bio;

}