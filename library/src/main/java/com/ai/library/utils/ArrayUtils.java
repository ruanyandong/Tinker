package com.ai.library.utils;

import android.renderscript.Allocation;

import java.lang.reflect.Array;

/**
 * @author -> miracle
 * @date -> 2019/10/13
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class ArrayUtils {
    /**
     * 合并数组
     * @param myElements  前数组(插队数组)
     * @param systemElements 后数组(已有数组)
     * @return 处理后的新数组
     */
    public static Object combineArray(Object myElements, Object systemElements) {
        // java.lang.Class.getComponentType() 方法返回类的组件类型的数组。如果这个类并不代表一个数组类，此方法返回null。
        //非数组类型无法通过getComponentType()获取到相应的 Class 对象；
        //非数组类型无法通过(String [])Array.newInstance(String[].class.getComponentType(), 10)方法反射生成数组对象，因为获取不到 Class 对象；
        //无论基本数据类型（byte short int long float double char boolean）的数组还是引用数据类型（Integer String 等）的数组，都可以通过getComponentType()获取到相应的 Class 对象，都可以通过(String [])Array.newInstance(String[].class.getComponentType(), 10)方法反射生成数组对象。
        Class<?> localClass = myElements.getClass().getComponentType();
        // 前数组长度
        int i = Array.getLength(myElements);
        // 新数组总长度 = 前数组长度 + 后数组长度
        int j = i + Array.getLength(systemElements);
        // 生成数组对象
        Object result = Array.newInstance(localClass,j);
        for (int k = 0; k < j; k++) {
            if (k < i){
                // 从0开始遍历，如果前数组有值，添加到新数组的第一个位置
                Array.set(result,k,Array.get(myElements,k));
            }else {
                // 添加完前数组，再添加后数组，合并完成
                Array.set(result,k,Array.get(systemElements,k-i));
            }
        }
        return result;
    }

}
