package ar.com.fennoma.paymentezsdk.controllers;

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

public class PmzPayAndPlaceActivity extends PmzBaseActivity {

    public static final String PMZ_ORDER = "pmz order";
    public static final String PMZ_PAYMENT_REFERENCE = "pmz payment reference";

    private PmzOrder order;
    private String paymentReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_pay_and_place_activity);
        setFullTitleWOBack(getString(R.string.activity_pmz_pay_and_place_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ORDER) != null) {
            this.order = getIntent().getParcelableExtra(PMZ_ORDER);
            this.paymentReference = getIntent().getStringExtra(PMZ_PAYMENT_REFERENCE);
            setButtons();
        } else {
            finish();
            animActivityLeftToRight();
            PmzData.getInstance().onPaymentCheckingError(null, new PmzError(PmzError.NO_ORDER_SET_ERROR));
        }
    }

    private void setViews() {
        if(PmzData.getInstance().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PmzData.getInstance().getBackgroundColor());
        }
        if(PmzData.getInstance().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PmzData.getInstance().getTextColor());
            ProgressBar progress = findViewById(R.id.progress);
            progress.getIndeterminateDrawable().setColorFilter(PmzData.getInstance().getTextColor(), PorterDuff.Mode.SRC_IN);
        }
        if(PmzData.getInstance().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.payment_error));
                replaceRippleBackgroundColor(findViewById(R.id.place_error));
                replaceRippleBackgroundColor(findViewById(R.id.success));
            }
            changeToolbarBackground(PmzData.getInstance().getButtonBackgroundColor());
        }
        if(PmzData.getInstance().getButtonTextColor() != null) {
            TextView paymentError = findViewById(R.id.payment_error);
            paymentError.setTextColor(PmzData.getInstance().getButtonTextColor());
            TextView placeError = findViewById(R.id.place_error);
            placeError.setTextColor(PmzData.getInstance().getButtonTextColor());
            TextView success = findViewById(R.id.success);
            success.setTextColor(PmzData.getInstance().getButtonTextColor());
            changeToolbarTextColor(PmzData.getInstance().getButtonTextColor());
        }
    }

    private void setButtons() {
        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PmzPayAndPlaceActivity.this, PmzResultActivity.class);
                intent.putExtra(PmzResultActivity.PMZ_ORDER, order);
                startActivity(intent);
                animActivityRightToLeft();
                finish();
            }
        });
        findViewById(R.id.place_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PLACE_ERROR));
            }
        });
        findViewById(R.id.payment_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PAYMENT_ERROR));
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
