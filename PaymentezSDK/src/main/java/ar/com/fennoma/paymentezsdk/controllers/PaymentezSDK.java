package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Context;
import android.text.TextUtils;

import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;

public class PaymentezSDK {

    private static PaymentezSDK instance;

    public static void initialize(String apiKey, String secret) {
        PaymentezSDK instance = getInstance();
        instance.setApiKey(apiKey);
        instance.setSecret(secret);
    }

    public interface PmzSearchListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onCancel();
    }

    public interface PmzPayAndPlaceListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onError(PmzOrder order, PmzError error);
    }

    public static PaymentezSDK getInstance() {
        if(instance == null) {
            instance = new PaymentezSDK();
        }
        return instance;
    }

    public void startSearch(Context context, Long storeId, PmzSearchListener listener) {
        if(isInitialized()) {
            checkContext(context);
            PmzData.getInstance().startSearch(context, storeId, listener);
        }
    }

    public void startSearch(Context context, PmzSearchListener listener) {
        if(isInitialized()) {
            checkContext(context);
            PmzData.getInstance().startSearch(context, null, listener);
        }
    }

    private boolean isInitialized() {
        if (TextUtils.isEmpty(PmzData.getInstance().getApiKey()) || TextUtils.isEmpty(PmzData.getInstance().getSecret())) {
            throw new RuntimeException("PaymentezSDK not initialized");
        } else {
            return true;
        }
    }

    public void startPayAndPlace(Context context, PmzOrder order, String paymentReference, PmzPayAndPlaceListener listener) {
        if(isInitialized()) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, order, paymentReference, listener);
        }
    }

    private boolean checkContext(Context context) {
        if(context == null) {
            throw new RuntimeException("PaymentezSDK has no context provided");
        } else {
            return true;
        }
    }

    public PaymentezSDK setButtonBackgroundColor(Integer buttonBackgroundColor) {
        PmzData.getInstance().setButtonBackgroundColor(buttonBackgroundColor);
        return this;
    }

    public PaymentezSDK setButtonTextColor(Integer buttonTextColor) {
        PmzData.getInstance().setButtonTextColor(buttonTextColor);
        return this;
    }

    public PaymentezSDK setTextColor(Integer textColor) {
        PmzData.getInstance().setTextColor(textColor);
        return this;
    }

    public PaymentezSDK setBackgroundColor(int color) {
        PmzData.getInstance().setBackgroundColor(color);
        return this;
    }

    public void setOrderResult(PmzOrder order) {
        PmzData.getInstance().setOrderResult(order);
    }

    public void setSecret(String secret) {
        PmzData.getInstance().setSecret(secret);
    }

    public void setApiKey(String apiKey) {
        PmzData.getInstance().setApiKey(apiKey);
    }
}
