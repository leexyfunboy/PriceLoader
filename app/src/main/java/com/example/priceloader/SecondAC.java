package com.example.priceloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.priceloader.adapter.GoodsListRVAdapter;
import com.example.priceloader.entity.GoodsListItem;

import java.util.ArrayList;
import java.util.List;

public class SecondAC extends AppCompatActivity {

    private final String TAG = "SecondAC";
    private List<GoodsListItem> list_goods = new ArrayList<>();
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private GoodsListRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
        layoutManager = new LinearLayoutManager(this);
        rv = findViewById(R.id.secondAC_recyclerView);

        adapter = new GoodsListRVAdapter(this,list_goods);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }

    public void initData(){
        for(int i=0;i<5;i++){
            GoodsListItem goodsListItem = new GoodsListItem();
            goodsListItem.setId(String.valueOf(i));
            goodsListItem.setName(String.valueOf(i)+"asd");
            goodsListItem.setSinglePrice(11.2*i);
            goodsListItem.setFinalPrice(12.2*i);
            goodsListItem.setCount(i);
            list_goods.add(goodsListItem);
        }
    }
}