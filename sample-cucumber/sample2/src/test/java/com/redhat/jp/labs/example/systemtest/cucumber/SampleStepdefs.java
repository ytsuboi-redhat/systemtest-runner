package com.redhat.jp.labs.example.systemtest.cucumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SampleStepdefs {

    private static Logger logger = LoggerFactory.getLogger(SampleStepdefs.class);

    @When("test executed")
    public void test_executed() {
        logger.info("run when: {}");
    }
    
    @Then("print {string}")
    public void print_step(String message) {
        logger.info("run then: {}", message);
    }
}
