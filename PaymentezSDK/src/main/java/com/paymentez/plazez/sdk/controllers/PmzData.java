package com.paymentez.plazez.sdk.controllers;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.paymentez.plazez.sdk.models.PmzBuyer;
import com.paymentez.plazez.sdk.models.PmzError;
import com.paymentez.plazez.sdk.models.PmzOrder;
import com.paymentez.plazez.sdk.models.PmzPaymentData;
import com.paymentez.plazez.sdk.models.PmzStore;
import com.paymentez.plazez.sdk.models.PmzStyle;

class PmzData {

    private static PmzData instance;

    private String secret;
    private String apiKey;

    private PaymentezSDK.PmzSearchListener searchListener;
    private PaymentezSDK.PmzPayAndPlaceListener paymentChecker;
    private PaymentezSDK.PmzPayAndPlaceMultipleOrderListener paymentMultipleOrdersChecker;

    private PmzStyle style;

    private PmzOrder order;
    private List<PmzOrder> orders;

    public static PmzData getInstance() {
        if(instance == null) {
            instance = new PmzData();
            instance.setStyle(new PmzStyle());
        }
        return instance;
    }

    private PmzData() {}

    public void startSearchWithStoreId(Context context, PmzBuyer buyer, String appOrderReference, Long storeId, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        Intent intent;
        if(storeId != null) {
            intent = new Intent(context, PmzMenuActivity.class);
            intent.putExtra(PmzMenuActivity.STORE_ID, storeId);
            intent.putExtra(PmzMenuActivity.FORCED_ID, true);
        } else {
            intent = new Intent(context, PmzStoresActivity.class);
        }
        context.startActivity(intent);
    }

    public void startSearch(Context context, PmzBuyer buyer, String appOrderReference, String searchStoresFilter, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        Intent intent = new Intent(context, PmzStoresActivity.class);
        if(!TextUtils.isEmpty(searchStoresFilter)) {
            intent.putExtra(PmzStoresActivity.SEARCH_STORES_FILTER, searchStoresFilter);
        }
        context.startActivity(intent);
    }

    public void showSummary(Context context, String appOrderReference, PmzOrder order, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        Intent intent = new Intent(context, PmzSummaryActivity.class);
        intent.putExtra(PmzSummaryActivity.SHOW_SUMMARY, true);
        intent.putExtra(PmzSummaryActivity.PMZ_ORDER, order);
        context.startActivity(intent);
    }

    public void startPayAndPlace(Context context, PmzOrder order, PmzPaymentData paymentData, boolean skipSummary, PaymentezSDK.PmzPayAndPlaceListener listener) {
        this.paymentChecker = listener;
        Intent intent = new Intent(context, PmzPayAndPlaceActivity.class);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_ORDER, order);
        intent.putExtra(PmzPayAndPlaceActivity.SKIP_SUMMARY, skipSummary);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_PAYMENT_DATA, paymentData);
        context.startActivity(intent);
    }

    public void startPayAndPlace(Context context, List<PmzOrder> orders, PmzPaymentData paymentData, boolean skipSummary, PaymentezSDK.PmzPayAndPlaceListener listener) {
        this.paymentChecker = listener;
        Intent intent = new Intent(context, PmzPayAndPlaceActivity.class);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_ORDERS, new ArrayList<>(orders));
        intent.putExtra(PmzPayAndPlaceActivity.SKIP_SUMMARY, skipSummary);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_PAYMENT_DATA, paymentData);
        context.startActivity(intent);
    }

    public void getStores(String filter, PaymentezSDK.PmzStoresListener listener) {
        listener.onFinishedSuccessfully(PmzStore.getHardcoded());
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

    public void onPaymentMultipleOrdersCheckingError(List<PmzOrder> orders, PmzError error) {
        if(paymentMultipleOrdersChecker != null) {
            paymentMultipleOrdersChecker.onError(orders, error);
        }
    }

    public void onPaymentCheckingSuccess(PmzOrder order) {
        if(paymentChecker != null) {
            paymentChecker.onFinishedSuccessfully(order);
        }
    }

    public void onPaymentCheckingSuccess(List<PmzOrder> orders) {
        if(paymentMultipleOrdersChecker != null) {
            paymentMultipleOrdersChecker.onFinishedSuccessfully(orders);
        }
    }

    public void setOrderResult(PmzOrder order) {
        this.order = order;
    }

    public void setOrderResult(List<PmzOrder> orders) {
        this.orders = orders;
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

    public PmzStyle getStyle() {
        return style;
    }

    public void setStyle(PmzStyle style) {
        this.style = style;
    }
}
