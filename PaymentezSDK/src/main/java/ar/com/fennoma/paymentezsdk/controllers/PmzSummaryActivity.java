package ar.com.fennoma.paymentezsdk.controllers;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;

public class PmzSummaryActivity extends PmzBaseActivity {

    public static final String SHOW_SUMMARY = "show summary";
    public static final String PMZ_ORDER = "order key";

    private PmzOrder order;
    private List<PmzOrder> orderList;
    private boolean justSummary = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_summary);
        setFullTitleWithBack(getString(R.string.activity_pmz_summary_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null) {
            justSummary = getIntent().getBooleanExtra(SHOW_SUMMARY, false);
            order = getIntent().getParcelableExtra(PMZ_ORDER);
            orderList = getIntent().getParcelableArrayListExtra(PMZ_ORDER);
        }
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            TextView back = findViewById(R.id.back);
            back.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorHelper.replaceButtonBackground(findViewById(R.id.next),
                        PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            }
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            TextView next = findViewById(R.id.next);
            next.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(justSummary) {
                    if(order != null) {
                        PaymentezSDK.getInstance().setOrderResult(order);
                    } else if(orderList != null) {
                        PaymentezSDK.getInstance().setOrderResult(orderList);
                    }
                    PmzData.getInstance().onSearchSuccess();
                } else {
                    PaymentezSDK.getInstance().setOrderResult(PmzOrder.hardcoded());
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(justSummary) {
            PmzData.getInstance().onSearchCancel();
        }
        animActivityLeftToRight();
    }
}
