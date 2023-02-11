package com.example.priceloader.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

public class DrawableTool {

    public static Drawable getDrawable(Context context, int resId){
        return AppCompatResources.getDrawable(context,resId);
    }

}
