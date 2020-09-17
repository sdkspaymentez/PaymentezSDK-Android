package ar.com.fennoma.paymentezsdk.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class PaymentezPurchaseDetailActivity extends PaymentezBaseActivity {

    public static final String PMZ_ORDER = "pmz order";

    private PmzOrder order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentez_purchase_detail);
        setFullTitleWithBack(getString(R.string.activity_purchase_detail_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ORDER) != null) {
            this.order = getIntent().getParcelableExtra(PMZ_ORDER);
            setButtons();
        } else {
            finish();
            animActivityLeftToRight();
            PaymentezSDK.getInstance().onPaymentCheckingError(null, new PmzError(PmzError.NO_ORDER_SET_ERROR));
        }
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PaymentezSDK.getInstance().getTextColor());
        }
        if(PaymentezSDK.getInstance().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.back));
            }
            changeToolbarBackground(PaymentezSDK.getInstance().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getButtonTextColor() != null) {
            TextView back = findViewById(R.id.back);
            back.setTextColor(PaymentezSDK.getInstance().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getButtonTextColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            PaymentezSDK.getInstance().onSearchSuccess();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PaymentezSDK.getInstance().onPaymentCheckingSuccess(order);
    }

}
