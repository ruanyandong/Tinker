package com.ai.tinker;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


/**
 * @author -> miracle
 * @date -> 2019/10/10
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装分包配置
        MultiDex.install(this);

        // 调用FixDexUtils修复工具，开始修复
        //FixDexUtils.loadFixDex(this);
    }

}
