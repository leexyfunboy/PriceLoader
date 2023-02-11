package com.example.priceloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.priceloader.activity.AddAC;
import com.example.priceloader.activity.ListAC;
import com.example.priceloader.activity.SearchAC;
import com.example.priceloader.databinding.ActivityMenuBinding;

public class MenuAC extends AppCompatActivity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.menuACLayout.getBackground().setAlpha(100);     //透明度范围：0~255

        initEvent();
    }

    public void initEvent(){

        binding.layout1.setOnClickListener(v->{
            Intent intent = new Intent(this, SearchAC.class);
            startActivity(intent);
        });

        binding.layout2.setOnClickListener(v->{
            Intent intent = new Intent(this, AddAC.class);
            startActivity(intent);
        });

        binding.layout3.setOnClickListener(v->{
            Intent intent = new Intent(this, ListAC.class);
            startActivity(intent);
        });

        binding.layout4.setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}