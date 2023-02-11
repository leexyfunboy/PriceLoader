package com.example.priceloader.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.priceloader.R;
import com.example.priceloader.databinding.ActivityAddBinding;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.entity.ProductItem;
import com.example.priceloader.utils.DBOpenHelper;
import com.example.priceloader.utils.DrawableTool;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

public class AddAC extends AppCompatActivity {

    private ActivityAddBinding binding;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase database;


    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    public static final int REQUEST_CODE = 0x0000c0de; // Only use bottom 16 bits
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    private boolean isContinuousScan;
    private boolean isNewData;
    private Drawable default_drawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbOpenHelper = new DBOpenHelper(this,"yunduostore",null,1);
        database = dbOpenHelper.getWritableDatabase();
        default_drawable = AppCompatResources.getDrawable(this,R.drawable.default_layout_style1);

        initEvent();
    }

    public void initEvent(){
//        binding.AddACBtScan.setOnClickListener(v->{
//            startScan(CaptureActivity.class,"aabb");
//        });

        binding.AddACImgvCamera.setOnClickListener(v->{
            startScan(CaptureActivity.class,"aabb");
        });

        binding.AddACEdId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TableRow vParent = (TableRow)v.getParent();
                Drawable drawable;

                if(hasFocus){
                    drawable = DrawableTool.getDrawable(AddAC.this,R.drawable.btn_style_red);
                    vParent.setBackground(drawable);
                }else{
                    vParent.setBackground(default_drawable);
                }

            }
        });

        binding.AddACEdName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TableRow vParent = (TableRow)v.getParent();
                Drawable drawable;
                if(hasFocus){
                    drawable = DrawableTool.getDrawable(AddAC.this,R.drawable.btn_style_red);
                    vParent.setBackground(drawable);
                }else{
                    vParent.setBackground(default_drawable);
                }

            }
        });

        binding.AddACEdSingleprice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TableRow vParent = (TableRow)v.getParent();
                Drawable drawable;
                if(hasFocus){
                    drawable = DrawableTool.getDrawable(AddAC.this,R.drawable.btn_style_red);
                    vParent.setBackground(drawable);
                }else{
                    vParent.setBackground(default_drawable);
                }

            }
        });

        binding.AddACBtCancel.setOnClickListener(v->{
            this.finish();
        });

        binding.AddACBtSubmit.setOnClickListener(v->{
            if(isAllEdittextEdited()){
                //判断是否是新的数据
                if(isNewData){
                    GoodsListItem goodsListItem = new GoodsListItem();
                    goodsListItem.setId(binding.AddACEdId.getText().toString());
                    goodsListItem.setName(binding.AddACEdName.getText().toString());
                    goodsListItem.setSinglePrice(Double.parseDouble(binding.AddACEdSingleprice.getText().toString()));
                    addData(goodsListItem);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("");
                    Toast.makeText(this,"商品信息！！！",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"商品属性缺失！！！",Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * 判断是否所有编辑框都写入了数据
     * @return boolean
     */
    public boolean isAllEdittextEdited(){
        if(binding.AddACEdId.getText().toString().trim().length()==0 || binding.AddACEdName.getText().toString().trim().length()==0 || binding.AddACEdSingleprice.getText().toString().trim().length()==0){
            return  false;
        }else{
            return true;
        }
    }

    /**
     * 向SQLite添加数据
     * @param item item
     */
    public void addData(GoodsListItem item){
        long res;
        ContentValues cv = new ContentValues();
        cv.put("id",item.getId());
        cv.put("name",item.getName());
        cv.put("singleprice",item.getSinglePrice());
        res = database.insert("product",null,cv);
        if(res == -1){
            Toast.makeText(this,"添加失败！！！",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "--添加成功--", Toast.LENGTH_SHORT).show();
            clearEdText();
        }
    }

    public void clearEdText(){
        binding.AddACEdId.setText(null);
        binding.AddACEdName.setText(null);
        binding.AddACEdSingleprice.setText(null);
        binding.AddACEdCount.setText(null);
        binding.AddACEdFinalprice.setText(null);
    }

    public ProductItem ifIDAlreadyExit(String id){
        Cursor cursor = database.query("product", null, "id=?", new String[]{id}, null, null, null);
        if(cursor.moveToNext()){
            ProductItem item = new ProductItem();
            item.setId(cursor.getString(0));
            item.setName(cursor.getString(1));
            item.setSingleprice(cursor.getDouble(2));
            return item;
        }else{
            return null;
        }
    }

    /**
     * 扫码
     * @param cls
     * @param title
     */
    private void startScan(Class<?> cls,String title){
        isContinuousScan = false;
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_IS_CONTINUOUS,isContinuousScan);
        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null){
            switch (requestCode){
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
//                    showToast(result);
                    Toast.makeText(this, "扫描结果:"+result, Toast.LENGTH_SHORT).show();
                    ProductItem item = ifIDAlreadyExit(result);
                    if(item == null){
                        isNewData = true;
                        binding.AddACEdId.setText(result);
                    }else{
                        isNewData = false;
                        Toast.makeText(this, "id已存在", Toast.LENGTH_SHORT).show();
                        binding.AddACEdId.setText(item.getId());
                        binding.AddACEdName.setText(item.getName());
                        binding.AddACEdSingleprice.setText(String.valueOf(item.getSingleprice()));
                    }


                    break;
                case REQUEST_CODE_PHOTO:
//                    parsePhoto(data);
                    break;
            }

        }

    }
}