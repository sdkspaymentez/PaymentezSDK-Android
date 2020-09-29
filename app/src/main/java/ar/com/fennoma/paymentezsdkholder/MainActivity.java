package ar.com.fennoma.paymentezsdkholder;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class MainActivity extends AppCompatActivity {

    private View button;
    private View detailButton;
    private View searchWithStoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setViews();
    }

    private void setViews() {
        setButtons();
    }

    private void setButtons() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        .setButtonBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                        .setTextColor(getResources().getColor(android.R.color.black))
                        .setButtonTextColor(getResources().getColor(android.R.color.white))
                        .startSearch(MainActivity.this, new PaymentezSDK.PmzSearchListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, "El flujo de compra terminó bien", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, "El flujo de compra fue Cancelado", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        searchWithStoreId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        .setButtonBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                        .setTextColor(getResources().getColor(android.R.color.black))
                        .setButtonTextColor(getResources().getColor(android.R.color.white))
                        .startSearch(MainActivity.this, 120L, new PaymentezSDK.PmzSearchListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, "El flujo de compra terminó bien", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, "El flujo de compra fue Cancelado", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                        .setButtonBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                        .setTextColor(getResources().getColor(android.R.color.black))
                        .setButtonTextColor(getResources().getColor(android.R.color.white))
                        .startPayAndPlace(MainActivity.this, PmzOrder.hardcoded(), "paymentReference", new PaymentezSDK.PmzPayAndPlaceListener() {
                            @Override
                            public void onFinishedSuccessfully(PmzOrder order) {
                                Toast.makeText(MainActivity.this, "La compra finalizó con éxito", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(PmzOrder order, PmzError error) {
                                switch (error.getType()) {
                                    case PmzError.NO_ORDER_SET_ERROR:
                                        Toast.makeText(MainActivity.this, "No se encontró la orden", Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PAYMENT_ERROR:
                                        Toast.makeText(MainActivity.this, "Ocurrió un error con el Pago de la orden", Toast.LENGTH_LONG).show();
                                        break;
                                    case PmzError.PLACE_ERROR:
                                        Toast.makeText(MainActivity.this, "Ocurrió un error con el Place de la orden", Toast.LENGTH_LONG).show();
                                        break;

                                }
                            }
                        });
            }
        });
    }

    private void findViews() {
        button = findViewById(R.id.button);
        searchWithStoreId = findViewById(R.id.search_with_store_id);
        detailButton = findViewById(R.id.detail);
    }
}

