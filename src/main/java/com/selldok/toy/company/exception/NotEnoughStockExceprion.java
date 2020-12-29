package com.selldok.toy.company.exception;

public class  NotEnoughStockExceprion extends RuntimeException{

    public NotEnoughStockExceprion() {
        super();
    }

    public NotEnoughStockExceprion(String message) {
        super(message);
    }

    public NotEnoughStockExceprion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockExceprion(Throwable cause) {
        super(cause);
    }

    protected NotEnoughStockExceprion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
