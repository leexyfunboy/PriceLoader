package com.example.priceloader.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.priceloader.R;
import com.example.priceloader.utils.OkHttpTool;
import com.example.priceloader.utils.RetrofitService;

import java.util.zip.Inflater;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Button bt = view.findViewById(R.id.userFrag_bt);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.106:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        bt.setOnClickListener(v->{
            Call<ResponseBody> call = service.getImg();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody responseBody = response.body();

                    Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                    int bitmapWidth = bitmap.getWidth();
                    int containerWidth = container.getWidth();
                    Log.e("bitmap","height:"+bitmap.getHeight());
                    Log.e("bitmap","width:"+bitmapWidth);
                    Log.e("bitmap","containner.height:"+container.getHeight());
                    Log.e("bitmap","containner.width:"+containerWidth);


                    ImageView imgv = view.findViewById(R.id.userFrag_imgV);
                    if(bitmapWidth<containerWidth){
                        imgv.setMaxWidth(bitmapWidth);
                    }else{

                    }

                    imgv.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });


        return view;
    }
}