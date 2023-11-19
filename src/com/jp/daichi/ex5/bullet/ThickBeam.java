package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

import java.awt.*;

public class ThickBeam extends Projectile {

    private final PositionConverter positionConverter;
    private final RotationConverter rotationConverter;

    public ThickBeam(Game game, LivingEntity holder, double length, double width, PositionConverter positionConverter, RotationConverter rotationConverter, double damage) {
        super(game, holder,length,Bullet.getBulletShape(length,width,0,0,new Vec2d(),true),damage);
        this.positionConverter = positionConverter;
        this.rotationConverter = rotationConverter;
        Vec2d pos = positionConverter.convert(holder.getX(),holder.getY());
        setX(pos.x);
        setY(pos.y);
        setRotation(rotationConverter.convert(holder.getRotation()));
    }

    @Override
    public void doTick(double deltaTime) {
        super.doTick(deltaTime);
        Vec2d pos = positionConverter.convert(getHolder().getX(),getHolder().getY());
        setX(pos.x);
        setY(pos.y);
        setRotation(rotationConverter.convert(getHolder().getRotation()));
    }

    @Override
    public void collideWith(GameEntity entity) {
        ((LivingEntity)entity).attackedBy(getHolder(),getDamage());
    }

    public void setColor(Color color) {
        displayEntity.setColor(color);
    }

}
