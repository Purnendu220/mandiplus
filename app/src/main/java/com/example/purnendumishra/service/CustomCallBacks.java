package com.example.purnendumishra.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.purnendumishra.myapplication.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Project : Mobikasa Cardamom
 * Author : Balwinder Singh Madaan
 * Creation Date : 25-july-2016
 * Description :
 */
public abstract class CustomCallBacks<T> implements Callback<T> {
    Context context;
    private ProgressDialog mProgressDialog;

    public CustomCallBacks(Context context, boolean showProgress) {
        this.context = context;

        if (showProgress) {
            setDialog();
        }
    }

    @Override
    public void failure(RetrofitError arg0) {
        //Log.i("Retro full error", arg0.getLocalizedMessage().toString());
        arg0.printStackTrace();
        stopDialog();
        if (arg0.isNetworkError()) {
            //SingletonUtility.getInstance(context).showToast(context.getResources().getString(R.string.failure));

        }
        this.onFailure(arg0);
    }

    @Override
    public void success(T arg0, Response arg1) {
        try {
            stopDialog();
        } catch (Exception e) {

        }
        this.onSucess(arg0, arg1);
        String json_str = new Gson().toJson(arg0);
        System.out.println("json_str=" + json_str);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json_str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void onSucess(T arg0, Response arg1);

    public abstract void onFailure(RetrofitError arg0);

    private void setDialog() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
        mProgressDialog.setCancelable(false);

    }

    void stopDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {

            }
        }
    }
}