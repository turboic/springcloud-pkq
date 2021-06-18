package com.turboic.cloud.callback;
public class NotifyImpl implements Notify {
    @Override
    public void onReturn(String msg, String parameter) {
        System.err.println("参数:"+parameter);
        System.err.println("返回内容:"+msg);
    }
    @Override
    public void onThrow(Throwable ex, String parameter) {
        System.err.println("异常参数:"+parameter);
        System.err.println("返回内容:"+ex.getMessage());
    }
}