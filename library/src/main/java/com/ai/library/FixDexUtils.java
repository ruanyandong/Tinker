package com.ai.library;

/**
 * @author -> miracle
 * @date -> 2019/10/13
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */

import android.content.Context;

import com.ai.library.utils.ArrayUtils;
import com.ai.library.utils.Constants;
import com.ai.library.utils.ReflectUtils;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author AI 
 * 修复工具
 */
public class FixDexUtils {
    /**
     * 需要修复的集合
     */
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        //  修复之前清理集合
        loadedDex.clear();
    }

    /**
     * 加载热修复的Dex文件
     * @param context
     */
    public static void loadFixDex(Context context) {
        /**
         * 私有目录中创建任意名字的子目录：
         * 该方法返回指向/data/data/<Package Name>/下指定名称的文件夹的一个File对象。
         * 如果该文件夹不存在则用指定名称创建一个新的文件夹。
         */
        File fileDir = context.getDir(Constants.DEX_DIR,Context.MODE_PRIVATE);
        // 循环私有目录中的所有文件
        File[] listFiles = fileDir.listFiles();
        // 筛选dex文件，加入集合
        for (File file:listFiles) {
            // 是dex文件并且不是主包
            if (file.getName().endsWith(Constants.DEX_SUFFIX) && !"classes.dex".equals(file.getName())){
                // 找到了修复包的dex文件
                loadedDex.add(file);
            }
        }
        
        // 创建类加载器
        createDexClassLoader(context,fileDir);
    }

    /**
     * 创建加载补丁的DexClassLoader类加载器
     * @param context
     * @param fileDir
     */
    private static void createDexClassLoader(Context context, File fileDir) {
        // 创建解压输出目录
        String optimizedDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";

        // 创建解压输出目录
        File fopt = new File(optimizedDir);

        if (!fopt.exists()){
            // 创建目录(层级目录创建) mkdir只能创建单级目录，mkdirs创建多级目录
            fopt.mkdirs();
        }

        for (File dex : loadedDex) {
            // 创建自有的类加载器
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(),
                    optimizedDir, null,context.getClassLoader());

            // 每循环一次，修复一次(插一次桩)
            hotfix(classLoader,context);
        }
    }

    private static void hotfix(DexClassLoader classLoader, Context context) {
        try {
            // 获取系统的pathClassLoader类加载器
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            // 获取自有的dexElements数组
            Object myElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));
            // 获取系统的dexElements数组
            Object systemElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));
            // 合并数组，并且生成一个新的dexElements数组(包含排序工作)
            Object dexElements = ArrayUtils.combineArray(myElements,systemElements);
            // 获取系统的pathList属性(通过反射技术)
            Object systemPathList = ReflectUtils.getPathList(pathClassLoader);
            // 通过反射技术，将新的dexElements数组 赋值给 系统的 pathList属性
            ReflectUtils.setField(systemPathList,systemPathList.getClass(),dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
