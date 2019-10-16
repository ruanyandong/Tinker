package com.ai.library.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author -> miracle
 * @date -> 2019/10/13
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class FileUtils {
    /**
     * 复制文件
     * @param sourceFile 源文件(修复文件，下载来的)
     * @param targetFile 目标文件(私有目录)
     * @throws IOException
     */
    public static void copyFile(File sourceFile,File targetFile) throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream bis = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream fos = new FileOutputStream(targetFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        // 缓冲数组
        byte[] b = new byte[1024*5];
        int len;
        while((len = bis.read(b)) != -1){
            bos.write(b,0,len);
        }
        // 刷新此缓冲的输出流
        bos.flush();

        input.close();
        bis.close();
        fos.close();
        bos.close();
    }
}
