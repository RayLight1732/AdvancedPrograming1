package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.AGameObject;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

/**
 * GameEntityに追従するパーティクル
 */
public abstract class SlaveParticle extends AGameObject implements Particle {
    protected final GameEntity master;
    private final PositionConverter positionConverter;
    private final RotationConverter rotationConverter;

    public SlaveParticle(GameEntity entity, PositionConverter positionConverter, RotationConverter rotationConverter) {
        this.master = entity;
        this.positionConverter = positionConverter;
        this.rotationConverter = rotationConverter;
    }

    /*
    public Vec2d getPosition() {
        return positionConverter.convert(master.getX(),master.getY());
    }

    public double getRotation() {
        return rotationConverter.convert(master.getRotation());
    }*/

    @Override
    public double getX() {
        return positionConverter.convert(master.getX(),master.getY()).x;
    }

    @Override
    public void setX(double x) {}

    @Override
    public void setX(double x, boolean teleported) {}

    @Override
    public double getY() {
        return positionConverter.convert(master.getX(),master.getY()).y;
    }

    @Override
    public void setY(double y) {}

    @Override
    public void setY(double y, boolean teleported) {}

    @Override
    public double getRotation() {
        return rotationConverter.convert(master.getRotation());
    }

    @Override
    public void setRotation(double rotation) {}
}
