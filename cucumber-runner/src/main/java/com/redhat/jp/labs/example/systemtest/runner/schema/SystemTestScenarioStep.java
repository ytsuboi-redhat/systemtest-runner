package com.redhat.jp.labs.example.systemtest.runner.schema;

import java.io.Serializable;

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
public class SystemTestScenarioStep implements Serializable {

    @NonNull
    private String target;
    
    @NonNull
    private String name;

    private SystemTestScenario parent;

    private ScenarioStatus status = ScenarioStatus.UNTOUCHED;

    public enum ScenarioStatus {
        SUCCESS("OK"), FAIL("NG"), UNTOUCHED("--");

        private String label;

        private ScenarioStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    }
}
