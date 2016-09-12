package com.lora.hyznkj.lora;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.lora.hyznkj.lora.adapter.LoraDeviceListAdapter;
import com.lora.hyznkj.lora.ui.WifiListActivity;
import java.util.ArrayList;
import java.util.HashMap;


public class DeviceListActivity extends Activity {
    private static int SUM;
    private ImageButton back;
    private ImageButton add;
    private Button receive;
    private ListView lv;
    private ConnectingSocket SocketServer = new ConnectingSocket();

    /*定义一个动态数组*/
    ArrayList<HashMap<String, Object>> listItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);
        Toast.makeText(this, "连接成功！", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = getSharedPreferences("sn",MODE_PRIVATE);
        int num = pref.getInt("sum", 0);
        SUM=1;//num;

        add = (ImageButton)findViewById(R.id.add);
        back = (ImageButton)findViewById(R.id.back);
        receive = (Button)findViewById(R.id.receive);
        lv = (ListView) findViewById(R.id.lv);
        LoraDeviceListAdapter mAdapter = new LoraDeviceListAdapter(this);//得到一个MyAdapter对象
        lv.setAdapter(mAdapter);//为ListView绑定Adapter
        /*为ListView添加点击事件*/
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(DeviceListActivity.this,
                        WifiListActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendMessage("1");

                Toast.makeText(DeviceListActivity.this, "添加中。。。",
                        Toast.LENGTH_SHORT).show();
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getMessage();
                Toast.makeText(DeviceListActivity.this, getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {


                }

        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean  onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                String item = (String) lv.getItemAtPosition(arg2+1);
                SharedPreferences pref = getSharedPreferences("sn", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                SUM--;
                editor.remove(item);

                refresh();
                return true;
            }
        });

    }
    public static ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    	 /*为动态数组添加数据*/

        for(int i=0;i<SUM;i++)
        {
            i++;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "子设备"+i);
            listItem.add(map);
        }
        return listItem;

    }

    private void refresh() {
        finish();
        Intent intent = new Intent(DeviceListActivity.this, DeviceListActivity.class);
        startActivity(intent);
    }

    private void sendMessage(String msgText)
    {
        if ( ConnectingSocket.mPrintWriterClient!=null )
        {
            try
            {
                ConnectingSocket.mPrintWriterClient.print(msgText);//发送给服务器
                ConnectingSocket.mPrintWriterClient.flush();
            }
            catch (Exception e)
            {
                // TODO: handle exception
                Toast.makeText(this, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getMessage()
    {

        String text = SocketServer.getMessage();
        return text;
    }

}