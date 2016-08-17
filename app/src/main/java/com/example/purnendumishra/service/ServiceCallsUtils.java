package com.example.purnendumishra.service;

import android.content.Context;


import com.example.purnendumishra.RefrenceWrapper;
import com.example.purnendumishra.handler.AlertUtils;
import com.example.purnendumishra.handler.AppMessages;
import com.example.purnendumishra.interfaces.SignupInterface;
import com.example.purnendumishra.service.request.SignupRequest;
import com.example.purnendumishra.service.response.SignupResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Balwinder on 25-07-2016.
 */
public class ServiceCallsUtils {
    private RefrenceWrapper refrenceWrapper;

    public void registration(final Context mContext, SignupRequest signupRequest, final SignupInterface signupInterface) {


        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
        refrenceWrapper.getService().signup(signupRequest, new CustomCallBacks<SignupResponse>(mContext, true) {
            @Override
            public void onSucess(SignupResponse signupResponse, Response arg1) {

                if (signupResponse != null) {
                    boolean status = signupResponse.isStatus();
                    if (status) {
                        signupInterface.successSignup(signupResponse);
                    } else {
                        signupInterface.failourSignup(signupResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(RetrofitError arg0) {
                arg0.printStackTrace();
                signupInterface.failourSignup(arg0.getMessage());
                AlertUtils.showToast(mContext, "" + arg0.getMessage());
            }
        });
    }







}

