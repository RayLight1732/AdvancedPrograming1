package com.jp.daichi.ex5.stage;

import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.LivingEntity;

public interface Stage {
    void startStage(Game game);

    void tick(double deltaTime);

    boolean isEnded();

    boolean started();

}
