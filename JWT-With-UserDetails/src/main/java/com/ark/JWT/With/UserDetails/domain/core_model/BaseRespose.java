package com.ark.JWT.With.UserDetails.domain.core_model;

import java.util.Date;

public class BaseRespose<T> {
    private Date date;
    private String message;
    private String status;
    private T data;

    public BaseRespose(Date date, String message, String status, T data) {
        this.date = date;
        this.message = message;
        this.status = status;
        this.data = data;
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
