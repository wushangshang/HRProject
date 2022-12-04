package com.wss.HrPro.util;

import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {


    private MyThreadLocal() {

    }

    private static final ThreadLocal<Map<String,String>> threadLocal = new ThreadLocal<>();


    public static void set(String code,String meg){

        Map<String, String> stringStringHashMap = threadLocal.get();
        if (stringStringHashMap==null){
            stringStringHashMap=new HashMap<>();
        }
        stringStringHashMap.put(code,meg);
        threadLocal.set(stringStringHashMap);
    }

    public static Map get(){

        return threadLocal.get();
    }


    public static void remove(){
        threadLocal.remove();
    }
}