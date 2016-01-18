package com.tlab.wish;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tlab.wish.activities.BaseActivity;
import com.tlab.wish.configs.ConfigurationManager;

public class StartupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }

    public void test(View v){
        int max = ConfigurationManager.getInstanse().getConfigs().getMaxSymbols();

        Log.d("testt", "max - " + max);
    }

}
