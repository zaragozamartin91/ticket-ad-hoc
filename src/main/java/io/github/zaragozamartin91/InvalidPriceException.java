package io.github.zaragozamartin91;

public class InvalidPriceException extends IllegalStateException {
    InvalidPriceException(String msg, Throwable ex) {
        super(msg, ex);
    }
    
    InvalidPriceException(String msg) {
        super(msg);
    }
}
