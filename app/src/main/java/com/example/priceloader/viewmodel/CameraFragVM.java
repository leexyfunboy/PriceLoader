package com.example.priceloader.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.priceloader.entity.GoodsListItem;

import java.util.List;

public class CameraFragVM extends ViewModel {

    MutableLiveData<List<GoodsListItem>> data;

    public MutableLiveData<List<GoodsListItem>> getData() {
        if(data==null){
            data = new MutableLiveData<>();
        }
        return data;
    }
}
