package com.example.purnendumishra.interfaces;


import com.example.purnendumishra.service.response.SignupResponse;

/**
 * Created by Mobikasa on 7/25/2016.
 */
public interface SignupInterface {
    public void successSignup(SignupResponse signupResponse);
    public void failourSignup(String message);
}
