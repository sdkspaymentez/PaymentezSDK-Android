package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Context;

import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzSession;

public class PaymentezSDK {

    private static PaymentezSDK instance;

    public static void initialize(String appCode, String appKey) {
        PmzSession session = new PmzSession(appCode, appKey);
        PmzData.getInstance().setSession(session);
    }

    public void setToken(String token) {
        PmzData.getInstance().setToken(token);
    }

    public interface PmzSearchListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onCancel();
    }

    public interface PmzPaymentCheckerListener {
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

    public PmzSession getSession() {
        if(isInitialized()) {
            return PmzData.getInstance().getSession();
        } else {
            return null;
        }
    }

    private boolean isInitialized() {
        if (!PmzData.getInstance().isSessionValid()) {
            throw new RuntimeException("PaymentezSDK not initialized");
        } else {
            return true;
        }
    }

    public void startPaymentChecking(Context context, PmzOrder order, PmzPaymentCheckerListener listener) {
        if(isInitialized()) {
            checkContext(context);
            PmzData.getInstance().startPaymentChecking(context, order, listener);
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

    public String getToken() {
        return PmzData.getInstance().getToken();
    }

    public void setOrderResult(PmzOrder order) {
        PmzData.getInstance().setOrderResult(order);
    }
}
