package com.jp.daichi.ex5shooting.stage;

import com.jp.daichi.ex5shooting.Game;

public interface Stage {
    void startStage(Game game);

    void tick(double deltaTime);

    boolean isEnded();

    boolean started();

}
