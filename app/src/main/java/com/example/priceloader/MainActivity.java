package com.example.priceloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.priceloader.adapter.GoodsListRVAdapter;
import com.example.priceloader.camera.MyCaptureAC;
import com.example.priceloader.databinding.ActivityMainBinding;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.entity.Product_detail;
import com.example.priceloader.fragment.CameraFragment;
import com.example.priceloader.fragment.ControllFragment;
import com.example.priceloader.fragment.UserFragment;
import com.example.priceloader.utils.DBOpenHelper;
import com.example.priceloader.utils.RetrofitService;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;
import com.king.zxing.CaptureFragment;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private FragmentManager fm;
    private final Fragment fragment01 = new CameraFragment();
    private final Fragment fragment02 = new ControllFragment();
    private final Fragment fragment03 = new UserFragment();

    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    public static final int REQUEST_CODE = 0x0000c0de; // Only use bottom 16 bits

    public void toSecondAC(View v){
        Intent intent = new Intent(MainActivity.this, SecondAC.class);
        startActivity(intent);
    }

    private DBOpenHelper dbOpenHelper;

    private ActivityMainBinding binding;
    /*  Camera_fragment */
    private Button bt_scan;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private List<GoodsListItem> list_goods = new ArrayList<>();

    private Class<?> cls;
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    private boolean isContinuousScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        dbOpenHelper = new DBOpenHelper(this,"yunduostore",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();


        adapter = new GoodsListRVAdapter(this,list_goods);

        if(savedInstanceState==null){

        }
        initFragment();
        View cameraFrag_view = LayoutInflater.from(this).inflate(R.layout.fragment_camera,null);
//        Button bt_camera = cameraFrag_view.findViewById(R.id.bt_scan);
//        bt_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("bt-scaning############");
//                startActivity(new Intent(MainActivity.this,MyCaptureAC.class));
//            }
//        });


//        for(GoodsListItem item : list_goods){
//            dbOpenHelper.addData(item.getId(),item.getName(),item.getSinglePrice());
//        }

        List<GoodsListItem> list = dbOpenHelper.getList();
        for(GoodsListItem it : list){
            System.out.println("---"+it.getId()+"   "+it.getName()+"   "+it.getSinglePrice());
        }


//        Intent intent = new Intent(MainActivity.this, MyCaptureAC.class);
//        startActivity(intent);
//        Intent intent = new Intent(MainActivity.this, TestAC.class);
//        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        rv = findViewById(R.id.fragment_goodsList);
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d("CameraFrag",o.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        binding.fragmentContainerView.findViewById(R.id.bt_scan).setOnClickListener(v->{
//            startActivityForResult(new Intent(m_context, CaptureActivity.class),0x0000c0de);
////            onScanBarcode(view);
//            startActivityForResult(new Intent(m_context, CaptureActivity.class),0x0000c0de);
            startScan(CaptureActivity.class,"aabb");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.2.106:80")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<List<Product_detail>> call = service.getData("123");

            Observable.create(new ObservableOnSubscribe<List<Product_detail>>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<List<Product_detail>> emitter) throws Throwable {
                            call.enqueue(new Callback<List<Product_detail>>() {
                                @Override
                                public void onResponse(Call<List<Product_detail>> call, Response<List<Product_detail>> response) {

                                    emitter.onNext(response.body());
                                }

                                @Override
                                public void onFailure(Call<List<Product_detail>> call, Throwable t) {

                                }
                            });
                        }
                    }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);

        });

        binding.fragmentContainerView.findViewById(R.id.controllFrag_bt_scan).setOnClickListener(v->{
            startScan(CaptureActivity.class,"aabb");
        });

        adapter = new GoodsListRVAdapter(this,list_goods);

        rv.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        rv.setAdapter(adapter);

        if(adapter==null){
            Log.d(TAG,"adapter为null");
        }else{
            Log.d(TAG,"adapter不为null");
        }
    }

    /**
          * 初始化fragment
          */
    private void initFragment(){
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fragmentContainerView,fragment01);
        transaction.add(R.id.fragmentContainerView,fragment02);
        transaction.add(R.id.fragmentContainerView,fragment03);
        transaction.setReorderingAllowed(true);
//        transaction.hide(fragment02);
//        transaction.hide(fragment03);
        transaction.commit();

        initFragmentChangeActions(transaction);
    }

    public void initData(){
        for(int i=0;i<5;i++){
            GoodsListItem goodsListItem = new GoodsListItem();
            goodsListItem.setId(String.valueOf(i)+"xxxxxxxxxx");
            goodsListItem.setName("xxxxxxxxxxxxxxxxxxx");
            goodsListItem.setSinglePrice(11*i);
            goodsListItem.setFinalPrice(12*i);
            goodsListItem.setCount(i);
            list_goods.add(goodsListItem);
//                viewModel.getData().setValue(list_goods);
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

    public void initFragmentChangeActions(FragmentTransaction transaction){
        hideFragment(transaction);
        binding.selectionBar.selectionImb01.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.replace(R.id.fragmentContainerView,fragment01,null).commit();
        });

        binding.selectionBar.selectionImb02.setOnClickListener(v -> {
            fm.beginTransaction().replace(R.id.fragmentContainerView,fragment02,null).commit();
        });

        binding.selectionBar.selectionImb03.setOnClickListener(v -> {
            fm.beginTransaction().replace(R.id.fragmentContainerView,fragment03,null).commit();
        });
    }

    /**
     //     * 隐藏
     //     * @param transaction fragmentTransaction
     //     */
    private void hideFragment(FragmentTransaction transaction){
//        transaction.hide(fragment01);
        transaction.hide(fragment02);
        transaction.hide(fragment03);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        Log.d("MainActivity","requestCode = "+requestCode);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
//                Log.d("MainActivity","条码值无");
//            } else {
//                Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
//                //此处进行数据库商品查询
//                Log.d("MainActivity","条码值:"+result.getContents());
//                //将查询数据返回到前端
//
//            }
//        } else {
//            // This is important, otherwise the result will not be passed to the fragment
//            Log.d("MainActivity","result为空");
//            super.onActivityResult(requestCode, resultCode, data);
//        }

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null){
            switch (requestCode){
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
//                    showToast(result);
                    Log.d("MainAC","###########");
                    Toast.makeText(this, "扫描结果:"+result, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_PHOTO:
//                    parsePhoto(data);
                    break;
            }

        }

    }

    public void onScanBarcode(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    public void onScanQrcode(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("扫描二维码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }



}