package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

/**
 * GameEntityに追従するパーティクル
 */
public abstract class SlaveParticle implements Particle {
    protected final GameEntity master;
    private final PositionConverter positionConverter;
    private final RotationConverter rotationConverter;

    public SlaveParticle(GameEntity entity, PositionConverter positionConverter, RotationConverter rotationConverter) {
        this.master = entity;
        this.positionConverter = positionConverter;
        this.rotationConverter = rotationConverter;
    }

    public Vec2d getPosition() {
        return positionConverter.convert(master.getX(),master.getY());
    }

    public double getRotation() {
        return rotationConverter.convert(master.getRotation());
    }

}
