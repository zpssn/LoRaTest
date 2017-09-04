package com.lora.hyznkj.lora.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;


public class ProgressDialogUtils {

	private ProgressDialog dialog ;
	
	private Activity ctx ;
	
	public final static int SHOW = 1 , DISMISS  = 0 ;
	
	public ProgressDialogUtils(Activity ctx){
		this.ctx = ctx ;
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW:
				if(Utility.isEmpty(dialog)){
					dialog = new ProgressDialog(ctx);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setMessage("正在加载数据。。。。。。");
					dialog.setCancelable(false);
				}
				if(!dialog.isShowing()){
					dialog.show();	
				}
				break;
			case DISMISS:
				if(!Utility.isEmpty(dialog) && dialog.isShowing()) {
					try{
						dialog.dismiss(); 
					}catch(Exception exp){

					}
				}
				break;
			default:
				break;
			}
		};
	};
	public void sendMessage(int what){
		handler.sendEmptyMessage(what);
	}
	
	public void setMessage(String message){
		if(!Utility.isEmpty(dialog)){
			dialog.setMessage(message);
		}else{
			dialog = new ProgressDialog(ctx);
			dialog.setMessage(message);
		}
	}
	
}
