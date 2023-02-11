package com.example.priceloader.fragment;

import android.os.Handler;
import android.util.Log;

import com.example.priceloader.entity.EventMes;
import com.google.zxing.Result;
import com.king.zxing.CaptureFragment;

import org.greenrobot.eventbus.EventBus;

public class MyCaptureFragment extends CaptureFragment {


    @Override
    public boolean onScanResultCallback(Result result) {
        String id = result.getText();
        Log.e("MyCaptureFragment::id==",id);

        EventMes msg = new EventMes(id);//这里的EventMessage是自己定义的用于传输数据的类
        EventBus.getDefault().post(msg);	//简单情况下常用方式
        return true;
    }
}
