package com.example.purnendumishra.service;



import com.example.purnendumishra.service.request.SignupRequest;
import com.example.purnendumishra.service.response.SignupResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Project : Mobikasa Retrofit Lib
 * Author : Balwinder Singh Madaan
 * Creation Date : 25-7-2016
 * Description :"for managing url calls like GET,POST.",
 * Creating RestAPI Interface to Send HTTP Request using Retrofit and We have to create an interface to handle our requests. So create a new RestAPI interface that will handle all HTTP Request.
 */
public interface RestAPI {

    @POST(Constants.ServiceConstants.SIGNUP)
    void signup(@Body SignupRequest signupRequest, CustomCallBacks<SignupResponse> callBacks);


}

