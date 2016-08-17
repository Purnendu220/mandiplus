package com.example.purnendumishra.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wheelview.ArrayWheelAdapter;
import com.wheelview.WheelView;


public class WheelPickerActivity extends Activity {

    private LinearLayout mBackId;
    private DiamondArrayAdapter mWheeladapter;
    private WheelView mWheelSubject;
    private Button mButtonDone,mButtonCancel;
    private  String mSubjectTitles[] = new String[200];
    private String mSubjectIntent="SUBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_picker);

        Bundle b=this.getIntent().getExtras();
        mSubjectTitles=b.getStringArray("data");
        mBackId = (LinearLayout) findViewById(R.id.back_id);
        mButtonDone=(Button)findViewById(R.id.btn_done);
        mButtonCancel=(Button)findViewById(R.id.btn_cancel);


        mWheelSubject = (WheelView) findViewById(R.id.slot_1);
        mWheeladapter = new DiamondArrayAdapter(WheelPickerActivity.this, mSubjectTitles, mWheelSubject.getCurrentItem());

        initWheel(mSubjectTitles);

        mBackId.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });

        mButtonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });

        mButtonDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectString = mSubjectTitles[mWheelSubject.getCurrentItem()];
                Intent i = new Intent();
                i.putExtra(mSubjectIntent, subjectString);
             //   i.putExtra(Constants.KEY_DATE, checkOutDate.toUpperCase());
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
    }

    private void initWheel(String wts[]) {
        mWheelSubject.setViewAdapter(mWheeladapter);
        mWheelSubject.setCurrentItem(0);
        mWheelSubject.setVisibleItems(3);
        mWheelSubject.setCyclic(false);
        mWheelSubject.setEnabled(true);

    }

    private WheelView getWheel(int id) {
        return (WheelView) this.findViewById(id);
    }

    private class DiamondArrayAdapter extends ArrayWheelAdapter<String> {
        int currentItem;
        int currentValue;

        public DiamondArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(20);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            view.setTextColor(Color.BLACK);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }

}
