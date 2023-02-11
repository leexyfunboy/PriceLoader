package com.example.priceloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TestAC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.testAC_bt).setOnClickListener(v->{
            System.out.println("&&&&&&&&&&&&    testAC onclick");
        });
    }
}