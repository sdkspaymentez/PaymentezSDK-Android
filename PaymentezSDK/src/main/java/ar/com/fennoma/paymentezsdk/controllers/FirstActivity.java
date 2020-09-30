package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.ErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.Store;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;

public class FirstActivity extends PaymentezBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        setFullTitleWithBack(getString(R.string.activity_first_title));
        setViews();
    }

    private void setViews() {
        if(PmzData.getInstance().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PmzData.getInstance().getBackgroundColor());
        }
        if(PmzData.getInstance().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PmzData.getInstance().getTextColor());
        }
        if(PmzData.getInstance().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.next));
            }
            changeToolbarBackground(PmzData.getInstance().getButtonBackgroundColor());
        }
        if(PmzData.getInstance().getButtonTextColor() != null) {
            TextView next = findViewById(R.id.next);
            next.setTextColor(PmzData.getInstance().getButtonTextColor());
            changeToolbarTextColor(PmzData.getInstance().getButtonTextColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();*/
                doThings();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            PmzData.getInstance().onSearchSuccess();
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
                        PmzData.getInstance().onSearchCancel();
                        FirstActivity.super.onBackPressed();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private void doThings() {
        API.getSession(PaymentezSDK.getInstance().getSession(), new API.ServiceCallback<String>() {
            @Override
            public void onSuccess(String token) {
                PaymentezSDK.getInstance().setToken(token);
                getStores();
            }

            @Override
            public void onError(ErrorMessage error) {
                DialogUtils.toast(FirstActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                DialogUtils.genericError(FirstActivity.this);
            }

            @Override
            public void sessionExpired() {
                onSessionExpired();
            }
        });
    }

    private void getStores() {
        API.getStores(new API.ServiceCallback<List<Store>>() {
            @Override
            public void onSuccess(List<Store> response) {
                if(response != null) {

                }
                startOrder();
            }

            @Override
            public void onError(ErrorMessage error) {
                DialogUtils.toast(FirstActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                DialogUtils.genericError(FirstActivity.this);
            }

            @Override
            public void sessionExpired() {
                onSessionExpired();
            }
        });
    }

    private void startOrder() {
        API.startOrder(PmzOrder.hardcodedForOrderStart(), new API.ServiceCallback<PmzOrder>() {
            @Override
            public void onSuccess(PmzOrder response) {
                if(response != null) {

                }
            }

            @Override
            public void onError(ErrorMessage error) {
                DialogUtils.toast(FirstActivity.this, error.getErrorMessage(FirstActivity.this));
            }

            @Override
            public void onFailure() {
                DialogUtils.genericError(FirstActivity.this);
            }

            @Override
            public void sessionExpired() {
                onSessionExpired();
            }
        });
    }
}
