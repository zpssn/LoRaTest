# LoRaTest third time
新增 heartbeat 心跳每隔 3秒一次
private Handler handler1 = new Handler(){
     public void handleMessage(Message msg2){
         super.handleMessage(msg2);
         if( isConnecting && mSocketClient!=null && msg2.what == 1){
             mPrintWriterClient.print("heartbeat"+"\n");//发送给服务器
             mPrintWriterClient.flush();
         }

     }
   };

   private Timer timer = new Timer(true);
   private TimerTask task =new TimerTask(){
     public void run(){
         if(!isSending)
         {
             Message msg2 = new Message();
             msg2.what = 1;
             handler1.sendMessage(msg2);
         }
     }
   };
   在oncreat方法中加timer.schedule(task,0,3000);

# LoRaTest
LoRa Project
# LoRaSecond
this for second time
