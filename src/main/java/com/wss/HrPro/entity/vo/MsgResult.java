package com.wss.HrPro.entity.vo;


import com.wss.HrPro.util.constans.HttpStatus;

import java.util.HashMap;

/**
 * 操作消息提醒

 */
public class MsgResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;



    /** 返回内容 */
    public static final String MSG_TAG = "message";



    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public MsgResult()
    {
    }



    public MsgResult(String msg)
    {
        super.put(MSG_TAG, msg);

    }


    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @return 成功消息
     */
    public static MsgResult success(String msg) {
        return new MsgResult( msg);
    }




    public static MsgResult error(String msg)
    {
        return new MsgResult(msg);
    }


    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public MsgResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
