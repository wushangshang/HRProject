package com.wss.HrPro.util.constans;

/**
 * 返回状态码
 * 
 * @author ruoyi
 */
public class HttpStatus
{
    /**
     * Success but no data updated
     */
    public static final int SUCCESS = 200;

    /**
     * - Data created or uploaded.
     */
    public static final int CREATED = 201;


    /**
     * Bad input - parsing error, duplicate row, invalid salary etc.
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    public static final int BAD_METHOD = 405;

    /**
     * 资源冲突，或者资源被锁
     */
    public static final int CONFLICT = 409;

    /**
     * 不支持的数据，媒体类型
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 接口未实现
     */
    public static final int NOT_IMPLEMENTED = 501;
}
