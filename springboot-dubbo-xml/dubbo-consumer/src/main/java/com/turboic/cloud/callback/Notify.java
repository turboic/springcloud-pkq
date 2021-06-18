package com.turboic.cloud.callback;

interface Notify {
    void onReturn(String msg, String parameter);
    void onThrow(Throwable ex, String parameter);
}