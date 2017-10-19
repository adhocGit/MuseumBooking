package com.museumbooking.model;

/**
 * Created by Aswathy_G on 9/30/2017.
 */

public class UserLoginModel {
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String userid;
    String msg;
}
