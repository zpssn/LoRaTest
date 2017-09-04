package com.lora.hyznkj.lora.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lora.hyznkj.lora.R;
import com.lora.hyznkj.lora.adapter.TestDeviceAdapter;
import com.lora.hyznkj.lora.util.DialogActivity;
import com.lora.hyznkj.lora.util.EventMessage;
import com.lora.hyznkj.lora.util.JsonSet;
import com.lora.hyznkj.lora.util.ListItemMessage;
import com.lora.hyznkj.lora.util.MessageInfo;
import com.lora.hyznkj.lora.util.ProgressDialogUtils;
import com.lora.hyznkj.lora.util.SettingMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kiss on 2017/8/28.
 */

public class TestAliActivity extends TitleActivity {
    private String TAG = "TestAliActivity";
    private SettingMessage message;
    private String ip;
    private String type;
    private List<ListItemMessage> listdata;
    private TestDeviceAdapter mDeviceAdapter;
    private Socket mSocketClient=null;
    private InputStream inputStream=null;//定义输入流
    private OutputStream outputStream=null;//定义输出流
    private boolean isConnect=true;
    private String getTCPString;
    private DialogActivity mDialog;
    private MessageInfo mEventMessage;
    private JsonSet mJsonSet;
    private ProgressDialogUtils dialogUtils;
    private ListView DeviceList;
    private Button DeviceConnect;
    private Button DeviceTest;
    private Button DeviceLogout;
    private Timer mCheckDelayTimer=null;
    private Timer mConnectDelayTimer=null;
    private int device_number=0;
    private TextView all_list_num;
    private int all_endpoint_id;
    private int now_point=1;
    private String[] MacArray;
    private int now_mac=0;
    @Override
    protected void onCreate(Bundle mBundle){
        super.onCreate(mBundle);
        setBackVisible();
        setTitle("测试");
        setContentView(R.layout.test_ali);
        init();
        setlistener();
        getConnect();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregiestEventBus();

    }
    private void init(){
        message = (SettingMessage)getIntent().getSerializableExtra("message");
        ip = message.getIp();
        type = message.getType();
        set_type(type);
        regiestEventBus();
        dialogUtils = new ProgressDialogUtils(this);
        mEventMessage = new MessageInfo();
        mJsonSet = new JsonSet();
        listdata = new ArrayList<>();
        getview();
        mDeviceAdapter = new TestDeviceAdapter(this,listdata);
        DeviceList.setAdapter(mDeviceAdapter);
    }
    private void set_type(String type){
        switch (type){
            case "1102":
                all_endpoint_id=2;
                break;
            case "1103":
                all_endpoint_id=3;
                break;
            default:
                all_endpoint_id =1;
                break;
        }
    }
    private void getview(){
        DeviceList = (ListView)findViewById(R.id.device_list);
        DeviceConnect = (Button)findViewById(R.id.Network_connect);
        DeviceTest = (Button)findViewById(R.id.test);
        DeviceLogout = (Button)findViewById(R.id.out_line);
        all_list_num = (TextView) findViewById(R.id.all_list_num);
    }
    private void setlistener(){
        DeviceTest.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                make_dialog("测试前请确认所有插座设备处于关闭状态",3);

            }
        });
        DeviceConnect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                make_dialog("配网前请确认所有待测试设备处于入网状态",2);
            }
        });
        DeviceLogout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                send_data(mJsonSet.base_format(1,mJsonSet.data_send("0000000000000000",1,mEventMessage.command_type_device_logout,mJsonSet.control_command("","",0))));
            }
        });
    }
    private void send_test_data(List<ListItemMessage> list,String state){
        MacArray = new String[list.size()];
            for(int i = list.size() - 1; i >= 0; i--){
                ListItemMessage item = list.get(i);
                MacArray[i] = item.getMac();
                timer_start(state);
                Log.i(TAG,"send_data:"+item.getMac());
            }
    }
    private void timer_connect(int time){
        dialogUtils.setMessage("设备搜索中，请稍后。。。");
        dialogUtils.sendMessage(1);
        if(mConnectDelayTimer==null){
            mConnectDelayTimer = new Timer();
            mConnectDelayTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventMessage(mEventMessage.connet_finish));
                    mConnectDelayTimer=null;
                }
            },1000*time);
        }
    }
    private void timer_start(final String state){
        if(mCheckDelayTimer ==  null){
            mCheckDelayTimer = new Timer();
                mCheckDelayTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        send_data(mJsonSet.base_format(1,mJsonSet.data_send(MacArray[now_mac], now_point,mEventMessage.command_type_set_light_state,mJsonSet.control_command(type,state,1))));
                        if(now_point==all_endpoint_id){
                            timer_pause();
                            if(now_mac==listdata.size()-1&&state.equals("1")&&(now_point==all_endpoint_id)){
                                EventBus.getDefault().post(new EventMessage(mEventMessage.first_finish));
                            }else if(now_mac==listdata.size()-1&&state.equals("0")&&(now_point==all_endpoint_id)){
                                EventBus.getDefault().post(new EventMessage(mEventMessage.second_finish));
                            }
                            now_point=0;
                            if(now_mac==listdata.size()-1){
                                now_mac=0;
                            }else{
                                now_mac++;
                                timer_start(state);
                            }

                        }
                        now_point++;
                    }
                }, 1000,1000);
        }
    }

    private void timer_pause(){
        if(mCheckDelayTimer != null){
            mCheckDelayTimer.cancel();
            mCheckDelayTimer = null;
        }
    }
    private void getlistdata(int serial,String mac,String state,String isPass){
        boolean isnew = true;
        for(int i = listdata.size() - 1; i >= 0; i--){
            ListItemMessage item1 = listdata.get(i);
            if(mac.equals(item1.getMac())){
                isnew=false;
            }else{

            }
        }
        if(isnew){
            ListItemMessage item = new ListItemMessage();
            item.setSerial(serial);
            item.setState(state);
            item.setMac(mac);
            item.setIsPass(isPass);
            listdata.add(item);
            EventBus.getDefault().post(new EventMessage(mEventMessage.find_new_device));
        }

    }

    private void getConnect(){
        dialogUtils.setMessage("正在连接，请稍后。。。");
        dialogUtils.sendMessage(1);
        if (isConnect == true)
        {
            isConnect = false;
            mSocketClient = null;
            Connect_Thread connect_Thread = new Connect_Thread();
            connect_Thread.start();
        }
        else
        {
            isConnect = true;//置为true
            try
            {

                mSocketClient.close();//关闭连接
                mSocketClient=null;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private class Connect_Thread extends Thread{
        public void run(){
            try
            {
                if (mSocketClient == null)
                {
                    int start = ip.indexOf(":");
                    String sIP = ip.substring(0, start);
                    String sPort = ip.substring(start+1);
                    int port = Integer.parseInt(sPort);
                    mSocketClient = new Socket(sIP,port);
                    Receive_Thread receive_Thread = new Receive_Thread();
                    receive_Thread.start();
                }
            }catch (Exception e){
                EventBus.getDefault().post(new EventMessage(mEventMessage.tcp_connect_lose));
                e.printStackTrace();
            }
        }
    }
    private void send_data(JSONObject send_data){
        try
        {
            outputStream = mSocketClient.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(send_data.toString());
            printWriter.flush();

        }
        catch (Exception e)
        {
            EventBus.getDefault().post(new EventMessage(mEventMessage.tcp_connect_lose));
            e.printStackTrace();
        }
    }
    private class Receive_Thread extends Thread{
        public void run(){
            try
            {
                dialogUtils.sendMessage(0);
                EventBus.getDefault().post(new EventMessage(mEventMessage.getTcp_connect_success));
                while (!isConnect)
                {
//                    InputStream inputStream = mSocketClient.getInputStream();
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    //读取流中的Json数据
//                    String line=null;
//                    line = bufferedReader.readLine();
//                    while (line!= null) {
//                        getTCPString = line;
//
//                    }

                    final byte[] buffer = new byte[1024];//创建接收缓冲区

                    inputStream = mSocketClient.getInputStream();
                    final int len = inputStream.read(buffer);//数据读出来，并且返回数据的长度
                    runOnUiThread(new Runnable()//不允许其他线程直接操作组件，用提供的此方法可以
                    {
                        public void run()
                        {
                            getTCPString = new String(buffer,0,len-1);

                            Log.i(TAG,"get_data:"+getTCPString);
                            choose_get_data(getTCPString);

                        }
                    });
                }
            }
            catch (IOException e)
            {
                EventBus.getDefault().post(new EventMessage(mEventMessage.tcp_connect_lose));
                e.printStackTrace();
            }
        }
    }
    private void make_dialog(String title,final int type){
        mDialog = new DialogActivity(this,title,new DialogActivity.LeaveMyDialogListener(){

            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.aler_ok:
                        dialog_click_ok(type);
                        mDialog.dismiss();
                        break;
                    case R.id.aler_cancel:
                        dialog_click_cancel(type);
                        mDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
        mDialog.show();
        mDialog.setCancelable(false);
    }
    private void dialog_click_ok(int type){
        switch (type){
            case 1:  //连接失败
                isConnect=true;
                getConnect();
                break;
            case 2:  //确认配网
                send_connect_data();
                timer_connect(30);
                break;
            case 3:
                in_test();
                send_test_data(listdata,"1");
                break;
            case 4:
                send_test_data(listdata,"0");
                break;
            case 5:
                test_finish();
                break;
            default:
                break;
        }
    }
    private void dialog_click_cancel(int type){
        switch (type){
            case 1:  //连接失败取消
                finish();
                break;
            default:
                break;
        }
    }
    private void send_connect_data(){
        send_data(mJsonSet.base_format(1,mJsonSet.data_send("0000000000000000",1,mEventMessage.command_type_get_pass_network,mJsonSet.control_command("","",2))));
    }
    private void choose_get_data(String data){
        int frameType;
        try {
            JSONObject get_data = new JSONObject(data);
            JSONObject Device_data = new JSONObject();
            frameType = get_data.getInt("frameType");
            Device_data = get_data.getJSONObject("data");
            switch (frameType){
                case 2:      //frameType_get_device_network
                    get_device_info(Device_data);
                    break;
                case 3:  //frameType_get_device_network_out
                    device_outline(Device_data);
                    break;
                case 4:   //frameType_get_device_state
                    get_device_state(Device_data);
                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void get_device_info(JSONObject data){
        String mac = null;
        try {
            mac = data.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getlistdata(1,mac,"--","未测试");
    }
    private void device_outline(JSONObject data){
        String mac = null;
        try {
            mac = data.getString("address");
            remove1(listdata,mac);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    private static void remove1(List<ListItemMessage> list, String mac){
        for(int i = list.size() - 1; i >= 0; i--){
            ListItemMessage item = list.get(i);
            if(mac.equals(item.getMac())){
                list.remove(item);
                EventBus.getDefault().post(new EventMessage(1));
            }
        }
    }
    private void in_test(){
        for(int i = listdata.size() - 1; i >= 0; i--) {
            ListItemMessage item = listdata.get(i);
            item.setIsPass("测试中");
        }
        EventBus.getDefault().post(new EventMessage(1));
    }
    private void test_finish(){
        for(int i = listdata.size() - 1; i >= 0; i--) {
            ListItemMessage item = listdata.get(i);
            item.setIsPass("测试结束");
        }
        EventBus.getDefault().post(new EventMessage(1));
    }
    private void change_device_state(List<ListItemMessage> list,String mac,String state,int endpoint_id){
        for(int i = list.size() - 1; i >= 0; i--){
            ListItemMessage item = list.get(i);
            if(mac.equals(item.getMac())){
                if(state.equals("1")){
                    item.setState("回路"+endpoint_id+"开");
                }else if(state.equals("0")){
                    item.setState("回路"+endpoint_id+"关");
                }
                EventBus.getDefault().post(new EventMessage(1));
            }
        }
    }
    private void get_device_state(JSONObject data){
        String mac = null;
        int endpoint_id;
        String state_type = null;
        JSONObject Status = new JSONObject();
        try {
            mac = data.getString("address");
            endpoint_id = data.getInt("endpoint_id");
            state_type = data.getString("state_type");
            Status = data.getJSONObject("Status");
            get_Starus(Status,state_type,endpoint_id,mac);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    private void get_Starus(JSONObject data,String type,int endpoint_id,String mac){
        if(type.equals(mEventMessage.state_type_switch)){
            String Status = null;
            try{
                 Status = data.getString("State");
            }catch (JSONException e){
                e.printStackTrace();
            }
            change_device_state(listdata,mac,Status,endpoint_id);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventMessage messageEvent){
        int message = messageEvent.getMessage();
        if(message==mEventMessage.tcp_connect_lose){
            dialogUtils.sendMessage(0);
            make_dialog("连接已断开是否重新连接？",1);
        }else if(message == mEventMessage.getTcp_connect_success){
            Toast.makeText(TestAliActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
            send_data(mJsonSet.base_format(1,mJsonSet.data_send("0000000000000000",1,mEventMessage.command_type_make_network,mJsonSet.control_command("","",0))));
        }else if(message==mEventMessage.find_new_device){
            mDeviceAdapter = new TestDeviceAdapter(this,listdata);
            device_number = listdata.size();
            all_list_num.setText("当前列表共有:"+device_number+"个");
            DeviceList.setAdapter(mDeviceAdapter);
            mDeviceAdapter.notifyDataSetChanged();
        }else if(message==mEventMessage.first_finish){
            make_dialog("一轮测试完毕，请确认设备是否处于打开状态",4);
        }else if(message == mEventMessage.second_finish){
            make_dialog("二轮测试完毕，请确认设备是否处于关闭状态,测试结束,请将所有设备复位后再进行下一轮测试",5);
        }else if(message == mEventMessage.connet_finish){
            dialogUtils.sendMessage(0);
            Toast.makeText(TestAliActivity.this,"搜索完毕，请确认设备数量",Toast.LENGTH_SHORT).show();
        }
    }
}
