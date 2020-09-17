package ar.com.fennoma.paymentezsdk.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import ar.com.fennoma.paymentezsdk.activities.FirstActivity;
import ar.com.fennoma.paymentezsdk.activities.PaymentezPaymentCheckerActivity;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;

public class PaymentezSDK {

    private static PaymentezSDK instance;

    private Context context;

    private String secret;
    private String apiKey;

    private PmzSearchListener searchListener;
    private PmzPaymentCheckerListener paymentChecker;

    private Integer backgroundColor;
    private Integer textColor;
    private Integer buttonBackgroundColor;
    private Integer buttonTextColor;

    private PmzOrder order;

    public static void initialize(String apiKey, String secret) {
        PaymentezSDK instance = getInstance();
        instance.setApiKey(apiKey);
        instance.setSecret(secret);
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

    public Integer getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public PaymentezSDK setButtonBackgroundColor(Integer buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
        return this;
    }

    public Integer getButtonTextColor() {
        return buttonTextColor;
    }

    public PaymentezSDK setButtonTextColor(Integer buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
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

    public PaymentezSDK setContext(Context context) {
        this.context = context;
        return this;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void startSearch(PmzSearchListener listener) {
        if(isInitialized()) {
            this.searchListener = listener;
            Intent intent = new Intent(context, FirstActivity.class);
            context.startActivity(intent);
        }
    }

    private boolean isInitialized() {
        if(TextUtils.isEmpty(secret) || TextUtils.isEmpty(apiKey)) {
            throw new RuntimeException("PaymentezSDK not initialized");
        } else {
            return true;
        }
    }

    public void startPaymentChecking(PmzOrder order, PmzPaymentCheckerListener listener) {
        if(isInitialized()) {
            this.paymentChecker = listener;
            Intent intent = new Intent(context, PaymentezPaymentCheckerActivity.class);
            intent.putExtra(PaymentezPaymentCheckerActivity.PMZ_ORDER, order);
            context.startActivity(intent);
        }
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
}
