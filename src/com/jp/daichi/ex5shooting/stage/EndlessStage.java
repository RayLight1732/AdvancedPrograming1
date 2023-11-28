package com.jp.daichi.ex5shooting.stage;

import com.jp.daichi.ex5shooting.Game;
import com.jp.daichi.ex5shooting.LivingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EndlessStage implements Stage {

    private boolean ended = false;
    private boolean started = false;
    private final Stage[] stages = new Stage[4];
    private final Random random = new Random();
    private final LivingEntity target;
    private final List<SlideSpawnInfo> infoList;

    public EndlessStage(LivingEntity target, List<SlideSpawnInfo> infoList) {
        this.target = target;
        this.infoList = infoList;
        for (int i =0;i < 4;i++) {
            stages[i] = createStage(i);
        }
    }

    @Override
    public void startStage(Game game) {
        for (Stage stage:stages) {
            if (!stage.started()) {
                stage.startStage(game);
            }
        }
    }

    @Override
    public void tick(double deltaTime) {
        for (int i = 0;i < 4;i++) {
            if (stages[i].isEnded()) {
                stages[i] = createStage(i);
                started = false;
            } else {
                stages[i].tick(deltaTime);
            }
        }
    }

    protected Stage createStage(int i) {
        SlideSpawnInfo info = infoList.get(random.nextInt(infoList.size()));
        List<SlideSpawnStage.TurretEnemyFactory> factories = new ArrayList<>(Collections.nCopies(4,null));
        factories.set(i,info.factory());
        return new SlideSpawnStage(target,factories,info.startSpeed(),info.finalSpeed(),info.endSlideTime());
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    @Override
    public boolean isEnded() {
        return ended;
    }

    @Override
    public boolean started() {
        return started;
    }


}
