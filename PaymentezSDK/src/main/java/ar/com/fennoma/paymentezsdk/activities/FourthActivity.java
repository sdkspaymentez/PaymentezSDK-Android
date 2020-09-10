package ar.com.fennoma.paymentezsdk.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class FourthActivity extends PaymentezBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        setFullTitleWithBack(getString(R.string.activity_fourth_title));
        setViews();
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PaymentezSDK.getInstance().getTextColor());
            TextView back = findViewById(R.id.back);
            back.setTextColor(PaymentezSDK.getInstance().getTextColor());
            TextView next = findViewById(R.id.next);
            next.setTextColor(PaymentezSDK.getInstance().getTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getTextColor());
        }
        if(PaymentezSDK.getInstance().getButtonBackgroundColor() != null) {
            changeToolbarBackground(PaymentezSDK.getInstance().getButtonBackgroundColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
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
        animActivityLeftToRight();
    }
}
