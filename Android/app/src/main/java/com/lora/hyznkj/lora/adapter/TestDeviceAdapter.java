package com.lora.hyznkj.lora.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lora.hyznkj.lora.R;
import com.lora.hyznkj.lora.util.ListItemMessage;

import java.util.List;

/**
 * Created by kiss on 2017/8/28.
 */

public class TestDeviceAdapter extends BaseAdapter {
    private List<ListItemMessage> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public TestDeviceAdapter(Context mcontext,List<ListItemMessage> listdata){
        mList = listdata;
        mContext = mcontext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.device_list_item,
                    null);
            holder = new ViewHolder();
	                /*得到各个控件的对象*/
            holder.serial = (TextView) convertView.findViewById(R.id.serial);
            holder.mac = (TextView) convertView.findViewById(R.id.device_mac);
            holder.state = (TextView) convertView.findViewById(R.id.device_state);
            holder.ispass = (TextView) convertView.findViewById(R.id.ispass);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        ListItemMessage mDeviceItem = mList.get(position);
        holder.serial.setText(mDeviceItem.getSerial()+"、");
        holder.mac.setText("mac:"+mDeviceItem.getMac());
        holder.state.setText("状态："+mDeviceItem.getState());
        holder.ispass.setText("测试结果："+mDeviceItem.getIsPass());
        return convertView;
    }
    class ViewHolder {
        TextView serial;
        TextView mac;
        TextView state;
        TextView ispass;
    }
}
