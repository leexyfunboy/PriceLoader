package com.example.priceloader.utils;

import com.example.priceloader.entity.Product_detail;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("/PriceLoaderWeb_war_exploded/getData_Servlet")
    Call<List<Product_detail>> getData(@Field("column") String column);


    @GET("PriceLoaderWeb_war_exploded/getimg")
    Call<ResponseBody> getImg();

}
