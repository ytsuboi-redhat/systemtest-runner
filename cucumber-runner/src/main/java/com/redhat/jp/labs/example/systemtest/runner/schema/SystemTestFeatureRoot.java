package com.redhat.jp.labs.example.systemtest.runner.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SystemTestFeatureRoot {

    @NonNull
    private SystemTestFeature feature;

}
