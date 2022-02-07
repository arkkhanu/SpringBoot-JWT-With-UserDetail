package com.ark.JWT.With.UserDetails.domain.core_model;

import javax.validation.constraints.NotBlank;

public class JWTRequest {
    @NotBlank(message = "UserName is Mandatory")
    private String userName;
    @NotBlank(message = "Password is Mandatory")
    private String password;

    public JWTRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
