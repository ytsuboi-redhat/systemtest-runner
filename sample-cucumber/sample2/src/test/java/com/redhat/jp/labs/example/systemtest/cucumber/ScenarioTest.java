package com.redhat.jp.labs.example.systemtest.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(tags = "not @ignore")
public class ScenarioTest {
}
