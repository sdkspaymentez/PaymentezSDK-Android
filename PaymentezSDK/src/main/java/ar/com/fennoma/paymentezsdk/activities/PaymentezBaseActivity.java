package ar.com.fennoma.paymentezsdk.activities;

import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ar.com.fennoma.paymentezsdk.R;

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

}
