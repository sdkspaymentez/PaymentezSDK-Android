package ar.com.fennoma.paymentezsdk.presenter;

import android.content.Context;
import android.content.Intent;

import ar.com.fennoma.paymentezsdk.activities.FirstActivity;

public class PaymentezSDK {

    private static PaymentezSDK instance;

    private Context context;
    private IPanchoSDKListener listener;
    private Integer backgroundColor;
    private Integer textColor;
    private Integer actionBarColor;
    private Integer buttonBackgroundColor;

    private String token;

    public interface IPanchoSDKListener {
        void onFinishedSuccessfully();
        void onCancel();
    }

    public static PaymentezSDK getInstance() {
        if(instance == null) {
            instance = new PaymentezSDK();
        }
        return instance;
    }

    public Integer getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public PaymentezSDK setButtonBackgroundColor(Integer buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
        return this;
    }

    public Integer getActionBarColor() {
        return actionBarColor;
    }

    public PaymentezSDK setActionBarColor(Integer actionBarColor) {
        this.actionBarColor = actionBarColor;
        return this;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public PaymentezSDK setTextColor(Integer textColor) {
        this.textColor = textColor;
        return this;
    }

    public PaymentezSDK setBackgroundColor(int color) {
        backgroundColor = color;
        return this;
    }

    public PaymentezSDK setListener(IPanchoSDKListener listener) {
        this.listener = listener;
        return this;
    }

    public PaymentezSDK setContext(Context context) {
        this.context = context;
        return this;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void start() {
        Intent intent = new Intent(context, FirstActivity.class);
        context.startActivity(intent);
    }

    public void onCancel() {
        if(listener != null) {
            listener.onCancel();
        }
    }

    public void onSuccess() {
        if(listener != null) {
            listener.onFinishedSuccessfully();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
