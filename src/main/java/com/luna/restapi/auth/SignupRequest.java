package com.luna.restapi.auth;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
}
