package com.example.purnendumishra.service;


/**
 * Project : Mobikasa Retrofit Lib
 * Author : Balwinder Singh Madaan
 * Creation Date : 25-july-2016
 */
public class Constants {
    static boolean isProduction = true;

    public interface WebConstants {
        String HOST_API = "http://cardamom-live.mobikasa.net/api/v1/";// Staging IP
        String DISPLAY_IMAGE = "http://cardamom-live.mobikasa.net";
        //String HOST_API = "";
    }

    public interface ServiceConstants {
        String SIGNUP = "/users/signupemail";
        String SIGNIN = "/users/signinemail";
        String FACEBOOKSIGNUP = "/users/facebooksignup";
        String FORGOTPASSWORD = "/users/forgotpassword";
        String UPLOADIMAGE = "/uploadimage";
    }

    public interface GeneralConstants {
        String IMAGE_STORE = "Image Capture";
        int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
        int RESULT_LOAD_IMAGE = 101;
    }

    public interface GCM_CONSTANTS {
        // Google Project Number
        String GOOGLE_PROJECT_ID = " ";
        String MESSAGE_KEY = "subtitle";
        String SUBJECT_KEY = "title";
        String REGISTER_AGAIN = "register_again";
        String FROM_NOTIFICATION = "from_notification";
    }

    public interface FacebookConstants {
        String FB_PIC_START_URL = "https://graph.facebook.com/";
        String FB_PIC_END_URL = "/picture?type=large";
    }
    public interface TermsAndConditionsPrivacyPolicy
    {
        String TERMSANDCONDITIONS="http://www.cardamomapp.com/#!terms-conditions/zcnpi";
        String PRIVACYPOLICY="http://www.cardamomapp.com/#!privacy-policy/sbwvp";
    }
}
