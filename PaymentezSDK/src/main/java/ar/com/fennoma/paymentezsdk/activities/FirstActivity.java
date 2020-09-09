package ar.com.fennoma.paymentezsdk.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;
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
        }
        setButtons();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void replaceRippleBackgroundColor() {
        TextView next = findViewById(R.id.next);
        RippleDrawable background = (RippleDrawable) next.getBackground();
        Drawable drawable = background.getDrawable(0);
        if(drawable != null) {
            ColorStateList myColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{},
                            new int[]{android.R.attr.state_pressed},
                    },
                    new int[] {
                            PaymentezSDK.getInstance().getButtonBackgroundColor(),
                            PaymentezSDK.getInstance().getButtonBackgroundColor()
                    }
            );
            drawable.setTintList(myColorStateList);
        }
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
