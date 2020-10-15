package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.MenuPagerAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzMenu;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;

public class PmzMenuActivity extends PmzBaseActivity {

    public static final String STORE_KEY = "store Id";
    public static final String FORCED_ID = "forced Id";

    private PmzStore store;
    private boolean forcedId = false;
    private MenuPagerAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_menu);
        setFullTitleWithBack(getString(R.string.activity_pmz_menu_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(STORE_KEY) != null) {
            store = getIntent().getParcelableExtra(STORE_KEY);
            forcedId = getIntent().getBooleanExtra(FORCED_ID, false);
            if(forcedId) {
                getToken();
            } else {
                getData(true);
            }
        } else {
            DialogUtils.genericError(this);
            onBackPressed();
        }
    }

    private void getToken() {
        showLoading();
        API.getSession(PmzData.getInstance().getSession(), new API.ServiceCallback<String>() {
            @Override
            public void onSuccess(String response) {
                PmzData.getInstance().setToken(response);
                getData(false);
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzMenuActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzMenuActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void getData(boolean showLoading) {
        if(showLoading) {
            showLoading();
        }
        API.getMenu(store.getId(), new API.ServiceCallback<PmzMenu>() {
            @Override
            public void onSuccess(PmzMenu response) {
                hideLoading();
                setDataIntoViews(response);
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzMenuActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzMenuActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void setDataIntoViews(PmzMenu menu) {
        adapter.setMenu(menu);

    }

    private void setViews() {
        if(PmzData.getInstance().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PmzData.getInstance().getBackgroundColor());
        }
        if(PmzData.getInstance().getTextColor() != null) {
        }
        if(PmzData.getInstance().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.chart_button));
            }
            changeToolbarBackground(PmzData.getInstance().getButtonBackgroundColor());
        }
        if(PmzData.getInstance().getButtonTextColor() != null) {
            TextView next = findViewById(R.id.chart_button);
            next.setTextColor(PmzData.getInstance().getButtonTextColor());
            changeToolbarTextColor(PmzData.getInstance().getButtonTextColor());
        }
        setButtons();
        setPager();
    }

    private void setPager() {
        ViewPager pager = findViewById(R.id.pager);
        adapter = new MenuPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        setTabLayout(pager);
    }

    private void setTabLayout(ViewPager pager) {
        final Typeface robotoCondensedRegular = Typeface.create("font_roboto_condensed_regular", Typeface.NORMAL);
        final Typeface robotoCondensedBold = Typeface.create("font_roboto_condensed_bold", Typeface.NORMAL);
        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout container = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) container.getChildAt(1);
                tabTextView.setTypeface(robotoCondensedBold, Typeface.BOLD);
                if(PmzData.getInstance().getTextColor() != null) {
                    tabTextView.setTextColor(PmzData.getInstance().getTextColor());
                } else {
                    tabTextView.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout container = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) container.getChildAt(1);
                tabTextView.setTypeface(robotoCondensedRegular, Typeface.NORMAL);
                if(PmzData.getInstance().getTextColor() != null) {
                    tabTextView.setTextColor(PmzData.getInstance().getTextColor());
                } else {
                    tabTextView.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        //LinearLayout container = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
        //TextView tabTextView = (TextView) container.getChildAt(1);
        //tabTextView.setTypeface(robotoCondensedBold, Typeface.BOLD);
        //tabTextView.setTextColor(getResources().getColor(R.color.orange));
    }

    private void setButtons() {
        findViewById(R.id.chart_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PmzMenuActivity.this, PmzProductActivity.class);
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            if(forcedId) {
                PmzData.getInstance().onSearchSuccess();
            } else {
                setResult(RESULT_OK);
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animActivityLeftToRight();
    }
}
