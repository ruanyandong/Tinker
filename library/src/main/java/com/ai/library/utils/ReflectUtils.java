package com.ai.library.utils;

import java.lang.reflect.Field;

/**
 * @author -> miracle
 * @date -> 2019/10/13
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class ReflectUtils {

    private static Object getField(Object baseDexClassLoader, Class<?> clazz, String objName) throws Exception{
        Field field = clazz.getDeclaredField(objName);
        field.setAccessible(true);
        return field.get(baseDexClassLoader);
    }


    /**
     * 通过反射给dexElements属性赋值
     * @param obj  pathList对象
     * @param clazz
     * @param value
     */
    public static void setField(Object obj,Class<?> clazz,Object value) throws Exception{
        Field localField = clazz.getDeclaredField("dexElements");
        localField.setAccessible(true);
        localField.set(obj,value);
    }

    /**
     * 通过反射获取BaseDexClassLoader对象中的pathList对象
     * @param baseDexClassLoader BaseDexClassLoader对象
     * @return pathList 对象
     */
    public static Object getPathList(Object baseDexClassLoader) throws Exception{
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象，再获取dexElements对象
     * @param paramObject pathList对象
     * @return dexElements 对象
     */
    public static Object getDexElements(Object paramObject) throws Exception{
        return getField(paramObject,paramObject.getClass(),"dexElements");
    }


}
