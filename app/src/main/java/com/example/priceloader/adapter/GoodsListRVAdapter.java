package com.example.priceloader.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.priceloader.R;
import com.example.priceloader.activity.DetailPageAC;
import com.example.priceloader.activity.ListAC;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.item.GoodsListRecyclerview;

import java.util.ArrayList;
import java.util.List;

public class GoodsListRVAdapter extends RecyclerView.Adapter<GoodsListRecyclerview> {

    private final String TAG = "GoodsListRVAdapter";
    private Context context;
    private List<GoodsListItem> list_goods;
    public  List<String> arr_id = new ArrayList<>();

    public GoodsListRVAdapter(Context context, List<GoodsListItem> list_goods) {
        this.context = context;
        this.list_goods = list_goods;
    }

    public void setList_goods(List<GoodsListItem> list_goods) {
        this.list_goods = list_goods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsListRecyclerview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_goodslist,parent,false);
        return new GoodsListRecyclerview(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsListRecyclerview holder, int position) {
        holder.checkbox.setChecked(list_goods.get(position).isSelected());
        holder.index.setText(String.valueOf(position+1));
        holder.id.setText(list_goods.get(position).getId());
        holder.name.setText(list_goods.get(position).getName());
        holder.singlePrice.setText(String.valueOf(list_goods.get(position).getSinglePrice()));
//        holder.count.setText(String.valueOf(list_goods.get(position).getCount()));
//        holder.finalPrice.setText(String.valueOf(list_goods.get(position).getFinalPrice()));

        holder.rootView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("id",holder.id.getText().toString());
            Intent intent = new Intent(context,DetailPageAC.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        holder.checkbox.setOnClickListener(v->{
            if(holder.checkbox.isChecked()){
                arr_id.add(holder.id.getText().toString());
            }else{
                arr_id.remove(holder.id.getText().toString());
            }
        });


    }

    public void cleanList_id(){
        arr_id = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return list_goods.size();
    }
}


