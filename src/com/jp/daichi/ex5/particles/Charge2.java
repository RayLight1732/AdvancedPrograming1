package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;

import java.awt.*;

public class Charge2 extends Charge {


    private long startTime;

    public Charge2(GameEntity entity, double radius, double expansion, Color color) {
        super(entity, radius, expansion, color);
    }

    public Charge2(GameEntity entity, double radius, double expansion, PositionConverter positionConverter, Color color) {
        super(entity, radius, expansion, positionConverter, color);
    }

    @Override
    public void tick(double deltaTime) {
        if (!lastTickExisted()) {
            startTime = System.currentTimeMillis();
        }
        super.tick(deltaTime);
    }

    public long getStartTime() {
        return startTime;
    }
}
