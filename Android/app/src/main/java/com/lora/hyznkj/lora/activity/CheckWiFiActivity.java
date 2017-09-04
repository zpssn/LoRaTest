package com.lora.hyznkj.lora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.lora.hyznkj.lora.R;

/**
 * Created by kiss on 2017/8/28.
 */

public class CheckWiFiActivity extends TitleActivity {
    private Button go_set;
    private Button set_ok;
    @Override
    protected void onCreate(Bundle mBundle){
        super.onCreate(mBundle);
        setTitleGone();
        setContentView(R.layout.check_wifi);
        findview();
        setlistener();
    }
    private void findview(){
        go_set = (Button)findViewById(R.id.go_set);
        set_ok = (Button)findViewById(R.id.set_ok);
    }
    private void setlistener(){
        go_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new
                        Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        set_ok.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWiFiActivity.this,BeforeTest.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
