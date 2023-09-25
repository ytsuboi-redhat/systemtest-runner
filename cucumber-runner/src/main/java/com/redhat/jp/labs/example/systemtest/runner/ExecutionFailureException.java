package com.redhat.jp.labs.example.systemtest.runner;

import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestScenarioStep;

public class ExecutionFailureException extends SystemTestRunnerException {

    private final SystemTestScenarioStep target;

    public ExecutionFailureException(SystemTestScenarioStep target) {
        super();
        this.target = target;
    }

    public SystemTestScenarioStep getTarget() {
        return target;
    }
    
}
