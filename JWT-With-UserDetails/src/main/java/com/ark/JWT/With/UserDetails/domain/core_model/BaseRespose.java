package com.ark.JWT.With.UserDetails.domain.core_model;

import java.util.Date;

public class BaseRespose<T> {
    private Date date;
    private String message;
    private String status;
    private String token;
    private String refreshToken;
    private T data;

    public BaseRespose(Date date, String message, String status, String token, String refreshToken,T data) {
        this.date = date;
        this.message = message;
        this.status = status;
        this.token = token;
        this.refreshToken = refreshToken;
        this.data = data;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
