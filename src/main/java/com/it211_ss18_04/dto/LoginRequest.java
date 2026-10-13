package com.it211_ss18_04.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}