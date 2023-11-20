package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

public abstract class RoundSlaveParticle extends RoundParticle {

    private final GameEntity master;
    private final PositionConverter positionConverter;
    private final RotationConverter rotationConverter;

    public RoundSlaveParticle(GameEntity master, PositionConverter positionConverter, RotationConverter rotationConverter) {
        this.master = master;
        this.positionConverter = positionConverter;
        this.rotationConverter = rotationConverter;
    }

    @Override
    public double getX() {
        return positionConverter.convert(master.getX(),master.getY()).x;
    }

    @Override
    public double getLastTickX() {
        return positionConverter.convert(master.getLastTickX(),master.getLastTickY()).x;
    }

    @Override
    public double getNewX() {
        return positionConverter.convert(master.getNewX(),master.getNewY()).x;
    }

    @Override
    public double getY() {
        return positionConverter.convert(master.getX(),master.getY()).y;
    }

    @Override
    public double getNewY() {
        return positionConverter.convert(master.getNewY(),master.getNewY()).y;
    }

    @Override
    public double getLastTickY() {
        return positionConverter.convert(master.getLastTickX(),master.getLastTickY()).y;
    }

    @Override
    public double getRotation() {
        return rotationConverter.convert(master.getRotation());
    }

    @Override
    public double getNewRotation() {
        return rotationConverter.convert(master.getNewRotation());
    }

    @Override
    public double getLastTickRotation() {
        return rotationConverter.convert(master.getLastTickRotation());
    }

    public GameEntity getMaster() {
        return master;
    }
}
