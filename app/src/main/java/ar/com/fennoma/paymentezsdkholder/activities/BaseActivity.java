package ar.com.fennoma.paymentezsdkholder.activities;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ar.com.fennoma.paymentezsdk.controllers.PmzBaseActivity;

public class BaseActivity extends AppCompatActivity {

    private Dialog loadingDialog;

    protected interface ISpinnerListener{
        void onSpinnerItemClicked(int position);
    }

    protected void setSpinner(List<String> itemsAndTitle, View clickableArea,
                              final Spinner spinner, View textView, final ISpinnerListener listener) {
        textView.setOnClickListener(new JustPerformClick(clickableArea));

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                itemsAndTitle.toArray(new String[0])) {
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(getResources().getColorStateList(android.R.color.white));
                if (position == 0) {
                    ((TextView) v).setHeight(0);
                    v.setVisibility(TextView.GONE);
                    v.setClickable(false);
                }
                return v;
            }
        };


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    listener.onSpinnerItemClicked(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        clickableArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
    }

    static class JustPerformClick implements View.OnClickListener {

        private final View toClick;

        public JustPerformClick(View toClick){
            this.toClick = toClick;
        }

        @Override
        public void onClick(View view) {
            toClick.performClick();
        }
    }

    public void showLoading() {
        if(isActivityAlive()) {
            hideLoading();
            loadingDialog = new Dialog(this, ar.com.fennoma.paymentezsdk.R.style.CustomAlertDialog);
            loadingDialog.setContentView(ar.com.fennoma.paymentezsdk.R.layout.dialog_loading);
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
    }

    public void hideLoading() {
        if (isActivityAlive()) {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
    }

    private boolean isActivityAlive() {
        return !isFinishing();
    }
}
