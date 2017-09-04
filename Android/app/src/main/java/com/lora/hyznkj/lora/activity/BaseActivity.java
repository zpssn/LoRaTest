package com.lora.hyznkj.lora.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by kiss on 2017/8/28.
 */

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle myBundle){
        super.onCreate(myBundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

}
