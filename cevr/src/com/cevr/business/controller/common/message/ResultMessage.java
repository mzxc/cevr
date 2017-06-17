package com.cevr.business.controller.common.message;

public class ResultMessage {
    
    private String code;
    
    private String msg;
    
    private boolean ifSuccess;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public boolean isIfSuccess() {
        return ifSuccess;
    }
    
    public void setIfSuccess(boolean ifSuccess) {
        this.ifSuccess = ifSuccess;
    }
    
    private ResultMessage(boolean ifSuccess, String code, String msg) {
        super();
        this.ifSuccess = ifSuccess;
        this.code = code;
        this.msg = msg;
    }
    
    public static ResultMessage initMsg(boolean ifSuccess, String code, String msg) {
        return new ResultMessage(ifSuccess, code, msg);
    }
}
