package com.udacity.jwdnd.course1.cloudstorage.data;

import org.springframework.stereotype.Service;

@Service
public class Message {

    private boolean error;
    private boolean success;
    private String msg;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
