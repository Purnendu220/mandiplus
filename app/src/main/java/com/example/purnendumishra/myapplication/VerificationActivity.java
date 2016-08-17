package com.example.purnendumishra.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.purnendumishra.BaseActivity;
import com.example.purnendumishra.RefrenceWrapper;
import com.example.purnendumishra.handler.AlertUtils;
import com.example.purnendumishra.handler.AppMessages;
import com.example.purnendumishra.handler.LogUtils;
import com.example.purnendumishra.handler.StringUtils;
import com.example.purnendumishra.interfaces.SmsHandler;
import com.example.purnendumishra.interfaces.Smsliste;

public class VerificationActivity extends BaseActivity implements View.OnClickListener,SmsHandler.Smsliste {

    private RefrenceWrapper refrenceWrapper;
    private EditText otp;
    private Button mNext;
    private Context mContext;
    private String otpString;

    @Override
    public int getContentViewID() {
        return R.layout.activity_verification;
    }

    @Override
    public void onCreateBase(Bundle savedInstanceState) {
        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
        mContext=getApplicationContext();
        Intent i=getIntent();
        otpString=i.getExtras().getString(AppMessages.Constants.OTP);
        InitUi();
        setlistener();
        refrenceWrapper.getSmsHandler().setSmsliste(this);
    }
    private void InitUi(){
        otp=(EditText)findViewById(R.id.editTextOtp);
        mNext=(Button)findViewById(R.id.buttonNext);

    }
    private void setlistener(){

        mNext.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonNext:
                Submit();
                break;

        }
    }

    private void Submit(){

        if (StringUtils.isPasswordLengthValid(otp.getText().toString(),4)){
                if (StringUtils.isPasswordValid(otp.getText().toString(),otpString)){
                    Intent i = new Intent(this, ProfileCreationActivity.class);
                    startActivity(i);
                    finish();
                }
            else{
                    AlertUtils.showToastLong(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_OTP);
                }

        }
        else {
            AlertUtils.showToastLong(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_OTP);
        }
    }

    @Override
    public void smsReceived(String sms) {
        LogUtils.debug(sms);
        activityCleanSwitcher(ProfileCreationActivity.class);

    }
}
