package com.example.purnendumishra.myapplication;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.purnendumishra.BaseActivity;
import com.example.purnendumishra.RefrenceWrapper;

import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private RefrenceWrapper refrenceWrapper;
    @Override
    public int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateBase(Bundle savedInstanceState) {
        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
    }

    private void initUI() {

        setListeners();
    }

    private void setListeners() {

    }


    @Override
    public void onClick(View view) {

    }
}
