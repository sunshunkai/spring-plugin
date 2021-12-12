package com.ssk.binlog;

/**
 * @author 惊云
 * @date 2021/12/12 10:44
 */
public class BinlogException extends Exception{
    public BinlogException() {
        super();
    }

    public BinlogException(String message) {
        super(message);
    }

    public BinlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public BinlogException(Throwable cause) {
        super(cause);
    }

    protected BinlogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
