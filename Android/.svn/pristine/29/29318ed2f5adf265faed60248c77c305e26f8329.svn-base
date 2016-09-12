package com.lora.hyznkj.lora.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lora.hyznkj.lora.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hyznkj on 16/7/22.
 */
    public class DeviceListAdapter extends BaseAdapter {
    public static int SUM=0;
    /*添加一个得到数据的方法，方便使用*/
    public static ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    	 /*为动态数组添加数据*/

        for(int i=0;i<SUM;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "子设备"+i);
            listItem.add(map);
        }
        return listItem;

    }
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        /*构造函数*/
        public DeviceListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return getDate().size();//返回数组的长度
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
            holder.title.setText(getDate().get(position).get("ItemTitle").toString());




            return convertView;
        }
        class ViewHolder {
            TextView title;
        }
    }
