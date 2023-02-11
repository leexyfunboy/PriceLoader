package com.example.priceloader.utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpTool {

    public OkHttpTool getInstance(){
        return new OkHttpTool();
    }

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public void getData(){
        FormBody fb = new FormBody.Builder()
                .add("column","xxx")
                .build();
//
//        Request request = new Request()
//                .url()

    }
}
