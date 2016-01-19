package com.tlab.wish.pin_staff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;
import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.MainActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class PinActivity extends MvpActivity<PinView, PinPresenter> implements PinView{

    @Bind(R.id.pin_title)
    TextView titleTv;

    @Bind({R.id.pin_1, R.id.pin_2, R.id.pin_3, R.id.pin_4})
    List<EditText> digitEts;

    @Bind({R.id.pin_digit_0, R.id.pin_digit_1, R.id.pin_digit_2, R.id.pin_digit_3, R.id.pin_digit_4,
            R.id.pin_digit_5, R.id.pin_digit_6, R.id.pin_digit_7, R.id.pin_digit_8, R.id.pin_digit_9,})
    List<TextView> digits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        ButterKnife.bind(this);

        presenter.onCreate();

        initEdittexts();
        initKeypad();
    }


    private void initKeypad(){
        for(final TextView tv : digits){
            tv.setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_LIGHT));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (EditText et : digitEts) {
                        if (et.hasFocus()) {
                            et.setText(tv.getText());
                            break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setTitle(String title) {
        titleTv.setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_REGULAR));
        titleTv.setText(title);
    }

    @OnClick(R.id.pin_clear)
    public void clearDigits(){
        for(EditText et : digitEts){
            et.setText("");
        }

        digitEts.get(0).requestFocus();
    }

    @Override
    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showWrongPinError() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);
        Toast.makeText(this, "WRONG PIN", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.pin_backspace)
    public void pinBackpress(){
        for(EditText et : digitEts){
            if(et.hasFocus()){
                et.setText("");
                break;
            }
        }
    }

    private void initEdittexts(){
        for(int i = 0; i < digitEts.size(); i++){
            digitEts.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        digitEts.get(0).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    digitEts.get(1).requestFocus();
                }
            }
        });

        digitEts.get(1).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    digitEts.get(2).requestFocus();
                } else {
                    digitEts.get(0).requestFocus();
                }
            }
        });

        digitEts.get(2).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    digitEts.get(3).requestFocus();
                } else {
                    digitEts.get(1).requestFocus();
                }
            }
        });

        digitEts.get(3).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    presenter.allDigitsEntered(
                            String.format("%s%s%s%s",
                                    digitEts.get(0).getText().toString(),
                                    digitEts.get(1).getText().toString(),
                                    digitEts.get(2).getText().toString(),
                                    digitEts.get(3).getText().toString()));
                } else {
                    digitEts.get(2).requestFocus();
                }
            }
        });
    }

}
