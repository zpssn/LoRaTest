package com.lora.hyznkj.lora.util;

import java.io.Serializable;

/**
 * Created by kiss on 2017/8/28.
 */

public class SettingMessage implements Serializable {
    private String ip;
    private String type;

    public String getIp(){
        return ip;
    }
    public void setIp(String ip){
        this.ip=ip;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
}
