package ar.com.fennoma.paymentezsdkholder;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class MainActivity extends AppCompatActivity {

    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setViews();
    }

    private void setViews() {
        setButton();
    }

    private void setButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            PaymentezSDK.getInstance()
                    .setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark))
                    .setButtonBackgroundColor(getResources().getColor(android.R.color.holo_green_dark))
                    .setTextColor(getResources().getColor(android.R.color.holo_red_dark))
                    .setContext(ar.com.fennoma.paymentezsdkholder.MainActivity.this)
                    .setListener(new PaymentezSDK.IPanchoSDKListener() {
                        @Override
                        public void onFinishedSuccessfully() {
                            Toast.makeText(ar.com.fennoma.paymentezsdkholder.MainActivity.this, "El flujo terminó bien", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(ar.com.fennoma.paymentezsdkholder.MainActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
                        }
                    })
                    .start();
            }
        });
    }

    private void findViews() {
        button = findViewById(R.id.button);
    }
}

