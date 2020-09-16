package ar.com.fennoma.paymentezsdk.activities;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class PaymentezBaseActivity extends AppCompatActivity {

    protected static final int MAIN_FLOW_KEY = 1001;
    private Toolbar toolbar;

    protected void setFullTitleWithBack(String text) {
        setToolbar();
        hideTitle();
        setToolbarTitle(text);
        setBackButton();
    }

    protected void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hideTitle();
    }

    protected void hideTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void changeToolbarBackground(Integer color) {
        if(toolbar != null && color != null) {
            toolbar.setBackgroundColor(color);
        }
    }

    protected void changeToolbarTextColor(Integer color) {
        TextView title = findViewById(R.id.toolbar_title);
        if(title != null && color != null) {
            title.setTextColor(color);
        }
        if(toolbar != null && toolbar.getNavigationIcon() != null && color != null) {
            Drawable drawable = toolbar.getNavigationIcon();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    protected void setToolbarTitle(String text) {
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(text);
    }

    protected void setBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void animActivityRightToLeft() {
        overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
    }

    protected void animActivityLeftToRight() {
        overridePendingTransition(R.anim.enter_left_to_right, R.anim.exit_left_to_right);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void replaceRippleBackgroundColor() {
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

    protected void showLoading(){

    }

    protected void hideLoading() {

    }

    protected void onSessionExpired() {

    }
}
