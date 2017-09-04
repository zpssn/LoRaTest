package com.lora.hyznkj.lora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lora.hyznkj.lora.R;
import com.lora.hyznkj.lora.util.SettingMessage;
import com.lora.hyznkj.lora.util.WifiAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiss on 2017/8/28.
 */

public class BeforeTest extends TitleActivity {
    private String TAG = "BeforeTest";
    private EditText input_ip;
    private Button go_test;
    private Spinner choose_type;
    private WifiAdmin mWifiAdmin;
    private String ip;
    private ArrayAdapter<String> choose_type_adapter;
    private List<String> data_list;
    private String typedata;
    private String[] ShowtypeArray={"10A/16A插座","一路开关","二路开关","三路开关"};
    private String[] typeArray={"1001","1101","1102","1103"};

    @Override
    protected void onCreate(Bundle mBundle){
        super.onCreate(mBundle);
        setTitle("设置");
        setContentView(R.layout.before_test);
        findview();
        setlistener();
    }

    private SettingMessage getMessage(String ip,String type){
        SettingMessage message = new SettingMessage();
        message.setIp(ip);
        message.setType(type);
        return message;
    }
    private void initip(){
        this.mWifiAdmin = new WifiAdmin(this);
        ip = mWifiAdmin.ipIntToString(mWifiAdmin.getIpAddress());
        if(ip.length()==14){
            ip = ip.substring(0,10);
        }else if(ip.length()==13){
            ip = ip.substring(0,9);
        }else if(ip.length()==14){
            ip = ip.substring(0,11);
        }else{
            Toast.makeText(BeforeTest.this,"请确保您的ip为：192.168.xx.xx形式",Toast.LENGTH_SHORT).show();
            ip="null";
        }
        input_ip.setText(ip+".1:60000");
    }
    private void initdata(){
        data_list = new ArrayList<String>();
        data_list.add("请选择");
        choose_type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        choose_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_type.setAdapter(choose_type_adapter);
        choose_type.setSelection(0, true);
        for(int i=0;i<ShowtypeArray.length;i++){
            data_list.add(ShowtypeArray[i]);
        }
    }
    private void findview(){
        input_ip = (EditText)findViewById(R.id.main_ip);
        go_test = (Button)findViewById(R.id.go_test);
        choose_type = (Spinner)findViewById(R.id.choose_type);
        initdata();
    }
    private void setlistener(){
        choose_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                typedata = typeArray[arg2-1];
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        go_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.equals("null")||ip==null){
                    Toast.makeText(BeforeTest.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                }else if(typedata==null){
                    Toast.makeText(BeforeTest.this,"请选择设备类型",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(BeforeTest.this,TestAliActivity.class);
                    intent.putExtra("message",getMessage(input_ip.getText().toString(),typedata));
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        initip();
    }
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
