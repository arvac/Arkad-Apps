package com.example.irir1.myapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal on 14/04/17.
 */

public class Result {
    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;


    public Result(Boolean error, String message, User user,Boolean success) {
        this.error = error;
        this.message = message;
        this.user = user;
        this.success = success;

    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Boolean getSuccess() {  return success; }
}
