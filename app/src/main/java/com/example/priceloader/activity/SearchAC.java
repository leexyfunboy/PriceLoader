package com.example.priceloader.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priceloader.R;
import com.example.priceloader.adapter.CartListRVAdapter;
import com.example.priceloader.adapter.GoodsListRVAdapter;
import com.example.priceloader.entity.CartListItem;
import com.example.priceloader.entity.EventMes;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.entity.ProductItem;
import com.example.priceloader.utils.DBOpenHelper;
import com.king.zxing.CameraScan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchAC extends AppCompatActivity {

    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    private Map<String,CartListItem> maplist;        //记录扫描商品的个数
    private static List<CartListItem> list_item;
//    private GoodsListRVAdapter rv_adapter;
    private CartListRVAdapter rv_adapter;

    private DBOpenHelper dbOpenHelper;
    private static double total_money;
    private static TextView tx_totalmoney;

    private ImageButton imgBt_delete;
    private Button bt_allDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView rv = findViewById(R.id.searchAC_rv);
        imgBt_delete = findViewById(R.id.searchAC_imgbt_delete);
        bt_allDelete = findViewById(R.id.searchAC_bt_clean);
        tx_totalmoney = findViewById(R.id.search_tx_totalmoney);
        initEvent();
        total_money = 0;
        maplist = new HashMap<>();
        list_item = new ArrayList<>();
//        CartListItem cartListItem = new CartListItem("xxxxxxxxx", "xxxxxxxxxxx", 10, 12, 10, false, 2, 24);
//        list_item.add(cartListItem);
        dbOpenHelper = new DBOpenHelper(this,"yunduostore",null,1);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_adapter = new CartListRVAdapter(this,list_item);
        rv.setAdapter(rv_adapter);

    }




    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgReceive(EventMes msg){
        ProductItem p = dbOpenHelper.getDataByID(msg.getId());
        String t_id ;
        if(p!=null){
            t_id = p.getId();
            int num;
            CartListItem ct = maplist.get(t_id);
            if(ct == null){
                num = 1;
                CartListItem item = new CartListItem(p.getId(),p.getName(),p.getInPrice(),p.getSingleprice(),p.getTotalCount(),false,num,num*p.getSingleprice());
                maplist.put(t_id,item);
//                list_item.add(item);
            }else{
                num = ct.getCount() + 1;
                ct.setCount(num);
                ct.setFinalPrice(num*ct.getSingleprice());
                maplist.put(t_id,ct);
            }
        }

        list_item = new ArrayList<>();
        for(String key : maplist.keySet()){
            list_item.add(maplist.get(key));
        }

        rv_adapter.setList_goods(list_item);
        refreshTotalMoney();
        Log.e("SearchAC:onMsgReceive:id",msg.getId());
    }

    public void initEvent(){

        imgBt_delete.setOnClickListener(v->{
            deleteBtResp();
        });

        bt_allDelete.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确认清空列表?");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list_item.clear();
                    maplist.clear();
                    rv_adapter.setList_goods(list_item);
                    refreshTotalMoney();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();

        });

    }

    /**
     * 删除键的响应
     */
    public void deleteBtResp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否删除所选项？");

        //设置正面按钮
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                for(Iterator<CartListItem> it = list_item.iterator();it.hasNext();){
//                    CartListItem item = it.next();
//                    if(item.isSelected()){
//                        list_item.remove(item);
//                        maplist.remove(item.getId());
//                        rv_adapter.setList_goods(list_item);
//                    }
//                }
                for(int i=0;i<rv_adapter.delete_list.size();i++){
                    CartListItem item = rv_adapter.delete_list.get(i);
                    list_item.remove(item);
                    maplist.remove(item.getId());
//                    if(item.isSelected()){
//                        list_item.remove(item);
//                        maplist.remove(item.getId());
//                        i-=1;
//                        rv_adapter.setList_goods(list_item);
//                    }
                    rv_adapter.setList_goods(list_item);
                }
                refreshTotalMoney();
                rv_adapter.cleanDeleteList();
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    /**
     *刷新总金额
     */
    public static void refreshTotalMoney(){
        total_money = 0;
        for(CartListItem item : list_item){
            total_money += item.getSingleprice() * item.getCount();
        }
        tx_totalmoney.setText(String.valueOf(total_money));
    }

    /**
     * 向购物列表中添加
     * @param item
     */
    public void addToList(CartListItem item){
        list_item.add(item);
        rv_adapter.setList_goods(list_item);
    }

    /**
     * 从购物列表中删除
     * @param position
     */
    public void removeFromList(int position){
        list_item.remove(position);
        rv_adapter.setList_goods(list_item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null){
            switch (requestCode){
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
//                    showToast(result);
                    Log.d("SearchAC","###########");
                    Toast.makeText(this, "扫描结果:"+result, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_PHOTO:
//                    parsePhoto(data);
                    break;
            }

        }

    }
}