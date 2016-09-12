package com.lora.hyznkj.lora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lora.hyznkj.lora.view.WifiConnDialog;

public class TestActivity extends Activity {
    /** Called when the activity is first created. */

    private Button startButton;
    private EditText IPText;
    private Button sendButtonClient;
    private Button sendButtonServer;
    private Button CreateButton;
    private Button recvTimeButtonClear;
    private Button sendTimeButtonClient;

    private EditText editMsgText, editMsgTextCilent;
    private TextView recvText;
    private TextView recvTime;
    private TextView sendTimes;
    private Button StartMouseButton;

    private Context mContext;
    private boolean isConnecting = false;

    private Thread mThreadClient = null;
    private Thread mThreadServer = null;
    private Socket mSocketServer = null;
    private Socket mSocketClient = null;
    static BufferedReader mBufferedReaderServer	= null;
    static PrintWriter mPrintWriterServer = null;
    static BufferedReader mBufferedReaderClient	= null;
    static PrintWriter mPrintWriterClient = null;
    private  String recvMessageClient = "";
    private  String recvMessageServer = "";
    private EditText sendtime;
    private EditText sendtiming;

    private int sendtimes=0;
    private int time=1;
    private int receivetime=0;
    private int delay;
    // public SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        String ip = getIntent().getStringExtra("ip");
        ip = ip.substring(0,9);
        IPText= (EditText)findViewById(R.id.IPText);
        IPText.setText(ip+".1:9876");
        startButton= (Button)findViewById(R.id.StartConnect);
        startButton.setOnClickListener(StartClickListener);

        recvTimeButtonClear= (Button)findViewById(R.id.RecvTimeButtonClear);
        recvTimeButtonClear.setOnClickListener(RecvTimeButtonClear);

        sendTimeButtonClient= (Button)findViewById(R.id.SendTimeButtonClient);
        sendTimeButtonClient.setOnClickListener(SendTimeButtonClient);

        editMsgTextCilent= (EditText)findViewById(R.id.clientMessageText);
        editMsgTextCilent.setText("test");

        sendtime= (EditText)findViewById(R.id.clientTimeMessageText);
        //sendtime.setText("1");

        sendtiming= (EditText)findViewById(R.id.TimeMessage);

        editMsgText= (EditText)findViewById(R.id.MessageText);
        editMsgText.setText("up");

        recvTime= (TextView)findViewById(R.id.RecvTime);
        sendTimes= (TextView)findViewById(R.id.SendTime);

        sendButtonClient= (Button)findViewById(R.id.SendButtonClient);
        sendButtonClient.setOnClickListener(SendClickListenerClient);
        sendButtonServer= (Button)findViewById(R.id.SendButtonServer);
        //sendButtonServer.setOnClickListener(SendClickListenerServer);

        CreateButton= (Button)findViewById(R.id.CreateConnect);
        CreateButton.setOnClickListener(CreateClickListener);

        recvText= (TextView)findViewById(R.id.RecvText);
        recvText.setMovementMethod(ScrollingMovementMethod.getInstance());

        StartMouseButton = (Button)findViewById(R.id.StartMouse);
        StartMouseButton.setOnClickListener(StartMouseClickListenerServer);
    }

    private OnClickListener StartClickListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (isConnecting)
            {
                isConnecting = false;
                try {
                    if(mSocketClient!=null)
                    {
                        mSocketClient.close();
                        mSocketClient = null;
                        mPrintWriterClient.close();
                        mPrintWriterClient = null;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mThreadClient.interrupt();
                StartMouseButton.setVisibility(View.GONE);
                startButton.setText("开始连接");
                IPText.setEnabled(true);
                recvText.setText("信息:\n");
            }
            else
            {
                isConnecting = true;
                startButton.setText("停止连接");
                IPText.setEnabled(false);

                mThreadClient = new Thread(mRunnable);
                mThreadClient.start();
            }
        }
    };


    private OnClickListener SendClickListenerClient = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if ( isConnecting && mSocketClient!=null)
            {
                String msgText =editMsgTextCilent.getText().toString();//取得编辑框中我们输入的内容
                if(msgText.length()<=0)
                {
                    Toast.makeText(mContext, "发送内容不能为空！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        for(int i=0;i<time;i++)
                        {

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss:SSS");
                            String str = formatter.format(new Date());
                            sendtimes++;
                            mPrintWriterClient.print(sendtimes+msgText+"\n");//发送给服务器
                            mPrintWriterClient.flush();
                            recvText.append("send: "+ msgText  + " time:"+str+"\n");	// 刷新
                            sendTimes.setText(String.valueOf(sendtimes));//显示发送次数
                            delayms();
                        }
                    }
                    catch (Exception e)
                    {
                        // TODO: handle exception
                        Toast.makeText(mContext, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
            {
                Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private OnClickListener RecvTimeButtonClear = new OnClickListener() {//计数清零按钮
        @Override
        public void onClick(View arg0) {
            receivetime=0;
            recvTime.setText("0");
            recvText.setText("");
            sendtimes=0;
            sendTimes.setText("0");

        }
    };

    private OnClickListener SendTimeButtonClient = new OnClickListener() {  //发送次数确认按钮
        @Override
        public void onClick(View arg0) {
            String  str=sendtime.getText().toString();//取得编辑框中我们输入的内容
            int times = Integer.parseInt(str);
            time = times;
            delay = Integer.parseInt(sendtiming.getText().toString());
            Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
        }
    };


    //线程:监听服务器发来的消息
    private Runnable	mRunnable	= new Runnable()
    {
        public void run()
        {
            String msgText =IPText.getText().toString();
            if(msgText.length()<=0)
            {
                //Toast.makeText(mContext, "IP不能为空！", Toast.LENGTH_SHORT).show();
                recvMessageClient = "IP不能为空！\n";//消息换行
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
                return;
            }
            int start = msgText.indexOf(":");
            if( (start == -1) ||(start+1 >= msgText.length()) )
            {
                StartMouseButton.setVisibility(View.GONE);
                recvMessageClient = "IP地址不合法\n";//消息换行
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
                return;
            }
            String sIP = msgText.substring(0, start);
            String sPort = msgText.substring(start+1);
            int port = Integer.parseInt(sPort);

            Log.d("gjz", "IP:"+ sIP + ":" + port);

            try
            {
                //连接服务器
                mSocketClient = new Socket(sIP, port);	//portnum
                //取得输入、输出流
                mBufferedReaderClient = new BufferedReader(new InputStreamReader(mSocketClient.getInputStream()));

                mPrintWriterClient = new PrintWriter(mSocketClient.getOutputStream(), true);
                StartMouseButton.setVisibility(View.GONE);
                recvMessageClient = "已经连接server!\n";//消息换行
                Message msg = new Message();
                msg.what = 1;
                //receivetime=0;
                mHandler.sendMessage(msg);
                //break;
            }
            catch (Exception e)
            {
                recvMessageClient = "连接IP异常:" + e.toString() + e.getMessage() + "\n";//消息换行
                StartMouseButton.setVisibility(View.GONE);
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
                return;
            }

            char[] buffer = new char[256];
            int count = 0;
            while (isConnecting)
            {
                try
                {
                    //if ( (recvMessageClient = mBufferedReaderClient.readLine()) != null )
                    if((count = mBufferedReaderClient.read(buffer))>0)
                    {
                        recvMessageClient = getInfoBuff(buffer, count);//消息换行
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                }
                catch (Exception e)
                {
                    recvMessageClient = "接收异常:" + e.getMessage() + "\n";//消息换行
                    StartMouseButton.setVisibility(View.GONE);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
            }
        }
    };

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss:SSS");
            String str = formatter.format(new Date());
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                recvText.append("Client: "+recvMessageServer);	// 刷新
            }
            else if(msg.what == 1)
            {
                recvText.append("receive: "+recvMessageClient  + " time:"+str+"\n");	// 刷新
                receivetime++;
                recvTime.setText(String.valueOf(receivetime));
            }
        }
    };
    //创建服务端ServerSocket对象
    private ServerSocket serverSocket = null;
    private boolean serverRuning = false;
    private OnClickListener CreateClickListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (serverRuning)
            {
                serverRuning = false;

                try {
                    if(serverSocket!=null)
                    {
                        serverSocket.close();
                        serverSocket = null;
                    }
                    if(mSocketServer!=null)
                    {
                        mSocketServer.close();
                        mSocketServer = null;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mThreadServer.interrupt();
                CreateButton.setText("创建服务");
                recvText.setText("信息:\n");
            }
            else
            {
                serverRuning = true;
                mThreadServer = new Thread(mcreateRunnable);
                mThreadServer.start();
                CreateButton.setText("停止服务");
            }
        }
    };
    //线程:监听服务器发来的消息
    private Runnable	mcreateRunnable	= new Runnable()
    {
        public void run()
        {
            try {
                serverSocket = new ServerSocket(0);

                SocketAddress address = null;
                if(!serverSocket.isBound())
                {
                    serverSocket.bind(address, 0);
                }


                getLocalIpAddress();

                //方法用于等待客服连接
                mSocketServer = serverSocket.accept();

                //接受客服端数据BufferedReader对象
                mBufferedReaderServer = new BufferedReader(new InputStreamReader(mSocketServer.getInputStream()));
                //给客服端发送数据
                mPrintWriterServer = new PrintWriter(mSocketServer.getOutputStream(),true);
                //mPrintWriter.println("服务端已经收到数据！");

                Message msg = new Message();
                msg.what = 0;
                recvMessageServer = "client已经连接上！\n";
                mHandler.sendMessage(msg);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                Message msg = new Message();
                msg.what = 0;
                recvMessageServer = "创建异常:" + e.getMessage() + e.toString() + "\n";//消息换行
                mHandler.sendMessage(msg);
                return;
            }
            char[] buffer = new char[256];
            int count = 0;
            while(serverRuning)
            {
                try
                {
                    //if( (recvMessageServer = mBufferedReaderServer.readLine()) != null )//获取客服端数据
                    if((count = mBufferedReaderServer.read(buffer))>0);
                    {
                        recvMessageServer = getInfoBuff(buffer, count) + "\n";//消息换行
                        Message msg = new Message();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    }
                }
                catch (Exception e)
                {
                    recvMessageServer = "接收异常:" + e.getMessage() + "\n";//消息换行
                    Message msg = new Message();
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    return;
                }
            }
        }
    };

    private void delayms(){

        try{
            Thread.sleep(delay);

        } catch(InterruptedException e){
            e.printStackTrace();

        }
    }
    public String getLocalIpAddress()
    {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();	enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //if (!inetAddress.isLoopbackAddress())
                    {
                        //return inetAddress.getHostAddress();
                        //recvMessage = inetAddress.getHostAddress()+ "isAnyLocalAddress: "+inetAddress.isAnyLocalAddress()
                        //+ "isLinkLocalAddress: "+inetAddress.isLinkLocalAddress()
                        //+ "isSiteLocalAddress: "+inetAddress.isSiteLocalAddress()+"\n";
                        //mHandler.sendMessage(mHandler.obtainMessage());
                        //if(inetAddress.isSiteLocalAddress())
                        {
                            recvMessageServer += "请连接IP："+inetAddress.getHostAddress()+":"
                                    + serverSocket.getLocalPort()+ "\n";
                            //Message msg = new Message();
                            //msg.what = 0;
                            //mHandler.sendMessage(msg);
                        }
                    }
                }
            }
        }
        catch (SocketException ex) {
            recvMessageServer = "获取IP地址异常:" + ex.getMessage() + "\n";//消息换行
            Message msg = new Message();
            msg.what = 0;
            mHandler.sendMessage(msg);
        }
        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessage(msg);
        return null;
    }

    private String getInfoBuff(char[] buff, int count)
    {
        char[] temp = new char[count];
        for(int i=0; i<count; i++)
        {
            temp[i] = buff[i];
        }
        return new String(temp);
    }

    private OnClickListener StartMouseClickListenerServer = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if ( (serverRuning && mSocketServer!=null) || (isConnecting && mSocketClient!=null) )
            {
                Intent intent = new Intent();
                intent.setClass(TestActivity.this, mouseActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(mContext, "没有连接", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onDestroy() {
        super.onDestroy();
        if (isConnecting)
        {
            isConnecting = false;
            try {
                if(mSocketClient!=null)
                {
                    mSocketClient.close();
                    mSocketClient = null;

                    mPrintWriterClient.close();
                    mPrintWriterClient = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mThreadClient.interrupt();
        }
        if (serverRuning)
        {
            serverRuning = false;
            try {
                if(serverSocket!=null)
                {
                    serverSocket.close();
                    serverSocket = null;
                }
                if(mSocketServer!=null)
                {
                    mSocketServer.close();
                    mSocketServer = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mThreadServer.interrupt();
        }
    }
}