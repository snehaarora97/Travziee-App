package com.example.travizee.payload;

public class SignupResponse {

    private Boolean success;
    private String message;
    private String token;
    private String id;

    public SignupResponse(Boolean success, String message, String token, String id) {
        this.success = success;
        this.message = message;
        this.token =  token;
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
