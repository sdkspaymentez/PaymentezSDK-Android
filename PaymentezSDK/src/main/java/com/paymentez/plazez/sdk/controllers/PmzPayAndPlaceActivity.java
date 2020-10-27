package com.paymentez.plazez.sdk.controllers;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import com.paymentez.plazez.sdk.R;
import com.paymentez.plazez.sdk.models.PmzError;
import com.paymentez.plazez.sdk.models.PmzOrder;
import com.paymentez.plazez.sdk.models.PmzPaymentData;

public class PmzPayAndPlaceActivity extends PmzBaseActivity {

    public static final String PMZ_ORDER = "pmz order";
    public static final String PMZ_PAYMENT_DATA = "pmz payment data";
    public static final String SKIP_SUMMARY = "skip summary";
    public static final String PMZ_ORDERS = "pmz order array";

    private PmzOrder order;
    private List<PmzOrder> orderList;
    private PmzPaymentData paymentData;
    private boolean skipSummary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_pay_and_place_activity);
        setFullTitleWOBack(getString(R.string.activity_pmz_pay_and_place_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ORDER) != null
                && getIntent().getParcelableExtra(PMZ_PAYMENT_DATA) != null) {
            this.order = getIntent().getParcelableExtra(PMZ_ORDER);
            this.orderList = getIntent().getParcelableExtra(PMZ_ORDERS);
            this.paymentData = getIntent().getParcelableExtra(PMZ_PAYMENT_DATA);
            this.skipSummary = getIntent().getBooleanExtra(SKIP_SUMMARY, false);
            setButtons();
        } else {
            finish();
            animActivityLeftToRight();
            PmzData.getInstance().onPaymentCheckingError(null, new PmzError(PmzError.NO_ORDER_SET_ERROR));
        }
    }

    private void setViews() {
        if(PmzData.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PmzData.getInstance().getStyle().getBackgroundColor());
        }
        if(PmzData.getInstance().getStyle().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PmzData.getInstance().getStyle().getTextColor());
            ProgressBar progress = findViewById(R.id.progress);
            progress.getIndeterminateDrawable().setColorFilter(PmzData.getInstance().getStyle().getTextColor(), PorterDuff.Mode.SRC_IN);
        }
        if(PmzData.getInstance().getStyle().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.payment_error));
                replaceRippleBackgroundColor(findViewById(R.id.place_error));
                replaceRippleBackgroundColor(findViewById(R.id.success));
            }
            changeToolbarBackground(PmzData.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PmzData.getInstance().getStyle().getButtonTextColor() != null) {
            TextView paymentError = findViewById(R.id.payment_error);
            paymentError.setTextColor(PmzData.getInstance().getStyle().getButtonTextColor());
            TextView placeError = findViewById(R.id.place_error);
            placeError.setTextColor(PmzData.getInstance().getStyle().getButtonTextColor());
            TextView success = findViewById(R.id.success);
            success.setTextColor(PmzData.getInstance().getStyle().getButtonTextColor());
            changeToolbarTextColor(PmzData.getInstance().getStyle().getButtonTextColor());
        }
    }

    private void setButtons() {
        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(skipSummary) {
                    finish();
                    if(order != null) {
                        PmzData.getInstance().onPaymentCheckingSuccess(order);
                    } else if(orderList != null) {
                        PmzData.getInstance().onPaymentCheckingSuccess(orderList);
                    }
                } else {
                    Intent intent = new Intent(PmzPayAndPlaceActivity.this, PmzResultActivity.class);
                    intent.putExtra(PmzResultActivity.PMZ_ORDER, order);
                    startActivity(intent);
                    animActivityRightToLeft();
                    finish();
                }
            }
        });
        findViewById(R.id.place_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(order != null) {
                    PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PLACE_ERROR));
                } else if(orderList != null) {
                    PmzData.getInstance().onPaymentMultipleOrdersCheckingError(orderList, new PmzError(PmzError.PLACE_ERROR));
                }
            }
        });
        findViewById(R.id.payment_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(order != null) {
                    PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PAYMENT_ERROR));
                } else if(orderList != null) {
                    PmzData.getInstance().onPaymentMultipleOrdersCheckingError(orderList, new PmzError(PmzError.PAYMENT_ERROR));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
