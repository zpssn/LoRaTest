package com.lora.hyznkj.lora.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kiss on 2017/8/31.
 */

public class JsonSet {
    public JSONObject base_format(int type,JSONObject data){
        JSONObject base_data = new JSONObject();
        try {
            base_data.put("frameType",type);
            base_data.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return base_data;
    }
    public JSONObject data_send(String address,int endpoint_id,String command_type,JSONObject command){
        JSONObject data_send = new JSONObject();
        try{
            data_send.put("address",address);
            data_send.put("endpoint_id",endpoint_id);
            data_send.put("command_type",command_type);
            data_send.put("command",command);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return data_send;
    }
    public JSONObject control_command(String type,String state,int change_type){
        JSONObject command = new JSONObject();
        if(change_type ==1){
            try{
                command.put("Type",type);
                command.put("State",state);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else if(change_type == 0){

        }else if(change_type ==2){
            try {
                command.put("Duration","30");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return command;
    }
}
