package com.example.purnendumishra.handler;

public class AppMessages {

    /*-------------------------------- Network Message -------------------------------*/
    public static final String CONNECT_NETWORK = "Please Check Your Network Connection";
    /*-------------------------------- Login Message -------------------------------*/
    public interface CommonSignInSignUpMessages
    {
        String NO_EMAIL = "Email is blank";
        String NO_PASSWORD = "Password is blank";
        String INVALID_EMAIL = "Invalid Email Address";
        String MIN_PASSWORD_CHECK = "Password must be min. 6 characters.";
        String INVALID_MOBILE="Invalid Mobile Number";
        String NO_MOBILE="Mobile is blank";
        String INVALID_OTP="Invalid OTP";
        String NO_OTP="OTP is blank";
        String INVALID_ADDRESS="Invalid Address";
        String INVALID_LANGUAGE="Please Select a Language";
        String INVALID_INTREST="Please Select a Intrestarea";
        String SOMETHING_WRONG="Something went wrong.";
    }
    /*-------------------------------- Signup Message -------------------------------*/

    public interface  SignUpMessages
    {
        String NO_FIRST_NAME = "Name is blank";
        String NO_LAST_NAME = "Last Name is blank";
        String N0_CHECK_BOX_TERMS_CONDITION="Please read and accept terms and condition before signup.";

    }

    public interface BundleValueSignIn
    {
        String EMAIL = "Email is blank";
        String length="email_length";
    }
    public interface BundleValueSignUp
    {
        String TERMSANDCONDITIONS_PRIVACYPOLICY = "TERMS_PRIVACY";
        String HEADERNAME="header_name";

    }


public interface Constants{
    String OTP="otp";
    String MOBILE="mobile";
    String CALL_MOBILE="call_mobile";


}

    /*	-------------------------------DownloadService-----------------------------------------*/


}

