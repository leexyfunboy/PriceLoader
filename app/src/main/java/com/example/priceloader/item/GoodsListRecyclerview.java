package com.example.priceloader.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.priceloader.R;

public class GoodsListRecyclerview extends RecyclerView.ViewHolder{


    public View rootView;
    public CheckBox checkbox;
    public TextView index;
    public TextView id;
    public TextView name;
    public TextView singlePrice;
    public TextView count;
    public TextView finalPrice;
    private RecyclerView.Adapter adapter;

    public GoodsListRecyclerview(@NonNull View itemView, RecyclerView.Adapter adapter) {
        super(itemView);
        rootView = itemView.findViewById(R.id.goodsList_rootView);
        this.checkbox = itemView.findViewById(R.id.item_goodsList_checkbox);
        this.index = itemView.findViewById(R.id.item_goodsList_index);
        this.id = itemView.findViewById(R.id.item_goodsList_id);
        this.name = itemView.findViewById(R.id.item_goodsList_name);
        this.singlePrice = itemView.findViewById(R.id.item_goodsList_singlePrice);
//        this.count = itemView.findViewById(R.id.item_goodsList_count);
//        this.finalPrice = itemView.findViewById(R.id.item_goodsList_finalPrice);
        this.adapter = adapter;
        //为组件添加点击事件
//        rootView.setOnClickListener(view ->{
//            int i = (int)(Math.random()*(datalist.size()+1));
//            Person person = new Person(datalist.get(i).getName(),datalist.get(i).getDesc());
//            adapter.notifyItemInserted(2);
//            datalist.add(2,person);
//            adapter.notifyItemRangeChanged(2,adapter.getItemCount());
//        });
    }
}
