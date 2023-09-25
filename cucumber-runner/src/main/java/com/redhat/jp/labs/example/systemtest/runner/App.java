package com.redhat.jp.labs.example.systemtest.runner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestFeature;
import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestScenario;
import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestScenarioStep;
import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestScenarioStep.ScenarioStatus;

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
        List<SystemTestFeature> features = SystemTestScenarioBuilder.build();
        
        try {
            features.stream().forEach(this::runScenarios);
        } catch (ExecutionFailureException e) {
            printErrorScenario(e.getTarget());
            throw new IOException(e);
        } finally {
            String report = buildReport(features);
            logger.info(report);
        }
    }

    void runScenarios(SystemTestFeature feature) {
        feature.getScenarios().stream().forEach(scenario -> {
            logger.info("実行するシナリオ: {}", scenario.getName());
            runSteps(scenario.getSteps(), feature, scenario);
        });
    }

    void runSteps(List<SystemTestScenarioStep> steps, SystemTestFeature feature, SystemTestScenario scenario) {
        scenario.setParent(feature);
        String testResourcesBaseDir = this.configuration.getProperty("systemtestrunner.test.basedir");
        steps.stream().forEach(s -> {
            s.setParent(scenario);
            String targetDir = testResourcesBaseDir + this.configuration.getProperty("systemtestrunner.workdir." + s.getTarget());
            logger.info("実行するシナリオのステップ: {}({}) - {}", s.getTarget(), targetDir, s.getName());
            int returnCode = executionService.execute(s.getName(), targetDir);
            if (returnCode == 0) {
                s.setStatus(ScenarioStatus.SUCCESS);
            } else {
                s.setStatus(ScenarioStatus.FAIL);
                throw new ExecutionFailureException(s);
            }
        });
    }

    void printErrorScenario(SystemTestScenarioStep step) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("失敗したシナリオ:%n"));
        messageBuilder.append(String.format("feature: %s%n", step.getParent().getParent().getName()));
        messageBuilder.append(String.format("  scenario: %s%n", step.getParent().getName()));
        messageBuilder.append(String.format("    %s / %s", step.getTarget(), step.getName()));
        String message = messageBuilder.toString();
        logger.error(message);
    }

    String buildReport(List<SystemTestFeature> features) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("実行結果:%n"));
        features.stream().forEach(feature -> {
            builder.append(String.format("feature: %s%n", feature.getName()));
            feature.getScenarios().stream().forEach(scenario -> {
                builder.append(String.format("  scenario: %s%n", scenario.getName()));
                scenario.getSteps().forEach(step -> {
                    builder.append(String.format("    [ %s ] - %s / %s%n", step.getStatus().getLabel(), step.getTarget(), step.getName()));
                });
            });
        });
        
        return builder.toString();
    }

    void parseConfiguration() throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties")) {
            this.configuration = new Properties();
            this.configuration.load(is);            
        }
    }
}
