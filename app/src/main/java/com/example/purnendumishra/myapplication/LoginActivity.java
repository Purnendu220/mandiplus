package com.example.purnendumishra.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.purnendumishra.BaseActivity;
import com.example.purnendumishra.RefrenceWrapper;
import com.example.purnendumishra.handler.AlertUtils;
import com.example.purnendumishra.handler.AppMessages;
import com.example.purnendumishra.handler.AppSharedPreferences;
import com.example.purnendumishra.handler.DeviceUtils;
import com.example.purnendumishra.handler.GPSTracker;
import com.example.purnendumishra.handler.LogUtils;
import com.example.purnendumishra.handler.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private RefrenceWrapper refrenceWrapper;
    private  EditText mobilenumber;
    private Button mNext;
    private Context mContext;
    private String otp;

private void InitUi(){
        mobilenumber=(EditText)findViewById(R.id.editTextNumber);
        mNext=(Button)findViewById(R.id.buttonNext);

}
    private void setlistener(){

        mNext.setOnClickListener(this);

    }
    @Override
    public int getContentViewID() {
        return R.layout.activity_login;
    }
    class GetSms extends AsyncTask<String, Void, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogShow();
            GPSTracker gps = new GPSTracker(mContext);
           gps.getLatitude();
           gps.getLongitude();
            otp = random(4);


        }

        @Override
        protected String doInBackground(String... params) {
            String address= "";
            try {
                address= sendsms(otp);

            }  catch (Exception e) {
                e.printStackTrace();
            }
            // return all the predictions based on the typing the in the
            // search
            return address;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            LogUtils.debug("String " + strings);
           // if(strings.contains("Successfully")){
                Intent i = new Intent(mContext, VerificationActivity.class);
                i.putExtra(AppMessages.Constants.OTP,otp);
                startActivity(i);
                AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.OTP,otp);
                AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.MOBILE,mobilenumber.getText().toString());
           /* }
            else {
                AlertUtils.showToastLong(mContext,AppMessages.CommonSignInSignUpMessages.SOMETHING_WRONG);
            }*/

            progressDialogDismiss();

        }
    }
    @Override
    public void onCreateBase(Bundle savedInstanceState) {
        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
        mContext=getApplicationContext();
        InitUi();
        setlistener();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (DeviceUtils.isNetworkAvailable(mContext)) {
                if (AppSharedPreferences.getInstance(mContext).getPrefrence(AppMessages.Constants.CALL_MOBILE).length() > 0) {
                    activityCleanSwitcher(HomeActivity.class);
                }
            }
else{
                showetDisabledAlertToUser();
            }
        }else{
            showGPSDisabledAlertToUser();
        }



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

        if (StringUtils.isEmptyMoileValid(mobilenumber.getText().toString())){
            new CheckUser().execute();

        }
        else {
            AlertUtils.showToastLong(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_MOBILE);
        }
    }

    public  String sendsms(String otp) {
        String smssu = otp;
        String fulladdress = null;
        try {
            String address = String
                    .format("http://smssigma.com/API/WebSMS/Http/v1.0a/index.php?username=mandiplus&password=mandiplu&sender=ACCEPT&to="+mobilenumber.getText().toString()+"&message="+otp+"&reqid=1&format={text}&route_id=7");
            URL googlePlaces;
            googlePlaces = new URL(address);
            URLConnection tc = googlePlaces.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            // take Google's legible JSON and turn it into one big
            // string.
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            fulladdress=sb.toString();
            LogUtils.debug(sb.toString());


        }
        catch (Exception e) {
            e.printStackTrace();

        }

        return fulladdress;
    }

    class CheckUser extends AsyncTask<String, Void, String> {


String mobile;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogShow();

            mobile=mobilenumber.getText().toString();

        }

        @Override
        protected String doInBackground(String... params) {
            String address= "";
            try {
                address= CheckUser(mobile);

            }  catch (Exception e) {
                e.printStackTrace();
            }
            // return all the predictions based on the typing the in the
            // search
            return address;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            LogUtils.debug("String " + strings);
            progressDialogDismiss();
            if (strings!=null&&strings.length()>0){

                try {
                    JSONObject jsonarray = new JSONObject(strings);
                    if (jsonarray.getString("isRegister").equalsIgnoreCase("YES")){
                                 showAlertToUser(jsonarray.getString("calling_num"));

                    }
                    else{
                        new GetSms().execute();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    new GetSms().execute();
                }
            }
            else{
                new GetSms().execute();
            }

            // if(strings.contains("Successfully")){

           /* }
            else {
                AlertUtils.showToastLong(mContext,AppMessages.CommonSignInSignUpMessages.SOMETHING_WRONG);
            }*/



        }
    }
    public  String CheckUser(String mobile) {
        String smssu = otp;
        String fulladdress = null;
        try {
            String address = String
                    .format("http://ec2-52-66-129-148.ap-south-1.compute.amazonaws.com:8080/app_service/isRegister/"+mobile);
            URL googlePlaces;
            googlePlaces = new URL(address);
            URLConnection tc = googlePlaces.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            // take Google's legible JSON and turn it into one big
            // string.
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            fulladdress=sb.toString();
            LogUtils.debug(sb.toString());


        }
        catch (Exception e) {
            e.printStackTrace();

        }

        return fulladdress;
    }
    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void showetDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Internet is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable Internet",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_WIFI_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void showAlertToUser(final String call_num){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("This number is already register press GO HOME to go home or click CANCLE to register with diffrent number.")
                .setCancelable(false)
                .setPositiveButton("GO HOME",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.CALL_MOBILE,call_num);
                                activityCleanSwitcher(HomeActivity.class);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        mobilenumber.setText("");
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
