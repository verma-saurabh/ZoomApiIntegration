package com.integration.ZoomApiIntegration.Constants;

public class Constants {
    public static final String clientId = "4_2R08mRkGyK8_NQFU_Vg";
    public static final String clientSecret = "RKpDuWBrkBtPPjTwBeJ9pa7JR30uLVHf";
    public static final String redirect_uri = "https://d6b4a645.ngrok.io/User/auth";

    static public class Zoom {
        public static final String zoomApiBaseUrl = "https://api.zoom.us/v2";
        public static final String zoomOathUrl = "https://zoom.us/oauth/token";
        public static final String getMeetings = "/users/me/meetings";
    }

}
