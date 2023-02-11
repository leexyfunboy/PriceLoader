package com.example.priceloader.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.priceloader.R;
import com.example.priceloader.activity.SearchAC;
import com.example.priceloader.entity.CartListItem;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.item.CartListRecyclerView;
import com.example.priceloader.item.GoodsListRecyclerview;

import java.util.ArrayList;
import java.util.List;

public class CartListRVAdapter extends RecyclerView.Adapter<CartListRecyclerView> {

    private final String TAG = "GoodsListRVAdapter";
    private Context context;
    private List<CartListItem> list_goods;
    public List<CartListItem> delete_list;

    public CartListRVAdapter(Context context, List<CartListItem> list_goods) {
        this.context = context;
        this.list_goods = list_goods;
    }

    public void setList_goods(List<CartListItem> list_goods) {
        this.list_goods = list_goods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartListRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_cartlist,parent,false);
        delete_list = new ArrayList<>();
        return new CartListRecyclerView(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListRecyclerView holder, int position) {
        holder.checkbox.setChecked(list_goods.get(position).isSelected());
        holder.id.setText(list_goods.get(position).getId());
        holder.name.setText(list_goods.get(position).getName());
        holder.singlePrice.setText(String.valueOf(list_goods.get(position).getSingleprice()));
        holder.count.setText(String.valueOf(list_goods.get(position).getCount()));

        //勾选框
//        if(holder.checkbox.isChecked()){
//            holder.checkbox.setOnClickListener(v->{
//                list_goods.get(position).setSelected(false);
//                holder.checkbox.setChecked(false);
//            });
//        }else{
//            holder.checkbox.setOnClickListener(v->{
//                list_goods.get(position).setSelected(true);
//                holder.checkbox.setChecked(true);
//            });
//        }
        holder.checkbox.setOnClickListener(v->{
            CartListItem it = list_goods.get(position);
            if(holder.checkbox.isChecked()){
                delete_list.add(it);
            }else{
                delete_list.remove(it);
            }
        });



        //减少按钮
        holder.bt_reduce.setOnClickListener(v->{
            int c = list_goods.get(position).getCount() ;
            if(c>1){
                c = 1;
            }
            list_goods.get(position).setCount(c);
            holder.count.setText(String.valueOf(list_goods.get(position).getCount()));
            SearchAC.refreshTotalMoney();
        });

        //增加按钮
        holder.bt_add.setOnClickListener(v->{
            int c = list_goods.get(position).getCount() + 1;
            list_goods.get(position).setCount(c);
            holder.count.setText(String.valueOf(list_goods.get(position).getCount()));
            SearchAC.refreshTotalMoney();
        });
//        holder.finalPrice.setText(String.valueOf(list_goods.get(position).getFinalPrice()));
    }

    /**
     * 清空待删除列表
     */
    public void cleanDeleteList(){
        delete_list = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return list_goods.size();
    }
}
