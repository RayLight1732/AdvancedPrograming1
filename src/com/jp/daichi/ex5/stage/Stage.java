package com.jp.daichi.ex5.stage;

import com.jp.daichi.ex5.Game;

public interface Stage {
    void startStage(Game game);

    void tick(double deltaTime);

    boolean isEnded();

    boolean started();

}
