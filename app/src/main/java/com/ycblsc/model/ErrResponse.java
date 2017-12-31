package com.ycblsc.model;

/**
 * Created by lm on 2017/11/22.
 * Description：错误信息
 */
public class ErrResponse
{
    private static final String TAG = "ErrResponse";
    private int ReturnCode;
    private String ReturnMessage;
    private String ReturnData;

    public int getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(int returnCode) {
        ReturnCode = returnCode;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        ReturnMessage = returnMessage;
    }

    public String getReturnData() {
        return ReturnData;
    }

    public void setReturnData(String returnData) {
        ReturnData = returnData;
    }
}
