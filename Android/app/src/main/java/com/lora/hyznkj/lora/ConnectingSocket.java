package com.lora.hyznkj.lora;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by kiss on 16/8/30.
 */

public class ConnectingSocket extends Activity {

    private String recvText;

    private Context mContext;
    private boolean isConnecting = false;

    private Thread mThreadClient = null;

    private Socket mSocketClient = null;

    static BufferedReader mBufferedReaderClient	= null;
    static PrintWriter mPrintWriterClient = null;
    private  String recvMessageClient = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecting);
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
            Toast.makeText(this, "连接Server成功", Toast.LENGTH_SHORT).show();
        }
        else
        {
            isConnecting = true;
            Toast.makeText(this, "连接Server中请稍后", Toast.LENGTH_SHORT).show();

            mThreadClient = new Thread(mRunnable);
            mThreadClient.start();
        }


    }


    private Runnable	mRunnable	= new Runnable()
    {
        public void run()
        {
            String msgText ="172.30.13.167:9999";//"192.168.4.1:9876";
            if(msgText.length()<=0)
            {
                Toast.makeText(mContext, "IP不能为空！", Toast.LENGTH_SHORT).show();

                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
                return;
            }
            int start = msgText.indexOf(":");
            if( (start == -1) ||(start+1 >= msgText.length()) )
            {
                Toast.makeText(mContext, "IP地址不合法！", Toast.LENGTH_SHORT).show();
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
                //StartMouseButton.setVisibility(View.VISIBLE);
                //isConnecting = true;

                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
                Intent intent = new Intent();
                intent.setClass(ConnectingSocket.this, DeviceListActivity.class);
                startActivity(intent);
                //break;
            }
            catch (Exception e)
            {
                Toast.makeText(mContext, "连接IP异常！", Toast.LENGTH_SHORT).show();
                //StartMouseButton.setVisibility(View.INVISIBLE);

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
                        recvMessageClient = getInfoBuff(buffer, count);
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                }
                catch (Exception e)
                {
                    recvMessageClient = "接收异常:" + e.getMessage();//消息换行
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
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                //recvText.append("Client: "+recvMessageServer);	// 刷新
            }
            else if(msg.what == 1)
            {
                setMessage(recvMessageClient);	// 刷新
                Log.e("ConnectingSocket",getMessage());
                //Log.e("ConnectingSocket",recvMessageClient);
            }
        }
    };

    public void setMessage(String recvText){
        this.recvText = recvText;
    }

    public String getMessage(){
        return this.recvText;
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
    }
}