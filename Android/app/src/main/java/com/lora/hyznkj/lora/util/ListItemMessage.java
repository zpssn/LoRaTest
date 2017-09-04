package com.lora.hyznkj.lora.util;

import java.io.Serializable;

/**
 * Created by kiss on 2017/8/28.
 */

public class ListItemMessage implements Serializable {
    private int serial;
    private String mac;
    private String state;
    private String isPass;

    public void setSerial(int serial){
        this.serial = serial;
    }
    public int getSerial(){
        return serial;
    }
    public void setMac(String mac){
        this.mac=mac;
    }
    public String getMac(){
        return mac;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return state;
    }
    public void setIsPass(String isPass){
        this.isPass=isPass;
    }
    public String getIsPass(){
        return isPass;
    }
}
