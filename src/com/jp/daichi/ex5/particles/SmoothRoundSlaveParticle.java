package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

public abstract class SmoothRoundSlaveParticle extends SmoothRoundParticle {

    private final GameEntity master;
    private final PositionConverter positionConverter;
    private final RotationConverter rotationConverter;

    public SmoothRoundSlaveParticle(GameEntity master, PositionConverter positionConverter, RotationConverter rotationConverter) {
        this.master = master;
        this.positionConverter = positionConverter;
        this.rotationConverter = rotationConverter;
    }

    @Override
    public double getX() {
        return positionConverter.convert(master.getX(),master.getY()).x;
    }

    @Override
    public double getY() {
        return positionConverter.convert(master.getX(),master.getY()).y;
    }

    public double getRotation() {
        return rotationConverter.convert(master.getRotation());
    }

    public GameEntity getMaster() {
        return master;
    }
}
