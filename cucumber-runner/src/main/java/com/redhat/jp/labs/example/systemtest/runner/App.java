package com.redhat.jp.labs.example.systemtest.runner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    ExecutionService executionService;

    Properties configuration;

    public static void main( String[] args )
    {
        try {
            new App(new ExecutionService()).run();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    App(ExecutionService executionService) throws IOException {
        this.executionService = executionService;
        parseConfiguration();
    }

    void run() throws IOException {
        String testResourcesBaseDir = this.configuration.getProperty("systemtestrunner.test.basedir");
        List<SystemTestScenario> scenarios = SystemTestScenarioBuilder.build();
        scenarios.stream().forEach(s -> {
            String targetDir = testResourcesBaseDir + this.configuration.getProperty("systemtestrunner.workdir." + s.getTarget());
            logger.info("{}({}) - {}", s.getTarget(), targetDir, s.getScenarioTitle());
            executionService.execute(s.getScenarioTitle(), targetDir);
        });
    }

    void parseConfiguration() throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties")) {
            this.configuration = new Properties();
            this.configuration.load(is);            
        }
    }
}
