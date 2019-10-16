package com.ai.tinker.error;

import android.content.Context;
import android.widget.Toast;

/**
 * @author -> miracle
 * @date -> 2019/10/13
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class ParamsSort {

    public void math(Context context){
        int a = 10;
        int b = 1;
        Toast.makeText(context, "math---->"+a/b, Toast.LENGTH_SHORT).show();
    }
}
