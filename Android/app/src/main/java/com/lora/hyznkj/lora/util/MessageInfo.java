package com.lora.hyznkj.lora.util;

/**
 * Created by kiss on 2017/8/30.
 */

public class MessageInfo {
    public int tcp_connect_lose = -1;
    public int getTcp_connect_success = 0;
    public int find_new_device = 1;
    public int first_finish = 2;
    public int second_finish = 3;
    public int connet_finish = 4;

    public String command_type_make_network = "0x01F0";
    public String command_type_get_pass_network = "0x0101";
    public String command_type_get_device_state = "0x0103";
    public String command_type_set_elc_state = "0x0105";
    public String command_type_set_light_state = "0x0106";
    public String command_type_device_logout = "0x010E";

    public String type_one_socket = "1001";
    public String type_one_switch = "1101";
    public String type_two_switch = "1102";
    public String type_three_switch = "1103";

    public String state_type_network_info = "0x0200";
    public String state_type_socket = "0x0202";
    public String state_type_switch = "0x0207";
}
