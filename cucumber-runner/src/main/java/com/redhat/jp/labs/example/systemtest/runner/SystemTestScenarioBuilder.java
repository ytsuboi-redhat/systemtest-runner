package com.redhat.jp.labs.example.systemtest.runner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestFeature;
import com.redhat.jp.labs.example.systemtest.runner.schema.SystemTestFeatureRoot;

public class SystemTestScenarioBuilder {

    private static final String[] FILTER_EXTENSION = { "scenario.yaml" };

    private SystemTestScenarioBuilder() {
    }

    static List<SystemTestFeature> build() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Collection<File> targetFiles = FileUtils.listFiles(new File(path), FILTER_EXTENSION, true);
        List<SystemTestFeature> features = new ArrayList<>();
        for (File file : targetFiles) {
            SystemTestFeature systemTestFeature = load(file);
            features.add(systemTestFeature);
        }
        return features;
    }

    static SystemTestFeature load(File file) throws IOException {
        try (InputStream is = Files.newInputStream(file.toPath())) {
            Yaml yaml = new Yaml(new Constructor(SystemTestFeatureRoot.class, new LoaderOptions()));
            Iterator<Object> lines = yaml.loadAll(is).iterator();
            SystemTestFeatureRoot featureRoot = (SystemTestFeatureRoot) lines.next();
            return featureRoot.getFeature();
        }
    }
}
