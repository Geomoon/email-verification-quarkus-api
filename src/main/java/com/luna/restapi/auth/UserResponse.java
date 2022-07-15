package com.luna.restapi.auth;

import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String name;
    private String lastname;
    private boolean verified;
}
