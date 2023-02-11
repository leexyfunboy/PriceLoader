package com.example.priceloader.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.priceloader.R;
import com.example.priceloader.adapter.GoodsListRVAdapter;
import com.example.priceloader.databinding.ActivityListBinding;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ListAC extends AppCompatActivity {

    private ActivityListBinding binding;
    private RecyclerView rv;
    private GoodsListRVAdapter rv_adapter;
    private List<GoodsListItem> list_goods = new ArrayList<>();

    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initEvent();

        rv = view.findViewById(R.id.ListAC_rv);
        dbOpenHelper = new DBOpenHelper(this,"yunduostore",null,1);

        list_goods = getListData();
        rv_adapter = new GoodsListRVAdapter(this,list_goods);

        rv.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        rv.setAdapter(rv_adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshList();
        Log.e("ListAC","onRestart");
    }

    public void initEvent(){
        binding.ListACBtReturn.setOnClickListener(v->{
            finish();
        });

        binding.ListACBtDelete.setOnClickListener(v->{
            List<String> list_id = rv_adapter.arr_id;
            int count = list_id.size();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确认删除这"+count+"件商品的信息？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int successCount = 0;
                    for(String t_id : list_id){
                        successCount += dbOpenHelper.deleteDataByID(new String[]{t_id}) == 1?1:0;
                    }
                    if( successCount > 0){
                        Toast.makeText(ListAC.this, "共删除"+successCount+"件商品,\n"+(count-successCount)+"失败", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ListAC.this, "删除失败！", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    refreshList();
                    rv_adapter.cleanList_id();
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        });
    }

    public List<GoodsListItem> getListData(){
        return dbOpenHelper.getList();
    }

    /**
     * 更新列表数据
     */
    public void refreshList(){
        list_goods = getListData();
        rv_adapter.setList_goods(list_goods);
    }
}