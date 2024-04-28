package com.base.core.exception;

public class DistributeLockException extends Exception {
    public DistributeLockException() {
        super();
    }
    
    public DistributeLockException(String message) {
        super(message);
    }
}
