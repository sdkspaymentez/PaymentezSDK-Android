package ar.com.fennoma.paymentezsdk.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class PaymentezPaymentCheckerActivity extends PaymentezBaseActivity {

    public static final String PMZ_ORDER = "pmz order";

    private PmzOrder order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_checker_activity);
        setFullTitleWOBack(getString(R.string.activity_payment_checker_title));
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
            ProgressBar progress = findViewById(R.id.progress);
            progress.getIndeterminateDrawable().setColorFilter(PaymentezSDK.getInstance().getTextColor(), PorterDuff.Mode.SRC_IN );
        }
        if(PaymentezSDK.getInstance().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.payment_error));
                replaceRippleBackgroundColor(findViewById(R.id.place_error));
                replaceRippleBackgroundColor(findViewById(R.id.success));
            }
            changeToolbarBackground(PaymentezSDK.getInstance().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getButtonTextColor() != null) {
            TextView paymentError = findViewById(R.id.payment_error);
            paymentError.setTextColor(PaymentezSDK.getInstance().getButtonTextColor());
            TextView placeError = findViewById(R.id.place_error);
            placeError.setTextColor(PaymentezSDK.getInstance().getButtonTextColor());
            TextView success = findViewById(R.id.success);
            success.setTextColor(PaymentezSDK.getInstance().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getButtonTextColor());
        }
    }

    private void setButtons() {
        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentezPaymentCheckerActivity.this, PaymentezPurchaseDetailActivity.class);
                intent.putExtra(PaymentezPurchaseDetailActivity.PMZ_ORDER, order);
                startActivity(intent);
                animActivityRightToLeft();
                finish();
            }
        });
        findViewById(R.id.place_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PaymentezSDK.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PLACE_ERROR));
            }
        });
        findViewById(R.id.payment_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PaymentezSDK.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PAYMENT_ERROR));
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
