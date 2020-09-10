package ar.com.fennoma.paymentezsdk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class ThirdActivity extends PaymentezBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setFullTitleWithBack(getString(R.string.activity_third_title));
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
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animActivityLeftToRight();
    }
}
