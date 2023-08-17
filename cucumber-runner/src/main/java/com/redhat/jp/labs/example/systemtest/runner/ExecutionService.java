package com.redhat.jp.labs.example.systemtest.runner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionService {

    private static Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    public int execute(String scenarioName, String workDirectory) {
        String[] commands = new String[]{"mvn", "test" ,"-Dcucumber.filter.name=" + scenarioName};
        logger.info("workDirectory: {}", workDirectory);
        logger.info("Command: {}", StringUtils.join(commands, " "));
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(new File(workDirectory));
        try {
            Process process= processBuilder.inheritIO().start();
            process.waitFor();
            return process.exitValue();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}