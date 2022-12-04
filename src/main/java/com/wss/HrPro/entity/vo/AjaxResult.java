package com.wss.HrPro.entity.vo;

import com.wss.HrPro.util.constans.HttpStatus;

import java.util.HashMap;

/**
 * 操作消息提醒
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;


    public static final String DATA_TAG = "results";

    public AjaxResult() {
    }

    public AjaxResult(Object data) {

        if (null != data) {
            super.put(DATA_TAG, data);
        }
    }


    public static AjaxResult success(Object data) {
        return new AjaxResult(data);
    }






    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
