package com.mini.cloud.common.exception;

/**
 * @author: Saber
 * @date: 19/04/01 14:12
 * @projectname:tpshop
 * @description:
 */
public class Rexception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public Rexception(String msg) {
        super(msg);
        this.msg = msg;
    }

    public Rexception(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public Rexception(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public Rexception(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
