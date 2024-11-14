package org.t1.demo.exception;

public class KafkaSendException  extends RuntimeException{
    public KafkaSendException() {
    }

    public KafkaSendException(String message) {
        super(message);
    }

    public KafkaSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
