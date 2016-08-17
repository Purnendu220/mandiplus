package com.example.purnendumishra;

import android.content.Context;


import com.example.purnendumishra.handler.AnimationHandler;
import com.example.purnendumishra.handler.DeviceUtils;
import com.example.purnendumishra.handler.FontTypeFace;
import com.example.purnendumishra.interfaces.SmsHandler;
import com.example.purnendumishra.service.Constants;
import com.example.purnendumishra.service.RestAPI;
import com.example.purnendumishra.service.ServiceCallsUtils;
import com.example.purnendumishra.service.response.SignupResponse;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Project : Mobikasa Cardamom
 * Author : Balwinder Singh Madaan
 * Creation Date : 25-july-2016
 * Description :
 */
public class RefrenceWrapper {
    public static RefrenceWrapper refrenceWrapper;
    private Context context;
    private RestAPI service;
    private ServiceCallsUtils mServiceCallHandler;
    private DeviceUtils mDeviceUtilHandler;
    private AnimationHandler mAnimationHandler;
    private SmsHandler msmsHandler;
    private FontTypeFace fontTypeFace;

    private RefrenceWrapper(Context activity) {
        this.context = activity;
    }

    public static RefrenceWrapper getRefrenceWrapper(Context context) {
        if (refrenceWrapper == null) {
            refrenceWrapper = new RefrenceWrapper(context);
        }
        return refrenceWrapper;
    }

    public RestAPI getService() {
        return setService();
    }

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {


        }
    };

    private RestAPI setService() {
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.WebConstants.HOST_API).setLogLevel(RestAdapter.LogLevel.FULL).setRequestInterceptor(requestInterceptor).setClient(new OkClient(new OkHttpClient())).build();
        service = restAdapter.create(RestAPI.class);
        return service;
    }

    public void destroyInstance() {
        if (refrenceWrapper != null) {
            refrenceWrapper = null;
        }

    }

    public ServiceCallsUtils getmServiceCallHandler() {
        if (mServiceCallHandler == null) {
            mServiceCallHandler = new ServiceCallsUtils();
        }
        return mServiceCallHandler;
    }

    public DeviceUtils getmDeviceUtilHandler() {
        if (mDeviceUtilHandler == null) {
            mDeviceUtilHandler = new DeviceUtils();
        }
        return mDeviceUtilHandler;
    }

    public AnimationHandler getmAnimationHandler() {
        if (mAnimationHandler == null) {
            mAnimationHandler = new AnimationHandler();
        }
        return mAnimationHandler;
    }

    public FontTypeFace getFontTypeFace() {
        if (fontTypeFace == null) {
            fontTypeFace = new FontTypeFace();
        }
        return fontTypeFace;
    }
public SmsHandler getSmsHandler(){

    if (msmsHandler == null) {
        msmsHandler = new SmsHandler();
    }
    return msmsHandler;

}

}