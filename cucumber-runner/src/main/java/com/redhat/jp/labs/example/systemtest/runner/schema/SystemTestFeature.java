package com.redhat.jp.labs.example.systemtest.runner.schema;

import java.io.Serializable;
import java.util.List;

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
public class SystemTestFeature implements Serializable {

    @NonNull
    private String name;
    
    private String description;

    @NonNull
    private List<SystemTestScenario> scenarios;

}
