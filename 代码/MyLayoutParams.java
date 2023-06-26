package com.example.viewtest1.customviewtest_4category.customviewgroup.example2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

//https://www.jianshu.com/p/138b98095778
//自定义LayoutParams
public class MyLayoutParams extends ViewGroup.LayoutParams {

    int left;
    int top;

    public MyLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public MyLayoutParams(int width, int height) {
        super(width, height);
    }

    public MyLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }
}
