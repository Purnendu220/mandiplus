package com.example.purnendumishra.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.purnendumishra.BaseActivity;
import com.example.purnendumishra.RefrenceWrapper;
import com.example.purnendumishra.handler.AlertUtils;
import com.example.purnendumishra.handler.AppMessages;
import com.example.purnendumishra.handler.AppSharedPreferences;
import com.example.purnendumishra.handler.DeviceUtils;
import com.example.purnendumishra.handler.GPSTracker;
import com.example.purnendumishra.handler.LogUtils;
import com.example.purnendumishra.handler.PlaceModel;
import com.example.purnendumishra.handler.StringUtils;
import com.example.purnendumishra.service.request.English;
import com.example.purnendumishra.service.request.Example;
import com.example.purnendumishra.service.request.Gujaratus;
import com.example.purnendumishra.service.request.Hindi;
import com.example.purnendumishra.service.request.RestLangList;
import com.example.purnendumishra.service.utils.JsonUtils;

import android.location.Address;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileCreationActivity extends BaseActivity implements View.OnClickListener {

    private RefrenceWrapper refrenceWrapper;
    private EditText editTextLanguage,editTextIntrest;
    AutoCompleteTextView editTextAddress;
    TextView app_title;
    private Button buttonRegister;
    private Context mContext;
    private String otpString,state="NA",selectedlag,selectedlagback,typ;
    private List<RestLangList> restLangList = new ArrayList<RestLangList>();
    private  LanguageListAdapter adpterlang;


    double latitude;
    double longitude;
    private final int REQUEST_CODE_SELECT_SUBJECT = 2;
    private final int REQUEST_CODE_SELECT_ITEM = 4;

    private String mSubjectIntent="SUBJECT";
    private String[] langlist= new String[200];
    private String[] itemlist= new String[200];
    private String[] itemlist1= new String[200];
    private String[] itemlist2= new String[200];
    private String[] itemlist3= new String[200];


    private String[]  langlistdefault={"HINDI", "ENGLISH", "GUJRATI","MARATHI","TELGU","KANNAD"};
    private String[]  itemlistdefault={"MANGO", "GRAPS", "BANANA","TOMATO","PUMPKIN","CABAGE","RED CHILLI","CLUSTER BEAN"};
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyDge-cfAuCx0DlvgBJaS7heD1m8hIeKoXI";
    private static final String LOG_TAG = "ExampleApp";
    StringBuilder selectedhoteltype=new StringBuilder();
    final ArrayList<Integer>    mSelectedItems = new ArrayList<Integer>();
    final ArrayList<String>    mSelectedItemsString= new ArrayList<String>();
    final ArrayList<String>    mSelectedItemsString1= new ArrayList<String>();
    final ArrayList<String>    mSelectedItemsString2= new ArrayList<String>();
    final ArrayList<String>    mSelectedItemsStringe= new ArrayList<String>();
    final ArrayList<String>    mSelectedItemsString1h= new ArrayList<String>();
    final ArrayList<String>    mSelectedItemsString2g= new ArrayList<String>();

    Example exmp;
   private boolean isGPSEnabled=false;
    boolean[] citem ={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
   String english="",hindi="",gujrati="";

    @Override
    public int getContentViewID() {
        return R.layout.activity_profile_creation;
    }

    @Override
    public void onCreateBase(Bundle savedInstanceState) {
        refrenceWrapper = RefrenceWrapper.getRefrenceWrapper(mContext);
        mContext=getApplicationContext();
        InitUi();
        setlistener();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            isGPSEnabled=true;
            if (DeviceUtils.isNetworkAvailable(mContext)) {
                new  GetAddress().execute();

            }
            else{
                showetDisabledAlertToUser();
            }
        }else{
            isGPSEnabled=false;
            if (DeviceUtils.isNetworkAvailable(mContext)) {
                new  GetAddress().execute();

            }
            else{
                showetDisabledAlertToUser();
            }
        }

    }
    private void InitUi(){
        editTextAddress=(AutoCompleteTextView)findViewById(R.id.editTextAddress);
        editTextLanguage=(EditText)findViewById(R.id.editTextLanguage);
        editTextIntrest=(EditText)findViewById(R.id.editTextIntrest);
        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        app_title=(TextView)findViewById(R.id.textView);
        editTextAddress.setThreshold(1);
        editTextAddress.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
    }
    private void setlistener(){
        editTextAddress.setOnClickListener(this);
        editTextLanguage.setOnClickListener(this);
        editTextIntrest.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);


        editTextAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() == 0) {
                    /*mRelativeLayoutBackground.setBackgroundColor(getResources().getColor(R.color.listview_back_google_api));
                    mListView.setVisibility(View.GONE);*/
                } else {
                   /* mListView.setVisibility(View.VISIBLE);
                    mListView.setBackgroundColor(getResources().getColor(R.color.white));*/
                    GetPlaces task = new GetPlaces();
                    String check = editTextAddress.getText().toString();
                    if (!StringUtils.isEmpty(check)) {
                        //task.execute(check);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editTextAddress:
               // Submit();
                break;
            case R.id.editTextLanguage:
                if (exmp!=null&&exmp.getRestLangList().size()>0){

                    if(selectedlagback.equalsIgnoreCase("english")){
                        english=editTextIntrest.getText().toString();
                    }
                    else if(selectedlagback.equalsIgnoreCase("hindi")){
                        hindi=editTextIntrest.getText().toString();
                    }
                    else if(selectedlagback.equalsIgnoreCase("gujarati")){
                        gujrati=editTextIntrest.getText().toString();
                    }
                    showDialog(1);
                }
                else {
                    AlertUtils.showToastLong(mContext,"No Language found for your location.");

                }

                // Submit();
                break;
            case R.id.editTextIntrest:
                if (exmp!=null&&exmp.getItemList().size()>0){

                    if(selectedlagback.equalsIgnoreCase("english")){
                        showMultiChoiceDialogWithSearchFilterUI(ProfileCreationActivity.this,exmp,R.string.app_name,null);
                    }
                    else if(selectedlagback.equalsIgnoreCase("hindi")){
                        showMultiChoiceDialogWithSearchFilterUI1(ProfileCreationActivity.this,exmp,R.string.app_name,null);
                    }
                    else if(selectedlagback.equalsIgnoreCase("gujarati")){
                        showMultiChoiceDialogWithSearchFilterUI2(ProfileCreationActivity.this,exmp,R.string.app_name,null);

                    }
                }
                else{
                    AlertUtils.showToastLong(mContext,"No intrest area found for you.");
                }

                // Submit();
                break;
            case R.id.buttonRegister:
                Register();
                break;

        }
    }

    private void Register(){

        if (StringUtils.isEmpty(editTextAddress.getText().toString())){
            AlertUtils.showToastLong(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_ADDRESS);
            return;
        }
        if(StringUtils.isEmpty(editTextLanguage.getText().toString())) {
            AlertUtils.showToastLong(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_LANGUAGE);
            return;
        }
        if(StringUtils.isEmpty(editTextIntrest.getText().toString())) {
            AlertUtils.showToastLong(mContext, AppMessages.CommonSignInSignUpMessages.INVALID_INTREST);
            return;
        }
        new Register().execute();

        
    }
    public  String getFromLocation(double lat, double lng, int maxResult) {
        String fulladdress = null;
        try {
            String address = String
                    .format(Locale.ENGLISH,
                            "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=false&language="
                                    + Locale.getDefault().getCountry(), lat, lng);
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
            List<Address> retList = null;


            JSONObject jsonObject = new JSONObject(sb.toString());

            LogUtils.debug("String " + jsonObject.toString());
            if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
                JSONArray results = jsonObject.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject result = results.getJSONObject(0);

                    fulladdress = result.getString("formatted_address");
                    JSONObject zero = results.getJSONObject(0);
                    JSONArray address_components = zero.getJSONArray("address_components");
                    for (int i = 0; i < address_components.length(); i++)
                    {
                        JSONObject zero2 = address_components.getJSONObject(i);
                        String long_name = zero2.getString("long_name");
                        JSONArray mtypes = zero2.getJSONArray("types");
                        String Type = mtypes.getString(0);
                        if (Type.equalsIgnoreCase("administrative_area_level_1"))
                        {
                            state = long_name.toLowerCase();


                            Log.e("current city name:","administrative_area_level_1:"+state);
                        }
                    }
                }

            }
        }
        catch (IOException e) {
            fulladdress = lat + "," + lng;

        } catch (JSONException e) {
            fulladdress = lat + "," + lng;

        }

        return fulladdress;
    }

    public  String getDetail(String state) {
        String fulladdress = null;
        try {
           //
            //
            // state ="Uttar Pradesh";
            //URLEncoder.encode(state, "utf8");
            String address;
            if(isGPSEnabled){
                 address = String.format("http://ec2-52-66-129-148.ap-south-1.compute.amazonaws.com:8080/app_service/language_item/"+URLEncoder.encode(state, "UTF-8"));

            }
            else{
                address = String.format("http://ec2-52-66-129-148.ap-south-1.compute.amazonaws.com:8080/app_service/default_item");

            }
            URL googlePlaces;
            Log.e("Tag",address);
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
            List<Address> retList = null;


          //  JSONArray jsonarray = new JSONArray(sb.toString());
           // if(jsonarray.length()>0){
                //selectedlag=object.getString("selected_language");
            Log.e("JSO RESPOSE",sb.toString());
            exmp=JsonUtils.fromJson(sb.toString(), Example.class);
            Log.e("JSO RESPOSE",base64Decode(exmp.getSelectedLang().getLangReal())+":"+base64Decode(exmp.getSelectedLang().getLangRealEng()));


            selectedlag=base64Decode(exmp.getSelectedLang().getLangReal());
            selectedlagback=base64Decode(exmp.getSelectedLang().getLangRealEng());
                   restLangList=exmp.getRestLangList();
            adpterlang=new LanguageListAdapter(getApplicationContext(),restLangList);
           List<Hindi> hindis= exmp.getItemList().get(0).getHindi();
            List<English> englishs= exmp.getItemList().get(1).getEnglish();
            List<Gujaratus> gujrati= exmp.getItemList().get(2).getGujarati();
            Log.e("JSO RESPOSE",base64Decode(gujrati.get(0).getLangReal())+":"+base64Decode(gujrati.get(0).getLangRealEng()));





     runOnUiThread(new Runnable() {
         @Override
         public void run() {

             if(selectedlagback.equalsIgnoreCase("english")){
                 editTextIntrest.setHint("Please select fruits and vegetables that you want to trade.");
                 buttonRegister.setText("Register");
                 app_title.setText(getResources().getString(R.string.app_name));
                 editTextAddress.setHint(getResources().getString(R.string.address_hint));
             }
             if(selectedlagback.equalsIgnoreCase("hindi")){
                 editTextIntrest.setHint("फलों और सब्जियों कि आप व्यापार करना चाहते हैं का चयन करें।");
                 buttonRegister.setText(getResources().getString(R.string.register_h));
                 app_title.setText(getResources().getString(R.string.app_name_hi));
                 editTextAddress.setHint(getResources().getString(R.string.address_hint_hi));
             }
             if(selectedlagback.equalsIgnoreCase("gujarati")){
                 editTextIntrest.setHint("ફળો અને શાકભાજી કે તમે વેપાર કરવા માંગો છો તે પસંદ કરો.");
                 buttonRegister.setText(getResources().getString(R.string.register_g));
                 app_title.setText(getResources().getString(R.string.app_name_gu));
                 editTextAddress.setHint(getResources().getString(R.string.address_hint_gu));
             }
             editTextLanguage.setText(selectedlag);
         }
     });
     //       }


        }
      catch (Exception e) {
           e.printStackTrace();

        }

        return "";
    }

    class GetAddress extends AsyncTask<String, Void, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogShow();
            GPSTracker gps = new GPSTracker(mContext);
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

        @Override
        protected String doInBackground(String... params) {
String address= "";
            try {
                address= getFromLocation(latitude,longitude,10);
                getDetail(state);

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
            editTextAddress.setText(strings);
            progressDialogDismiss();

        }
    }
    class GetPlaces extends AsyncTask<String, Void, ArrayList<PlaceModel>> {

        @Override
        protected ArrayList<PlaceModel> doInBackground(String... params) {
            ArrayList<PlaceModel> predictionsArr = new ArrayList<>();

            URL googlePlaces;
            try {
               // if (type.equals("1")) {
                    googlePlaces = new URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + URLEncoder.encode(params[0].toString(), "UTF-8") + "&types=address&language=en&sensor=true&key=AIzaSyDwe8LgB_vTGMvoPjZOfqTI6RNqJ54F_go");
               /* } else {
                    googlePlaces = new URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + URLEncoder.encode(params[0].toString(), "UTF-8") + "&types=(regions)&key=AIzaSyDwe8LgB_vTGMvoPjZOfqTI6RNqJ54F_go");
                }*/

                URLConnection tc = googlePlaces.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));

                String line;
                StringBuffer sb = new StringBuffer();
                // take Google's legible JSON and turn it into one big
                // string.
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                // turn that string into a JSON object
                JSONObject predictions = new JSONObject(sb.toString());
                LogUtils.debug(predictions.toString());
                // now get the JSON array that's inside that object
                JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    // add each entry to our array
                    LogUtils.debug("jo.getString(\"description\") " + jo.getString("description"));
                    PlaceModel placeModel = new PlaceModel();
                    placeModel.setDescription(jo.getString("description"));
                    placeModel.setId(jo.getString("place_id"));
                    predictionsArr.add(placeModel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // return all the predictions based on the typing the in the
            // search
            return predictionsArr;
        }

        @Override
        protected void onPostExecute(ArrayList<PlaceModel> strings) {
            super.onPostExecute(strings);
            LogUtils.debug("String " + strings);
          /*  if (strings != null && strings.size() > 0) {
                googlePlaceAdapter.clear();
                googlePlaceAdapter.setNearSchoolModelArrayList(strings);
                googlePlaceAdapter.notifyDataSetChanged();
            } else {
                googlePlaceAdapter.clear();
                googlePlaceAdapter.notifyDataSetChanged();
            }*/

        }
    }



    public  String[] getStringArray(JSONArray jsonArray) throws JSONException {
        String[] stringArray = null;
        int length = jsonArray.length();
        if(jsonArray!=null){
            stringArray = new String[length];
            for(int i=0;i<length;i++){
                stringArray[i]= base64Decode(jsonArray.getString(i));

            }
        }
        return stringArray;
    }
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent intent) {
        super.onActivityResult(arg0, arg1, intent);
        if (REQUEST_CODE_SELECT_SUBJECT == arg0) {
            if (RESULT_OK == arg1) {
                String mSubject = intent.getStringExtra(mSubjectIntent);
                editTextLanguage.setText(mSubject);
            }
        }
        if (REQUEST_CODE_SELECT_ITEM == arg0) {
            if (RESULT_OK == arg1) {
                String mSubject = intent.getStringExtra(mSubjectIntent);
                editTextIntrest.setText(mSubject);
            }
        }
    }
    class Register extends AsyncTask<String, Void, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogShow();

        }

        @Override
        protected String doInBackground(String... params) {
            String address= "";
            try {
                address= RegisterDetail();

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
if (!StringUtils.isEmpty(strings)&&strings.contains("9")){
    try {
        JSONObject predictions = new JSONObject(strings);
        LogUtils.debug(predictions.getString("calling_num"));
        AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.CALL_MOBILE,predictions.getString("calling_num"));
        AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.MSG,predictions.getString("message"));
        AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.LANG,selectedlagback);
        activityCleanSwitcher(HomeActivity.class);
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
            else{
   // AppSharedPreferences.getInstance(mContext).setPrefrence(AppMessages.Constants.CALL_MOBILE,"9911024648");
    //activityCleanSwitcher(HomeActivity.class);
   AlertUtils.showToastLong(mContext,"Failed to Register please try after sometime.Server may be busy.");
}
            progressDialogDismiss();

        }
    }

    public  String RegisterDetail() {
        String address1=editTextAddress.getText().toString();
        String lag=editTextLanguage.getText().toString();
        String item=editTextIntrest.getText().toString();
        String mobile= AppSharedPreferences.getInstance(mContext).getPrefrence(AppMessages.Constants.MOBILE);
        String otp= AppSharedPreferences.getInstance(mContext).getPrefrence(AppMessages.Constants.OTP);
        String deviceutil= DeviceUtils.getDeviceID(mContext);
        String fulladdress = null;
        try {
            String address = String
                    .format("http://ec2-52-66-129-148.ap-south-1.compute.amazonaws.com:8080/app_service/user_register/"+mobile+"/"+URLEncoder.encode(state,"UTF-8")+"/"+selectedlagback.toUpperCase()+"/"+base64Encode(item)+"/"+otp+"/"+deviceutil);
           Log.e("TAD",address);
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
fulladdress =sb.toString();


        }
        catch (Exception e) {
            e.printStackTrace();

        }

        return fulladdress;
    }


    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            //sb.append("&components=country:all");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
           // sb.append("&types=(cities)");
            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
    @Deprecated
    private Dialog Dingtop(final String title, final String[] list, final String type)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileCreationActivity.this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setAdapter(adpterlang, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (type.equalsIgnoreCase("1")){
                    editTextLanguage.setText(base64Decode(restLangList.get(which).getLangReal()));
                    selectedlagback=base64Decode(restLangList.get(which).getLangRealEng());
                    if(selectedlagback.equalsIgnoreCase("english")){
                        Log.e("TAG","Selected eglish");
                        app_title.setText(getResources().getString(R.string.app_name));
                        buttonRegister.setText("Register");
                        editTextIntrest.setHint("Please select fruits and vegetables that you want to trade.");
                        editTextIntrest.setText(english);
                        editTextAddress.setHint(getResources().getString(R.string.address_hint));
                        itemlist= new String[200];
                        itemlist=itemlist1;
                    }
                    else if(selectedlagback.equalsIgnoreCase("hindi")){
                        Log.e("TAG","Selected hindi");
                        editTextAddress.setHint(getResources().getString(R.string.address_hint_hi));
                        app_title.setText(getResources().getString(R.string.app_name_hi));
                        buttonRegister.setText(getResources().getString(R.string.register_h));
                        itemlist= new String[200];
                        itemlist=itemlist2;
                        editTextIntrest.setHint("फलों और सब्जियों कि आप व्यापार करना चाहते हैं का चयन करें।");
                        editTextIntrest.setText(hindi);
                    }
                    else if(selectedlagback.equalsIgnoreCase("gujarati")){
                        Log.e("TAG","Selected gujarati");
                        editTextAddress.setHint(getResources().getString(R.string.address_hint_gu));
                        app_title.setText(getResources().getString(R.string.app_name_gu));
                        buttonRegister.setText(getResources().getString(R.string.register_g));
                        itemlist= new String[200];
                        itemlist=itemlist3;
                        editTextIntrest.setHint("ફળો અને શાકભાજી કે તમે વેપાર કરવા માંગો છો તે પસંદ કરો.");
                        editTextIntrest.setText(gujrati);

                    }
                }
                if (type.equalsIgnoreCase("2")){

                }


            }
        });
        return builder.create();
    };
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case 1:
                return Dingtop("Language",langlist,"1");
            case 2:
                return Dingtop("Items",langlist,"1");
        }
        return null;
    }

    public void alertMultipleChoiceItems(){

        // where we will store or remove selected items


        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileCreationActivity.this);
        for(Integer i : mSelectedItems){
            System.out.println(itemlist[i]);
            citem[i]=true;
        }
        // set the dialog title
        builder.setTitle("Please select fruits and vegetables that you want to trade.")
                .setCancelable(false)
                // specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive call backs when items are selected
                // R.array.choices were set in the resources res/values/strings.xml
                .setMultiChoiceItems(itemlist, citem, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            // if the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        }

                        else if (mSelectedItems.contains(which)) {
                            // else if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }

                        // you can also add other codes here,
                        // for example a tool tip that gives user an idea of what he is selecting
                        // showToast("Just an example description.");
                    }

                })

                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // user clicked OK, so save the mSelectedItems results somewhere
                        // here we are trying to retrieve the selected items indices
                        String selectedIndex = "";
                        typ="";
                        selectedhoteltype=new StringBuilder();
                        if(mSelectedItems.size()>5)
                        {
                            showalert(1);

                        }
                        else{
                            for(Integer i : mSelectedItems){
                                selectedIndex += i + ", ";
                                System.out.println(itemlist[i]);

                                if(selectedhoteltype.toString().contains(itemlist[i]))
                                {
                                    System.out.println(itemlist[i]+"Already Exists " );

                                }
                                else
                                {
                                    selectedhoteltype.append(itemlist[i]+",");
                                    typ=selectedhoteltype.toString();
                                    //typ.append(items[i]+",");

                                }
                            }
                            editTextIntrest.setText(typ);
                        }
                        System.out.println("Selected index: " + typ);

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })

                .show();

    }
    public void showalert(final int type)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileCreationActivity.this);
        builder.setTitle("Sorry");
        String message_reject="You are authorized to select only five types of intrest you have selected more.Please select again.";

        builder.setMessage(message_reject).setCancelable(true);
        builder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //alertMultipleChoiceItems();
                if(type==1){
                showMultiChoiceDialogWithSearchFilterUI(ProfileCreationActivity.this,exmp,R.string.app_name,null);}
                if(type==1){
                    showMultiChoiceDialogWithSearchFilterUI1(ProfileCreationActivity.this,exmp,R.string.app_name,null);}
                if(type==2){
                    showMultiChoiceDialogWithSearchFilterUI2(ProfileCreationActivity.this,exmp,R.string.app_name,null);}


            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private static final class ListItemWithIndex {
        public final int index;
        public final String value;
        public final String valueEng;
        public final String valueEng1;


        public ListItemWithIndex(final int index, final String value,final String valueEng,final String valueEng1) {
            super();
            this.index = index;
            this.value = value;
            this.valueEng = valueEng;
            this.valueEng1= valueEng1;

        }

        @Override
        public String toString() {
            return value;
        }
    }

    public  void showMultiChoiceDialogWithSearchFilterUI(final Activity _activity, final Example exmp,
                                                               final int _titleResId, final View.OnClickListener _itemSelectionListener) {

        final ArrayList<String> mSelectedItemsStringLocal= new ArrayList<String>();

        final ListView listView = new ListView(_activity);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity);

        final List<ListItemWithIndex> allItems = new ArrayList<ListItemWithIndex>();
        final List<ListItemWithIndex> filteredItems = new ArrayList<ListItemWithIndex>();
        List<English> englishs= exmp.getItemList().get(1).getEnglish();


        for (int i = 0; i < englishs.size(); i++) {
            final English obj = englishs.get(i);
            final ListItemWithIndex listItemWithIndex = new ListItemWithIndex(i, base64Decode(obj.getLangReal()),base64Decode(obj.getLangRealEng()),base64Decode(obj.getLangEng()));
            allItems.add(listItemWithIndex);
            filteredItems.add(listItemWithIndex);
        }

        dialogBuilder.setTitle("Please select fruits and vegetables that you want to trade.");
        dialogBuilder.setCancelable(false);
        final ArrayAdapter<ListItemWithIndex> objectsAdapter = new ArrayAdapter<ListItemWithIndex>(_activity,
                android.R.layout.simple_list_item_multiple_choice, filteredItems) {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @SuppressWarnings("unchecked")
                    @Override
                    protected void publishResults(final CharSequence constraint, final FilterResults results) {
                        filteredItems.clear();
                        filteredItems.addAll((List<ListItemWithIndex>) results.values);
                        notifyDataSetChanged();

                        for(int i = 0;i<filteredItems.size();i++){
                            listView.setItemChecked( i, citem[i]);
                            for (int j=0;j<mSelectedItemsString.size();j++){
                                if (mSelectedItemsString.get(j).equalsIgnoreCase(filteredItems.get(i).valueEng)){
                                    listView.setItemChecked( i, true);
                                }

                            }
                        }

/*
                        for (int i=0;i<filteredItems.size();i++){
                            for (int j=0;j<mSelectedItemsString.size();j++){
                                if (mSelectedItemsString.get(j).equalsIgnoreCase(filteredItems.get(i).value)){
                                    listView.setItemChecked( i, true);
                                } else{
                                }

                            }
                        }*/
                    }

                    @Override
                    protected FilterResults performFiltering(final CharSequence constraint) {
                        final FilterResults results = new FilterResults();

                        final String filterString = constraint.toString();
                        final ArrayList<ListItemWithIndex> list = new ArrayList<ListItemWithIndex>();
                        for (final ListItemWithIndex obj : allItems) {
                            final String objStr = obj.value;
                            final String objStr1=obj.valueEng;
                            if ("".equals(filterString)
                                    || objStr.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))||objStr1.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))) {
                                list.add(obj);

                            }
                            Log.e("TAG","list"+allItems.get(0));
                        }

                        results.values = list;
                        results.count = list.size();
                        return results;
                    }
                };
            }
        };

        final EditText searchEditText = new EditText(_activity);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence arg0, final int arg1, final int arg2, final int arg3) {
            }

            @Override
            public void beforeTextChanged(final CharSequence arg0, final int arg1, final int arg2, final int arg3) {
            }

            @Override
            public void afterTextChanged(final Editable arg0) {
                objectsAdapter.getFilter().filter(searchEditText.getText());
            }
        });


        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        final Button cancel=new Button(_activity);
        final Button Ok=new Button(_activity);
        cancel.setText("Cancel");
        Ok.setText("Ok");
        cancel.setBackgroundColor(Color.TRANSPARENT);
        Ok.setBackgroundColor(Color.TRANSPARENT);
        cancel.setTextColor(getResources().getColor(R.color.app_green));
        Ok.setTextColor(getResources().getColor(R.color.app_green));
        listView.setAdapter(objectsAdapter);
        for(int i = 0;i<filteredItems.size();i++){
            listView.setItemChecked( i, citem[i]);
            for (int j=0;j<mSelectedItemsString.size();j++){
                if (mSelectedItemsString.get(j).equalsIgnoreCase(filteredItems.get(i).value)){
                    listView.setItemChecked( i, true);
                }

            }
        }
        final LinearLayout linearLayout = new LinearLayout(_activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(searchEditText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        linearLayout.addView(listView, layoutParams);
        final LinearLayout linearLayout1 = new LinearLayout(_activity);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        final LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.RIGHT;
        linearLayout1.addView(cancel, layoutParams1);
        linearLayout1.addView(Ok, layoutParams1);
        linearLayout.addView(linearLayout1, layoutParams1);


        dialogBuilder.setView(linearLayout);

        final AlertDialog dialog = dialogBuilder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                //dialog.dismiss();
               /// _itemSelectionListener.onClick(null, filteredItems.get(position).index);
                Log.e("TAG","list"+filteredItems.get(position).index);
                for (int i=0;i<mSelectedItemsString.size();i++){
                    if (mSelectedItemsString.get(i).equalsIgnoreCase(filteredItems.get(position).value)){
                        mSelectedItemsString.remove(filteredItems.get(position).value);
                        mSelectedItemsStringLocal.remove(filteredItems.get(position).value);

                        return;
                    }

                }
                mSelectedItemsString.add(filteredItems.get(position).value);
                mSelectedItemsStringLocal.add(filteredItems.get(position).value);

                Log.e("TAG","list"+filteredItems.get(position).index);
            }
        });
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                selectedhoteltype=new StringBuilder();
                if(mSelectedItemsString.size()>5){
                    showalert(1);
                    return;
                }
                for (int i=0;i<mSelectedItemsString.size();i++){
                    selectedhoteltype.append(mSelectedItemsString.get(i)+",");
                }
                editTextIntrest.setText(selectedhoteltype.toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               // selectedhoteltype=new StringBuilder();
                for (int i=0;i<mSelectedItemsStringLocal.size();i++){

                    for (int j=0;j<mSelectedItemsString.size();j++){
                        if(mSelectedItemsStringLocal.get(i).equalsIgnoreCase(mSelectedItemsString.get(j))){
                            mSelectedItemsString.remove(mSelectedItemsString.get(j));

                        }


                    }


                }
               // editTextIntrest.setText(selectedhoteltype.toString());
            }
        });
        dialog.show();
    }
    public  void showMultiChoiceDialogWithSearchFilterUI1(final Activity _activity, final Example exmp,
                                                         final int _titleResId, final View.OnClickListener _itemSelectionListener) {

        final ArrayList<String> mSelectedItemsStringLocal= new ArrayList<String>();

        final ListView listView = new ListView(_activity);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity);

        final List<ListItemWithIndex> allItems = new ArrayList<ListItemWithIndex>();
        final List<ListItemWithIndex> filteredItems = new ArrayList<ListItemWithIndex>();
        List<Hindi> hindis= exmp.getItemList().get(0).getHindi();
        for (int i = 0; i < hindis.size(); i++) {
            final Hindi obj = hindis.get(i);
            final ListItemWithIndex listItemWithIndex = new ListItemWithIndex(i, base64Decode(obj.getLangReal()),base64Decode(obj.getLangRealEng()),base64Decode(obj.getLangEng()));
            allItems.add(listItemWithIndex);
            filteredItems.add(listItemWithIndex);
        }

        dialogBuilder.setTitle("फलों और सब्जियों कि आप व्यापार करना चाहते हैं का चयन करें।");
        dialogBuilder.setCancelable(false);
        final ArrayAdapter<ListItemWithIndex> objectsAdapter = new ArrayAdapter<ListItemWithIndex>(_activity,
                android.R.layout.simple_list_item_multiple_choice, filteredItems) {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @SuppressWarnings("unchecked")
                    @Override
                    protected void publishResults(final CharSequence constraint, final FilterResults results) {
                        filteredItems.clear();
                        filteredItems.addAll((List<ListItemWithIndex>) results.values);
                        notifyDataSetChanged();

                        for(int i = 0;i<filteredItems.size();i++){
                            listView.setItemChecked( i, citem[i]);
                            for (int j=0;j<mSelectedItemsString1.size();j++){
                                if (mSelectedItemsString1.get(j).equalsIgnoreCase(filteredItems.get(i).valueEng)){
                                    listView.setItemChecked( i, true);
                                }

                            }
                        }

/*
                        for (int i=0;i<filteredItems.size();i++){
                            for (int j=0;j<mSelectedItemsString.size();j++){
                                if (mSelectedItemsString.get(j).equalsIgnoreCase(filteredItems.get(i).value)){
                                    listView.setItemChecked( i, true);
                                } else{
                                }

                            }
                        }*/
                    }

                    @Override
                    protected FilterResults performFiltering(final CharSequence constraint) {
                        final FilterResults results = new FilterResults();

                        final String filterString = constraint.toString();
                        final ArrayList<ListItemWithIndex> list = new ArrayList<ListItemWithIndex>();
                        for (final ListItemWithIndex obj : allItems) {
                            final String objStr = obj.value;
                            final String objStr1=obj.valueEng;
                            final String objStr2=obj.valueEng1;
                            if ("".equals(filterString)
                                    || objStr.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))||objStr1.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))||objStr2.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))) {
                                list.add(obj);

                            }
                            Log.e("TAG","list"+allItems.get(0));
                        }

                        results.values = list;
                        results.count = list.size();
                        return results;
                    }
                };
            }
        };

        final EditText searchEditText = new EditText(_activity);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence arg0, final int arg1, final int arg2, final int arg3) {
            }

            @Override
            public void beforeTextChanged(final CharSequence arg0, final int arg1, final int arg2, final int arg3) {
            }

            @Override
            public void afterTextChanged(final Editable arg0) {
                objectsAdapter.getFilter().filter(searchEditText.getText());
            }
        });


        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        final Button cancel=new Button(_activity);
        final Button Ok=new Button(_activity);
        cancel.setText("रद्द करना");
        Ok.setText("ठीक");
        cancel.setBackgroundColor(Color.TRANSPARENT);
        Ok.setBackgroundColor(Color.TRANSPARENT);
        cancel.setTextColor(getResources().getColor(R.color.app_green));
        Ok.setTextColor(getResources().getColor(R.color.app_green));
        listView.setAdapter(objectsAdapter);
        for(int i = 0;i<filteredItems.size();i++){
            listView.setItemChecked( i, citem[i]);
            for (int j=0;j<mSelectedItemsString1.size();j++){
                if (mSelectedItemsString1.get(j).equalsIgnoreCase(filteredItems.get(i).valueEng)){
                    listView.setItemChecked( i, true);
                }

            }
        }
        final LinearLayout linearLayout = new LinearLayout(_activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(searchEditText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        linearLayout.addView(listView, layoutParams);
        final LinearLayout linearLayout1 = new LinearLayout(_activity);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        final LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.RIGHT;
        linearLayout1.addView(cancel, layoutParams1);
        linearLayout1.addView(Ok, layoutParams1);
        linearLayout.addView(linearLayout1, layoutParams1);


        dialogBuilder.setView(linearLayout);

        final AlertDialog dialog = dialogBuilder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                //dialog.dismiss();
                /// _itemSelectionListener.onClick(null, filteredItems.get(position).index);
                Log.e("TAG","list"+filteredItems.get(position).index);
                for (int i=0;i<mSelectedItemsString1.size();i++){
                    if (mSelectedItemsString1.get(i).equalsIgnoreCase(filteredItems.get(position).valueEng)){
                        mSelectedItemsString1.remove(filteredItems.get(position).valueEng);
                        mSelectedItemsStringLocal.remove(filteredItems.get(position).valueEng);
                        mSelectedItemsString1h.remove(filteredItems.get(position).value);

                        return;
                    }

                }
                mSelectedItemsString1.add(filteredItems.get(position).valueEng);
                mSelectedItemsStringLocal.add(filteredItems.get(position).valueEng);
                mSelectedItemsString1h.add(filteredItems.get(position).value);

                Log.e("TAG","list"+filteredItems.get(position).index);
            }
        });
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                selectedhoteltype=new StringBuilder();
                if(mSelectedItemsString1h.size()>5){
                    showalert(2);
                    return;
                }
                for (int i=0;i<mSelectedItemsString1h.size();i++){
                    selectedhoteltype.append(mSelectedItemsString1h.get(i)+",");
                }
                editTextIntrest.setText(selectedhoteltype.toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                // selectedhoteltype=new StringBuilder();
                for (int i=0;i<mSelectedItemsStringLocal.size();i++){

                    for (int j=0;j<mSelectedItemsString1.size();j++){
                        if(mSelectedItemsStringLocal.get(i).equalsIgnoreCase(mSelectedItemsString1.get(j))){
                            mSelectedItemsString1.remove(mSelectedItemsString1.get(j));
                            mSelectedItemsString1h.remove(j);
                        }


                    }


                }
                // editTextIntrest.setText(selectedhoteltype.toString());
            }
        });
        dialog.show();
    }
    public  void showMultiChoiceDialogWithSearchFilterUI2(final Activity _activity, final Example exmp,
                                                          final int _titleResId, final View.OnClickListener _itemSelectionListener) {

        final ArrayList<String> mSelectedItemsStringLocal= new ArrayList<String>();

        final ListView listView = new ListView(_activity);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity);

        final List<ListItemWithIndex> allItems = new ArrayList<ListItemWithIndex>();
        final List<ListItemWithIndex> filteredItems = new ArrayList<ListItemWithIndex>();
        List<Gujaratus> gujrati= exmp.getItemList().get(2).getGujarati();
        for (int i = 0; i < gujrati.size(); i++) {
            final Gujaratus obj = gujrati.get(i);
            final ListItemWithIndex listItemWithIndex = new ListItemWithIndex(i, base64Decode(obj.getLangReal()),base64Decode(obj.getLangRealEng()),base64Decode(obj.getLangEng()));
            allItems.add(listItemWithIndex);
            filteredItems.add(listItemWithIndex);
        }

        dialogBuilder.setTitle("ફળો અને શાકભાજી કે તમે વેપાર કરવા માંગો છો તે પસંદ કરો.");
        dialogBuilder.setCancelable(false);
        final ArrayAdapter<ListItemWithIndex> objectsAdapter = new ArrayAdapter<ListItemWithIndex>(_activity,
                android.R.layout.simple_list_item_multiple_choice, filteredItems) {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @SuppressWarnings("unchecked")
                    @Override
                    protected void publishResults(final CharSequence constraint, final FilterResults results) {
                        filteredItems.clear();
                        filteredItems.addAll((List<ListItemWithIndex>) results.values);
                        notifyDataSetChanged();

                        for(int i = 0;i<filteredItems.size();i++){
                            listView.setItemChecked( i, citem[i]);
                            for (int j=0;j<mSelectedItemsString2.size();j++){
                                if (mSelectedItemsString2.get(j).equalsIgnoreCase(filteredItems.get(i).valueEng)){
                                    listView.setItemChecked( i, true);
                                }

                            }
                        }


                    }

                    @Override
                    protected FilterResults performFiltering(final CharSequence constraint) {
                        final FilterResults results = new FilterResults();

                        final String filterString = constraint.toString();
                        final ArrayList<ListItemWithIndex> list = new ArrayList<ListItemWithIndex>();
                        for (final ListItemWithIndex obj : allItems) {
                            final String objStr = obj.value;
                            final String objStr1=obj.valueEng;
                            final String objStr2=obj.valueEng1;
                            if ("".equals(filterString)
                                    || objStr.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))||objStr1.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))||objStr2.toLowerCase(Locale.getDefault()).contains(
                                    filterString.toLowerCase(Locale.getDefault()))) {
                                list.add(obj);

                            }
                            Log.e("TAG","list"+allItems.get(0));
                        }

                        results.values = list;
                        results.count = list.size();
                        return results;
                    }
                };
            }
        };

        final EditText searchEditText = new EditText(_activity);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence arg0, final int arg1, final int arg2, final int arg3) {
            }

            @Override
            public void beforeTextChanged(final CharSequence arg0, final int arg1, final int arg2, final int arg3) {
            }

            @Override
            public void afterTextChanged(final Editable arg0) {
                objectsAdapter.getFilter().filter(searchEditText.getText());
            }
        });


        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        final Button cancel=new Button(_activity);
        final Button Ok=new Button(_activity);
        cancel.setText("રદ કરો");
        Ok.setText("ઠીક છે");
        cancel.setBackgroundColor(Color.TRANSPARENT);
        Ok.setBackgroundColor(Color.TRANSPARENT);
        cancel.setTextColor(getResources().getColor(R.color.app_green));
        Ok.setTextColor(getResources().getColor(R.color.app_green));
        listView.setAdapter(objectsAdapter);
        for(int i = 0;i<filteredItems.size();i++){
            listView.setItemChecked( i, citem[i]);
            for (int j=0;j<mSelectedItemsString2.size();j++){
                if (mSelectedItemsString2.get(j).equalsIgnoreCase(filteredItems.get(i).valueEng)){
                    listView.setItemChecked( i, true);
                }

            }
        }
        final LinearLayout linearLayout = new LinearLayout(_activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(searchEditText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        linearLayout.addView(listView, layoutParams);
        final LinearLayout linearLayout1 = new LinearLayout(_activity);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        final LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.RIGHT;
        linearLayout1.addView(cancel, layoutParams1);
        linearLayout1.addView(Ok, layoutParams1);
        linearLayout.addView(linearLayout1, layoutParams1);


        dialogBuilder.setView(linearLayout);

        final AlertDialog dialog = dialogBuilder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                //dialog.dismiss();
                /// _itemSelectionListener.onClick(null, filteredItems.get(position).index);
                Log.e("TAG","list"+filteredItems.get(position).index);
                for (int i=0;i<mSelectedItemsString2.size();i++){
                    if (mSelectedItemsString2.get(i).equalsIgnoreCase(filteredItems.get(position).valueEng)){
                        mSelectedItemsString2.remove(filteredItems.get(position).valueEng);
                        mSelectedItemsString2g.remove(filteredItems.get(position).value);

                        mSelectedItemsStringLocal.remove(filteredItems.get(position).valueEng);

                        return;
                    }

                }
                mSelectedItemsString2.add(filteredItems.get(position).valueEng);
                mSelectedItemsString2g.add(filteredItems.get(position).value);
                mSelectedItemsStringLocal.add(filteredItems.get(position).valueEng);

                Log.e("TAG","list"+filteredItems.get(position).index);
            }
        });
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                selectedhoteltype=new StringBuilder();
                if(mSelectedItemsString2.size()>5){
                    showalert(3);
                    return;
                }
                for (int i=0;i<mSelectedItemsString2g.size();i++){
                    selectedhoteltype.append(mSelectedItemsString2g.get(i)+",");
                }
                editTextIntrest.setText(selectedhoteltype.toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                // selectedhoteltype=new StringBuilder();
                for (int i=0;i<mSelectedItemsStringLocal.size();i++){

                    for (int j=0;j<mSelectedItemsString2.size();j++){
                        if(mSelectedItemsStringLocal.get(i).equalsIgnoreCase(mSelectedItemsString2.get(j))){
                            mSelectedItemsString2.remove(mSelectedItemsString2.get(j));
                            mSelectedItemsString2g.remove(j);


                        }


                    }


                }
                // editTextIntrest.setText(selectedhoteltype.toString());
            }
        });
        dialog.show();
    }

    public  String base64Decode(String token) {
        String text="";
        byte[] data = Base64.decode(token, Base64.DEFAULT);

        try {
             text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public  String base64Encode(String token) {
        byte[] data=null;
        try{
      data = token.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        return base64.replaceAll("\\s+","");
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
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
                .setPositiveButton("Enable Internet",
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
    @Override
    protected void onResume() {
        super.onResume();

    }
}
