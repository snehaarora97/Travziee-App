package com.example.travizee.payload;

public class UserDetails {

   private String emailId;
   private Long userId;

   private SignupResponse signupResponse;


    public UserDetails(String emailId, Long userId, SignupResponse signupResponse) {
        this.emailId = emailId;
        this.userId = userId;
        this.signupResponse=signupResponse;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SignupResponse getSignupResponse() {
        return signupResponse;
    }

    public void setSignupResponse(SignupResponse signupResponse) {
        this.signupResponse = signupResponse;
    }


}
