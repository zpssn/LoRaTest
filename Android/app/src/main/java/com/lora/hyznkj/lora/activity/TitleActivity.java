package com.lora.hyznkj.lora.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lora.hyznkj.lora.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by kiss on 2017/8/28.
 */

public class TitleActivity extends BaseActivity {

    private FrameLayout mBody;
    private ImageButton mReturnButton;
    protected ImageButton mDeleteButton;
    protected ImageButton mAddButton;
    private Button mRightButton;
    protected TextView mRefresh;
    protected TextView mDeleteall;
    private TextView mTitle;
    private RelativeLayout title_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.title_layout);

        findView();

        setListener();
    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mBody, true);
    }

    private void findView() {
        mBody = (FrameLayout) findViewById(R.id.body);
        mReturnButton = (ImageButton) findViewById(R.id.btn_return);
        mDeleteButton = (ImageButton) findViewById(R.id.btn_delete);
        mAddButton = (ImageButton) findViewById(R.id.btn_add_device);
        mRightButton = (Button) findViewById(R.id.btn_right);
        mTitle = (TextView) findViewById(R.id.title);
        mRefresh=(TextView) findViewById(R.id.refresh);
        mDeleteall=(TextView) findViewById(R.id.deleteall);
        title_layout = (RelativeLayout)findViewById(R.id.title_layout);
    }

    private void setListener(){
        mReturnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                back();

            }
        });
    }

    public void setRightButtonClickListener(int resId, View.OnClickListener onSingleClickListener){
        mRightButton.setVisibility(View.VISIBLE);
        mRightButton.setText(resId);
        mRightButton.setOnClickListener(onSingleClickListener);
    }

    public void setBackVisible(){
        mReturnButton.setVisibility(View.VISIBLE);
    }

    public void setRefreshVisible(){
        mRefresh.setVisibility(View.VISIBLE);
    }

    public void setTitleGone(){
        title_layout.setVisibility(View.GONE);
    }
    public void setAddButtonVisible(){
        mAddButton.setVisibility(View.VISIBLE);
    }

    public void setDeleteButtonVisible(){
        mDeleteButton.setVisibility(View.VISIBLE);
    }

    public void setDeleteallVisible(){
        mDeleteall.setVisibility(View.VISIBLE);
    }

    public void setTitle(int resId) {
        mTitle.setText(resId);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void regiestEventBus(){
        EventBus.getDefault().register(this);
    }

    public void unregiestEventBus(){
        EventBus.getDefault().unregister(this);
    }
    public void back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
    }

}
