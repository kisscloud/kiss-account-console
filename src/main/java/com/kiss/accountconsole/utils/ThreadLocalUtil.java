package com.kiss.accountconsole.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {
    private static ThreadLocal<Map<String,byte[]>> threadLocal = new ThreadLocal<>();

    /**
     * @Title: 获取线程变量value
     * @Description: TODO
     */
    public static byte[] getByte(String key){
        Map<String,byte[]> map = threadLocal.get();
        if(map==null){
            return null;
        }
        return map.get(key);
    }
    /**
     * @Title:设置线程变量
     * @Description: TODO
     */
    public static void setByte(String key,byte[] value){
        Map<String,byte[]> map = threadLocal.get();
        if(map==null){
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key,value);
    }

}

