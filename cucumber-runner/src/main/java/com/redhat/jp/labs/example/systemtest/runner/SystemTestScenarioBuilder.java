package com.redhat.jp.labs.example.systemtest.runner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class SystemTestScenarioBuilder {

    private static final String[] FILTER_EXTENSION = { "scenario" };

    private SystemTestScenarioBuilder() {
    }
    
    static List<SystemTestScenario> build() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Collection<File> files = FileUtils.listFiles(new File(path), FILTER_EXTENSION, true);
        List<SystemTestScenario> scenarios = new ArrayList<>();
        for (File f : files) {
            try (Stream<String> stream = Files.lines(f.toPath(), StandardCharsets.UTF_8)) {
                stream.forEach(line -> {
                    String[] elements = line.split("\t");
                    scenarios.add(new SystemTestScenario(elements[0], elements[1]));
                });
            }
        }
        return scenarios;
    }
}
