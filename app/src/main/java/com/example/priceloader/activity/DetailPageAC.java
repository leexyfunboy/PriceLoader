package com.example.priceloader.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.priceloader.R;
import com.example.priceloader.databinding.ActivityDetailPageBinding;
import com.example.priceloader.entity.ProductItem;
import com.example.priceloader.utils.DBOpenHelper;

public class DetailPageAC extends AppCompatActivity {

    private ActivityDetailPageBinding binding;

    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase database;
    private ProductItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initEvent();

        dbOpenHelper = new DBOpenHelper(this,"yunduostore",null,1);
        database = dbOpenHelper.getWritableDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id = (String)bundle.get("id");

        item = getDetailByID(id);
        if(item!=null){
            binding.DetailPageACEdId.setText(item.getId());
            binding.DetailPageACEdName.setText(item.getName());
            binding.DetailPageACEdSingleprice.setText(String.valueOf(item.getSingleprice()));
        }

    }

    /**
     * 获取商品数据
     * @param id id
     * @return ProductItem
     */
    public ProductItem getDetailByID(String id){
        return dbOpenHelper.getDataByID(id);
    }

    public void initEvent(){
        binding.DetailPageACBtConfirm.setOnClickListener(v->{
            finish();
        });

        binding.DetailPageACBtDelete.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确认是否删除该商品的信息？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] arr_id = new String[]{binding.DetailPageACEdId.getText().toString()};
                    if(dbOpenHelper.deleteDataByID(arr_id) >= 1){
                        Toast.makeText(DetailPageAC.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
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

        binding.DetailPageACBtUpdate.setOnClickListener(v->{
            ProductItem it = new ProductItem();
            it.setId(binding.DetailPageACEdId.getText().toString());
            it.setName(binding.DetailPageACEdName.getText().toString());
            it.setSingleprice(Double.parseDouble(binding.DetailPageACEdSingleprice.getText().toString()));
            if(isDataChanged(it)){
                if(dbOpenHelper.updateData(it) >= 1){
                    Toast.makeText(this, "商品数据更新成功！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "商品数据更新失败！", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "商品属性未变更", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * 与之前的商品数据进行比较,
     * 只比较了，id,name,singleprice
     */
    public boolean isDataChanged(ProductItem newItem){
        if(!item.getId().equals(newItem.getId())){
            Toast.makeText(this, "不能改变商品的id,如果需要改变，请作为新商品进行添加", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if( !item.getName().equals(newItem.getName()) || !(item.getSingleprice() == newItem.getSingleprice()) ){
                return true;
            }else{
                return false;
            }
        }

    }

    public void clearEdText(){
        binding.DetailPageACEdId.setText(null);
        binding.DetailPageACEdName.setText(null);
        binding.DetailPageACEdSingleprice.setText(null);
        binding.DetailPageACEdCount.setText(null);
        binding.DetailPageACEdFinalprice.setText(null);
    }
}