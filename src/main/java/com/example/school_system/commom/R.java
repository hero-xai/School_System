package com.example.school_system.commom;

import lombok.Data;
/**
 * 通用的返回结果类
 * @param
 */
@Data
public class R {
    private Integer code; //编码：200成功，其它数字为失败

    private String msg; //信息

    private Object data; //数据

    public R(){};
    public R (Integer code){
        this.code=code;
    };
    public R (Integer code,String msg){
        this.code=code;
        this.msg=msg;
    };
    public R (Integer code,String msg,Object data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    };
    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static R success() {
        return R.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static R success(Object data) {
        return R.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static R success(String msg) {
        return R.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static R success(String msg, Object data) {
        return new R(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static R error() {
        return R.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static R error(String msg) {
        return R.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static R error(String msg, Object data) {
        return new R(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static R error(int code, String msg) {
        return new R(code, msg, null);
    }


    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    public static R toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    public static R toAjax(boolean result) {
        return result ? success() : error();
    }
}
