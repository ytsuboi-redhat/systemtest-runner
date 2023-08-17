package com.redhat.jp.labs.example.systemtest.runner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class SystemTestScenario {

    @NonNull
    private String target;
    
    @NonNull
    private String scenarioTitle;

    private boolean success = false;
}
