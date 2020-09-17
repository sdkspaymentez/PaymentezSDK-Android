package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Context;
import android.content.Intent;

import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;

class PmzData {

    private static PmzData instance;

    private Context context;

    private String secret;
    private String apiKey;

    private PaymentezSDK.PmzSearchListener searchListener;
    private PaymentezSDK.PmzPaymentCheckerListener paymentChecker;

    private Integer backgroundColor;
    private Integer textColor;
    private Integer buttonBackgroundColor;
    private Integer buttonTextColor;

    private PmzOrder order;

    public static PmzData getInstance() {
        if(instance == null) {
            instance = new PmzData();
        }
        return instance;
    }

    private PmzData() {}

    public Integer getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public void setButtonBackgroundColor(Integer buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
    }

    public Integer getButtonTextColor() {
        return buttonTextColor;
    }

    public void setButtonTextColor(Integer buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public void setTextColor(Integer textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(int color) {
        backgroundColor = color;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void startSearch(PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        Intent intent = new Intent(context, FirstActivity.class);
        context.startActivity(intent);
    }

    public void startPaymentChecking(PmzOrder order, PaymentezSDK.PmzPaymentCheckerListener listener) {
        this.paymentChecker = listener;
        Intent intent = new Intent(context, PaymentezPaymentCheckerActivity.class);
        intent.putExtra(PaymentezPaymentCheckerActivity.PMZ_ORDER, order);
        context.startActivity(intent);
    }

    public void onSearchCancel() {
        if(searchListener != null) {
            searchListener.onCancel();
        }
    }

    public void onSearchSuccess() {
        if(searchListener != null) {
            searchListener.onFinishedSuccessfully(order);
        }
    }

    public void onPaymentCheckingError(PmzOrder order, PmzError error) {
        if(paymentChecker != null) {
            paymentChecker.onError(order, error);
        }
    }

    public void onPaymentCheckingSuccess(PmzOrder order) {
        if(paymentChecker != null) {
            paymentChecker.onFinishedSuccessfully(order);
        }
    }

    public void setOrderResult(PmzOrder order) {
        this.order = order;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Context getContext() {
        return context;
    }
}
