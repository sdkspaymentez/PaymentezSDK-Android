package ar.com.fennoma.paymentezsdk.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.ErrorMessage;
import ar.com.fennoma.paymentezsdk.models.Store;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.services.Services;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;

public class FirstActivity extends PaymentezBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        setFullTitleWithBack(getString(R.string.activity_first_title));
        setViews();
        getData();
    }

    private void getData() {
        showLoading();
        API.getStores(new API.ServiceCallback<List<Store>>() {
            @Override
            public void onSuccess(List<Store> response) {
                hideLoading();

            }

            @Override
            public void onError(ErrorMessage error) {
                hideLoading();
            }

            @Override
            public void onFailure() {
                hideLoading();
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getActionBarColor() != null) {
            changeToolbarBackground(PaymentezSDK.getInstance().getActionBarColor());
            changeToolbarTextColor(getResources().getColor(android.R.color.white));
        }
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
                replaceRippleBackgroundColor();
            }
            TextView next = findViewById(R.id.next);
            next.setTextColor(getResources().getColor(android.R.color.white));
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            PaymentezSDK.getInstance().onSuccess();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DialogUtils.showDialog(this, getString(R.string.first_activity_cancel_title),
                getString(R.string.first_activity_cancel_message),
                new DialogUtils.IDialogListener() {
                    @Override
                    public void onAccept() {
                        PaymentezSDK.getInstance().onCancel();
                        FirstActivity.super.onBackPressed();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
