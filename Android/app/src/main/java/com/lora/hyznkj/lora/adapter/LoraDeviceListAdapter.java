package com.lora.hyznkj.lora.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lora.hyznkj.lora.DeviceListActivity;
import com.lora.hyznkj.lora.R;


/**
 * Created by kiss on 16/8/30.
 */
public class LoraDeviceListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

    /*构造函数*/
    public LoraDeviceListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return DeviceListActivity.getDate().size();//返回数组的长度
    }



    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        Log.v("MyListViewBase", "getView " + position + " " + convertView);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item,
                    null);
            holder = new ViewHolder();
	                /*得到各个控件的对象*/
            holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);

            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
			 /*设置TextView显示的内容为我们存放在动态数组中的数据*/
        holder.title.setText(DeviceListActivity.getDate().get(position).get("ItemTitle").toString());




        return convertView;
    }
    class ViewHolder {
        TextView title;
    }
}
