package com.auth.auth.oauth2.dto;

import lombok.Data;

@Data
public class LoginBody {
    private String code;
    private String grant_type;
    private String client_id;
    private String redirect_uri;
}
