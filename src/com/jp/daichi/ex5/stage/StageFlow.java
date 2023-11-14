package com.jp.daichi.ex5.stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StageFlow {

    private final List<Stage> stages;
    private Stage stage = null;
    public StageFlow(List<Stage> stages) {
        this.stages = new ArrayList<>(stages);
    }

    public Stage next() {
        stages.remove(stage);
        if (!stages.isEmpty()) {
            stage = stages.get(0);
            return stage;
        } else {
            return null;
        }
    }

    public static class StageFlowFactory {
        public static StageFlowFactory createInstance() {
            return new StageFlowFactory();
        }
        private final List<Stage> stages = new ArrayList<>();
        public StageFlowFactory add(Stage stage) {
            stages.add(stage);
            return this;
        }

        public StageFlowFactory addAll(Collection<Stage> stages) {
            this.stages.addAll(stages);
            return this;
        }

        public StageFlow create() {
            return new StageFlow(stages);
        }
    }
}
