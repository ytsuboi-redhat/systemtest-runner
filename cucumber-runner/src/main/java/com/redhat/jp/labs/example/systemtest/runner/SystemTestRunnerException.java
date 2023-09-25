package com.redhat.jp.labs.example.systemtest.runner;

public class SystemTestRunnerException extends RuntimeException {

    public SystemTestRunnerException() {
        super();
    }

    public SystemTestRunnerException(String message) {
        super(message);
    }

    public SystemTestRunnerException(Throwable e) {
        super(e);
    }
}
