package com.auth.auth.domain.user.controller.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String code;
    private String state;
    private String social;
}
