package com.example.priceloader.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.priceloader.R;

public class CartListRecyclerView extends RecyclerView.ViewHolder{


    View rootView;
    public CheckBox checkbox;
    public TextView id;
    public TextView name;
    public TextView singlePrice;
    public TextView count;
    public ImageButton bt_reduce;
    public ImageButton bt_add;
    public TextView finalPrice;
    private RecyclerView.Adapter adapter;

    public CartListRecyclerView(@NonNull View itemView, RecyclerView.Adapter adapter) {
        super(itemView);
        this.checkbox = itemView.findViewById(R.id.item_cartlist_checkbox);
        this.id = itemView.findViewById(R.id.item_cartlist_id);
        this.name = itemView.findViewById(R.id.item_cartlist_name);
        this.singlePrice = itemView.findViewById(R.id.item_cartlist_singlePrice);
        this.count = itemView.findViewById(R.id.item_cartlist_ed_count);
        this.bt_reduce = itemView.findViewById(R.id.item_cartlist_bt_reduce);
        this.bt_add = itemView.findViewById(R.id.item_cartlist_bt_add);
//        this.finalPrice = itemView.findViewById(R.id.item_cart);
        this.adapter = adapter;
        //为组件添加点击事件

//        itemView.findViewById(R.id.item_cartlist_bt_add).setOnClickListener(v->{
//            int c = Integer.parseInt(count.getText().toString()) + 1;
//            count.setText(String.valueOf(c));
//        });
//
//        itemView.findViewById(R.id.item_cartlist_bt_reduce).setOnClickListener(v->{
//            int c = Integer.parseInt(count.getText().toString());
//            if(c>1){
//                c = 1;
//            }
//            count.setText(String.valueOf(c));
//        });



//        rootView.setOnClickListener(view ->{
//            int i = (int)(Math.random()*(datalist.size()+1));
//            Person person = new Person(datalist.get(i).getName(),datalist.get(i).getDesc());
//            adapter.notifyItemInserted(2);
//            datalist.add(2,person);
//            adapter.notifyItemRangeChanged(2,adapter.getItemCount());
//        });
    }
}

