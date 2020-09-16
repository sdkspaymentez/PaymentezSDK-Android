package ar.com.fennoma.paymentezsdkholder.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;
import ar.com.fennoma.paymentezsdkholder.R;
import ar.com.fennoma.paymentezsdkholder.models.Color;

public class MainActivity extends BaseActivity {

    private List<Color> colors;

    private View button;

    private TextView bgColor;
    private TextView textColor;
    private TextView buttonColor;

    private Color bgColorSelected;
    private Color textColorSelected;
    private Color buttonColorSelected;

    private View bgColorShower;
    private View textColorShower;
    private View buttonColorShower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colors = Color.getColorList(this);
        findViews();
        setViews();
        selectRandomColors();
    }

    private void setViews() {
        setButton();
        setRandomizeButton();
        setBgSpinner();
        setTextSpinner();
        setButtonSpinner();
    }

    private void setRandomizeButton() {
        findViewById(R.id.randomize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectRandomColors();
            }
        });
    }

    private void setButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentezSDK.getInstance()
                        .setBackgroundColor(bgColorSelected.getColorRes())
                        //.setButtonBackgroundColor(buttonColorSelected.getColorRes())
                        .setButtonBackgroundColor(getResources().getColor(R.color.buttons_color))
                        .setActionBarColor(getResources().getColor(R.color.colorPrimaryDark))
                        .setTextColor(textColorSelected.getColorRes())
                        .setContext(MainActivity.this)
                        .setListener(new PaymentezSDK.IPanchoSDKListener() {
                            @Override
                            public void onFinishedSuccessfully() {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_success), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, getString(R.string.home_flow_cancelled), Toast.LENGTH_LONG).show();
                            }
                        })
                        .start();
            }
        });
    }

    private void setBgSpinner() {
        View bgArea = findViewById(R.id.bg_color_area);
        Spinner bgSpinner = findViewById(R.id.bg_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.background_sdk_color_picker_title));

        setSpinner(strings, bgArea, bgSpinner, bgColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setBgSelection(position);
            }
        });
    }

    private void setBgSelection(int position) {
        bgColorSelected = colors.get(position);
        bgColorShower.setBackgroundColor(bgColorSelected.getColorRes());
        bgColor.setText(bgColorSelected.getColorName());
    }

    private void setTextSpinner() {
        View textArea = findViewById(R.id.text_color_area);
        Spinner textSpinner = findViewById(R.id.text_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.text_sdk_color_picker_title));

        setSpinner(strings, textArea, textSpinner, textColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setTextSelection(position);
            }
        });
    }

    private void setTextSelection(int position) {
        textColorSelected = colors.get(position);
        textColorShower.setBackgroundColor(textColorSelected.getColorRes());
        textColor.setText(textColorSelected.getColorName());
    }

    private void setButtonSpinner() {
        View buttonArea = findViewById(R.id.button_color_area);
        Spinner buttonSpinner = findViewById(R.id.button_color_spinner);

        List<String> strings = Color.convertToStringList(colors);
        strings.add(0, getString(R.string.button_sdk_color_picker_title));

        setSpinner(strings, buttonArea, buttonSpinner, buttonColor, new ISpinnerListener() {
            @Override
            public void onSpinnerItemClicked(int position) {
                setButtonSelection(position);
            }
        });
    }

    private void setButtonSelection(int position) {
        buttonColorSelected = colors.get(position);
        buttonColorShower.setBackgroundColor(buttonColorSelected.getColorRes());
        buttonColor.setText(buttonColorSelected.getColorName());
    }

    private void findViews() {
        button = findViewById(R.id.button);

        bgColor = findViewById(R.id.bg_color);
        textColor = findViewById(R.id.text_color);
        buttonColor = findViewById(R.id.button_color);

        bgColorShower = findViewById(R.id.bg_color_shower);
        textColorShower = findViewById(R.id.text_color_shower);
        buttonColorShower = findViewById(R.id.button_color_shower);
    }

    private void selectRandomColors() {
        setBgSelection(getRandomTill(colors.size()));
        setTextSelection(getRandomTill(colors.size()));
        setButtonSelection(getRandomTill(colors.size()));
    }

    private int getRandomTill(int max) {
        return new Random().nextInt(max);
    }
}
