package com.wss.HrPro.entity.vo;


import java.util.HashMap;


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




    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
