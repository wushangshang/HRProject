package com.wss.HrPro.entity.vo;


import java.util.HashMap;


public class MsgResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;



    public static final String MSG_TAG = "message";



    public MsgResult()
    {
    }



    public MsgResult(String msg)
    {
        super.put(MSG_TAG, msg);

    }



    public static MsgResult success(String msg) {
        return new MsgResult( msg);
    }




    public static MsgResult error(String msg)
    {
        return new MsgResult(msg);
    }


    @Override
    public MsgResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
