package com.example.priceloader.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.priceloader.MainActivity;
import com.example.priceloader.R;
import com.example.priceloader.adapter.GoodsListRVAdapter;
import com.example.priceloader.camera.MyCaptureAC;
import com.example.priceloader.databinding.FragmentCameraBinding;
import com.example.priceloader.entity.GoodsListItem;
import com.example.priceloader.entity.Product_detail;
import com.example.priceloader.item.GoodsListRecyclerview;
import com.example.priceloader.utils.RetrofitService;
import com.example.priceloader.viewmodel.CameraFragVM;
import com.google.zxing.integration.android.IntentIntegrator;
import com.king.zxing.CaptureActivity;
import com.king.zxing.CaptureFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {

    private final String TAG = "CameraFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView.LayoutManager layoutManager;
    private List<GoodsListItem> list_goods = new ArrayList<>();

    private Context m_context;
    private FragmentCameraBinding binding;
    private CameraFragVM viewModel;

    private RecyclerView rv;
    private Button bt_scan;
    RecyclerView.Adapter<GoodsListRecyclerview> adapter;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        initData();
        Log.e("CameraFragment","fragment onCreate...........");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        m_context = view.getContext();
        binding = FragmentCameraBinding.inflate(getLayoutInflater(),container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(CameraFragVM.class);

        layoutManager = new LinearLayoutManager(m_context);

        rv = view.findViewById(R.id.fragment_goodsList);
        bt_scan = view.findViewById(R.id.bt_scan);


        Log.e("CameraFragment","fragment onCreateView...........");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }





    public void initData(){
        list_goods = new ArrayList<>();
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

    public void initEvent(){
        bt_scan.setOnClickListener(v->{
            System.out.println("bt_scan....");
            for(int i=0;i<5;i++){
                GoodsListItem goodsListItem = new GoodsListItem();
                goodsListItem.setId(String.valueOf(i));
                goodsListItem.setName(String.valueOf(i)+"asd");
                goodsListItem.setSinglePrice(11.2*i);
                goodsListItem.setFinalPrice(12.2*i);
                goodsListItem.setCount(i);
                list_goods.add(goodsListItem);
                viewModel.getData().setValue(list_goods);
            }

            viewModel.getData().setValue(list_goods);
            adapter.notifyDataSetChanged();
        });

    }

    public void onScanBarcode(View v){
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

}