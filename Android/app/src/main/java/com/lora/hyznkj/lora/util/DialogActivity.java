package com.lora.hyznkj.lora.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.lora.hyznkj.lora.R;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by kiss on 2017/8/30.
 */

public class DialogActivity extends Dialog implements View.OnClickListener {
    private Context mContext;
    private String TitleName;
    private LeaveMyDialogListener listener;
    private Button btnok;
    private Button btncancle;
    private TextView titlename;

    public interface LeaveMyDialogListener{
        public void onClick(View view);
    }
    public DialogActivity(Context context,String Name,LeaveMyDialogListener listener) {
        super(context);
        this.mContext = context;
        this.TitleName = Name;
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_activity);
        btncancle = (Button)findViewById(R.id.aler_cancel);
        btnok     = (Button)findViewById(R.id.aler_ok);
        titlename = (TextView)findViewById(R.id.aler_descr);
        btncancle.setOnClickListener(this);
        btnok.setOnClickListener(this);
        titlename.setText(TitleName);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        listener.onClick(v);
    }
}
