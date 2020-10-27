package com.paymentez.plazez.sdktester;

import android.app.Application;

import com.paymentez.plazez.sdk.controllers.PaymentezSDK;

public class PmzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaymentezSDK.initialize("api_key", "secret");
    }
}
