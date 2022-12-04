package com.wss.HrPro.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeanCopyUtil {

    public static <T> List copyList(List<T> list, Class tClass) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSONArray.parseArray(JSONObject.toJSONString(list), tClass);
    }



}
