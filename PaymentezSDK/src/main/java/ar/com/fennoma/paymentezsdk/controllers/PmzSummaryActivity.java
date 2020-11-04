package ar.com.fennoma.paymentezsdk.controllers;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.PmzSummaryAdapter;
import ar.com.fennoma.paymentezsdk.adapters.SwiperAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzItem;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;
import ar.com.fennoma.paymentezsdk.utils.PmzCurrencyUtils;

public class PmzSummaryActivity extends AbstractSwiperContainerActivity<PmzItem, PmzSummaryAdapter.PmzSummaryHolder> {

    public static final String SHOW_SUMMARY = "show summary";

    private boolean justSummary = false;

    private PmzOrder order;
    private List<PmzOrder> orders;
    private PmzStore store;

    private RecyclerView recycler;
    private PmzSummaryAdapter adapter;

    private TextView price;

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
            orders = getIntent().getParcelableArrayListExtra(PMZ_ORDER);
            store = getIntent().getParcelableExtra(PMZ_STORE);

            if(order == null) {
                order = new PmzOrder();
            }
            setDataIntoViews();
        }
    }

    private void setDataIntoViews() {
        setStoreData();
        recalculatePrice();
        setDataIntoRecycler();
    }

    private void setDataIntoRecycler() {
        if(order == null || order.getItems() == null || order.getItems().size() == 0) {
            recycler.setVisibility(View.GONE);
        } else {
            recycler.setVisibility(View.VISIBLE);
            adapter.setItems(order.getItems());
        }
    }

    private void setStoreData() {
        ImageView icon = findViewById(R.id.icon);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);

        if(store != null) {
            ImageUtils.loadStoreImage(this, icon, store.getImageUrl());

            title.setText(store.getName());
            description.setText(store.getCommerceName());
        }

        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            title.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            description.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
        }
    }

    private void setViews() {
        PmzStyle style = PaymentezSDK.getInstance().getStyle();
        if(style != null) {
            if (style.getTextColor() != null) {
                price = findViewById(R.id.price);
                price.setTextColor(style.getTextColor());
            }
            if (style.getBackgroundColor() != null) {
                View background = findViewById(R.id.background);
                background.setBackgroundColor(style.getBackgroundColor());
            }
            if (style.getButtonBackgroundColor() != null) {
                changeToolbarBackground(style.getButtonBackgroundColor());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorHelper.replaceButtonBackground(findViewById(R.id.next),
                            style.getButtonBackgroundColor());
                }
                changeToolbarBackground(style.getButtonBackgroundColor());
            }
            if (style.getButtonTextColor() != null) {
                TextView next = findViewById(R.id.next);
                TextView keepBuying = findViewById(R.id.keep_buying);
                next.setTextColor(style.getButtonTextColor());
                keepBuying.setTextColor(style.getButtonTextColor());
                changeToolbarTextColor(style.getButtonTextColor());
            }
        }
        setRecycler();
        setButtons();
    }

    private void setRecycler() {
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PmzSummaryAdapter(this, new PmzSummaryAdapter.IPmzSummaryAdapterListener() {
            @Override
            public void onItemRemoved(PmzItem item, int position) {
                /*itemRemoved = item;
                positionRemoved = position;
                order.removeItem(item);*/
                recalculatePrice();
            }

            @Override
            public void onItemRestored(PmzItem item) {
                /*if(itemRemoved != null) {
                    order.addItem(itemRemoved, positionRemoved);
                }*/
                recalculatePrice();
            }

            @Override
            public void onEditItem(PmzItem item) {

            }
        });
        recycler.setAdapter(adapter);
        recycler.setNestedScrollingEnabled(false);
        setRecyclerItemTouchHelper(recycler);
    }

    private void recalculatePrice() {
        if(order != null) {
            price.setText(PmzCurrencyUtils.formatPrice(order.getFullPrice()));
        } else {
            price.setText(PmzCurrencyUtils.formatPrice(0L));
        }
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(justSummary) {
                    if(order != null) {
                        PaymentezSDK.getInstance().setOrderResult(order);
                    } else if(orders != null) {
                        PaymentezSDK.getInstance().setOrderResult(orders);
                    }
                    PmzData.getInstance().onSearchSuccess();
                } else {
                    PaymentezSDK.getInstance().setOrderResult(order);
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
        findViewById(R.id.keep_buying).setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected String getDeletedLabelWarning() {
        return getString(R.string.swipe_to_delete_favorite_warn);
    }

    @Override
    protected CoordinatorLayout getCoordinatorLayout() {
        return findViewById(R.id.coordinator);
    }

    @Override
    protected SwiperAdapter<PmzItem, PmzSummaryAdapter.PmzSummaryHolder> getAdapter() {
        return adapter;
    }

    @Override
    protected List<PmzItem> getItems() {
        return order.getItems();
    }
}
