package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.controls.QuantitySelector;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzProduct;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;

public class PmzProductActivity extends PmzBaseActivity {

    public static final String PRODUCT_KEY = "product key";

    private PmzProduct product;

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView quantityTitle;
    private TextView totalTitle;
    private TextView totalPrice;
    private QuantitySelector quantitySelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_product);
        setFullTitleWithBack(getString(R.string.activity_pmz_product_title));
        findViews();
        setViews();
        handleIntent();
    }

    private void findViews() {
        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        quantityTitle = findViewById(R.id.quantity_title);
        totalTitle = findViewById(R.id.total_title);
        quantitySelector = findViewById(R.id.quantity_selector);
        totalPrice = findViewById(R.id.total_price);
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PRODUCT_KEY) != null) {
            product = getIntent().getParcelableExtra(PRODUCT_KEY);
            setDataIntoViews();
        } else {
            DialogUtils.genericError(this);
            onBackPressed();
        }
    }

    private void setDataIntoViews() {
        ImageUtils.loadProductImage(this, image, product.getImageUrl());
        title.setText(product.getName());
        description.setText(product.getDescription());
        price.setText("$".concat(String.valueOf(product.getListPrice())));
        totalPrice.setText("$".concat(String.valueOf(product.getListPrice())));
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            TextView back = findViewById(R.id.back);
            Integer textColor = PaymentezSDK.getInstance().getStyle().getTextColor();
            back.setTextColor(textColor);
            title.setTextColor(textColor);
            description.setTextColor(textColor);
            price.setTextColor(textColor);
            quantityTitle.setTextColor(textColor);
            totalTitle.setTextColor(textColor);
            totalPrice.setTextColor(textColor);
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            ColorHelper.replaceButtonBackground(findViewById(R.id.next),
                    PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
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
                Intent intent = new Intent(PmzProductActivity.this, PmzSummaryActivity.class);
                intent.putExtra(PmzSummaryActivity.SHOW_SUMMARY, PmzOrder.hardcoded());
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
