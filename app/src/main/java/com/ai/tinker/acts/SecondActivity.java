package com.ai.tinker.acts;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ai.library.FixDexUtils;
import com.ai.library.utils.Constants;
import com.ai.library.utils.FileUtils;
import com.ai.tinker.BaseActivity;
import com.ai.tinker.R;
import com.ai.tinker.error.ParamsSort;

import java.io.File;
import java.io.IOException;

/**
 * @author AI
 */
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    /**
     * 点击事件
     * @param view
     */
    public void fix(View view) {
        // 不模拟网络下载过程，直接将修复包放置在SDCard中classes2.dex文件
        File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);

        // 目标文件，私有目录
        // getDir-->/data/user/0/com.ai.tinker/app_odex,其中app是系统拼上去的
        File targetFile = new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()+File.separator+Constants.DEX_NAME);
        // 有没有可能已经存在classes2.dex
        if (targetFile.exists()){
            // 他之前修复过了，现在过期了，替换
            targetFile.delete();
            Toast.makeText(this, "删除原有的dex文件", Toast.LENGTH_SHORT).show();
        }

        try {
            // 将下载的修复包，复制到私有目录，然后再做解压工作(dex压缩包，解压得到class文件)
            FileUtils.copyFile(sourceFile,targetFile);
            Toast.makeText(this, "复制dex文件完成", Toast.LENGTH_SHORT).show();

            // 调用FixDexUtils修复工具，开始修复
            FixDexUtils.loadFixDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 业务上或者逻辑上的bug
     * @param view
     * 点击事件
     */
    public void show(View view) {
        ParamsSort sort = new ParamsSort();
        // 计算错误，闪退
        sort.math(this);
    }
}
